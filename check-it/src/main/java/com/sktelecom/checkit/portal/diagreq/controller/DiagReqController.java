package com.sktelecom.checkit.portal.diagreq.controller;

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
import com.sktelecom.checkit.core.annotation.NoLogin;
import com.sktelecom.checkit.portal.diagreq.service.DiagReqService;

/**
 * 
 */
@Controller
@RequestMapping("/diagreq")
public class DiagReqController{
	private final static Log log = LogFactory.getLog(DiagReqController.class);

	@Resource
	private DiagReqService diagReqService;

	/**
	 * 진단 신청 페이지 이동
	 */
	@NoLogin
	@RequestMapping({"/diagReq.do"})
	public ModelAndView diagReq(ModelAndView modelAndView) throws Exception {
		return modelAndView;
	}

	/**
	 * 인프라 진단 신청 페이지 이동
	 */
	@NoLogin
	@RequestMapping({"/infraDiagReq.do"})
	public ModelAndView infraDiagReq(ModelAndView modelAndView) throws Exception {
		return modelAndView;
	}

	/**
	 * 
	 */
	@Api
	@RequestMapping({"/insDiag.ajax"})
	@ResponseBody
	public Map<String, Object> insDiag(@RequestParam HashMap<String, Object> param) throws Exception {
		return diagReqService.insDiag(param);
	}

	/**
	 * 서비스별 진단 신청 현황 
	 */
	@NoLogin
	@RequestMapping({"/svcDiagList.do"})
	public ModelAndView svcDiagList(ModelAndView modelAndView) throws Exception {
		return modelAndView;
	}

	/**
	 * 유형별 진단 신청 현황 
	 */
	@NoLogin
	@RequestMapping({"/typeDiagList.do"})
	public ModelAndView typeDiagList(ModelAndView modelAndView) throws Exception {
		return modelAndView;
	}

}
