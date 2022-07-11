package com.sktelecom.checkit.admin.system.dao;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.sktelecom.checkit.core.common.dao.AbstractMapper;

/**
 * 공통코드권한관리 DAO
 * @author jhbang
 *
 */
@Repository
public class CodeDAO extends AbstractMapper {

	private final static Log log = LogFactory.getLog(CodeDAO.class);

	/**
	 * 공통코드관리 ROOT코드 리스트
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> sysm04GetCodeList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = (HashMap<String, Object>)super.selectList("admin.system.dao.sysm04GetCodeList", param);
		return rtn;
	}

	/**
	 * 정규작업 목록 화면
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> sysm04GetUnderCodeList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = (HashMap<String, Object>)super.selectList("admin.system.dao.sysm04GetUnderCodeList", param);
		return rtn;
	}

	/**
	 * 정규작업 목록 화면
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> sysm04GetDepthCodeList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = (HashMap<String, Object>)super.selectList("admin.system.dao.sysm04GetDepthCodeList", param);
		return rtn;
	}

	/**
	 * 정규작업 목록 화면
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> sysm04CheckDuplicate(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = (HashMap<String, Object>)super.selectOne("admin.system.dao.sysm04CheckDuplicate", param);
		return rtn;
	}

	/**
	 * 정규작업 목록 화면
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> sysm04CheckCodeId(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = (HashMap<String, Object>)super.selectOne("admin.system.dao.sysm04CheckCodeId", param);
		return rtn;
	}

	/**
	 * 등록/수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int sysm04InsertCodeManager(HashMap<String, Object> param) throws Exception{
		int rtn = 1;
		rtn *= super.insert("admin.system.dao.sysm04InsertCodeManager", param);
		return rtn;
	}

	/**
	 * 등록/수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int sysm04InsertCodeResource(HashMap<String, Object> param) throws Exception{
		int rtn = 1;
		rtn *= super.insert("admin.system.dao.sysm04InsertCodeResource", param);
		return rtn;
	}

	/**
	 * 등록/수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int sysm04UpdateCodeManager(HashMap<String, Object> param) throws Exception{
		int rtn = 1;
		rtn *= super.update("admin.system.dao.sysm04UpdateCodeManager", param);
		return rtn;
	}

	/**
	 * 등록/수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int sysm04UpdateCodeResource(HashMap<String, Object> param) throws Exception{
		int rtn = 1;
		rtn *= super.update("admin.system.dao.sysm04UpdateCodeResource", param);
		return rtn;
	}

	/**
	 * 등록/수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int sysm04DeleteCodeManager(HashMap<String, Object> param) throws Exception{
		int rtn = super.delete("admin.system.dao.sysm04DeleteCodeManager", param);
		return rtn;
	}

	/**
	 * 등록/수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int sysm04DeleteCodeResource(HashMap<String, Object> param) throws Exception{
		int rtn = super.delete("admin.system.dao.sysm04DeleteCodeResource", param);
		return rtn;
	}

	/**
	 * 등록/수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int sysm04DeleteUnderCodeResource(HashMap<String, Object> param) throws Exception{
		int rtn = 1;
		rtn *= super.delete("admin.system.dao.sysm04DeleteUnderCodeResource", param);
		return rtn;
	}

	
	/**
	 * 공통속성 N 초기화
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int sysm04UpdateAttCleanCodeResource(HashMap<String, Object> param) throws Exception{
		int rtn = super.insert("admin.system.dao.sysm04UpdateAttCleanCodeResource", param);
		return rtn;
	}
	
	/**
	 * 공통속성 수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int sysm04UpdateAttCodeResource(HashMap<String, Object> param) throws Exception{
		int rtn = super.insert("admin.system.dao.sysm04UpdateAttCodeResource", param);
		return rtn;
	}
}
