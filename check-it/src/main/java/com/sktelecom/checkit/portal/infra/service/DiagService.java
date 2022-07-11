package com.sktelecom.checkit.portal.infra.service;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.checkit.core.common.service.CommonService;
import com.sktelecom.checkit.core.util.StringUtils;
import com.sktelecom.checkit.core.util.TypeCastUtils;
import com.sktelecom.checkit.portal.infra.dao.DiagDAO;

/**
 * 내부 공지 ServiceImpl
 * @author devbaekgh
 *
 */
@Service
public class DiagService {

	private final static Log log = LogFactory.getLog(DiagService.class.getName());

	/* 내부 공지 DAO */
	@Resource
	private DiagDAO diagDAO;

	@Resource
    private CommonService commonService;

	/**
	 * 인프라 진단 목록
	 */
	public HashMap<String, Object> diagList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = diagDAO.diagList(param);
		return rtn;
	}

	/**
	 * 인프라 진단 상세
	 */
	public HashMap<String, Object> diagDetail(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = diagDAO.diagDetail(param);
		return rtn;
	}

	/**
	 * 인프라 진단 호스트 목록
	 */
	public HashMap<String, Object> diagHostList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = diagDAO.diagHostList(param);
		return rtn;
	}

	/**
	 * 인프라 진단 객체 목록
	 */
	public HashMap<String, Object> diagObjList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = diagDAO.diagObjList(param);
		return rtn;
	}
	
	/**
	 * 호스트 삭제 시 호스트 기본정보에서 삭제해야 하는가...?? 아니면 진단 목록에서 삭제해야 하는가...??
	 */
	public void deleteHost(HashMap<String, Object> param) throws Exception{
		TypeCastUtils typeCastUtils = new TypeCastUtils();
		String[] hostList = typeCastUtils.stringToArray(StringUtils.defaultString(param.get("hostList")));
		param.put("hostList",hostList);
		diagDAO.deleteHost(param);
	}

}