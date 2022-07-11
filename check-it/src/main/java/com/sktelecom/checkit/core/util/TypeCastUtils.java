package com.sktelecom.checkit.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 형변환 유틸
 * @author devkimsj
 *
 */
@Component("typeCastUtils")
public class TypeCastUtils {

	private final static Log log = LogFactory.getLog(TypeCastUtils.class);

	/**
	 * String -> ArrayList
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public ArrayList<HashMap<String, Object>> stringToArrayList(String param){

		ArrayList<HashMap<String, Object>> rtn = new ArrayList<HashMap<String, Object>>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			rtn = mapper.readValue(param, new TypeReference<ArrayList<HashMap<String, Object>>>(){});
		} catch (IOException e) {
			log.error("TypeCastUtils ERROR [stringToArrayList] : " + e.getMessage());
		}

		return rtn;

	}

	/**
	 * String -> HashMap
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> stringToMap(String param){

		HashMap<String, Object> rtn = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			rtn = mapper.readValue(param, new TypeReference<HashMap<String, Object>>(){});
		} catch (IOException e) {
			log.error("TypeCastUtils ERROR [stringToMap] : " + e.getMessage());
		}

		return rtn;

	}

	/**
	 * String -> Array
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public String[] stringToArray(String param){

		String[] rtn = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			rtn = mapper.readValue(param, new TypeReference<String[]>(){});
		} catch (IOException e) {
			log.error("TypeCastUtils ERROR [stringToArray] : " + e.getMessage());
		}

		return rtn;

	}

	/**
	 * JsonString -> HashMap
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> jsonToMap(String param){

		HashMap<String, Object> rtn = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			rtn = mapper.readValue(param, new TypeReference<HashMap<String, Object>>(){});
		} catch (IOException e) {
			log.error("TypeCastUtils ERROR [jsonToArray] : " + e.getMessage());
		}

		return rtn;

	}

	/**
	 * JsonString -> List
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, Object>> jsonToList(String param){

		List<HashMap<String, Object>> rtn = new ArrayList<HashMap<String, Object>>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			rtn = mapper.readValue(param, new TypeReference<List<HashMap<String, Object>>>(){});
		} catch (IOException e) {
			log.error("TypeCastUtils ERROR [jsonToList] : " + e.getMessage());
		}

		return rtn;

	}

	/**
	 * HashMap -> Json
	 * @param String
	 * @return
	 * @throws JSONException
	 * @throws Exception
	 */
	public JSONObject mapToJson(HashMap<String, Object> param) throws JSONException{

		JSONObject rtn = new JSONObject();
		ObjectMapper mapper = new ObjectMapper();
		for(HashMap.Entry<String, Object> entry : param.entrySet() ) {
			String key = entry.getKey();
			Object value = entry.getValue();
			rtn.put(key, value);
        }

		return rtn;

	}

}
