package com.sktelecom.checkit.admin.system.controller;

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

import com.sktelecom.checkit.admin.system.service.CodeService;
import com.sktelecom.checkit.core.annotation.Paging;
import com.sktelecom.checkit.core.util.Session;

/**
 * 업무지원시스템 > 공통코드관리
 * @author jh.bang
 * @since 2018.08.02
 */
@Controller
@RequestMapping("/admin/system")
public class CodeController{
	private final static Log log = LogFactory.getLog(CodeController.class);

	@Resource
	private CodeService codeService;

	/**
	 * 공통코드관리 화면
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@RequestMapping({"/codeGrpList.do"})
	public ModelAndView codeGrpList(ModelAndView modelAndView) throws Exception {
		return modelAndView;
	}

	/**
	 * 공통코드관리 팝업 화면
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@RequestMapping({"/codeGrpRegPop.do", "/codeListPop.do", "/codeRegPop.do", "/codeEditPop.do"})
	public ModelAndView system04Pop(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		modelAndView.addObject("result", param);
		return modelAndView;
	}

	/**
	 * 공통코드관리 화면
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@Paging(value=false)
	@RequestMapping({"/codeGrpList.ajax"})
	public HashMap<String, Object> codeGrpList(@RequestParam HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("result", codeService.codeGrpList(param));
		return rtn;
	}

	/**
	 * 공통코드관리 화면
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@Paging(value=false)
	@RequestMapping({"/underCodeList.do"})
	public ModelAndView underCodeList(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		modelAndView.addObject("result", codeService.underCodeList(param));
		return modelAndView;
	}

	/**
	 * 공통코드관리 화면
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@RequestMapping({"/depthCodeList.do"})
	public ModelAndView depthCodeList(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		modelAndView.addObject("result", codeService.depthCodeList(param));
		return modelAndView;
	}

	/**
	 * 공통코드관리 화면
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@RequestMapping({"/checkDuplicate.do"})
	public ModelAndView checkDuplicate(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		modelAndView.addObject("result", codeService.checkDuplicate(param));
		return modelAndView;
	}

	/**
	 * 공통코드관리 화면
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@RequestMapping({"/codeGrpProcess.do"})
	public ModelAndView codeGrpProcess(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		param.put("userId", session.getUserId());
		modelAndView.addObject("result", codeService.codeGrpProcess(param));
		return modelAndView;
	}

	/**
	 * 공통코드관리 화면
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@RequestMapping({"/codeProcess.do"})
	public ModelAndView codeProcess(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		param.put("userId", session.getUserId());
		modelAndView.addObject("result", codeService.codeProcess(param));
		return modelAndView;
	}
	
	/**
	 * 공통속성 수정
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@RequestMapping({"/codeAttUpdate.do"})
	public ModelAndView codeAttUpdate(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		param.put("updId", session.getUserId());
		modelAndView.addObject("result", codeService.codeAttUpdate(param));
		return modelAndView;
	}

}
