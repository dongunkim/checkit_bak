package com.sktelecom.checkit.portal.websvc.service;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.checkit.core.common.service.CommonService;
import com.sktelecom.checkit.portal.websvc.dao.WebSvcDiagDAO;

/**
 * 웹서비스 진단 Service
 */
@Service
public class WebSvcDiagService {

	private final static Log log = LogFactory.getLog(WebSvcDiagService.class.getName());

	@Resource
	private WebSvcDiagDAO webSvcDiagDAO;

	@Resource
    private CommonService commonService;

	/**
	 * 진단 목록
	 */
	public HashMap<String, Object> selWebSvcDiagList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = webSvcDiagDAO.selWebSvcDiagList(param);
			rtn.put("errorCode", "00");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_websvc_webSvcDiagList_01");
		}
		return rtn;
	}

	/**
	 * 진단 상세
	 */
	public HashMap<String, Object> selWebSvcDiagDetail(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = webSvcDiagDAO.selWebSvcDiagDetail(param);
			rtn.put("errorCode", "00");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_websvc_webSvcDiagList_01");
		}
		return rtn;
	}

}