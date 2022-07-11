package com.sktelecom.checkit.admin.system.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.checkit.admin.system.dao.Sysm01DAO;
import com.sktelecom.checkit.core.common.service.CommonService;
import com.sktelecom.checkit.core.util.EncryptUtils;
import com.sktelecom.checkit.core.util.StringUtils;
import com.sktelecom.checkit.core.util.TypeCastUtils;

/**
 * 시스템관리 ServiceImpl
 * @author devkimsj
 */
@Service
public class Sysm01Service{

	private final static Log log = LogFactory.getLog(Sysm01Service.class.getName());

	/* 시스템관리 DAO */
	@Resource
	private Sysm01DAO sysm01DAO;

	@Resource
	private CommonService commonService; 
	
	/**
	 * 사용자관리 목록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> sysm0101List(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = sysm01DAO.sysm0101List(param);
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		return rtn;
	}

	/**
	 * 사용자 정보조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> sysm0101Info(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = sysm01DAO.sysm0101Info(param);
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		return rtn;
	}

	/**
	 * 사용자 권한 목록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> sysm0102List(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = sysm01DAO.sysm0102List(param);
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		return rtn;
	}

	/**
	 * 권한 목록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> sysm0102PopList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = sysm01DAO.sysm0102PopList(param);
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		return rtn;
	}

	/**
	 * 사용자 권한 수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> insertSysm0101(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> paramList = new ArrayList<HashMap<String, Object>>();

		int cnt = sysm01DAO.deleteSysm0101(param);

		TypeCastUtils typeCastUtils = new TypeCastUtils();
		paramList = typeCastUtils.stringToArrayList(String.valueOf(param.get("paramList")));

		if(paramList != null && paramList.size() > 0) {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			for(int i = 0; i < paramList.size(); i++){
				paramMap = paramList.get(i);
				paramMap.put("userId", param.get("userId"));
				cnt = sysm01DAO.insertSysm0101(paramMap);
			}
		}

		if(cnt == 0) {
			//throw new Exception("처리중 오류가 발생하였습니다.", "ERR_Sysm_insertSysm0101_01");
			throw new Exception("처리중 오류가 발생하였습니다.");
		}

		rtn.put("errorCode", "00");
		rtn.put("errorMessage", "정상 등록 되었습니다.");

		return rtn;
	}

	/**
	 * 사용자 권한 수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> insertSysm0102(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		int cnt = 1;
		int nmsCnt = 1;

		if("A".equals(String.valueOf(param.get("status")))) {
			// 사용여부 사용일 경우
			if("A".equals(String.valueOf(param.get("firstStatus")))) {
				String formationCd_2 = StringUtils.defaultString(param.get("formationCd_2"));
				if(!"".equals(formationCd_2)) {
					param.put("formationCd", formationCd_2);
				}
				cnt *= sysm01DAO.updateUserInfo(param);
				cnt *= sysm01DAO.updateUserIdc(param);
								

			}else {

				SecureRandom random = new SecureRandom();
				int numLength = 10;
				String tmpPasswd = "";
				String encPasswd = "";
				param.put("loginFailCnt","0");

				for (int i = 0; i < numLength; i++) {
					int tempNum = random.nextInt(35);
					if (tempNum < 10) {
						tmpPasswd += tempNum;
					} else if (tempNum > 10) {
						tmpPasswd += (char)(tempNum + 86);
					}
				}

				encPasswd = EncryptUtils.getHash(tmpPasswd);

				param.put("tmpPasswd", encPasswd);
				cnt *= sysm01DAO.updateUserInfo(param);
				if(cnt != 1) {
					//throw new Exception("사용자 정보 수정증 오류가 발생하였습니다.", "ERR_Sysm_insertSysm0102_01");
					throw new Exception("사용자 정보 수정증 오류가 발생하였습니다.");
				}
				String formationCd_2 = StringUtils.defaultString(param.get("formationCd_2"));
				if(!"".equals(formationCd_2)) {
					param.put("formationCd", formationCd_2);
				}
				cnt *= sysm01DAO.updateUserIdc(param);
				
				if(cnt != 1) {
					//throw new Exception("사용자 정보 수정증 오류가 발생하였습니다.", "ERR_Sysm_insertSysm0102_02");
					throw new Exception("사용자 정보 수정증 오류가 발생하였습니다.");
				}
			}
		}else if("L".equals(String.valueOf(param.get("status")))) {
			//사용여부 잠금
			param.put("tmpPasswd", " ");
			String formationCd_2 = StringUtils.defaultString(param.get("formationCd_2"));
			if(!"".equals(formationCd_2)) {
				param.put("formationCd", formationCd_2);
			}
			cnt *= sysm01DAO.updateUserInfo(param);
			cnt *= sysm01DAO.updateUserIdc(param);
			
			if(cnt != 1) {
				//throw new Exception("사용자 정보 수정증 오류가 발생하였습니다.", "ERR_Sysm_insertSysm0102_03");
				throw new Exception("사용자 정보 수정증 오류가 발생하였습니다.");
			}
		}
		
		// 사용여부가 바뀌었을 경우			
		//System.out.println("hstDesc :::::::::::::::::::"+StringUtils.defaultString(param.get("hstDesc")));
		if(!"".equals(StringUtils.defaultString(param.get("hstDesc")))) {
			// preStatus + "->" + newStatus 이전상태 -> 신규상태
			cnt *= sysm01DAO.sysm01InsertUserInfoHst(param);
		}
					
		// 소속이 바뀌었을 경우 사용자 이력
		if(!"".equals(StringUtils.defaultString(param.get("manageDesc")))) {
			param.put("hstDesc",StringUtils.defaultString(param.get("manageDesc")));
			cnt *= sysm01DAO.sysm01InsertUserInfoHst(param);				
		}
		
		// 권한이 바뀌었을 경우 사용자 이력
		if(!"".equals(StringUtils.defaultString(param.get("roleViewAreaDesc")))) {
			param.put("hstDesc",StringUtils.defaultString(param.get("roleViewAreaDesc")).replaceAll("<br>", ""));
			cnt *= sysm01DAO.sysm01InsertUserInfoHst(param);				
		}
		

		rtn.put("errorCode", "00");
		rtn.put("errorMessage", "정상 등록 되었습니다.");

		return rtn;
	}

	/**
	 * 사용자 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> deleteSysm0102(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		int cnt = 0;

		cnt = sysm01DAO.updateUserInfo(param);
		cnt = sysm01DAO.sysm01InsertUserInfoHst(param);
		
		if(cnt != 1) {
			//throw new Exception("처리중 오류가 발생하였습니다.", "ERR_Sysm_deleteSysm0102_01");
			throw new Exception("처리중 오류가 발생하였습니다.");
		}

		rtn.put("errorCode", "00");
		rtn.put("errorMessage", "정상 등록 되었습니다.");

		return rtn;
	}

	/**
	 * 사용자관리 소속 상세 AJAX 목록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> sysm0101srchFormationCd2(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = sysm01DAO.sysm0101srchFormationCd2(param);
		return rtn;
	}

}