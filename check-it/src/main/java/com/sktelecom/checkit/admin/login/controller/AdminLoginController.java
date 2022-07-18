package com.sktelecom.checkit.admin.login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sktelecom.checkit.admin.login.service.AdminLoginService;
import com.sktelecom.checkit.core.annotation.Api;
import com.sktelecom.checkit.core.annotation.NoLogin;
import com.sktelecom.checkit.core.util.Session;

/**
 * 로그인
 * @author devkimsj
 * @since 2018.10.28
 */
@Controller
@RequestMapping("/admin/login")
public class AdminLoginController{

	private static final Logger log = LoggerFactory.getLogger(AdminLoginController.class);

	@Resource
	private AdminLoginService adminLoginService;

	/**
	 * 로그인화면 호출
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@NoLogin
	@RequestMapping({"/login0101Page.do"})
	public ModelAndView login0101Page(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		modelAndView.addObject("result", param);
		return modelAndView;
	}

	/**
	 * 로그인
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@NoLogin
	@Api
	@RequestMapping({"/adLogin.do"})
	@ResponseBody
	public Map<String, Object> adLogin(HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> rtn = new HashMap<String, Object>();
		String ip  = req.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		param.put("userIP",ip);
		
		//rtn = adminLoginService.loginUserInfo(param);
		rtn.put("result", adminLoginService.loginUserInfo(param));
		//modelAndView.addObject("result", rtn);

		//return modelAndView;
		return rtn;
	}

	/**
	 * ID 찾기, 비밀번호 찾기 팝업, 사용자등록요청 팝업
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@NoLogin
	@RequestMapping({"/login0102Pop.do", "/login0103Pop.do", "/login0104Pop.do"})
	public ModelAndView login0102Pop(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		modelAndView.addObject("result", param);
		return modelAndView;
	}

	/**
	 * 신규 ID 체크
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@NoLogin
	@RequestMapping({"/newIdCheck.do"})
	public ModelAndView newIdCheck(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();

		try{
			rtn = adminLoginService.newIdCheck(param);
		}catch(Exception e){
			log.error(e.getMessage());
		}

		modelAndView.addObject("result", rtn);

		return modelAndView;
	}

	/**
	 * ID 찾기
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@NoLogin
	@RequestMapping({"/idSearch.do"})
	public ModelAndView idSearch(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();

		try{
			rtn = adminLoginService.idSearch(param);
		}catch(Exception e){
			//rtn.put("errorCode", e.getCode());
			//rtn.put("errorMessage", e.getMessage());
			log.error(e.getMessage());
		}

		modelAndView.addObject("result", rtn);

		return modelAndView;
	}

	/**
	 * 비밀번호 찾기
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@NoLogin
	@RequestMapping({"/pwdSearch.do"})
	public ModelAndView pwdSearch(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();

		try{
			rtn = adminLoginService.pwdSearch(param);
		}catch(Exception e){
			//rtn.put("errorCode", e.getCode());
			//rtn.put("errorMessage", e.getMessage());
			log.error(e.getMessage());
		}

		modelAndView.addObject("result", rtn);

		return modelAndView;
	}

	/**
	 * 사용자 등록
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@NoLogin
	@RequestMapping({"/insertNewUser.do"})
	public ModelAndView insertNewUser(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();

		try{
			rtn = adminLoginService.insertNewUser(param);
		}catch(Exception e){
			//rtn.put("errorCode", e.getCode());
			//rtn.put("errorMessage", e.getMessage());
			log.error(e.getMessage());
		}

		modelAndView.addObject("result", rtn);

		return modelAndView;
	}

	/**
	 * 로그아웃
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/logout.do"})
	public void logOut(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		session.removeSession();
		res.sendRedirect("/admin/login/login0101Page.do");
	}
	
	/**
	 * 세션아웃
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@NoLogin
	@RequestMapping({"/login0201Page.do"})
	public ModelAndView sessionOut(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		session.removeSession();
		modelAndView.addObject("result", param);
		return modelAndView;
	}
	
}

