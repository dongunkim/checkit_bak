package com.sktelecom.checkit.admin.main.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.sktelecom.checkit.admin.main.service.AdminMainService;
import com.sktelecom.checkit.core.util.EncryptUtils;
import com.sktelecom.checkit.core.util.Session;

/**
 * 업무지원시스템 메인
 * @author devkimsj
 * @since 2018.10.28
 */
@Controller
@RequestMapping("/admin")
public class AdminMainController{
	private final static Log log = LogFactory.getLog(AdminMainController.class);

	@Resource
	private AdminMainService amdinMainService;

	/**
	 * 메인화면 호출
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	@RequestMapping
	public void page(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception, IOException {
		res.sendRedirect("/admin/main/main0101Page.do");
	}

	/**
	 * 메인 팝업페이지
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/main/main0103RegPop.do", "/main/main0104ListPop.do", "/main/main0105ListPop.do", "/main/main0106ListPop.do", "/main/main0107Pop.do"})
	public ModelAndView popPage(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		modelAndView.addObject("result", param);
		return modelAndView;
	}

	/**
	 * 개인정보변경 화면
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	@RequestMapping({"/main/main0108Pop.do"})
	public ModelAndView main0108Pop(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception, IOException {

//		if("Y".equals(String.valueOf(session.getEtc()))) {
//			session.setEtc(null);
//			HashMap<String, Object> rtn = new HashMap<String, Object>();
//			rtn.put("userNm", session.getUserNm());
//			rtn.put("manageCid", session.getManageCid());
//			rtn.put("telNo", session.getTelNo());
//			rtn.put("hpNo", session.getHpNo());
//			rtn.put("email", session.getEmail());
//			rtn.put("manageCidNm", session.getManageCidNm());
//			rtn.put("manageCnm", session.getManageCnm());
//			rtn.put("formationCd", session.getFormationCd());
//			rtn.put("formationNm", session.getFormationNm());
//			rtn.put("param", param);
//			modelAndView.addObject("result", rtn);
//		}else {
//			res.sendRedirect("/admin/main/main0107Pop.do");
//		}

		return modelAndView;
	}

	/**
	 * 메인화면 호출
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/main/main0101Page.do"})
	public ModelAndView mainPage(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception, IOException {
		
//		HashMap<String, Object> rtn = new HashMap<String, Object>();
//
//		try{
//			modelAndView.addObject("result", rtn);
//
//		}catch(Exception e){
//			log.error(e.getMessage());
//		}
//		log.error(modelAndView);

		return modelAndView;
	}

	/**
	 * 비밀번호 확인
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/main/pwdCheck.do"})
	public ModelAndView pwdCheck(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {

		HashMap<String, Object> rtn = new HashMap<String, Object>();

		try{

			String passwdEnc = EncryptUtils.getHash((String)param.get("passwd"));
			param.put("passwdEnc", passwdEnc);
			rtn = amdinMainService.pwdCheck(param);

//			if(String.valueOf(rtn.get("errorCode")) == "00") {
//				session.setEtc("Y");
//			}

		}catch(Exception e){
			//rtn.put("errorCode", e.getCode());
			//rtn.put("errorMessage", e.getMessage());
			log.debug(e.getMessage());
		}

		modelAndView.addObject("result", rtn);

		return modelAndView;
	}

	/**
	 * 정보변경
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/main/updateUser.do"})
	public ModelAndView updateUser(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {

		HashMap<String, Object> rtn = new HashMap<String, Object>();

		try{

			param.put("userId", session.getUserId());
			rtn = amdinMainService.updateUser(param);

			if("00".equals(String.valueOf(rtn.get("errorCode")))){
				HttpSession reSession = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();

				// 로그인 여부
				reSession.setAttribute("userNm", String.valueOf(param.get("userNm")));
				reSession.setAttribute("telNo", String.valueOf(param.get("telNo")));
				reSession.setAttribute("hpNo", String.valueOf(param.get("hpNo")));
				reSession.setAttribute("email", String.valueOf(param.get("email")));
				//				session.setUserNm(String.valueOf(param.get("userNm")));
				//				session.setTelNo(String.valueOf(param.get("telNo")));
				//				session.setHpNo(String.valueOf(param.get("hpNo")));
				//				session.setEmail(String.valueOf(param.get("email")));
			}

		}catch(Exception e){
			//rtn.put("errorCode", e.getCode());
			//rtn.put("errorMessage", e.getMessage());
			log.debug(e.getMessage());
		}

		modelAndView.addObject("result", rtn);

		return modelAndView;
	}




}