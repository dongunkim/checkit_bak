package com.sktelecom.checkit.portal.websvc.dao;

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
public class WebSvcDiagDAO extends AbstractMapper {

	private final static Log log = LogFactory.getLog(WebSvcDiagDAO.class);

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectWebSvcDiagList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("portal.websvc.dao.selectWebSvcDiagList", param);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectWebSvcDiagDetail(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectOne("portal.websvc.dao.selectWebSvcDiagDetail", param);
	}

}
