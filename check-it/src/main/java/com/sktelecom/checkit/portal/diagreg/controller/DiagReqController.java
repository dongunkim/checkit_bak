package com.sktelecom.checkit.portal.diagreg.controller;

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
import com.sktelecom.checkit.portal.diagreg.service.DiagReqService;

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
	 * 
	 */
	@NoLogin
	@RequestMapping({"/diagReq.do"})
	public ModelAndView diagReq(ModelAndView modelAndView) throws Exception {
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

}
