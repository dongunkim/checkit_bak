package com.sktelecom.checkit.admin.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sktelecom.checkit.admin.board.service.BoardService;
import com.sktelecom.checkit.core.annotation.Paging;
import com.sktelecom.checkit.core.util.Session;

/**
 * 
 */
@Controller
@RequestMapping("/admin/board")
public class BoardController{
	private final static Log log = LogFactory.getLog(BoardController.class);

	@Resource
	private BoardService boardService;

	/**
	 * 
	 */
	@RequestMapping({"/boardList.do"})
	public ModelAndView boardList(ModelAndView modelAndView) throws Exception {
		return modelAndView;
	}

	/**
	 * 
	 */
	@Paging(value=true)
	@RequestMapping({"/boardList.ajax"})
	@ResponseBody
	public Map<String, Object> boardList(@RequestParam HashMap<String, Object> param) throws Exception {
		return boardService.boardList(param);
	}

	/**
	 * 
	 */
	@RequestMapping({"/boardDetail.do"})
	public ModelAndView boardDetail(ModelAndView modelAndView, @RequestParam HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = boardService.boardDetail(param);
		rtn.put("param", param.get("param"));
		modelAndView.addObject("result", rtn);
		return modelAndView;
	}

	/**
	 * 
	 */
	@RequestMapping({"/boardReg.do"})
	public ModelAndView boardReg(ModelAndView modelAndView, @RequestParam HashMap<String, Object> param) throws Exception {
		modelAndView.addObject("result", param);
		return modelAndView;
	}

	/**
	 * 
	 */
	@RequestMapping({"/insertBoard.ajax"})
	public HashMap<String, Object> insertBoard(@RequestParam HashMap<String, Object> param, Session session) throws Exception {
		param.put("regId", session.getUserId());
		return boardService.insertBoard(param);
	}

	/**
	 * 
	 */
	@RequestMapping({"/boardEdit.do"})
	public ModelAndView boardEdit(ModelAndView modelAndView, @RequestParam HashMap<String, Object> param) throws Exception {
		modelAndView.addObject("result", param);
		return modelAndView;
	}

	/**
	 * 
	 */
	@RequestMapping({"/updateBoard.ajax"})
	public ModelAndView updateBoard(ModelAndView modelAndView, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		param.put("updId", session.getUserId());
		boardService.updateBoard(param);
		modelAndView.addObject("result", rtn);
		return modelAndView;
	}

	/**
	 * 
	 */
	@RequestMapping({"/deleteBoard.ajax"})
	public ModelAndView deleteBoard(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		param.put("updId", session.getUserId());
		boardService.deleteBoard(param);
		modelAndView.addObject("result", rtn);
		return modelAndView;
	}
}
