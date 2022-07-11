package com.sktelecom.checkit.core.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class CommLoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

	private static final Logger log = LoggerFactory.getLogger(CommLoginFailHandler.class);

	private String userIdTagName;       // 로그인 id값이 들어오는 input 태그 name
	private String passwdTagName;   // 로그인 password 값이 들어오는 input 태그 name
	private String loginredirectname; // 로그인 성공시 redirect 할 URL이 지정되어 있는 input 태그 name
	private String exceptionMsg;  // 예외 메시지를 request의 Attribute에 저장할 때 사용될 key 값
	private String defaultFailureUrl; // 화면에 보여줄 URL(로그인 화면)


	public CommLoginFailHandler(){
		this.userIdTagName	   = "userId";
		this.passwdTagName	   = "passwd";
		this.loginredirectname = "loginRedirect";
		this.exceptionMsg      = "securityexceptionmsg";
		this.defaultFailureUrl = "/login.do";

		this.setDefaultFailureUrl("/my/main/main0102Page.do?errorCode=01");
	}

	public void setUserIdTagName(String userIdTagName) {
		this.userIdTagName = userIdTagName;
	}

	public String getPasswd() {
		return passwdTagName;
	}

	public void setPasswdTagName(String passwdTagName) {
		this.passwdTagName = passwdTagName;
	}

	public String getLoginredirectname() {
		return loginredirectname;
	}

	public void setLoginredirectname(String loginredirectname) {
		this.loginredirectname = loginredirectname;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	@Override
	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException exception) throws IOException, ServletException {


//		String userId = req.getParameter(userIdTagName);
//		String loginpasswd = req.getParameter(passwdTagName);
//		String loginRedirect = req.getParameter(loginredirectname);
//		req.setAttribute(loginidname, loginid);
//		req.setAttribute(loginpasswdname, loginpasswd);
//		req.setAttribute(loginredirectname, loginRedirect);


//		super.onAuthenticationFailure(req, res, exception);

		res.setContentType("application/json");
		res.setCharacterEncoding("utf-8");
		String data = StringUtils.join(new String[]{
													" {",
													" \"error\" : true , ",
													" \"message\" : \"" + exception.getMessage() + "\" ",
													"} "
													}
									);
		PrintWriter out = res.getWriter();
		out.print(data);
		out.flush();
		out.close();
	}
}