package com.sktelecom.checkit.core.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.annotation.Resource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sktelecom.checkit.core.property.PropertyService;

@Component("irisUtil")
public class IrisUtil {
	
	private final static Log log = LogFactory.getLog(IrisUtil.class.getName());
	
	@Resource
	private PropertyService propertyService;
	
	public String mapperFiles(String mapperQueryId, HashMap<String, Object> param){
		String query = "";
		
		String mapperPath = getClass().getResource("/").getPath()+propertyService.getString("datv.class.mapper.path");
		//log.debug("mapperPath:"+mapperPath);
		
		String mapperName = ".*NmsPlusIrisMapper.xml"; 
		File dir = new File(mapperPath);
		File files[] = dir.listFiles();
		
		for(int i = 0 ; i < files.length; i++){
			//log.debug("file: " + files[i]);
			String fileName = files[i]+"";
			if(fileName.matches(mapperName)) {
				query = xmlReader(mapperQueryId, fileName);
			}
			if(query != null && query.length() > 0) {
				break;
			}
		}
		
		if(query != null && query != "" && query.length() > 0) {
			Iterator<String> keys = param.keySet().iterator();
			while(keys.hasNext()) {
				String key = keys.next();
				if(key.equals("devIdArray") && !(param.get(key) instanceof Object[]) ) {
					String[] tempDevId = (param.get(key)+"").split(",");
					ArrayList<String> listDevId = new ArrayList<String>();
					for(String temp : tempDevId) {
						if(!listDevId.contains(temp)) {
							listDevId.add(temp);
						}
					}
					param.put(key, listDevId);
				}
				
				if(param.get(key) instanceof Object[]) {
					String[] valueArray = (String[]) param.get(key);
					ArrayList<String> chkCon = new ArrayList<String>();
					String tempArray = "";
					for(int a = 0 ; a < valueArray.length; a++) {
						if(!chkCon.contains(valueArray[a])) {
							tempArray = tempArray + "\\'"+valueArray[a]+"\\'";
							if(a < valueArray.length-1) {
								tempArray = tempArray + ",";
							}
							chkCon.add(valueArray[a]);
						}
					}
					param.put(key, tempArray);
				}
				//log.debug("#{"+key+"} :"+ param.get(key));
				query = query.replaceAll("\\#\\{"+key+"\\}", param.get(key)+"");
			}
		}
		return query;
	}
	
	public String xmlReader(String mapperQueryId, String mapperFiles) {
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc;
		String query = "";
		int lastIndex = mapperQueryId.lastIndexOf(".");
		String mapperId = mapperQueryId.substring(0,lastIndex);
		String queryId = mapperQueryId.substring(lastIndex+1);
		
		try {
			dBuilder = dbFactoty.newDocumentBuilder();
			doc = dBuilder.parse(mapperFiles);
			
			Element root = doc.getDocumentElement();
			String namespace = root.getAttribute("namespace");
			//log.debug("mapper namespace : "+ namespace);
			
			if(namespace.equals(mapperId)){
				NodeList childeren = root.getChildNodes();
				for(int c = 0 ; c < childeren.getLength(); c++){
					Node node = childeren.item(c);
					if(node.getNodeType() == Node.ELEMENT_NODE) {
						Element ele = (Element)node;
						String elementQueryId = ele.getAttribute("id");
						//log.debug("elementQueryId: " + elementQueryId);
						if(elementQueryId.equals(queryId)) {
							query = ele.getTextContent();
							break;
						}
					}
				}
			}
				
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return query;
	}
}
