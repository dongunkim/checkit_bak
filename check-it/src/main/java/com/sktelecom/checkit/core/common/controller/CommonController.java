package com.sktelecom.checkit.core.common.controller;

import java.io.File;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sktelecom.checkit.core.annotation.NoLogin;
import com.sktelecom.checkit.core.annotation.Paging;
import com.sktelecom.checkit.core.common.service.CommonService;
import com.sktelecom.checkit.core.file.FileUpload;
import com.sktelecom.checkit.core.util.DateUtils;
import com.sktelecom.checkit.core.util.Session;

/**
 * 공통 controller
 * @author devkimsj
 *
 */
@Controller
@RequestMapping("/common")
public class CommonController {
	private final static Log log = LogFactory.getLog(CommonController.class);

	@Resource
	private CommonService commonService;

	@Resource
	private FileUpload fileUpload;
	/**
	 * 권한없음
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/accessDenied.do"})
	public ModelAndView accessDenied(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> rtn = new HashMap<String, Object>();

		try{

			modelAndView.addObject("result", rtn);

		}catch(Exception e){
			log.error(e.getMessage());
		}

		return modelAndView;

	}

	/**
	 * 세션 시간 체크
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/accessTime.do"})
	public ModelAndView accessTime(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {

		HashMap<String, Object> rtn = new HashMap<String, Object>();

		try{

			int sessionTime = session.getMaxInactiveInterval();

			rtn.put("sessionTime", sessionTime);

			modelAndView.addObject("result", rtn);

		}catch(Exception e){
			log.error(e.getMessage());
		}

		return modelAndView;

	}

	/**
	 * 세션 연장
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/accessNew.do"})
	public ModelAndView accessNew(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {

		try{

			session.setMaxInactiveInterval(10*60);
			modelAndView.addObject("result", param);

		}catch(Exception e){
			log.error(e.getMessage());
		}

		return modelAndView;

	}

	@Paging(value=true)
	@RequestMapping({"/comm0109Pop.do"})
	public ModelAndView commPage(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		modelAndView.addObject("result", param);
		return modelAndView;
	}

	/**
	 * 공통코드
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@NoLogin
	@RequestMapping({"/getCommonCode.do"})
	public ModelAndView getCommonCode(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestBody HashMap<String, Object> param, Session session) throws Exception {

		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{

			String code = "";
			if(null != param.get("root")) {
				code = String.valueOf(param.get("root"));
				rtn = commonService.selCode(code);
			}else{
				code = String.valueOf(param.get("code"));
				rtn = commonService.selCode(code);
			}

			modelAndView.addObject("result", rtn);

		}catch(Exception e){
			log.error(e.getMessage());
		}

		return modelAndView;

	}
	
	/**
	 * 공통코드 (하위 depth code 설정)
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@NoLogin
	@RequestMapping({"/getCommonCodeSubDepth.do"})
	public ModelAndView getCommonCodeSubDepth(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestBody HashMap<String, Object> param, Session session) throws Exception {
		
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		try{
			params.put("code", String.valueOf(param.get("code")));
			params.put("higher", String.valueOf(param.get("higher")));
			
			rtn = commonService.selCodeSubDepth(params);
			modelAndView.addObject("result", rtn);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		
		return modelAndView;
		
	}

	/**
	 * 파일조회
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/commFileList.do"})
	public ModelAndView commFileList(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> rtn = new HashMap<String, Object>();

		try{

			rtn = commonService.selAttachFileList(param);

			modelAndView.addObject("result", rtn);

		}catch(Exception e){
			log.error(e.getMessage());
		}

		return modelAndView;

	}

	/**
	 * 파일업로드
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping({"/fileupload.do"})
	public ModelAndView fileupload(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res) throws Exception {

		HashMap<String, Object> rtn = new HashMap<String, Object>();
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<HashMap<String, Object>> fileList = null;
		String uuid = null;
		String attachId = null;

		try{

			uuid = String.valueOf(req.getParameter("uuid"));
			attachId = String.valueOf(req.getParameter("attachId"));

			String keyStr = "filePath.upload.tmp";     // context-properties 파일에 설정된 파일경로
			result = fileUpload.tmpUpload(req, keyStr, uuid, attachId);
			fileList = (List<HashMap<String, Object>>)result.get("fileList");

		}catch(Exception e){
			//log.debug(e.getCode());
			log.debug(e.getMessage());
			//result.put("errorCode", e.getCode());
			//result.put("errorMessage", e.getMessage());
		}

		modelAndView.addObject("result", result);

		return modelAndView;

	}

	/**
	 * 파일다운로드
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/fileDownload.do"})
	public ModelAndView filedownload(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> fileInfo = new HashMap<String, Object>();
		String filePath = "";
		File file = null;

		try {

			modelAndView = new ModelAndView("downloadView");

			fileInfo = commonService.selAttachFileDetail(param);

			filePath = String.valueOf(fileInfo.get("filePath")) + File.separator + String.valueOf(fileInfo.get("fileName"));
			file = new File(filePath);
			modelAndView.addObject("file", file);
			modelAndView.addObject("fileOriginName", String.valueOf(fileInfo.get("fileOriginName")));

		}catch(Exception e){
			log.error(e.getMessage());
		}

		return modelAndView;

	}

	/**
	 * CSV 다운로드
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/csvDownload.do")
	public ModelAndView csvDownload(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {

		File file = null;

		try {
			file = session.getCsvFile();
			modelAndView = new ModelAndView("csvDownloadView", "file", file);
		
		}catch(Exception e){
			log.error(e.getMessage());
			
		}finally {
			session.setCsvFile(null);
		}

		return modelAndView;

	}

	/**
	 * 워드 다운로드
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/wordDownload.do")
	public ModelAndView wordDownload(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {

		List<HashMap<String, Object>> csvList = null;
		List<HashMap<String, Object>> headList  = null;
		File file = null;
		String html = "";
		try {
			html = String.valueOf(param.get("html"));
//			file = session.getCsvFile();
//			modelAndView = new ModelAndView("csvDownloadView", "file", file);
			modelAndView.addObject("result", URLDecoder.decode(html));
		}catch(Exception e){
			log.error(e.getMessage());
		}

		return modelAndView;

	}

	/**
	 * 대상자 설정리스트 - 그룹
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/comm0105Pop.do"})
	public ModelAndView comm0105Pop(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		modelAndView.addObject("result", param);
		return modelAndView;
	}

	/**
	 * 대상자 설정리스트 - 그룹등록
	 * @param modelAndView
	 * @param req
	 * @param res
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/comm0107Pop.do"})
	public ModelAndView comm0107Pop(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse res, @RequestParam HashMap<String, Object> param, Session session) throws Exception {
		param.put("makeUserNm", session.getUserNm());
		param.put("makeDt", DateUtils.getToday("yyyy-MM-dd"));
		modelAndView.addObject("result", param);
		return modelAndView;
	}

}
