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
	public ModelAndView codeList(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
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
	@RequestMapping({"/system04GetCodeList.do"})
	public ModelAndView system04GetCodeList(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<>();
		rtn = codeService.sysm04GetCodeList(param);
		modelAndView.addObject("result", rtn);
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
	@RequestMapping({"/system04GetUnderCodeList.do"})
	public ModelAndView system04GetUnderCodeList(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<>();
		rtn = codeService.sysm04GetUnderCodeList(param);
		modelAndView.addObject("result", rtn);
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
	@RequestMapping({"/system04GetDepthCodeList.do"})
	public ModelAndView system04GetDepthCodeList(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<>();
		rtn = codeService.sysm04GetDepthCodeList(param);
		modelAndView.addObject("result", rtn);
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
	@RequestMapping({"/system04CheckDuplicate.do"})
	public ModelAndView system04CheckDuplicate(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<>();
		rtn = codeService.sysm04CheckDuplicate(param);
		modelAndView.addObject("result", rtn);
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
	@RequestMapping({"/system04Process.do"})
	public ModelAndView system04Process(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<>();
		try {
			log.debug("system04Process::::"+param);
			param.put("userId", session.getUserId());
			rtn = codeService.sysm04Process(param);
		}catch(Exception e) {
			//rtn.put("errorCode", e.getCode());
			//rtn.put("errorMessage", e.getMessage());
			log.error(e.getMessage());
		}
		modelAndView.addObject("result", rtn);
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
	@RequestMapping({"/system04ResourceProcess.do"})
	public ModelAndView system04ResourceProcess(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<>();
		try {
			param.put("userId", session.getUserId());
			rtn = codeService.sysm04ResourceProcess(param);
		}catch(Exception e) {
			//rtn.put("errorCode", e.getCode());
			//rtn.put("errorMessage", e.getMessage());
			log.error(e.getMessage());
		}
		modelAndView.addObject("result", rtn);
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
	@RequestMapping({"/system04UpdateAttCodeResource.do"})
	public ModelAndView updatesystem0403(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			param.put("updId", session.getUserId());
			rtn = codeService.sysm04UpdateAttCodeResource(param);
		}catch(Exception e){
			//rtn.put("errorCode", e.getCode());
			//rtn.put("errorMessage", e.getMessage());
			log.error(e.getMessage());
		}
		modelAndView.addObject("result", rtn);
		return modelAndView;
	}

}
