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

import com.sktelecom.checkit.admin.system.service.MenuService;
import com.sktelecom.checkit.core.annotation.Paging;
import com.sktelecom.checkit.core.util.Session;

/**
 * 고객지원시스템 > 시스템관리
 * @author sj.kim
 * @since 2018.08.02
 */
@Controller
@RequestMapping("/admin/system")
public class MenuController{
	private final static Log log = LogFactory.getLog(MenuController.class);

	@Resource
	private MenuService menuService;

	/**
	 * 메뉴 관리 페이지 이동
	 */
	@RequestMapping({"/menuList.do"})
	public ModelAndView menuList(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		return modelAndView;
	}

	/**
	 * 메뉴/프로그램 등록 팝업 이동
	 */
	@RequestMapping({"/menuRegPop.do", "/menuPgmRegPop.do"})
	public ModelAndView sysm02Pop(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		modelAndView.addObject("result", param);
		return modelAndView;
	}

	/**
	 * 메뉴 트리 목록 조회
	 */
	@RequestMapping({"/menuTreeList.do"})
	public ModelAndView menTreeList(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		modelAndView.addObject("result", menuService.menuTreeList(param));
		return modelAndView;
	}

	/**
	 * 프로그램 목록 조회
	 */
	@Paging(value=false)
	@RequestMapping({"/menuPgmList.do"})
	public ModelAndView menuPgmList(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		modelAndView.addObject("result", menuService.menuPgmList(param));
		return modelAndView;
	}

	/**
	 * 메뉴 등록
	 */
	@RequestMapping({"/menuInsert.do"})
	public ModelAndView menuInsert(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		param.put("userId", session.getUserId());
		modelAndView.addObject("result", menuService.menuInsert(param));
		return modelAndView;
	}

	/**
	 * 메뉴 수정
	 */
	@RequestMapping({"/menuUpdate.do"})
	public ModelAndView menuUpdate(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		param.put("userId", session.getUserId());
		modelAndView.addObject("result", menuService.menuUpdate(param));
		return modelAndView;
	}

	/**
	 * 메뉴 삭제
	 */
	@RequestMapping({"/menuDelete.do"})
	public ModelAndView menuDelete(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		param.put("userId", session.getUserId());
		modelAndView.addObject("result", menuService.menuDelete(param));
		return modelAndView;
	}
	
	/**
	 * 프로그램 등록
	 */
	@RequestMapping({"/menuPgmInsert.do"})
	public ModelAndView menuPgmInsert(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		param.put("userId", session.getUserId());
		modelAndView.addObject("result", menuService.menuPgmInsert(param));
		return modelAndView;
	}

	/**
	 * 프로그램 수정
	 */
	@RequestMapping({"/menuPgmUpdate.do"})
	public ModelAndView menuPgmUpdate(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		param.put("userId", session.getUserId());
		modelAndView.addObject("result", menuService.menuPgmUpdate(param));
		return modelAndView;
	}

	/**
	 * 프로그램 삭제
	 */
	@RequestMapping({"/sysm02MenuPgmDelete.do"})
	public ModelAndView sysm02MenuPgmDelete(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		param.put("userId", session.getUserId());
		modelAndView.addObject("result", menuService.menuPgmDelete(param));
		return modelAndView;
	}
}
