package com.sktelecom.checkit.admin.board.service;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.checkit.admin.board.dao.BoardDAO;
import com.sktelecom.checkit.core.common.service.CommonService;

/**
 *
 */
@Service
public class BoardService {

	private final static Log log = LogFactory.getLog(BoardService.class.getName());

	@Resource
	private BoardDAO boardDAO;

	@Resource
    private CommonService commonService;

	/**
	 * 
	 */
	public HashMap<String, Object> boardList(HashMap<String, Object> param){
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = boardDAO.selectBoardList(param);
			rtn.put("errorCode", "00");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_board_boardListt_01");
		}
		return rtn;
	}

	/**
	 * 
	 */
	public HashMap<String, Object> boardDetail(HashMap<String, Object> param){
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			boardDAO.updateBoardViewCnt(param);
			rtn.put("errorCode", "00");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_board_boardDetail_01");
		}
		return rtn;
	}

	/**
	 * 
	 */
	public HashMap<String, Object> insertBoard(HashMap<String, Object> param){
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			int attachId = commonService.commonFileUplod(param);
			if(attachId != 0) // ATTACH_FILE 테이블의 ATTACH_ID 를 return 합니다. 파일업로드 건수가 없을경우에는 0을 리턴
				param.put("attachId", attachId);
			
			boardDAO.insertBoard(param);
			rtn.put("errorCode", "00");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_board_boardInsert_01");
		}
		return rtn;
	}

	/**
	 * 
	 */
	public HashMap<String, Object> updateBoard(HashMap<String, Object> param){
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			int attachId = commonService.commonFileUplod(param);
			if(attachId != 0) // ATTACH_FILE 테이블의 ATTACH_ID 를 return 합니다. 파일업로드 건수가 없을경우에는 0을 리턴
				param.put("attachId", attachId);
	
			boardDAO.updateBoard(param);
			rtn.put("errorCode", "00");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_board_boardUpdate_01");
		}
		return rtn;
	}

	/**
	 * 
	 */
	public HashMap<String, Object> deleteBoard(HashMap<String, Object> param){
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			param.put("delYn", "Y");
			boardDAO.deleteBoard(param);
			rtn.put("errorCode", "00");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_board_boardDelete_01");
		}
		return rtn;
	}
}