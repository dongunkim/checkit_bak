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
public class DiagDAO extends AbstractMapper {

	private final static Log log = LogFactory.getLog(DiagDAO.class);

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> diagList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectList("portal.infra.dao.diagList", param);

		return rtn;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> diagDetail(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = (HashMap<String, Object>)super.selectOne("portal.infra.dao.diagDetail", param);

		return rtn;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> diagHostList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectList("portal.infra.dao.diagHostList", param);

		return rtn;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> diagObjList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectList("portal.infra.dao.diagObjList", param);

		return rtn;
	}

	public void deleteHost(HashMap<String, Object> param) throws Exception{
		super.delete("portal.infra.dao.deleteHost", param);
	}
}
