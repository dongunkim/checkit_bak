package com.sktelecom.checkit.core.locale.controller;

import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import com.sktelecom.checkit.core.annotation.NoLogin;
import com.sktelecom.checkit.core.util.Session;

/**
 * 공통 controller
 * @author devkimsj
 *
 */
@Controller
@RequestMapping("/locale")
public class LocaleController {
	private final static Log log = LogFactory.getLog(LocaleController.class);

	@Autowired
	private LocaleResolver localeResolver;

	/**
	 * locale 변경
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@NoLogin
	@RequestMapping({"/changeLocale.do"})
	public ModelAndView changeLocale(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
//		https://code.i-harness.com/ko-kr/q/5ee4da
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		Locale locale = null;

		try{

			if("en".matches(String.valueOf(param.get("locale")))) {
				locale = Locale.ENGLISH;
			}else{
				locale = Locale.KOREAN;
			}

			localeResolver.setLocale(req, res, locale);
			session.setLocale(locale);
			rtn.put("locale", locale);
			modelAndView.addObject("result", rtn);

		}catch(Exception e){
			log.error(e.getMessage());
		}

        return modelAndView;

	}

}
