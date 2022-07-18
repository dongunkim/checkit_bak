package com.sktelecom.checkit.portal.login.controller;

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

import com.sktelecom.checkit.core.annotation.Api;
import com.sktelecom.checkit.core.annotation.NoLogin;
import com.sktelecom.checkit.core.util.Session;
import com.sktelecom.checkit.portal.login.service.LoginService;

/**
 * 로그인
 * @author devkimsj
 * @since 2018.10.28
 */
@Controller
@RequestMapping("/login")
public class LoginController{

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Resource
	private LoginService loginService;

	/**
	 * 로그인화면 호출
	 * @param  ModelAndView
	 * @param  @RequestParam HashMap
	 * @return ModelAndView
	 */
	@NoLogin
	@RequestMapping({"/login.do"})
	public ModelAndView loginPage(ModelAndView modelAndView, @RequestParam HashMap<String, Object> param) throws Exception {
		modelAndView.addObject("result", param);
		return modelAndView;
	}

	/**
	 * 로그인
	 * @param  HttpServletResponse
	 * @param  @RequestParam HashMap 
	 * @return Map
	 */
	@NoLogin
	@Api	
	@ResponseBody
	@RequestMapping({"/loginCheck.ajax"})
	public Map<String, Object> login(HttpServletRequest req, @RequestParam HashMap<String, Object> param) throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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
		param.put("userIP", ip);
		Map<String, Object> rtn = loginService.loginCheck(param);
		
		resultMap.put("result", rtn);
		return resultMap;
	}

	/**
	 * 로그아웃
	 * @param  HttpServletResponse
	 * @param  Session
	 * @return void
	 */
	@RequestMapping({"/logout.do"})
	public void logOut(HttpServletResponse res, Session session) throws Exception {
		session.removeSession();
		res.sendRedirect("/login/loginPage.do");
	}
	
		
}

