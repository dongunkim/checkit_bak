package com.sktelecom.checkit.admin.board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sktelecom.checkit.admin.board.service.BoardMgmtService;
import com.sktelecom.checkit.core.annotation.Api;
import com.sktelecom.checkit.core.annotation.Paging;
import com.sktelecom.checkit.core.util.ExcelUtils;
import com.sktelecom.checkit.core.util.Session;

/**
 * 
 */
@Controller
@RequestMapping("/admin/boardmgmt")
public class BoardMgmtController{
	private final static Log log = LogFactory.getLog(BoardMgmtController.class);

	@Resource
	private BoardMgmtService boardMgmtService;

	/**
	 * 게시판 목록 화면 이동
	 */
	@RequestMapping({"/boardMgmtList.do"})
	public ModelAndView boardMgmtList(ModelAndView modelAndView, @RequestParam HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("searchParam", param.get("searchParam")); // 조회조건 유지를 위한 파라미터 Setting
		modelAndView.addObject("result", rtn);
		return modelAndView;
	}

	/**
	 * 게시판 목록 조회
	 */
	@Paging(value=true)
	@Api
	@RequestMapping({"/selBoardMgmtList.ajax"})
	@ResponseBody
	public Map<String, Object> selBoardList(@RequestParam HashMap<String, Object> param) throws Exception {
		return boardMgmtService.selBoardList(param);
	}

	/**
	 * 게시판 목록 Excel 다운로드
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	@Api
	@RequestMapping({"/selBoardMgmtExcel.ajax"})
	@ResponseBody
	public void selBoardExcel(HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		List<Map<String, Object>> list = (List)boardMgmtService.selBoardList(param).get("list");
		
		String fileName = "게시판 목록";
		String[] columnNames = {"Id","작성자","제목","등록일시","조회수","내용"};
		String[] columnIds = {"boardId:L","regUserName:C","boardTitle:L","regDt:L","viewCount:R","boardDesc:L"};
		String[] columnWidths = {"4000","4000","10000","4000","4000","30000"};
		
		List<String[]> columnInfo = new ArrayList<String[]>();
		columnInfo.add(columnNames);
		columnInfo.add(columnIds);
		columnInfo.add(columnWidths);
		
		ExcelUtils.downloadExcel(fileName, columnInfo, list, res);
	}

	/**
	 * 게시판 상세 조회
	 */
	@RequestMapping({"/boardMgmtDetail.do"})
	public ModelAndView boardDetail(ModelAndView modelAndView, @RequestParam HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = boardMgmtService.selBoardDetail(param);
		if(!"00".equals(rtn.get("errorCode").toString())) throw new Exception();
		
		rtn.put("searchParam", param.get("searchParam")); // 조회조건 유지를 위한 파라미터 Setting
		modelAndView.addObject("result", rtn);
		return modelAndView;
	}

	/**
	 * 게시판 등록 화면 이동
	 */
	@RequestMapping({"/boardMgmtReg.do"})
	public ModelAndView boardReg(ModelAndView modelAndView, @RequestParam HashMap<String, Object> param) throws Exception {
		modelAndView.addObject("result", param);
		return modelAndView;
	}

	/**
	 * 게시판 등록
	 */
	@Api
	@RequestMapping({"/insBoardMgmt.ajax"})
	@ResponseBody
	public HashMap<String, Object> insBoard(@RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		param.put("regId", session.getUserId());
		rtn.put("result",boardMgmtService.insBoard(param));
		return rtn;
	}

	/**
	 * 게시판 수정 화면 이동
	 */
	@RequestMapping({"/boardMgmtEdit.do"})
	public ModelAndView boardEdit(ModelAndView modelAndView, @RequestParam HashMap<String, Object> param) throws Exception {
		modelAndView.addObject("result", param);
		return modelAndView;
	}

	/**
	 * 게시판 수정
	 */
	@Api
	@RequestMapping({"/updBoardMgmt.ajax"})
	@ResponseBody
	public HashMap<String, Object> updBoard(@RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		param.put("updId", session.getUserId());
		rtn.put("result", boardMgmtService.updBoard(param));
		return rtn;
	}

	/**
	 * 게시판 삭제
	 */
	@Api
	@RequestMapping({"/delBoardMgmt.ajax"})
	@ResponseBody
	public HashMap<String, Object> delBoard(@RequestParam HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("result", boardMgmtService.delBoard(param));
		return rtn;
	}
}
