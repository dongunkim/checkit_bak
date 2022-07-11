package com.sktelecom.checkit.core.security.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sktelecom.checkit.core.security.dao.CommLoginDao;

@Service
public class CommLoginServiceImpl implements CommLoginService{

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CommLoginDao commLoginDao;

	/**
	 * 로그인 고객지원시스템 사용자조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public HashMap<String, Object> loginMyUserInfo(HashMap<String, Object> param) throws Exception{

		HashMap<String, Object> userMap = new HashMap<String, Object>();

		try{

			userMap = commLoginDao.loginMyUserInfo(param);

		}catch(Exception e){
			throw new Exception("" + e.getMessage());
		}

		return userMap;
	}

	/**
	 * 로그인 고객지원시스템 비밀번호 틀린횟수 추가
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public void loginMyUserPwdErr(HashMap<String, Object> param) throws Exception{

		try{

			commLoginDao.loginMyUserPwdErr(param);

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
	@Override
	public HashMap<String, Object> loginAdUserInfo(HashMap<String, Object> param) throws Exception{

		HashMap<String, Object> userMap = new HashMap<String, Object>();

		try{

			userMap = commLoginDao.loginAdUserInfo(param);

		}catch(Exception e){
			throw new Exception("" + e.getMessage());
		}

		return userMap;
	}

}