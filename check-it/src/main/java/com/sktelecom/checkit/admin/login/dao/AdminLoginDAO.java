package com.sktelecom.checkit.admin.login.dao;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.sktelecom.checkit.core.common.dao.AbstractMapper;

/**
 * 업무지원시스템 로그인  DAO
 * @author devkimsj
 *
 */
@Repository
public class AdminLoginDAO extends AbstractMapper {

	private final static Log log = LogFactory.getLog(AdminLoginDAO.class);

	/**
	 * 로그인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> loginUserInfo(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectOne("admin.login.dao.loginUserInfo", param);
		return rtn;
	}

	/**
	 * 신규 ID 체크
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getRid(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectOne("admin.login.dao.getRid", param);
		return rtn;
	}
	
	/**
	 * 접근 IP 체크
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getLoginIp(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectList("admin.login.dao.getLoginIp", param);
		return rtn;
	}

	/**
	 * 신규 ID 체크
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> newIdCheck(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectOne("admin.login.dao.newIdCheck", param);
		return rtn;
	}

	/**
	 * 아이디 찾기
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> idSearch(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectOne("admin.login.dao.idSearch", param);
		return rtn;
	}

	/**
	 * 비밀번호 찾기
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> pwdSearch(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectOne("admin.login.dao.pwdSearch", param);
		return rtn;
	}

	/**
	 * 임시비밀번호 등록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updatePwd(HashMap<String, Object> param) throws Exception{
		int rtn = super.update("admin.login.dao.updatePwd", param);
		return rtn;
	}
	
	/**
	 * 로그인 실패횟수 초기화
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int loginAdminUserPwdInit(HashMap<String, Object> param) throws Exception{
		int rtn = super.update("admin.login.dao.loginAdminUserPwdInit", param);
		return rtn;
	}
	
	/**
	 * 로그인 실패시 실패카운팅 및 잠금
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int loginAdminUserPwdErr(HashMap<String, Object> param) throws Exception{
		int rtn = super.update("admin.login.dao.loginAdminUserPwdErr", param);
		return rtn;
	}
	
	/**
	 * 사용자 등록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertUserInfo(HashMap<String, Object> param) throws Exception{
		int rtn = super.insert("admin.login.dao.insertUserInfo", param);
		return rtn;
	}

	/**
	 * IDC 사용자 등록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertUserIdc(HashMap<String, Object> param) throws Exception{
		int rtn = super.insert("admin.login.dao.insertUserIdc", param);
		return rtn;
	}

}
