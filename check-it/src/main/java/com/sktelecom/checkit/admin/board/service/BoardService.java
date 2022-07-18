package com.sktelecom.checkit.admin.board.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sktelecom.checkit.admin.board.dao.BoardDAO;
import com.sktelecom.checkit.core.common.dao.CommonDAO;
import com.sktelecom.checkit.core.common.service.CommonService;
import com.sktelecom.checkit.core.file.FileUpload;

/**
 *
 */
@Service
public class BoardService {

	private final static Log log = LogFactory.getLog(BoardService.class.getName());

	@Resource
    private CommonService commonService;

	@Resource
	private BoardDAO boardDAO;

	@Resource
	private CommonDAO commonDAO;

	@Resource
	private FileUpload fileUpload;

	/**
	 * 
	 */
	public HashMap<String, Object> selBoardList(HashMap<String, Object> param){
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = boardDAO.selBoardList(param);
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
	public HashMap<String, Object> selBoardDetail(HashMap<String, Object> param){
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn.put("param", param.get("param"));
			rtn = boardDAO.selBoardDetail(param);
			param.put("attachId", rtn.get("attachId"));
			rtn.put("attachList", commonDAO.selAttachFileList(param));
			boardDAO.updBoardViewCnt(param);
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
	public HashMap<String, Object> insBoard(HashMap<String, Object> param){
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			// File Upload
			List<HashMap<String, Object>> fileList = new ObjectMapper().readValue(String.valueOf(param.get("attachFileList")), new TypeReference<List<HashMap<String, Object>>>(){});
			fileUpload.upload(fileList, "filePath.upload");

			// Upload File 정보 DB 반영
			int attachId = commonDAO.updUploadFile(fileList);
			if(attachId != 0) // ATTACH_FILE 테이블의 ATTACH_ID 를 return 합니다. 파일업로드 건수가 없을경우에는 0을 리턴
				param.put("attachId", attachId);
			
			boardDAO.insBoard(param);
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
	public HashMap<String, Object> updBoard(HashMap<String, Object> param){
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			// File Upload
			List<HashMap<String, Object>> fileList = new ObjectMapper().readValue(String.valueOf(param.get("attachFileList")), new TypeReference<List<HashMap<String, Object>>>(){});
			fileUpload.upload(fileList, "filePath.upload");

			// Upload File 정보 DB 반영
			int attachId = commonDAO.updUploadFile(fileList);
			if(attachId != 0) // ATTACH_FILE 테이블의 ATTACH_ID 를 return 합니다. 파일업로드 건수가 없을경우에는 0을 리턴
				param.put("attachId", attachId);
	
			boardDAO.updBoard(param);
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
	public HashMap<String, Object> delBoard(HashMap<String, Object> param){
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			param.put("delYn", "Y");
			boardDAO.delBoard(param);
			rtn.put("errorCode", "00");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_board_boardDelete_01");
		}
		return rtn;
	}
}