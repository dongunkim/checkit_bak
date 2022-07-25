package com.sktelecom.checkit.portal.websvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sktelecom.checkit.core.annotation.Api;
import com.sktelecom.checkit.core.annotation.Paging;
import com.sktelecom.checkit.core.util.ExcelUtils;
import com.sktelecom.checkit.portal.websvc.service.WebSvcDiagService;

/**
 * 
 */
@Controller
@RequestMapping("/websvc")
public class WebSvcDiagController{
	private final static Log log = LogFactory.getLog(WebSvcDiagController.class);

	@Resource
	private WebSvcDiagService webSvcDiagService;

	/**
	 * 
	 */
	@RequestMapping({"/webSvcDiagList.do"})
	public ModelAndView webSvcDiagList(ModelAndView modelAndView) throws Exception {
		return modelAndView;
	}

	/**
	 * 
	 */
	@Paging(value=true)
	@Api
	@RequestMapping({"/selWebSvcDiagList.ajax"})
	@ResponseBody
	public Map<String, Object> selWebSvcDiagList(@RequestParam HashMap<String, Object> param) throws Exception {
		return webSvcDiagService.selWebSvcDiagList(param);
	}

	/**
	 * 
	 */
	@Api
	@RequestMapping({"/selWebSvcDiagExcel.ajax"})
	@ResponseBody
	@SuppressWarnings({"unchecked","rawtypes"})
	public void selWebSvcDiagExcel(HttpServletResponse res, @RequestParam HashMap<String, Object> param) throws Exception {
		List<Map<String, Object>> list = (List)webSvcDiagService.selWebSvcDiagList(param).get("list");
		
		String fileName = "웹서비스_진단목록";
		String[] columnNames = {"테스트"};
		String[] columnIds = {"diagId:L"};
		String[] columnWidths = {"4000"};
		
		List<String[]> columnInfo = new ArrayList<String[]>();
		columnInfo.add(columnNames);
		columnInfo.add(columnIds);
		columnInfo.add(columnWidths);
		
		//TODO Excel Download Exception 처리
		ExcelUtils.downloadExcel(fileName, columnInfo, list, res);
	}

	/**
	 * 
	 */
	@RequestMapping({"/webSvcDiagDetail.do"})
	public ModelAndView selWebSvcDiagDetail(ModelAndView modelAndView, @RequestParam HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> rtn = webSvcDiagService.selWebSvcDiagDetail(param);
		if(!"00".equals(rtn.get("errorCode").toString())) throw new Exception();
		
		rtn.put("param", param.get("param"));
		modelAndView.addObject("result", rtn);
		return modelAndView;
	}
}
