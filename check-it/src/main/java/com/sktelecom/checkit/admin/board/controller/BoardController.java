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
 * 업무지원시스템 > 공지/게시판
 * @author gh.baek
 * @since 2018.12.17
 */
@Controller
@RequestMapping("/admin/board")
public class BoardController{
	private final static Log log = LogFactory.getLog(BoardController.class);

	@Resource
	private BoardService boardService;

	/**
	 * 내부 공지 목록 화면
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@Paging(value=true)
	@RequestMapping({"/boardList.do"})
	public ModelAndView board01List(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		return modelAndView;
	}

	@Paging(value=true)
	@RequestMapping({"/ajaxBoardList.do"})
	@ResponseBody
	public Map<String, Object> ajaxBoardList(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = boardService.boardList(param);
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		return rtn;
	}

	/**
	 * 내부 공지 조회 화면
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@RequestMapping({"/boardDetail.do"})
	public ModelAndView board02Detail(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = boardService.boardDetail(param);
			rtn.put("param", param.get("param"));
			modelAndView.addObject("result", rtn);
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		return modelAndView;
	}

	/**
	 * 내부 공지 등록 페이지 이동
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@Paging(value=true)
	@RequestMapping({"/boardReg.do"})
	public ModelAndView boardReg(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		modelAndView.addObject("result", param);
		return modelAndView;
	}

	/**
	 * 내부 공지 등록
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@RequestMapping({"/insertBoard.do"})
	public ModelAndView insertBoard(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			// Session의 값을 저장해서 전달한다.
			param.put("regId", session.getUserId());
			boardService.insertBoard(param);
			rtn.put("errorCode", "00");
			rtn.put("errorMessage", "정상등록되었습니다.");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "BOARD_01");
			rtn.put("errorMessage", "등록 중 오류가 발생하였습니다.");
		}
		modelAndView.addObject("result", rtn);
		return modelAndView;
	}

	/**
	 * 내부 공지 수정 페이지 이동
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@Paging(value=true)
	@RequestMapping({"/board04Edit.do"})
	public ModelAndView board04Edit(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		modelAndView.addObject("result", param);
		return modelAndView;
	}

	/**
	 * 내부 공지 수정
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@RequestMapping({"/updateBord0204.do"})
	public ModelAndView updateBord0204(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		param.put("updId", session.getUserId());
		try{
			boardService.updateBoard(param);
		}catch(Exception e){
			log.error(e.getMessage());
			//log.error(e.getMessage() + " ERROR_CODE[" + e.getCode() + "]");
			//rtn.put("errorCode", e.getCode());
			//rtn.put("errorMessage", e.getMessage());
		}
		modelAndView.addObject("result", rtn);
		return modelAndView;
	}

	/**
	 * 내부 공지 삭제
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@RequestMapping({"/deleteBord0205.do"})
	public ModelAndView deleteBord0205(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			param.put("updId", session.getUserId());
			boardService.deleteBoard(param);
			modelAndView.addObject("result", rtn);
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		return modelAndView;
	}
}
