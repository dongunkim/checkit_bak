package com.sktelecom.checkit.admin.board.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sktelecom.checkit.admin.board.service.OuterNoticeService;
import com.sktelecom.checkit.core.annotation.Paging;
import com.sktelecom.checkit.core.util.Session;

/**
 * 업무지원시스템 > 공지/게시판
 * @author gh.baek
 * @since 2018.12.17
 */
@Controller
@RequestMapping("/admin/board")
public class OuterBoardController{
	private final static Log log = LogFactory.getLog(OuterBoardController.class);

	@Resource
	private OuterNoticeService bord03Service;

	/**
	 * 고객 공지 목록 화면
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@Paging(value=true)
	@RequestMapping({"/bord0301List.do"})
	public ModelAndView bord0301List(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = bord03Service.bord0301List(param);
			modelAndView.addObject("result", rtn);
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		return modelAndView;
	}

	/**
	 * 고객 공지 조회 화면
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@RequestMapping({"/bord0302Detail.do"})
	public ModelAndView bord0302Detail(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = bord03Service.bord0302Detail(param);
			rtn.put("param", param.get("param"));
			modelAndView.addObject("result", rtn);
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		return modelAndView;
	}

	/**
	 * 고객 공지 등록 페이지 이동
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@Paging(value=true)
	@RequestMapping({"/bord0303Reg.do"})
	public ModelAndView bord0303Reg(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		modelAndView.addObject("result", param);
		return modelAndView;
	}

	/**
	 * 고객 공지 등록
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@RequestMapping({"/insertBord0303.do"})
	public ModelAndView insertBord0303(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			// Session의 값을 저장해서 전달한다.
			param.put("regId", session.getUserId());
			bord03Service.insertBord0303(param);
		}catch(Exception e){
			//log.error(e.getMessage() + " ERROR_CODE[" + e.getCode() + "]");
			//rtn.put("errorCode", e.getCode());
			//rtn.put("errorMessage", e.getMessage());
		}
		modelAndView.addObject("result", rtn);
		return modelAndView;
	}

	/**
	 * 고객 공지 수정 페이지 이동
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@Paging(value=true)
	@RequestMapping({"/bord0304Edit.do"})
	public ModelAndView bord0304Edit(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		//modelAndView.addObject("result", param);
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			//param.put("updId", session.getUserId());
			rtn = bord03Service.bord0304Edit(param);
			modelAndView.addObject("result", rtn);
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		return modelAndView;
	}

	/**
	 * 고객 공지 수정
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@RequestMapping({"/updateBord0304.do"})
	public ModelAndView updateBord0304(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		param.put("updId", session.getUserId());
		try{
			bord03Service.updateBord0304(param);
		}catch(Exception e){
			log.error(e.getMessage());
			//rtn.put("errorCode", e.getCode());
			//rtn.put("errorMessage", e.getMessage());
		}
		modelAndView.addObject("result", rtn);
		return modelAndView;
	}

	/**
	 * 고객 공지 삭제
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@RequestMapping({"/deleteBord0305.do"})
	public ModelAndView deleteBord0305(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			param.put("updId", session.getUserId());
			bord03Service.deleteBord0305(param);
			modelAndView.addObject("result", rtn);
		}catch(Exception e){
			log.error(e.getMessage());
			//rtn.put("errorCode", e.getCode());
			//rtn.put("errorMessage", e.getMessage());
		}
		return modelAndView;
	}
}
