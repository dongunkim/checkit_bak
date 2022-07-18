package com.sktelecom.checkit.core.security;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sktelecom.checkit.core.security.service.CommLoginService;
import com.sktelecom.checkit.core.util.EncryptUtils;

public class CommLoginProvider implements AuthenticationProvider {

	private static final Logger logger = LoggerFactory.getLogger(CommLoginProvider.class);

	@Autowired
    private CommLoginService commLoginService;

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String userId = (String)authentication.getPrincipal();
		String passwd = EncryptUtils.getHash((String)authentication.getCredentials());

		UsernamePasswordAuthenticationToken result = null;

		HashMap<String, Object> userMap = new HashMap<String, Object>();
		HashMap<String, Object> menuMap = new HashMap<String, Object>();

		try{

			logger.debug("Welcome authenticate! {}", userId + "/" + passwd);

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userId", userId);

//			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//			ConfigurableEnvironment env = ctx.getEnvironment();
//			String serverType = env.getProperty("domain.type");

			userMap = commLoginService.loginAdUserInfo(param);
//			if("ad".equals(serverType)) {
//				userMap = commLoginService.loginAdUserInfo(param);
//			}else {
//				userMap = commLoginService.loginMyUserInfo(param);
//			}

			if(userMap != null){

				if(!userMap.isEmpty()) {
					if(Integer.valueOf(String.valueOf(userMap.get("loginFailCnt"))) >= 5){
						throw new BadCredentialsException("비밀번호 5회입력에 5회 실패하셨습니다.관리자에게 문의 잠김을 풀어주세요.");
					}
				}

//				logger.debug((String)userMap.get("passwd") + "/" + passwd);
				if(passwd.equals(userMap.get("passwd"))){
					HttpSession session = null;

					session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession(true);

					// 로그인 여부
					session.setAttribute("isLogin", true);

					session.setAttribute("sysType", String.valueOf(userMap.get("sysType")));
					// 로그인 시간
					session.setAttribute("loginTime", String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
					// 사용자 ID
					session.setAttribute("userId", String.valueOf(userMap.get("userId")));
					// 사용자 이름
					session.setAttribute("userNm", String.valueOf(userMap.get("userNm")));
					// 전화번호
					session.setAttribute("telNo", String.valueOf(userMap.get("telNo")));
					// 휴대폰번호
					session.setAttribute("hpNo", String.valueOf(userMap.get("hpNo")));
					// 이메일
					session.setAttribute("email", String.valueOf(userMap.get("email")));
					// CS번호
					session.setAttribute("csNo", String.valueOf(userMap.get("csNo")));
					// CS명
					session.setAttribute("csName", String.valueOf(userMap.get("csName")));
					//
					session.setAttribute("chorusNo", String.valueOf(userMap.get("chorusNo")));
					//
					session.setAttribute("ukeyNo", String.valueOf(userMap.get("ukeyNo")));
					//
					session.setAttribute("csAgentUserid", String.valueOf(userMap.get("csAgentUserid")));
					//
					session.setAttribute("csAgentUserid2", String.valueOf(userMap.get("csAgentUserid2")));
					// 사용여부
					session.setAttribute("status", String.valueOf(userMap.get("status")));
					// 관리자 센터코드
					session.setAttribute("manageCid", String.valueOf(userMap.get("manageCid")));
					// 관리자 센터명
					session.setAttribute("manageCidNm", String.valueOf(userMap.get("manageCidNm")));
					// 최종접속IP
					session.setAttribute("lastIp", String.valueOf(userMap.get("lastIp")));
					// 최종접속일시
					session.setAttribute("lastDt", String.valueOf(userMap.get("lastDt")));
					//
					session.setAttribute("csAgentUserNm", String.valueOf(userMap.get("csAgentUserNm")));
					//
					session.setAttribute("csAgentHpNo", String.valueOf(userMap.get("csAgentHpNo")));
					//
					session.setAttribute("csAgentTelNo", String.valueOf(userMap.get("csAgentTelNo")));
					//
					session.setAttribute("csAgentEmail", String.valueOf(userMap.get("csAgentEmail")));
					// 관리자여부
					session.setAttribute("adminYn", String.valueOf(userMap.get("adminYn")));

					session.setAttribute("loginFailCnt", String.valueOf(userMap.get("loginFailCnt")));

					session.setMaxInactiveInterval(60*10); // 세션유지 시간 10분

					param.put("sysType", String.valueOf(userMap.get("sysType")));

					List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
					roles.add(new SimpleGrantedAuthority("ROLE_USER"));
					result = new UsernamePasswordAuthenticationToken(userId, passwd, roles);
					result.setDetails(new CommLoginUserDetails(userId, passwd));
				}else{

//					if(!"ad".equals(serverType)) {
//						commLoginService.loginMyUserPwdErr(param);
//					}

					throw new BadCredentialsException("아이디 또는 비밀번호를 확인해주세요.");
				}

			}

		}catch(UsernameNotFoundException e){
			logger.debug(e.toString());
			throw new UsernameNotFoundException(e.getMessage());
		}catch(BadCredentialsException e){
			logger.debug(e.toString());
			throw new BadCredentialsException(e.getMessage());
		}catch(RuntimeException e){
			logger.debug(e.toString());
			throw new RuntimeException(e.getMessage());
		}catch(Exception e){
			logger.debug(e.toString());
			throw new NullPointerException(e.getMessage());
		}

		return result;

	}
}