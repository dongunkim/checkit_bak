package com.sktelecom.checkit.admin.system.dao;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.sktelecom.checkit.core.common.dao.AbstractMapper;

/**
 * 시스템 관리 DAO
 * @author devkimsj
 *
 */
@Repository
public class MenuDAO extends AbstractMapper {

	private final static Log log = LogFactory.getLog(MenuDAO.class);

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> menuTreeList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("admin.system.dao.menuTreeList", param);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> menuPgmList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("admin.system.dao.menuPgmList", param);
	}

	/**
	 * 
	 */
	public int menuInsert(HashMap<String, Object> param) throws Exception{
		return super.insert("admin.system.dao.menuInsert", param);
	}

	/**
	 * 
	 */
	public int menuUpdate(HashMap<String, Object> param) throws Exception{
		return super.update("admin.system.dao.menuUpdate", param);
	}

	/**
	 * 
	 */
	public int downMenuDelete(HashMap<String, Object> param) throws Exception{
		return super.update("admin.system.dao.downMenuDelete", param);
	}

	/**
	 * 
	 */
	public int menuDelete(HashMap<String, Object> param) throws Exception{
		return super.update("admin.system.dao.menuDelete", param);
	}
	
	/**
	 * 
	 */
	public int menuPgmInsert(HashMap<String, Object> param) throws Exception{
		return super.insert("admin.system.dao.menuPgmInsert", param);
	}

	/**
	 * 
	 */
	public int menuPgmUpdate(HashMap<String, Object> param) throws Exception{
		return super.update("admin.system.dao.menuPgmUpdate", param);
	}
	
	/**
	 * 
	 */
	public int menuPgmDelete(HashMap<String, Object> param) throws Exception{
		return super.update("admin.system.dao.menuPgmDelete", param);
	}
}
