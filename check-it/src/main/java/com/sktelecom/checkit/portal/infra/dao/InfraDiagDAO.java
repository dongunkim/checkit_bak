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
	public HashMap<String, Object> selectInfraDiagList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("portal.infra.dao.selectInfraDiagList", param);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectInfraDiagDetail(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectOne("portal.infra.dao.selectInfraDiagDetail", param);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectInfraDiagHostList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("portal.infra.dao.selectInfraDiagHostList", param);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectInfraDiagObjList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("portal.infra.dao.selectInfraDiagObjList", param);
	}

	/**
	 * 
	 */
	public int deleteHost(HashMap<String, Object> param) throws Exception{
		return super.delete("portal.infra.dao.deleteHost", param);
	}
}
