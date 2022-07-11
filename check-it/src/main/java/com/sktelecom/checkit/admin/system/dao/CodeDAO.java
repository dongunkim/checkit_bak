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
	public HashMap<String, Object> codeGrpList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("admin.system.dao.codeGrpList", param);
	}

	/**
	 * 정규작업 목록 화면
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> underCodeList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("admin.system.dao.underCodeList", param);
	}

	/**
	 * 정규작업 목록 화면
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> depthCodeList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("admin.system.dao.depthCodeList", param);
	}

	/**
	 * 정규작업 목록 화면
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> checkDuplicate(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectOne("admin.system.dao.checkDuplicate", param);
	}

	/**
	 * 정규작업 목록 화면
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> checkCodeGrpId(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectOne("admin.system.dao.checkCodeGrpId", param);
	}

	/**
	 * 등록/수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int codeGrpInsert(HashMap<String, Object> param) throws Exception{
		return super.insert("admin.system.dao.codeGrpInsert", param);
	}

	/**
	 * 등록/수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int codeInsert(HashMap<String, Object> param) throws Exception{
		return super.insert("admin.system.dao.codeInsert", param);
	}

	/**
	 * 등록/수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int codeGrpUpdate(HashMap<String, Object> param) throws Exception{
		return super.update("admin.system.dao.codeGrpUpdate", param);
	}

	/**
	 * 등록/수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int codeUpdate(HashMap<String, Object> param) throws Exception{
		return super.update("admin.system.dao.codeUpdate", param);
	}

	/**
	 * 등록/수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int codeGrpDelete(HashMap<String, Object> param) throws Exception{
		return super.delete("admin.system.dao.codeGrpDelete", param);
	}

	/**
	 * 등록/수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int codeDelete(HashMap<String, Object> param) throws Exception{
		return super.delete("admin.system.dao.codeDelete", param);
	}

	/**
	 * 등록/수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int underCodeDelete(HashMap<String, Object> param) throws Exception{
		return super.delete("admin.system.dao.underCodeDelete", param);
	}

	
	/**
	 * 공통속성 N 초기화
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int codeAttCleanUpdate(HashMap<String, Object> param) throws Exception{
		return super.insert("admin.system.dao.codeAttCleanUpdate", param);
	}
	
	/**
	 * 공통속성 수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int codeAttUpdate(HashMap<String, Object> param) throws Exception{
		return super.insert("admin.system.dao.codeAttUpdate", param);
	}
}
