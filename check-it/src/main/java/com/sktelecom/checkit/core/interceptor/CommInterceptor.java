package com.sktelecom.checkit.core.interceptor;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.cxf.message.Message;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sktelecom.checkit.core.annotation.Api;
import com.sktelecom.checkit.core.annotation.Mask;
import com.sktelecom.checkit.core.annotation.NoLogin;
import com.sktelecom.checkit.core.annotation.Paging;
import com.sktelecom.checkit.core.common.SingLoginService;
import com.sktelecom.checkit.core.common.dao.CommonDAO;
import com.sktelecom.checkit.core.common.service.CommonService;
import com.sktelecom.checkit.core.util.ExcelUtils;
import com.sktelecom.checkit.core.util.Session;
import com.sktelecom.checkit.core.util.CommMap;
import com.sktelecom.checkit.core.util.StringUtils;
import com.sktelecom.checkit.core.util.TypeCastUtils;
import com.sktelecom.checkit.core.util.XssMaskUtils;
import com.sktelecom.checkit.core.util.XssUtils;

/**
 * CommInterceptor
 * @author devkimsj
 * @param <T>
 *
 */
public class CommInterceptor<T extends Message> implements HandlerInterceptor {

	private static final Logger log = LoggerFactory.getLogger(CommInterceptor.class.getName());
		
	@Autowired
    private CommonService commonService;

	@Autowired
	private ExcelUtils excelUtils;
	
	@Resource
    private CommonDAO commonDAO;
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {

		// 로그인 어노테이션 판단
		NoLogin noLogin = ((HandlerMethod)handler).getMethodAnnotation(NoLogin.class);
		Session session = new Session();

		if("Y".equals(String.valueOf(req.getParameter("search")))) {
			session.setParam(req.getParameterMap());
		}else {
			session.setParam(null);
		}

		
		/********************************************************************
		 * 접속자 IP 로그를 위해 START
		 * log4j에 선언할 경우  log4j.xml 에
		 *       <param name="ConversionPattern" value="[%X{client_req_info}] %-5p: %c - %m%n" />
		 ********************************************************************/
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
        StringBuffer request_info = new StringBuffer();
        request_info.append("IP["+ip+"]");
        if(session.isLogin()){
        	request_info.append(" USER["+session.getUserId()+"]");
        }
        request_info.append(" URL["+req.getRequestURI()+"]");
        MDC.put("client_req_info", request_info.toString());
       
        /**
         * 아래의 url이 아닐경우 로그를 찍은나
         * log4j 선언
         */
        if( req.getRequestURI().indexOf("/common/getCommonCode.do") < 0 ) 
        {
        	log.info(request_info.toString());        
        }
        	
		/********************************************************************
		 * 접속자 IP 로그를 위해 END
		 ********************************************************************/
        
        if(noLogin != null){
			// 페이징  어노테이션
			Paging paging = ((HandlerMethod)handler).getMethodAnnotation(Paging.class);
			if("Y".equals(req.getParameter("csvDownload"))) {
				session.setPaging(false);
			}else {
				session.setPaging(Boolean.valueOf((paging != null)?paging.value():false));
			}
			
			return true;
			
		}else{

			Enumeration enumeration = req.getHeaderNames();

			String url = req.getRequestURI().toString();
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ConfigurableEnvironment env = ctx.getEnvironment();
			String serverType = env.getProperty("domain.type");

			if(!session.isLogin()){
				if("ad".equals(serverType)) {
					if(url.indexOf("/common/sendUrl/") == 0 ) {
						res.sendRedirect("/admin/login/login0101Page.do?returnUrl="+URLEncoder.encode(url, "UTF-8").replaceAll("\\+", "%20"));
					} else {
						res.sendRedirect("/admin/login/login0101Page.do");
					}
					
				}else{
					res.sendRedirect("/my/login/login0101Page.do");
				}
				return false;
			}else{
//				String referrer = req.getHeader("Referer");
//				req.getSession().setAttribute("url_prior_login", referrer);
				
				//중복로그인 방지 체크
				HttpSession sessionChk = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession(false);		
			
				log.info("session chk Interceptor preHandle not null >>>>>>>>>>>>>>>>>>>>> " + sessionChk.getId() );
				log.info("session chk Interceptor preHandle not null session.getUserId() >>>>>>>>>>>>>>>>>>>>> " + session.getUserId());
				//boolean isAjax = req.getHeader("AJAX") != null;
				//!isAjax && 
				
				if(req.getRequestURL().indexOf("accessTime.do") == -1) {
					session.setUserLastAccessedTime(System.currentTimeMillis());
				}

				// 페이징  어노테이션
				Paging paging = ((HandlerMethod)handler).getMethodAnnotation(Paging.class);
				if("Y".equals(req.getParameter("csvDownload"))) {
					session.setPaging(false);
				}else {
					session.setPaging(Boolean.valueOf((paging != null)?paging.value():false));
				}
				return true;
			}

        }

	}

