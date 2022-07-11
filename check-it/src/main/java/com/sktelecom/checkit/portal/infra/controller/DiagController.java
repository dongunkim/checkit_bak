package com.sktelecom.checkit.portal.infra.controller;

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

import com.sktelecom.checkit.core.annotation.Paging;
import com.sktelecom.checkit.portal.infra.service.DiagService;

/**
 * 업무지원시스템 > 공지/게시판
 * @author gh.baek
 * @since 2018.12.17
 */
@Controller
@RequestMapping("/portal/infra")
public class DiagController{
	private final static Log log = LogFactory.getLog(DiagController.class);

	@Resource
	private DiagService diagService;

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
	@RequestMapping({"/diagList.do"})
	public ModelAndView diagList(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		return modelAndView;
	}

	@Paging(value=true)
	@RequestMapping({"/ajaxDiagList.do"})
	@ResponseBody
	public Map<String, Object> ajaxDiagList(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = diagService.diagList(param);
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
	@RequestMapping({"/diagDetail.do"})
	public ModelAndView diagDetail(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = diagService.diagDetail(param);
			rtn.put("param", param.get("param"));
			modelAndView.addObject("result", rtn);
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping({"/ajaxDiagHostList.do"})
	@ResponseBody
	public Map<String, Object> ajaxDiagHostList(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = diagService.diagHostList(param);
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		return rtn;
	}
	
	@RequestMapping({"/ajaxDiagObjList.do"})
	@ResponseBody
	public Map<String, Object> ajaxDiagObjList(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = diagService.diagObjList(param);
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		return rtn;
	}

	@RequestMapping({"/ajaxDelHost.do"})
	@ResponseBody
	public Map<String, Object> ajaxDelHost(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			diagService.deleteHost(param);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return rtn;
	}


}
