package com.sktelecom.checkit.core.security.service;

import java.util.HashMap;

public interface CommLoginService {

	/**
	 * 로그인 고객지원시스템 사용자조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> loginMyUserInfo(HashMap<String, Object> param) throws Exception;

	/**
	 * 로그인 고객지원시스템 비밀번호 틀린횟수 추가
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void loginMyUserPwdErr(HashMap<String, Object> param) throws Exception;

	/**
	 * 로그인 업무지원시스템 사용자조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> loginAdUserInfo(HashMap<String, Object> param) throws Exception;

}

