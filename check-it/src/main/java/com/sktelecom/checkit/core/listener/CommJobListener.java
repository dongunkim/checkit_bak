package com.sktelecom.checkit.core.listener;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sktelecom.checkit.core.common.service.CommonService;


public class CommJobListener implements ServletContextListener {

	private final static Log log = LogFactory.getLog(CommJobListener.class.getName());

	private ServletContext servletContext = null;

	/**
	 * 서버 shutdown
	 */
	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		this.servletContext = null;
	}

	/**
	 * 서버 실행시 같이 실행
	 */
	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		this.servletContext = contextEvent.getServletContext();

		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		CommonService commonService = webApplicationContext.getBean(CommonService.class);

		try {
			// 고객지원시스템 메뉴만 캐쉬처리
			HashMap<String, Object> param = new HashMap<String, Object>();

			/*
			param.put("sysType", "2");
			commonService.getMenuList(param);	// 한글 메뉴리스트
			*/

		}catch(Exception e) {
			log.error("Cache excute Error");
		}
	}


}

