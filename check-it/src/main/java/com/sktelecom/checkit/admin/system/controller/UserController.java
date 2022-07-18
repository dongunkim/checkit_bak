package com.sktelecom.checkit.admin.system.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sktelecom.checkit.admin.system.service.Sysm01Service;
import com.sktelecom.checkit.core.annotation.Mask;
import com.sktelecom.checkit.core.annotation.Paging;
import com.sktelecom.checkit.core.util.Session;

/**
 * 고객지원시스템 > 시스템관리
 * @author sj.kim
 * @since 2018.08.02
 */
@Controller
@RequestMapping("/admin/system")
public class UserController{
	private final static Log log = LogFactory.getLog(UserController.class);

	@Resource
	private Sysm01Service sysm01Service;

	/**
	 * 사용자관리 목록
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Mask
	@Paging(value=true)
	@RequestMapping(value="/sysm0101List.do", method=RequestMethod.POST)
	public ModelAndView sysm0101List(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = sysm01Service.sysm0101List(param);
			modelAndView.addObject("result", rtn);
//			session.setMaskIsComplete(false);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return modelAndView;
	}

	/**
	 * 사용자정보 조회
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/sysm0101Info.do"})
	public ModelAndView sysm0101Info(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = sysm01Service.sysm0101Info(param);
			modelAndView.addObject("result", rtn);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return modelAndView;
	}

	/**
	 * 사용자 권한 목록
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Paging(value=false)
	@RequestMapping({"/sysm0102List.do"})
	public ModelAndView sysm0102List(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = sysm01Service.sysm0102List(param);
			modelAndView.addObject("result", rtn);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return modelAndView;
	}

	/**
	 * 권한 목록
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Paging(value=false)
	@RequestMapping({"/sysm0102Pop.do"})
	public ModelAndView sysm0102Pop(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = sysm01Service.sysm0102PopList(param);
			rtn.put("userId", param.get("userId"));
		}catch(Exception e){
			log.error(e.getMessage());
		}
		modelAndView.addObject("result", rtn);
		return modelAndView;
	}

	/**
	 * 권한 등록
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/insertSysm0101.do"})
	public ModelAndView insertSysm0101(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			param.put("regId", session.getUserId());
			rtn = sysm01Service.insertSysm0101(param);
		}catch(Exception e){
			//rtn.put("errorCode", e.getCode());
			//rtn.put("errorMessage", e.getMessage());
			log.error(e.getMessage());
		}
		modelAndView.addObject("result", rtn);
		return modelAndView;
	}

	/**
	 * 사용자 정보 수정
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/insertSysm0102.do"})
	public ModelAndView insertSysm0102(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			param.put("updId", session.getUserId());
			rtn = sysm01Service.insertSysm0102(param);
		}catch(Exception e){
			//rtn.put("errorCode", e.getCode());
			//rtn.put("errorMessage", e.getMessage());
			log.debug(e.getMessage());
		}
		modelAndView.addObject("result", rtn);
		return modelAndView;
	}

	/**
	 * 사용자 삭제
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/deleteSysm0102.do"})
	public ModelAndView deleteSysm0102(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			param.put("updId", session.getUserId());
			rtn = sysm01Service.deleteSysm0102(param);
		}catch(Exception e){
			//rtn.put("errorCode", e.getCode());
			//rtn.put("errorMessage", e.getMessage());
			log.error(e.getMessage());
		}
		modelAndView.addObject("result", rtn);
		return modelAndView;
	}

	/**
	 * 사용자관리 소속 상세 AJAX 목록
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Paging(value=false)
	@RequestMapping({"/sysm0101srchFormationCd2.do"})
	public ModelAndView sysm0101srchFormationCd2(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = sysm01Service.sysm0101srchFormationCd2(param);
			modelAndView.addObject("result", rtn);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return modelAndView;
	}

}
