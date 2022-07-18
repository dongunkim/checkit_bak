package com.sktelecom.checkit.portal.login.dao;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.sktelecom.checkit.core.common.dao.AbstractMapper;

/**
 * 포털 로그인 DAO
 * @author JY.Park
 *
 */
@Repository
public class LoginDAO extends AbstractMapper {

	private final static Log log = LogFactory.getLog(LoginDAO.class);

	/**
	 * 사용자 정보 조회
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> loginCheck(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectOne("login.dao.loginCheck", param);
	}

	/**
	 * 로그인 실패시 실패카운팅 및 잠금
	 */
	public int loginUserPwdErr(HashMap<String, Object> param) throws Exception{
		return super.update("login.dao.loginUserPwdErr", param);
	}
	
	
	/**
	 * 로그인 실패횟수 초기화
	 */
	public int loginUserPwdInit(HashMap<String, Object> param) throws Exception{
		return super.update("login.dao.loginUserPwdInit", param);
	}
	
	
}
