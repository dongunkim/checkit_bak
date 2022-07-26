package com.sktelecom.checkit.portal.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sktelecom.checkit.core.annotation.Api;
import com.sktelecom.checkit.core.annotation.Paging;
import com.sktelecom.checkit.portal.board.service.BoardService;

/**
 * 
 */
@Controller
@RequestMapping("/board")
public class BoardController{
	private final static Log log = LogFactory.getLog(BoardController.class);

	@Resource
	private BoardService noticeService;

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
	@Api
	@RequestMapping({"/selBoardList.ajax"})
	@ResponseBody
	public Map<String, Object> selBoardList(@RequestParam HashMap<String, Object> param) throws Exception {
		return noticeService.selBoardList(param);
	}

	/**
	 * 
	 */
	@RequestMapping({"/boardDetail.do"})
	public ModelAndView boardDetail(ModelAndView modelAndView, @RequestParam HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = noticeService.selBoardDetail(param);
		if(!"00".equals(rtn.get("errorCode").toString())) throw new Exception();
		rtn.put("param", param.get("param"));
		modelAndView.addObject("result", rtn);
		return modelAndView;
	}
}