	@Override
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView modelAndView) throws Exception {

		// 로그인 어노테이션 판단
		Api api = ((HandlerMethod)handler).getMethodAnnotation(Api.class);
		Mask mask = ((HandlerMethod)handler).getMethodAnnotation(Mask.class);
		boolean isAjax = false;
		Enumeration enumeration = req.getHeaderNames();
		String url = req.getRequestURI().toString();
		Session session = new Session();
		HttpSession sessionChk = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession(false);		
		
		if(sessionChk != null) {
			log.info("session chk Interceptor postHandle >>>>>>>>>>>>>>>>>>>>> " + sessionChk.getId());
			if(session.isLogin()) {
				String urlArr[] = url.split("/");
				String adres = urlArr[1];
				if("my".equals(urlArr[1])) {
					String userAgent = req.getHeader("user-agent");
					String pCode = "22201";
					String mCode = "";
				    boolean mobile1 = userAgent.matches(".*(iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson).*");
				    boolean mobile2 = userAgent.matches(".*(LG|SAMSUNG|Samsung).*");
				    if(mobile1 || mobile2) {
				    	pCode = "22202";
				    }
				    if("cust".equals(urlArr[2])) {
				    	if("cust0301List.do".equals(urlArr[3])) {
				    		mCode= pCode+"01";
				    	}
				    }else if("visi".equals(urlArr[2])) {
				    	if("visi0101List.do".equals(urlArr[3])) {
				    		mCode= pCode+"02";
				    	}
				    }else if("rele".equals(urlArr[2])) {
				    	if("rele0101List.do".equals(urlArr[3]) || "rele0103Reg.do".equals(urlArr[3]) || "rele0105Reg.do".equals(urlArr[3])) {
				    		mCode= pCode+"03";
				    	}
				    }else if("cons".equals(urlArr[2])) {
				    	if("cons0101List.do".equals(urlArr[3]) || "cons0201List.do".equals(urlArr[3]) || "cons0301List.do".equals(urlArr[3]) || "cons0401List.do".equals(urlArr[3])) {
				    		mCode= pCode+"04";
				    	}
				    }else if("main".equals(urlArr[2])) {
				    	if("main0107List.do".equals(urlArr[3]) || "main0109List.do".equals(urlArr[3])) {
				    		mCode= pCode+"05";
				    	}
				    }
				    
			    }
			}
		}
		while(enumeration.hasMoreElements()){
			String headerName = (String)enumeration.nextElement();

			if("AJAX".equals(headerName.toUpperCase())){
				isAjax = true;
				break;
			}

		}

		if(api != null){
			isAjax = true;
		}

		if(isAjax){

			if(modelAndView != null){

				if(modelAndView.getModelMap().get("result") != null){

					if(mask != null) {

						if(session.getMarskRelease()) {
							if(modelAndView.getModelMap().get("result") instanceof HashMap){
								HashMap result = (HashMap)modelAndView.getModelMap().get("result");
								modelAndView.clear();
								modelAndView.addObject("result", XssUtils.stripXSS(result));
							}else if(modelAndView.getModelMap().get("result") instanceof ArrayList){
								ArrayList result = (ArrayList)modelAndView.getModelMap().get("result");
								modelAndView.clear();
								modelAndView.addObject("result", XssUtils.stripXSS(result));
							}else{
								modelAndView.addObject("result", "");
							}
							/*if(!req.getHeader("referer").contains("/admin/sale/sale1301Pop.do")){
								session.setMarskRelease(false);
							}*/
						}else {
							if(modelAndView.getModelMap().get("result") instanceof HashMap){
								HashMap result = (HashMap)modelAndView.getModelMap().get("result");
								modelAndView.clear();
								modelAndView.addObject("result", XssMaskUtils.stripXSS(result));
							}else if(modelAndView.getModelMap().get("result") instanceof ArrayList){
								ArrayList result = (ArrayList)modelAndView.getModelMap().get("result");
								modelAndView.clear();
								modelAndView.addObject("result", XssMaskUtils.stripXSS(result));
							}else{
								modelAndView.addObject("result", "");
							}
							//session.setMarskRelease(false);
						}

					}else {
						if(modelAndView.getModelMap().get("result") instanceof HashMap){
							HashMap result = (HashMap)modelAndView.getModelMap().get("result");
							modelAndView.clear();
							modelAndView.addObject("result", XssUtils.stripXSS(result));
						}else if(modelAndView.getModelMap().get("result") instanceof ArrayList){
							ArrayList result = (ArrayList)modelAndView.getModelMap().get("result");
							modelAndView.clear();
							modelAndView.addObject("result", XssUtils.stripXSS(result));
						}else{
							modelAndView.addObject("result", "");
						}
					}

				}else{
					modelAndView.addObject("result", "");
				}
				
				AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
				  ConfigurableEnvironment env = ctx.getEnvironment();
				  String serverType = env.getProperty("domain.type");
				  
				if( ( !"ad".equals(serverType) && "Y".equals(req.getParameter("csvDownload")) ) ||
					( "Y".equals(req.getParameter("csvDownload")) && "ad".equals(serverType) 
							&& session != null && session.getAdminYn() != null	&& session.getAdminYn().equals("Y"))
						) {
					HashMap result = (HashMap)modelAndView.getModelMap().get("result");
					HashMap param = (HashMap)result.get("param");
					String fileName      = String.valueOf(param.get("fileName"));
					String headListParam = String.valueOf(param.get("headList"));
					TypeCastUtils typeCastUtils = new TypeCastUtils();

					List<HashMap<String, Object>> csvList = (List<HashMap<String, Object>>)result.get("list");
					List<HashMap<String, Object>> headList  = typeCastUtils.stringToArrayList(headListParam);

					
					String searchTextBox = (String)param.get("searchTextBox");
					File file = null;
					if(searchTextBox != null && searchTextBox != "") {
						file = excelUtils.csvDownload(fileName, csvList, headList, req, res, searchTextBox);
					} else {
						file = excelUtils.csvDownload(fileName, csvList, headList, req, res);
					}
					
					session.setCsvFile(file);

				}

				modelAndView.setViewName("jsonView");

			}

		}else{
			if(modelAndView != null && modelAndView.getViewName() != null){
			//if(modelAndView != null){

				List<CommMap> adMenuList;
				List<HashMap<String, Object>> myMenuList;

				if(modelAndView.getViewName() != null &&"jsonView".indexOf(modelAndView.getViewName()) < 0){
					ObjectMapper objectMapper = new ObjectMapper();
					String jsonObject = "";

					if(mask != null) {
						if(session.getMarskRelease()) {
							if(modelAndView.getModelMap().get("result") instanceof HashMap){
								HashMap result = (HashMap)modelAndView.getModelMap().get("result");
								jsonObject = objectMapper.defaultPrettyPrintingWriter().writeValueAsString(XssUtils.stripXSS(result));
							}else if(modelAndView.getModelMap().get("result") instanceof ArrayList){
								ArrayList result = (ArrayList)modelAndView.getModelMap().get("result");
								jsonObject = objectMapper.defaultPrettyPrintingWriter().writeValueAsString(XssUtils.stripXSS(result));
							}else{
								jsonObject = objectMapper.defaultPrettyPrintingWriter().writeValueAsString(XssUtils.stripXSS(""));
							}
							//session.setMarskRelease(false);
						}else {
							if(modelAndView.getModelMap().get("result") instanceof HashMap){
								HashMap result = (HashMap)modelAndView.getModelMap().get("result");
								jsonObject = objectMapper.defaultPrettyPrintingWriter().writeValueAsString(XssMaskUtils.stripXSS(result));
							}else if(modelAndView.getModelMap().get("result") instanceof ArrayList){
								ArrayList result = (ArrayList)modelAndView.getModelMap().get("result");
								jsonObject = objectMapper.defaultPrettyPrintingWriter().writeValueAsString(XssMaskUtils.stripXSS(result));
							}else{
								jsonObject = objectMapper.defaultPrettyPrintingWriter().writeValueAsString(XssMaskUtils.stripXSS(""));
							}
						}

					}else {
						if(modelAndView.getModelMap().get("result") instanceof HashMap){
							HashMap result = (HashMap)modelAndView.getModelMap().get("result");
							jsonObject = objectMapper.defaultPrettyPrintingWriter().writeValueAsString(XssUtils.stripXSS(result));
						}else if(modelAndView.getModelMap().get("result") instanceof ArrayList){
							ArrayList result = (ArrayList)modelAndView.getModelMap().get("result");
							jsonObject = objectMapper.defaultPrettyPrintingWriter().writeValueAsString(XssUtils.stripXSS(result));
						}else{
							jsonObject = objectMapper.defaultPrettyPrintingWriter().writeValueAsString(XssUtils.stripXSS(""));
						}
					}

					AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
					ConfigurableEnvironment env = ctx.getEnvironment();
					String serverType = env.getProperty("domain.type");

					HashMap<String, Object> param = new HashMap<String, Object>();

					if("ad".equals(serverType)) {
					
						adMenuList = session.getMenuList();

						if(adMenuList != null && adMenuList.size() > 0) {
							String rollUrl = url.substring(0, url.indexOf(".do"));
//							HashMap<String, Object> menuMap = new HashMap<String, Object>();
							CommMap menuMap = new CommMap();
							for(int i = 0; i < adMenuList.size(); i++) {
								menuMap = adMenuList.get(i);
								String menuUrl = StringUtils.defaultString(menuMap.get("menuUrl"), "");
								String menuId = StringUtils.defaultString(menuMap.get("menuId"), "");
								if(menuUrl.indexOf(rollUrl) > -1) {
									session.setMenuId(menuId);
									break;
								}
							}
						}

						modelAndView.addObject("menuId", String.valueOf(StringUtils.defaultString(session.getMenuId(), "1")));
						modelAndView.addObject("rid", String.valueOf(session.getRid()));
						modelAndView.addObject("menuList", objectMapper.defaultPrettyPrintingWriter().writeValueAsString(adMenuList));

					}else{
						param.put("sysType", StringUtils.defaultString(session.getSysType(), "2"));
						myMenuList = commonService.getMenuList(param);
						modelAndView.addObject("menuList", objectMapper.defaultPrettyPrintingWriter().writeValueAsString(myMenuList));

					}

					modelAndView.addObject("result", jsonObject);

				}

			}

		}
		
		String requestUri = req.getRequestURI().trim();
		if (requestUri.length() > 3 && requestUri.substring(requestUri.length() - 3, requestUri.length()).equals(".do")) {
			Enumeration<String> params = req.getParameterNames();
			String strParam = "";
			String match = "[^\uAC00-\uD7A30-9a-zA-Z]";
			while (params.hasMoreElements()) {
				String name = (String) params.nextElement();
				String value = req.getParameter(name);
				value = value.replaceAll(System.lineSeparator(), "").replaceAll(match, ""); //파라미터에 특수기호 제거
				if(value.indexOf("{") >= 0) {
					continue;
				}
				strParam += name + "=" + value + "&";
			}
			if (strParam.length() > 0) {
				req.setAttribute("toRequestUri",
						req.getRequestURI() + "?" + strParam.substring(0, strParam.length() - 1));
			} else {
				req.setAttribute("toRequestUri", req.getRequestURI());
			}
		}
		

	}

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) throws Exception {
	}
	
}
