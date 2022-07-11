package com.sktelecom.checkit.core.intf.api.base;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * API 연결 공통 
 * @fileName BaseAPI.java
 * @author taesoo
 * @since 2021. 5. 6.
 */
public class BaseAPI {
	
	private static String baseApiUrl;
	
	private final static String boundary = "aJ123Af2318";
	private final static String charset = "UTF-8";
	
	static {
		baseApiUrl = "https://jsonplaceholder.typicode.com"; // 테스트 할 url
	}
	
	/**
	 * MAP 형식 리턴 api
	 * @author taesoo
	 * @param urlStr
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public HashMap<String, Object> callMap(String urlStr, HashMap<String, String> map) throws IOException {
		
		// rest api template 선언
		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
					
		// rest api 사용할 map 변수 설정
		MultiValueMap<String, String> parameters = getMultValueMap(map);
		
		URI uri = UriComponentsBuilder.fromUriString(baseApiUrl)
				.path(urlStr)
//									.queryParam("postId", "1")				
				.queryParams(parameters)
				.build().encode().toUri();
		
		RequestEntity<Void> requestEntity = RequestEntity.get(uri)
					.header("requestHeader", "headerValue")
					.header("MyRequestHeader", "MyValue")
					.build();
		
//		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> requestEntity.getUrl() :: " + requestEntity.getUrl().toString());
	
		ResponseEntity responseEntity 
		= restTemplate.exchange(requestEntity.getUrl(),HttpMethod.GET,requestEntity, JSONObject.class);
		
//		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> responseCode :: " + responseEntity.getStatusCode());
	
		HashMap<String, Object> responseMap = getMapFromJsonObject((JSONObject)responseEntity.getBody());

		return responseMap;
	}
	
	/**
	 * list 형식 리턴 api
	 * @author taesoo
	 * @param urlStr
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public List<HashMap<String, Object>> callList(String urlStr, Map<String, String> map) throws IOException {
		
		// rest api template 선언
		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
					
		// rest api 사용할 map 변수 설정
		MultiValueMap<String, String> parameters = getMultValueMap(map);
		
		URI uri = UriComponentsBuilder.fromUriString(baseApiUrl)
				.path(urlStr)
//							.queryParam("postId", "1")				
				.queryParams(parameters)
				.build().encode().toUri();
		
		RequestEntity<Void> requestEntity = RequestEntity.get(uri)
					.header("requestHeader", "headerValue")
					.header("MyRequestHeader", "MyValue")
					.build();
		
		ResponseEntity<List> responseListR = restTemplate.exchange(requestEntity.getUrl(),HttpMethod.GET,requestEntity, List.class);
		
//		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> responseCode :: " + responseListR.getStatusCode());
		List<HashMap<String, Object>> listHashMap = responseListR.getBody();
		
		return listHashMap;
	}
	
	 /**
	 * resttemplate paramer map 으로 전송하기위한 변환 method
	 * @author taesoo
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public MultiValueMap<String, String> getMultValueMap(Map<String, String> map) throws IOException {
		 
		// rest api 사용할 map 변수 설정
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		 
		 if (map != null) {
			Set key = map.keySet();
			for (Iterator iterator = key.iterator(); iterator.hasNext();) {
				String keyName = (String) iterator.next();
//								System.out.println("keyName   >>>>>>>4444>>>>>> " + keyName);
//								System.out.println("map.get(keyName)   >>>>>>>4444>>>>>> " + map.get(keyName));
				parameters.add( keyName, map.get(keyName) );
			}
		}
		 
		return parameters;
	 }
	 
	/**
	 * json 형식을 map 로 변환 
	 * @author taesoo
	 * @param jsonObj
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public HashMap<String, Object> getMapFromJsonObject( JSONObject jsonObj )
    {
	  	HashMap<String, Object> map = null;
        
        try {
            
            map = new ObjectMapper().readValue(jsonObj.toJSONString(), HashMap.class) ;
            
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        return map;
    }

}
