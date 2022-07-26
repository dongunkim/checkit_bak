package com.sktelecom.checkit.portal.diagreq.dao;

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
public class DiagReqDAO extends AbstractMapper {

	private final static Log log = LogFactory.getLog(DiagReqDAO.class);

	/**
	 * 
	 */
	public int insDiag(HashMap<String, Object> param) throws Exception{
		return super.insert("portal.diagreq.dao.insDiag", param);
	}

	/**
	 * 
	 */
	public int insDiagHist(HashMap<String, Object> param) throws Exception{
		return super.insert("portal.diagreq.dao.insDiagHist", param);
	}

	/**
	 * 
	 */
	public int insDiagObj(HashMap<String, Object> param) throws Exception{
		return super.insert("portal.diagreq.dao.insDiagObj", param);
	}

	/**
	 * 상품별 진단 신청 현황
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selSvcDiagList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectOne("portal.diagreq.dao.selSvcDiagReqList", param);
	}

	/**
	 * 유형별 진단 신청 현황
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selTypeDiagList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectOne("portal.diagreq.dao.selTypeDiagReqList", param);
	}

}
