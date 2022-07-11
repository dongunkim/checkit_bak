package com.sktelecom.checkit.admin.main.service;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.checkit.admin.main.dao.AdminMainDAO;
import com.sktelecom.checkit.core.common.service.CommonService;
import com.sktelecom.checkit.core.util.EncryptUtils;
import com.sktelecom.checkit.core.util.StringUtils;

/**
 * 업무지원시스템 메인 ServiceImpl
 * @author devkimsj
 *
 */
@Service
public class AdminMainService{

	private final static Log log = LogFactory.getLog(AdminMainService.class.getName());

	/* 자산 관리 > 상면자원  DAO */
	@Resource
	private AdminMainDAO adminMainDAO;
		
	/* common Service */
	@Resource
	private CommonService commonService;

	/**
	 * 비밀번호 확인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> pwdCheck(HashMap<String, Object> param) throws Exception{

		HashMap<String, Object> rtn = new HashMap<String, Object>();

		String passwdEnc = null;
		String passwd    = null;

		rtn = adminMainDAO.pwdCheck(param);

		passwdEnc = String.valueOf(param.get("passwdEnc"));
		passwd    = String.valueOf(rtn.get("passwd"));

		if(passwd.equals(passwdEnc)) {
			rtn.put("errorCode", "00");
			rtn.put("errorMessage", "정상");
		}else {
			//throw new Exception("잘못된 비밀번호 입니다.", "ERR_Main_pwdCheck_01");
			throw new Exception("잘못된 비밀번호 입니다.");
		}

		return rtn;
	}

	/**
	 * 사용자 정보변경
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> updateUser(HashMap<String, Object> param) throws Exception{

		HashMap<String, Object> rtn = new HashMap<String, Object>();
		int checkCnt = 0;
		String passwd = null;

		passwd = String.valueOf(StringUtils.defaultString(param.get("passwd"), ""));

		if(!"".equals(passwd)) {

			String encPasswd = "";
			
			if(!EncryptUtils.chkPatternPW(passwd)){
				//throw new Exception("사용자 정보변경 오류가 발생하였습니다.", "ERR_Main_updateUser_01");
				throw new Exception("사용자 정보변경 오류가 발생하였습니다.");
			}
			encPasswd = EncryptUtils.getHash(passwd);
			param.put("encPasswd", encPasswd);

		}

		checkCnt = adminMainDAO.updateUser(param);

		if(checkCnt != 1) {
			//throw new Exception("사용자 정보변경 오류가 발생하였습니다.", "ERR_Main_updateUser_01");
			throw new Exception("사용자 정보변경 오류가 발생하였습니다.");
		}
		rtn.put("errorCode", "00");
		rtn.put("errorMessage", "정보를 변경하였습니다.");
			
		return rtn;
	}

}
