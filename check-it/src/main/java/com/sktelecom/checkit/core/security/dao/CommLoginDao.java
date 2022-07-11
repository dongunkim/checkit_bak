package com.sktelecom.checkit.core.security.dao;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sktelecom.checkit.core.common.dao.AbstractMapper;

@Repository
public class CommLoginDao extends AbstractMapper{

	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 로그인 고객지원시스템 사용자조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> loginMyUserInfo(HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> userMap = new HashMap<String, Object>();

		try{

			userMap = (HashMap<String, Object>)selectOne("com.sktelecom.checkit.core.security.mapper.loginMyUserInfo", param);

		}catch(Exception e){
			throw new Exception("" + e.getMessage());
		}

		return userMap;
	}

	/**
	 * 로그인 고객지원시스템 사용자조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void loginMyUserPwdErr(HashMap<String, Object> param) throws Exception {

		try{

			update("com.sktelecom.checkit.core.security.mapper.loginMyUserPwdErr", param);

		}catch(Exception e){
			throw new Exception("" + e.getMessage());
		}

	}

	/**
	 * 로그인 업무지원시스템 사용자조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> loginAdUserInfo(HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> userMap = new HashMap<String, Object>();

		try{

			userMap = (HashMap<String, Object>)selectOne("com.sktelecom.checkit.core.security.mapper.loginAdUserInfo", param);

		}catch(Exception e){
			throw new Exception("" + e.getMessage());
		}

		return userMap;
	}

}