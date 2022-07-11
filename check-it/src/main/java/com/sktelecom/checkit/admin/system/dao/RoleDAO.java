package com.sktelecom.checkit.admin.system.dao;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.sktelecom.checkit.core.common.dao.AbstractMapper;

/**
 * 공통권한관리 DAO
 * @author jhbang
 *
 */
@Repository
public class RoleDAO extends AbstractMapper {

	private final static Log log = LogFactory.getLog(RoleDAO.class);

	/**
	 * 공통권한관리 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> roleList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("admin.system.dao.roleList", param);
	}

	/**
	 * 공통권한관리 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> roleTreeList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("admin.system.dao.roleTreeList", param);
	}

	/**
	 * 공통권한관리 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> pgmPidList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("admin.system.dao.pgmPidList", param);
	}

	/**
	 * 공통권한 insert
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int roleInsert(HashMap<String, Object> param) throws Exception{
		return super.insert("admin.system.dao.roleInsert", param);
	}

	/**
	 * 공통권한 update
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int roleUpdate(HashMap<String, Object> param) throws Exception{
		return super.update("admin.system.dao.roleUpdate", param);
	}

	/**
	 * 공통메뉴권한 insert
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int rolePgmInsert(HashMap<String, Object> param) throws Exception{
		return super.insert("admin.system.dao.rolePgmInsert", param);
	}

	/**
	 * 공통메뉴권한 insert
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int rolePgmDelete(HashMap<String, Object> param) throws Exception{
		return super.delete("admin.system.dao.rolePgmDelete", param);
	}
}
