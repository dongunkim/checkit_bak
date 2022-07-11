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
public class Sysm01DAO extends AbstractMapper {

	private final static Log log = LogFactory.getLog(Sysm01DAO.class);

	/**
	 * 사용자관리 목록
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> sysm0101List(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectList("admin.sysm.mapper.sysm0101List", param);
		return rtn;
	}

	/**
	 * 사용자정보 조회
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> sysm0101Info(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectOne("admin.sysm.mapper.sysm0101Info", param);
		return rtn;
	}

	/**
	 * 사용자 권한 목록
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> sysm0102List(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectList("admin.sysm.mapper.sysm0102List", param);
		return rtn;
	}

	/**
	 * 권한 목록
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> sysm0102PopList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectList("admin.sysm.mapper.sysm0102PopList", param);
		return rtn;
	}

	/**
	 * 권한 삭제
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int deleteSysm0101(HashMap<String, Object> param) throws Exception{
		int rtn = super.delete("admin.sysm.mapper.deleteSysm0101", param);
		return rtn;
	}

	/**
	 * 권한 등록
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int insertSysm0101(HashMap<String, Object> param) throws Exception{
		int rtn = super.insert("admin.sysm.mapper.insertSysm0101", param);
		return rtn;
	}

	/**
	 * 사용자 정보 수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int updateUserInfo(HashMap<String, Object> param) throws Exception{
		int rtn = super.update("admin.sysm.mapper.updateUserInfo", param);
		return rtn;
	}

	/**
	 * IDC 사용자 정보 수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int updateUserIdc(HashMap<String, Object> param) throws Exception{
		int rtn = super.update("admin.sysm.mapper.updateUserIdc", param);
		return rtn;
	}

	/**
	 * 사용자관리 소속 상세 AJAX 목록
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> sysm0101srchFormationCd2(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectList("admin.sysm.mapper.sysm0101srchFormationCd2", param);
		return rtn;
	}
	
	/**
	 * 유저 수정 이력 저장
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int sysm01InsertUserInfoHst(HashMap<String, Object> param) throws Exception{
		int rtn = super.insert("admin.sysm.mapper.sysm01InsertUserInfoHst", param);
		return rtn;
	}
}
