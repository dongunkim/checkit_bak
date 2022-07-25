package com.sktelecom.checkit.portal.infra.controller;

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

import com.sktelecom.checkit.core.annotation.Paging;
import com.sktelecom.checkit.portal.infra.service.InfraDiagService;
import com.sktelecom.checkit.portal.infra.service.InfraHostService;

/**
 * 
 */
@Controller
@RequestMapping("/infra")
public class InfraDiagController{
	private final static Log log = LogFactory.getLog(InfraDiagController.class);

	@Resource
	private InfraDiagService infraDiagService;

	@Resource
	private InfraHostService infraHostService;

	/**
	 * 
	 */
	@RequestMapping({"/infraDiagList.do"})
	public ModelAndView infraDiagList(ModelAndView modelAndView) throws Exception {
		return modelAndView;
	}

	/**
	 * 
	 */
	@Paging(value=true)
	@RequestMapping({"/infraDiagList.ajax"})
	@ResponseBody
	public Map<String, Object> infraDiagList(@RequestParam HashMap<String, Object> param) throws Exception {
		return infraDiagService.infraDiagList(param);
	}

	/**
	 * 
	 */
	@RequestMapping({"/infraDiagDetail.do"})
	public ModelAndView infraDiagDetail(ModelAndView modelAndView, @RequestParam HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> rtn = infraDiagService.infraDiagDetail(param);
		if(!"00".equals(rtn.get("errorCode").toString())) throw new Exception();
		rtn.put("param", param.get("param"));
		modelAndView.addObject("result", rtn);
		return modelAndView;
	}

	/**
	 * 
	 */
	@RequestMapping({"/selInfraDiagObjList.ajax"})
	@ResponseBody
	public Map<String, Object> selInfraDiagObjList(@RequestParam HashMap<String, Object> param) throws Exception {
		return infraDiagService.selInfraDiagObjList(param);
	}

	/**
	 * 
	 */
	@RequestMapping({"/delHost.ajax"})
	@ResponseBody
	public Map<String, Object> delHost(@RequestParam HashMap<String, Object> param) throws Exception {
		return infraHostService.delHost(param);
	}

	/**
	 * 
	 */
	@RequestMapping({"/selDiagHostList.ajax"})
	@ResponseBody
	public Map<String, Object> selDiagHostList(@RequestParam HashMap<String, Object> param) throws Exception {
		return infraHostService.selDiagHostList(param);
	}
	

}
