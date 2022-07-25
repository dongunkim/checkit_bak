package com.sktelecom.checkit.portal.infra.service;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.checkit.core.common.service.CommonService;
import com.sktelecom.checkit.core.util.StringUtils;
import com.sktelecom.checkit.core.util.TypeCastUtils;
import com.sktelecom.checkit.portal.infra.dao.InfraDiagDAO;
import com.sktelecom.checkit.portal.infra.dao.InfraHostDAO;

/**
 * 
 */
@Service
public class InfraDiagService {

	private final static Log log = LogFactory.getLog(InfraDiagService.class.getName());

	@Resource
	private InfraDiagDAO infraDiagDAO;

	@Resource
    private CommonService commonService;

	/**
	 * 진단 목록
	 */
	public HashMap<String, Object> infraDiagList(HashMap<String, Object> param){
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = infraDiagDAO.selInfraDiagList(param);
			rtn.put("errorCode", "00");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_infra_infraDiagList_01");
		}
		
		return rtn;
	}

	/**
	 * 진단 상세
	 */
	public HashMap<String, Object> infraDiagDetail(HashMap<String, Object> param){
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = infraDiagDAO.selInfraDiagDetail(param);
			rtn.put("errorCode", "00");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_infra_infraDiagDetail_01");
		}
		return rtn;
	}

	/**
	 * 인프라 진단 객체 목록
	 */
	public HashMap<String, Object> selInfraDiagObjList(HashMap<String, Object> param){
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = infraDiagDAO.selInfraDiagObjList(param);
			rtn.put("errorCode", "00");
		} catch (Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_infra_infraDiagObjList_01");
		}
		return rtn;
	}

}