package com.sktelecom.checkit.admin.board.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.checkit.admin.board.dao.OuterNoticeDAO;
import com.sktelecom.checkit.core.common.service.CommonService;
import com.sktelecom.checkit.core.common.service.IdGenService;
import com.sktelecom.checkit.core.util.StringUtils;
import com.sktelecom.checkit.core.util.TypeCastUtils;

/**
 * 고객 공지 ServiceImpl
 * @author devbaekgh
 *
 */
@Service
public class OuterNoticeService {

	private final static Log log = LogFactory.getLog(OuterNoticeService.class.getName());

	/* 고객 공지 DAO */
	@Resource
	private OuterNoticeDAO bord03DAO;

	@Resource
    private CommonService commonService;

	@Resource
    private IdGenService idGenService;

	/**
	 * 고객 공지 목록 화면
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public HashMap<String, Object> bord0301List(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = bord03DAO.bord0301List(param);
		return rtn;
	}

	/**
	 * 고객 공지 상세 화면
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public HashMap<String, Object> bord0302Detail(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = bord03DAO.bord0302Detail(param);
		return rtn;
	}
	
	/**
	 * 고객 공지 수정 화면
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public HashMap<String, Object> bord0304Edit(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = bord03DAO.bord0304Edit(param);
		return rtn;
	}
	
	/**
	 * 고객 공지 등록
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public void insertBord0303(HashMap<String, Object> param) throws Exception{
		int attachId = commonService.commonFileUplod(param);
		if(attachId != 0) // ATTACH_FILE 테이블의 ATTACH_ID 를 return 합니다. 파일업로드 건수가 없을경우에는 0을 리턴
			param.put("attachId", attachId);

		// SEQ 확인
		HashMap<String, Object> paramSeq = idGenService.getSeq("OUTER_GONGJI");
		String csGongjiYn = StringUtils.defaultString(param.get("csGongjiYn"), "N");
		param.put("bbsNo", paramSeq.get("seq"));
		param.put("bbsSubNo", 0);
		param.put("replCnt", 0);
		param.put("viewCnt", 0);
		param.put("delYn", "N");
		param.put("csGongjiYn", csGongjiYn);
		bord03DAO.insertBord0303(param);

		if("Y".equals(csGongjiYn)) {
			TypeCastUtils typeCastUtils = new TypeCastUtils();
			ArrayList<HashMap<String, Object>> csNoArr = typeCastUtils.stringToArrayList(String.valueOf(param.get("csNoArr")));

			if(csNoArr != null && csNoArr.size() > 0) {
				HashMap<String, Object> csNoMap = new HashMap<String, Object>();
				for(int i = 0; i < csNoArr.size(); i++){

					csNoMap = csNoArr.get(i);
					csNoMap.put("bbsNo", paramSeq.get("seq"));
					csNoMap.put("regId", param.get("regId"));
					bord03DAO.insertBordCsNo(csNoMap);
				}
			}
		}
	}

	/**
	 * 고객 공지 수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public void updateBord0304(HashMap<String, Object> param) throws Exception{
		int attachId = commonService.commonFileUplod(param);
		if(attachId != 0) // ATTACH_FILE 테이블의 ATTACH_ID 를 return 합니다. 파일업로드 건수가 없을경우에는 0을 리턴
			param.put("attachId", attachId);

		bord03DAO.updateBord0304(param);
		bord03DAO.deleteBordCsNo(param);

		String csGongjiYn = StringUtils.defaultString(param.get("csGongjiYn"), "N");
		if("Y".equals(csGongjiYn)) {
			TypeCastUtils typeCastUtils = new TypeCastUtils();
			log.info("param.get(\"csNoArr\")>>>>>>>>>>>"+param.get("csNoArr"));
			ArrayList<HashMap<String, Object>> csNoArr = typeCastUtils.stringToArrayList(String.valueOf(param.get("csNoArr")));
			if(csNoArr != null && csNoArr.size() > 0) {
				HashMap<String, Object> csNoMap = new HashMap<String, Object>();
				for(int i = 0; i < csNoArr.size(); i++){

					//csNoMap.put("csNo",csNoArr[i]);
					csNoMap = csNoArr.get(i);
					csNoMap.put("bbsNo", param.get("bbsNo"));
					csNoMap.put("regId", param.get("regId"));
					bord03DAO.insertBordCsNo(csNoMap);
				}
			}
		}
	}

	/**
	 * 고객 공지 삭제
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public void deleteBord0305(HashMap<String, Object> param) throws Exception{
		param.put("delYn", "Y");
		bord03DAO.deleteBord0305(param);
	}
}