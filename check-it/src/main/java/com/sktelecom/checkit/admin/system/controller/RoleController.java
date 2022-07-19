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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sktelecom.checkit.admin.system.service.RoleService;
import com.sktelecom.checkit.core.annotation.Api;
import com.sktelecom.checkit.core.annotation.Paging;
import com.sktelecom.checkit.core.util.Session;

/**
 * 업무지원시스템 > 공통코드관리
 * @author jh.bang
 * @since 2018.08.02
 */
@Controller
@RequestMapping("/admin/system")
public class RoleController{
	private final static Log log = LogFactory.getLog(RoleController.class);

	@Resource
	private RoleService roleService;

	/**
	 * 
	 */
	@RequestMapping({"/roleList.do"})
	public ModelAndView roleList(ModelAndView modelAndView) throws Exception {
		return modelAndView;
	}

	/**
	 * 
	 */
	@Paging(value=false)
	@Api
	@RequestMapping({"/roleList.ajax"})
	@ResponseBody
	public HashMap<String, Object> roleList(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("result", roleService.roleList(param));
		return rtn;
	}

	/**
	 * 
	 */
	@Paging(value=false)
	@RequestMapping({"/roleTreeList.do"})
	public ModelAndView roleTreeList(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		modelAndView.addObject("result", roleService.roleTreeList(param));
		return modelAndView;
	}

	/**
	 * 
	 */
	@RequestMapping({"/roleRegPop.do"})
	public ModelAndView roleRegPopup(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		modelAndView.addObject("result", param);
		return modelAndView;
	}

	/**
	 * 
	 */
	@RequestMapping({"/roleInsert.do"})
	public ModelAndView roleInsert(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		param.put("userId", session.getUserId());
		modelAndView.addObject("result", roleService.roleInsert(param));
		return modelAndView;
	}

	/**
	 * 
	 */
	@RequestMapping({"/rolePgmInsert.do"})
	public ModelAndView rolePgmInsert(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		param.put("userId", session.getUserId());
		modelAndView.addObject("result", roleService.rolePgmInsert(param));
		return modelAndView;
	}
}
