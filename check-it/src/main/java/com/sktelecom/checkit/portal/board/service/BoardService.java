package com.sktelecom.checkit.portal.board.service;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.checkit.core.common.dao.CommonDAO;
import com.sktelecom.checkit.core.common.service.CommonService;
import com.sktelecom.checkit.portal.board.dao.BoardDAO;

/**
 *
 */
@Service
public class BoardService {

	private final static Log log = LogFactory.getLog(BoardService.class.getName());

	@Resource
    private CommonService commonService;

	@Resource
	private BoardDAO noticeDAO;

	@Resource
	private CommonDAO commonDAO;

	/**
	 * 
	 */
	public HashMap<String, Object> selBoardList(HashMap<String, Object> param){
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = noticeDAO.selBoardList(param);
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
			rtn = noticeDAO.selBoardDetail(param);
			param.put("attachId", rtn.get("attachId"));
			rtn.put("attachList", commonDAO.selAttachFileList(param));
			noticeDAO.updBoardViewCnt(param);
			rtn.put("errorCode", "00");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_board_boardDetail_01");
		}
		return rtn;
	}

}