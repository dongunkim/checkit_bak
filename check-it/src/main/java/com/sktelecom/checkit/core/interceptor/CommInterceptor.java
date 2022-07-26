package com.sktelecom.checkit.core.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.cxf.message.Message;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sktelecom.checkit.core.annotation.Api;
import com.sktelecom.checkit.core.annotation.NoLogin;
import com.sktelecom.checkit.core.annotation.Paging;
import com.sktelecom.checkit.core.util.CommMap;
import com.sktelecom.checkit.core.util.Session;
import com.sktelecom.checkit.core.util.StringUtils;

/**
 * CommInterceptor
 * @author devkimsj
 * @param <T>
 *
 */
public class CommInterceptor<T extends Message> implements HandlerInterceptor {

	private static final Logger log = LoggerFactory.getLogger(CommInterceptor.class.getName());
				
	@SuppressWarnings("resource")
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		
		Session session = new Session();

		if("Y".equals(String.valueOf(req.getParameter("search")))) {
			session.setParam(req.getParameterMap());
		}else {
			session.setParam(null);
		}

		
		/********************************************************************
		 * 접속자 IP 로깅 START
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
         * 모든 URL 로깅
         */
        log.info(request_info.toString());
        	
		/********************************************************************
		 * 접속자 IP 로깅 END
		 ********************************************************************/
        
        // 로그인 어노테이션 확인
     	NoLogin noLogin = ((HandlerMethod)handler).getMethodAnnotation(NoLogin.class);
     	
     	// 로그인이 필요없는 URL
        if (noLogin != null) {        	
			// 페이징 어노테이션 확인
			Paging paging = ((HandlerMethod)handler).getMethodAnnotation(Paging.class);
			session.setPaging(Boolean.valueOf((paging != null) ? paging.value() : false));
			
			return true;			
		}
        // 로그인이 필요한 URL
        else {
			String url = req.getRequestURI().toString();
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ConfigurableEnvironment env = ctx.getEnvironment();
			String domainType = env.getProperty("domain.type");

			// 로그인 안한 상태
			if (!session.isLogin()) {
				// IP(내부망) 사이트인 경우
				if ("IP".equals(domainType)) {
					// Admin 페이지인 경우
					if (url.startsWith("/admin/")) {
						res.sendRedirect("/admin/login/login0101Page.do");
					} 
					// 사용자 페이지인 경우	
					else {
						res.sendRedirect("/login/login0101Page.do");
					}					
				}
				// BP(외부망) 사이트인 경우
				else {
					res.sendRedirect("/login/login0101Page.do");
				}
				return false;
			}
			// 로그인 한 상태
			else {			
				// 세션 로깅
				HttpSession sessionChk = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession(false);		
				log.info("session chk Interceptor preHandle not null >>>>>>>>>>>>>>>>>>>>> " + sessionChk.getId() );
				log.info("session chk Interceptor preHandle not null session.getUserId() >>>>>>>>>>>>>>>>>>>>> " + session.getUserId());
				
				// .ajax가 아닌 .do URL에 대한 마지막 접근 시각 기록
				//if (url.lastIndexOf(".do") > -1) session.setUserLastAccessedTime(System.currentTimeMillis());

				// 페이징 어노테이션 확인
				Paging paging = ((HandlerMethod)handler).getMethodAnnotation(Paging.class);
				session.setPaging(Boolean.valueOf((paging != null) ? paging.value() : false));
				
				return true;
			}
        }
	}

	@Override	
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView modelAndView) throws Exception {
		// 세션 로깅
		HttpSession sessionChk = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession(false);		
		if (sessionChk != null) {
			log.info("session chk Interceptor postHandle >>>>>>>>>>>>>>>>>>>>> " + sessionChk.getId());
		}

		// API(Ajax) 어노테이션 확인
		Api api = ((HandlerMethod)handler).getMethodAnnotation(Api.class);
		String url = req.getRequestURI().toString();
		Session session = new Session();
		
		// (Ajax가 아니고) 페이지 호출인 경우, 현재 MenuID와 메뉴목록 설정
		if (api == null) {			
			if (modelAndView != null && modelAndView.getViewName() != null) {
				List<CommMap> adMenuList;
				
				if (modelAndView.getViewName() != null && "jsonView".indexOf(modelAndView.getViewName()) == -1) {
					ObjectMapper objectMapper = new ObjectMapper();

					// 현재 메뉴 설정
					adMenuList = session.getMenuList();
					if (adMenuList != null && adMenuList.size() > 0) {
						String rollUrl = url.substring(0, url.indexOf(".do"));
						CommMap menuMap = new CommMap();
						for (int i = 0; i < adMenuList.size(); i++) {
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
					modelAndView.addObject("menuList", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(adMenuList));

					// TODO
					modelAndView.addObject("result", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(modelAndView.getModelMap().get("result")));
				}
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) throws Exception {
	}
	
}
