package com.sktelecom.checkit.portal.diagreg.service;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.checkit.core.common.service.CommonService;
import com.sktelecom.checkit.portal.diagreg.dao.DiagReqDAO;

/**
 * 웹서비스 진단 Service
 */
@Service
public class DiagReqService {

	private final static Log log = LogFactory.getLog(DiagReqService.class.getName());

	@Resource
	private DiagReqDAO diagReqDAO;

	@Resource
    private CommonService commonService;

	/**
	 * 진단 목록
	 */
	public HashMap<String, Object> insDiag(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			param.put("diagId",commonService.getNextId("T_DIAG"));
			diagReqDAO.insDiag(param);
			rtn.put("errorCode", "00");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_websvc_webSvcDiagList_01");
		}
		return rtn;
	}

}