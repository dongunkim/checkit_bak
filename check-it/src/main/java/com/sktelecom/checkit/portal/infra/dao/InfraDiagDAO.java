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
	public HashMap<String, Object> selInfraDiagList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("portal.infra.dao.selInfraDiagList", param);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selInfraDiagDetail(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectOne("portal.infra.dao.selInfraDiagDetail", param);
	}

	/**
	 * 진단 인프라 카테고리 Insert
	 */
	public int insDiagInfraCategory(HashMap<String, Object> param) throws Exception{
		return super.insert("portal.infra.dao.insDiagInfraCategory", param);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selInfraDiagObjList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("portal.infra.dao.selInfraDiagObjList", param);
	}

}
