package com.sktelecom.checkit.admin.system.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.checkit.admin.system.dao.CodeDAO;
import com.sktelecom.checkit.core.common.service.CommonService;
import com.sktelecom.checkit.core.util.StringUtils;
import com.sktelecom.checkit.core.util.TypeCastUtils;
/**
 * 시스템관리 ServiceImpl
 * @author devkimsj
 */
@Service
public class CodeService {

	private final static Log log = LogFactory.getLog(CodeService.class.getName());

	/* 시스템관리 DAO */
	@Resource
	private CodeDAO codeDAO;
	
	@Resource
	private CommonService commonService;

	/**
	 * 공통코드권한관리 목록 화면
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> sysm04GetCodeList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = codeDAO.sysm04GetCodeList(param);
		return rtn;
	}

	/**
	 * 공통코드권한관리 목록 화면
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> sysm04GetUnderCodeList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = codeDAO.sysm04GetUnderCodeList(param);
		return rtn;
	}

	/**
	 * 공통코드권한관리 목록 화면
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> sysm04GetDepthCodeList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = codeDAO.sysm04GetDepthCodeList(param);
		return rtn;
	}

	/**
	 * 코드 리소스 중복체크
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> sysm04CheckDuplicate(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = codeDAO.sysm04CheckDuplicate(param);
		return rtn;
	}

	/**
	 * 등록/수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> sysm04Process(HashMap<String, Object> param) throws Exception{

		HashMap<String, Object> rtn = new HashMap<String, Object>();
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		HashMap<String, Object> errParamsMap = new HashMap<String, Object>();
		int chkCnt = 0;
		String cmd = StringUtils.defaultString(param.get("cmd"));
		String codeGrpId = StringUtils.defaultString(param.get("codeGrpId"));
		String codeGrpName = StringUtils.defaultString(param.get("codeGrpName"));
		String codeGrpDesc = StringUtils.defaultString(param.get("codeGrpDesc"));
		String useYn = StringUtils.defaultString(param.get("useYn"));
		String userId = StringUtils.defaultString(param.get("userId"));

		String ment = "";
		paramMap.clear();
		errParamsMap.clear();
		paramMap.put("codeGrpId", codeGrpId);
		paramMap.put("codeGrpName", codeGrpName);
		paramMap.put("codeGrpDesc", codeGrpDesc);
		paramMap.put("useYn", useYn);
		paramMap.put("userId", userId);

		if("I".equals(cmd)) {
			//코드 중복체크
			rtn = codeDAO.sysm04CheckCodeId(param);

			if(rtn.size() > 0) {
				rtn.clear();
				rtn.put("errorCode", "01");
				rtn.put("errorMessage", "입력하신 코드아이디 ["+ codeGrpId +"] 는 이미 사용중 입니다.");
				return rtn;
			}
			chkCnt = codeDAO.sysm04InsertCodeManager(paramMap);
			ment = "저장";
			if(chkCnt == 0) {
				throw new Exception("저장 중 오류가 발생하였습니다.");
			}
		} else if("U".equals(cmd)) {
			chkCnt = codeDAO.sysm04UpdateCodeManager(paramMap);
			ment = "수정";
			if(chkCnt == 0) {
				throw new Exception("수정 중 오류가 발생하였습니다.");
			}
		} else if("D".equals(cmd)) {
			paramMap.clear();
			paramMap.put("codeGrpId", codeGrpId);
			paramMap.put("codeGrpId", codeGrpId);
			paramMap.put("heigher", codeGrpId);
			chkCnt = codeDAO.sysm04DeleteUnderCodeResource(paramMap);
			chkCnt = codeDAO.sysm04DeleteCodeManager(paramMap);
			ment = "삭제";
		} else {
			throw new Exception("cmd 값이 누락 되었습니다.");
		}

		rtn.clear();
		rtn.put("errorCode", "00");
		rtn.put("errorMessage", "정상 " + ment + " 되었습니다.");

		return rtn;
	}

	/**
	 * 등록/수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> sysm04ResourceProcess(HashMap<String, Object> param) throws Exception{

		HashMap<String, Object> rtn = new HashMap<String, Object>();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		HashMap<String, Object> errParamsMap = new HashMap<String, Object>();
		TypeCastUtils typeCastUtils = new TypeCastUtils();

		String codeGrpId = "";
		String codeId = "";
		String codeName = "";
		String upCodeId = "";
		String depth = "";
		String codeDesc = "";
		String userId = StringUtils.defaultString(param.get("userId"));
		String searchYn = "Y";

		List<HashMap<String, Object>> addCodeArray = typeCastUtils.stringToArrayList(StringUtils.defaultString(param.get("addCodeArray")));
		List<HashMap<String, Object>> updCodeArray = typeCastUtils.stringToArrayList(StringUtils.defaultString(param.get("updCodeArray")));
		List<HashMap<String, Object>> delCodeArray = typeCastUtils.stringToArrayList(StringUtils.defaultString(param.get("delCodeArray")));

		int chkCnt = 0;
		int chkSmtIdcCnt = 1;
		log.debug("addCodeArray::" + addCodeArray);
		log.debug("updCodeArray::" + updCodeArray);
		log.debug("delCodeArray::" + delCodeArray);

		for(HashMap<String, Object> addCodeMap : addCodeArray){
			codeGrpId = StringUtils.defaultString(addCodeMap.get("codeGrpId"));
			codeId = StringUtils.defaultString(addCodeMap.get("codeId"));
			codeName = StringUtils.defaultString(addCodeMap.get("codeName"));
			upCodeId = StringUtils.defaultString(addCodeMap.get("upCodeId"));
			depth = StringUtils.defaultString(addCodeMap.get("depth"));
			codeDesc = StringUtils.defaultString(addCodeMap.get("codeDesc"));;
			log.debug("addCodeMap::" + addCodeMap);
			
			paramsMap.clear();
			errParamsMap.clear();
			paramsMap.put("codeGrpId", codeGrpId);
			paramsMap.put("codeId", codeId);
			paramsMap.put("codeName", codeName);
			paramsMap.put("upCodeId", upCodeId);
			paramsMap.put("depth", depth);
			paramsMap.put("codeDesc", codeDesc);
			paramsMap.put("userId", userId);
			paramsMap.put("searchYn", searchYn);

			chkCnt = codeDAO.sysm04InsertCodeResource(paramsMap);
			
			if(chkCnt == 0) {
				throw new Exception("코드 등록에 실패하였습니다.");
			}
		}

		for(HashMap<String, Object> updCodeMap : updCodeArray){
			codeGrpId = StringUtils.defaultString(updCodeMap.get("codeGrpId"));
			codeId = StringUtils.defaultString(updCodeMap.get("codeId"));
			codeName = StringUtils.defaultString(updCodeMap.get("codeName"));
			log.debug("addCodeMap::" + updCodeMap);
			paramsMap.clear();
			paramsMap.put("codeGrpId", codeGrpId);
			paramsMap.put("codeId", codeId);
			paramsMap.put("codeName", codeName);
			paramsMap.put("userId", userId);

			chkCnt = codeDAO.sysm04UpdateCodeResource(paramsMap);
			
			if(chkCnt == 0) {
				throw new Exception("코드 수정에 실패하였습니다.");
			}
		}

		for(HashMap<String, Object> delCodeMap : delCodeArray){

			codeGrpId = StringUtils.defaultString(delCodeMap.get("codeGrpId"));
			codeId = StringUtils.defaultString(delCodeMap.get("codeId"));

			paramsMap.clear();
			paramsMap.put("codeGrpId", codeGrpId);
			paramsMap.put("upCodeId", upCodeId);
			chkCnt = codeDAO.sysm04DeleteUnderCodeResource(paramsMap);
			
			paramsMap.clear();
			paramsMap.put("codeGrpId", codeGrpId);
			paramsMap.put("codeId", codeId);
			chkCnt = codeDAO.sysm04DeleteCodeResource(paramsMap);
			
		}
		rtn.clear();
		rtn.put("errorCode", "00");
		rtn.put("errorMessage", "정상 처리 되었습니다.");

		return rtn;
	}
	
	
	/**
	 * 공통속성 수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> sysm04UpdateAttCodeResource(HashMap<String, Object> param) throws Exception{
		int cnt = 1;
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		TypeCastUtils typeCastUtils = new TypeCastUtils();
		String[] paramsA = typeCastUtils.stringToArray(StringUtils.defaultString(param.get("paramsAList")));
		
		String codeGrpId = StringUtils.defaultString(param.get("codeGrpId"));
		String updId = StringUtils.defaultString(param.get("updId"));

		
		//속성 초기화
		paramMap.clear();
		paramMap.put("codeGrpId", codeGrpId);
		paramMap.put("updId", updId);
		cnt = codeDAO.sysm04UpdateAttCleanCodeResource(paramMap);
		
		//search 여부
		if(paramsA.length > 0) {
			paramMap.clear();
			paramMap.put("codeGrpId", codeGrpId);
			paramMap.put("updId", updId);
			paramMap.put("searchYn", "Y");
			paramMap.put("codeList", paramsA);
			cnt = codeDAO.sysm04UpdateAttCodeResource(paramMap);				
		}

		if(cnt == 0) {
			throw new Exception("처리중 오류가 발생하였습니다.");
		}

		rtn.put("errorCode", "00");
		rtn.put("errorMessage", "정상 등록 되었습니다.");

		return rtn;
	}
	
}