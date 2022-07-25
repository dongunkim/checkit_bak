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
public class InfraHostDAO extends AbstractMapper {

	private final static Log log = LogFactory.getLog(InfraHostDAO.class);

	/**
	 * Host 기본정보 Insert
	 */
	public int insHost(HashMap<String, Object> param) throws Exception{
		return super.insert("portal.infra.dao.insHost", param);
	}

	/**
	 * Host 기본정보 Delete
	 */
	public int delHost(HashMap<String, Object> param) throws Exception{
		return super.delete("portal.infra.dao.delHost", param);
	}
	
	/**
	 * Host 계정 정보 Insert
	 */
	public int insHostAccount(HashMap<String, Object> param) throws Exception{
		return super.delete("portal.infra.dao.delHost", param);
	}
	
	/**
	 * 진단 호스트 Insert
	 */
	public int insDiagHost(HashMap<String, Object> param) throws Exception{
		return super.insert("portal.infra.dao.insDiagHost", param);
	}
	
	/**
	 * 진단 호스트 목록 조회.
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selDiagHostList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("portal.infra.dao.selDiagHostList", param);
	}

}
