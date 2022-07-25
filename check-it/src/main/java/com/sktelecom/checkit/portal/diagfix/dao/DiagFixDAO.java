package com.sktelecom.checkit.portal.diagfix.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.sktelecom.checkit.core.common.dao.AbstractMapper;


/**
 * 
 */
@Repository
public class DiagFixDAO extends AbstractMapper {

	private final static Log log = LogFactory.getLog(DiagFixDAO.class);

	/**
	 * 상품별 조치 목록
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selSvcDiagList(HashMap<String, Object> param) throws Exception{
		//TODO 진단자 유형에 따른 조회 조건 추가
		return (HashMap<String, Object>)super.selectOne("portal.diagfix.dao.selSvcDiagFixList", param);
	}

	/**
	 * 유형별 조치 목록
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selTypeDiagList(HashMap<String, Object> param) throws Exception{
		//TODO 진단자 유형에 따른 조회 조건 추가
		return (HashMap<String, Object>)super.selectOne("portal.diagfix.dao.selTypeDiagFixList", param);
	}

}
