package com.sktelecom.checkit.portal.infra.dao;

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
public class InfraDiagDAO extends AbstractMapper {

	private final static Log log = LogFactory.getLog(InfraDiagDAO.class);

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> infraDiagList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("portal.infra.dao.infraDiagList", param);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> infraDiagDetail(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectOne("portal.infra.dao.infraDiagDetail", param);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> infraDiagHostList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("portal.infra.dao.infraDiagHostList", param);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> infraDiagObjList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("portal.infra.dao.infraDiagObjList", param);
	}

	/**
	 * 
	 */
	public int deleteHost(HashMap<String, Object> param) throws Exception{
		return super.delete("portal.infra.dao.deleteHost", param);
	}
}
