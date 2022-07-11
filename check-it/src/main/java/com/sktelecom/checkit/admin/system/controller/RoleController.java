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

import com.sktelecom.checkit.admin.system.service.RoleService;
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
	 * 공통코드관리 화면
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/sysm0301List.do"})
	public ModelAndView sysm0401List(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		return modelAndView;
	}

	/**
	 * 공통코드관리 화면
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Paging(value=false)
	@RequestMapping({"/sysm03GetSysRoleList.do"})
	public ModelAndView sysm03GetSysRoleList(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		modelAndView.addObject("result", roleService.roleList(param));
		return modelAndView;
	}

	/**
	 * 공통코드관리 화면
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Paging(value=false)
	@RequestMapping({"/sysm03SysRoleTree.do"})
	public ModelAndView sysm03SysRoleTree(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		modelAndView.addObject("result", roleService.roleTreeList(param));
		return modelAndView;
	}

	/**
	 * 공통코드관리 화면
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/sysm0302Pop.do"})
	public ModelAndView sysm03Popup(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
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
	 * @throws Exception
	 */
	@RequestMapping({"/sysm03SysRoleProcess.do"})
	public ModelAndView sysm03SysRoleProcess(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		param.put("userId", session.getUserId());
		modelAndView.addObject("result", roleService.roleInsert(param));
		return modelAndView;
	}

	/**
	 * 공통코드관리 화면
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/sysm03InsertSysRolePgm.do"})
	public ModelAndView sysm03InsertSysRolePgm(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		param.put("userId", session.getUserId());
		modelAndView.addObject("result", roleService.rolePgmInsert(param));
		return modelAndView;
	}
}
