package com.sktelecom.checkit.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import com.sktelecom.checkit.core.property.PropertyService;


@Component("irisHttpClient")
public class IrisHttpClient {
	
	private final static Log log = LogFactory.getLog(IrisHttpClient.class.getName());
	
	@Resource
	private IrisUtil irisUtil;
	
	@Resource
	private PropertyService propertyService;
	
	public List<HashMap<String, Object>> getIrisDataList(String mapperQueryId, HashMap<String, Object> param){
		List<HashMap<String, Object>> retMap = new ArrayList<HashMap<String,Object>>();
		
		String query = irisUtil.mapperFiles(mapperQueryId, param);
		
		if(query != null && query.length() > 0 && query != "") {
			//httpClient = HttpClientBuilder.create().build();
			
			try {
				retMap = toHashMapList(getHttpClientData(query));
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			} 
		}
		return retMap;
	}
	
	private JSONObject getHttpClientData(String query) throws ClientProtocolException, IOException, ParseException {
		CloseableHttpClient httpClient = null;
		JSONObject jsonObj = new JSONObject();
		String token = "";
		String sid = "";
		try {
			
			httpClient = HttpClientBuilder.create().build();
			token = (String) httpClientPost(httpClient).get("token");
			sid = (String) httpClientPost2(httpClient, token, query).get("sid");
			jsonObj = httpClientGet(httpClient, token, sid);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		log.info("token : " + token + ", sid : " + sid);
		
		return jsonObj;
	}
	
	private JSONObject httpClientPost(CloseableHttpClient httpClient) throws ClientProtocolException, IOException, ParseException{

		JSONObject json = new JSONObject();
		json.put("id", propertyService.getString("iris.post.id"));
	    json.put("password", propertyService.getString("iris.post.pwd"));
	    json.put("group_id", propertyService.getString("iris.group.id"));
	    json.put("encrypted", propertyService.getString("iris.encrypted"));
	    String irisUrl = propertyService.getString("iris.url"); 
		String tokenUrl = propertyService.getString("iris.token.url");
	    HttpPost httpPost = new HttpPost(irisUrl+tokenUrl);

	    StringEntity postingString = new StringEntity(json.toString());
		httpPost.addHeader("Content-type", "application/json");
		httpPost.setEntity(postingString);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		JSONObject jsonObj = httpClientDataReader(httpResponse);

		return jsonObj;
	}
	
	private JSONObject httpClientPost2(CloseableHttpClient httpClient, String token ,String query) throws ClientProtocolException, IOException, ParseException{
		
		JSONObject json = new JSONObject();
	    json.put("q", query);
		json.put("size", propertyService.getString("iris.size"));
		json.put("fetch_size", propertyService.getString("iris.fetch.size"));
		String irisUrl = propertyService.getString("iris.url"); 
		String postUrl = propertyService.getString("iris.query.jobs.url");
		
		HttpPost httpPost = new HttpPost(irisUrl+postUrl);
		
		StringEntity postingString = new StringEntity(json.toString());
		httpPost.addHeader("Authorization", "Angora "+token);
    	httpPost.addHeader("Content-type", "application/json");
    	httpPost.setEntity(postingString);
    	HttpResponse httpResponse = httpClient.execute(httpPost);
    	JSONObject jsonObj = httpClientDataReader(httpResponse);

		return jsonObj;
	}
	
	private JSONObject httpClientGet(CloseableHttpClient httpClient, String token, String sid) throws ClientProtocolException, IOException, ParseException {
		
		String irisUrl = propertyService.getString("iris.url"); 
		String postUrl = propertyService.getString("iris.query.jobs.url");
		
		HttpGet httpGet = new HttpGet(irisUrl+postUrl+"/"+sid);
		httpGet.addHeader("AUTHORIZATION", "Angora "+token);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		JSONObject jsonObj = httpClientDataReader(httpResponse);
	    return jsonObj;
	}
	
	private JSONObject httpClientDataReader(HttpResponse httpResponse) throws IOException, ParseException{
		
		JSONObject jsonObj = new JSONObject();
			
		BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
 
        StringBuffer response = new StringBuffer();
        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        
        br.close();
        String result = response.toString();
        JSONParser parser = new JSONParser();
        jsonObj = (JSONObject) parser.parse(result);
		
	    return jsonObj;
	}
	
	private List<HashMap<String, Object>> toHashMapList(JSONObject object){
		List<HashMap<String, Object>> hashMapList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<String> arrayKeys = new ArrayList<String>();
	    
    	
    	if(object != null) {
    		
    		JSONArray jo = (JSONArray) object.get("fields");
    		
    		if(jo != null) {
	    		for(int j = 0 ; j < jo.size(); j++) {
	        		JSONObject jso = (JSONObject) jo.get(j);
	        		arrayKeys.add(jso.get("name")+"");
	        	}
	    			
	    	    JSONArray jor = (JSONArray) object.get("results");
	        	for(int r = 0 ; r < jor.size(); r++) {
	        		JSONArray jorsub = (JSONArray) jor.get(r);
	        		for(int s = 0 ; s < jorsub.size(); s++) {
	        			map.put(isKey(arrayKeys.get(s).toLowerCase()), isNumber(jorsub.get(s).toString()));
	        		}
	        		
	        		hashMapList.add(map);
	        		map = new HashMap<String, Object>();
	        	}
	    	}
    	}
    	
    	
	    return hashMapList;
	}
	
	private Object isNumber(String str) {
		Object object = str ; 
		try {
			Pattern p = Pattern.compile("^20(\\d{12})$");	//날짜는 String
			if(!p.matcher(str).matches()){
				BigDecimal bd = new BigDecimal(str);
				object = bd;
			}
		} catch (Exception e) { }
		return object;
	}
	
	private static String isKey(String key) {
		String isKey = key;
		int idxOf = isKey.indexOf("_");
		if(idxOf > -1) {
			String temp = isKey.substring(0, idxOf);
			isKey = (isKey.charAt(idxOf+1)+"").toUpperCase() + isKey.substring(idxOf+2);
			return temp + isKey(isKey);
		}else {
			return isKey;
		}
	}
}
