package com.sktelecom.checkit.admin.main.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.sktelecom.checkit.core.common.dao.AbstractMapper;

/**
 * 업무지원시스템 메인  DAO
 * @author devkimsj
 *
 */
@Repository
public class AdminMainDAO extends AbstractMapper {

	private final static Log log = LogFactory.getLog(AdminMainDAO.class);

	/**
	 * 비밀번호 확인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> pwdCheck(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectOne("admin.main.mapper.pwdCheck", param);
		return rtn;
	}

	/**
	 * 사용자정보변경
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateUser(HashMap<String, Object> param) throws Exception{
		int cnt = super.update("admin.main.mapper.updateUser", param);
		return cnt;
	}

}