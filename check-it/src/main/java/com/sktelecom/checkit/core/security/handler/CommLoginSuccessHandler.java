package com.sktelecom.checkit.core.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class CommLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	private static final Logger log = LoggerFactory.getLogger(CommLoginSuccessHandler.class);
	
	public CommLoginSuccessHandler(){
		this.setDefaultTargetUrl("/demo/common/demoPage.do");
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException {
		
//		super.onAuthenticationSuccess(req, res, auth);
		res.setContentType("application/json");
		res.setCharacterEncoding("utf-8");
		
		String data = StringUtils.join(new String[]{
													" { \"response\" : {",
													" \"error\" : false , ",
													" \"message\" : \"로그인하였습니다.\" ",
													"} } "
													}
										);
		
		PrintWriter out = res.getWriter();
		out.print(data);
		out.flush();
		out.close();
		
	}
}