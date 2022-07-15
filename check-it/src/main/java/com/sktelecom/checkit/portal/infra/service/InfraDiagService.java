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
			rtn = infraDiagDAO.selectInfraDiagList(param);
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
			rtn = infraDiagDAO.selectInfraDiagDetail(param);
			rtn.put("errorCode", "00");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_infra_infraDiagDetail_01");
		}
		return rtn;
	}
	/**
	 * 인프라 진단 호스트 목록
	 */
	public HashMap<String, Object> infraDiagHostList(HashMap<String, Object> param){
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = infraDiagDAO.selectInfraDiagHostList(param);
			rtn.put("errorCode", "00");
		} catch (Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_infra_infraDiagHostList_01");
		}
		return rtn;
	}

	/**
	 * 인프라 진단 객체 목록
	 */
	public HashMap<String, Object> infraDiagObjList(HashMap<String, Object> param){
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = infraDiagDAO.selectInfraDiagObjList(param);
			rtn.put("errorCode", "00");
		} catch (Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_infra_infraDiagObjList_01");
		}
		return rtn;
	}
	
	/**
	 * 호스트 삭제 시 호스트 기본정보에서 삭제해야 하는가...?? 아니면 진단 목록에서 삭제해야 하는가...??
	 */
	public HashMap<String, Object> deleteHost(HashMap<String, Object> param){
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			TypeCastUtils typeCastUtils = new TypeCastUtils();
			String[] hostList = typeCastUtils.stringToArray(StringUtils.defaultString(param.get("hostList")));
			param.put("hostList",hostList);
			infraDiagDAO.deleteHost(param);
			
			rtn.put("errorCode", "00");
		} catch (Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_infra_hostDelete_01");
		}
		return rtn;
	}

}