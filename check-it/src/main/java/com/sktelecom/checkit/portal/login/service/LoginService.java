package com.sktelecom.checkit.portal.login.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sktelecom.checkit.core.common.service.CommonService;
import com.sktelecom.checkit.core.util.EncryptUtils;
import com.sktelecom.checkit.portal.login.dao.LoginDAO;

/**
 * 포털 로그인 Service
 * @author JY.Park
 *
 */
@Service
public class LoginService {

	private final static Log log = LogFactory.getLog(LoginService.class.getName());

	/* 로그인  DAO */
	@Resource
	private LoginDAO loginDAO;
		
	@Autowired
    private CommonService commonService;

	/**
	 * 로그인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> loginCheck(HashMap<String, Object> param) throws Exception{

		HashMap<String, Object> rtn = new HashMap<String, Object>();
		HashMap<String, Object> userMap = new HashMap<String, Object>();
//		HashMap<String, Object> rollMap = new HashMap<String, Object>();
		
		String passwd = EncryptUtils.getHash(String.valueOf(param.get("passwd")));
		param.put("passwd", passwd);
		
		// 로그인 체크 (사용자정보 조회)
		userMap = loginDAO.loginCheck(param);

		// user ID가 존재(사용중)하는 경우 
		if (userMap.isEmpty()) {
			rtn.put("errorCode", "99");
			rtn.put("errorMessage", "아이디 또는 비밀번호를 확인해주세요.");
		}
		else {
			// 잠김 상태 체크 (userStatus - 1: 대기, 2: 사용, 3: 잠김)
			if (String.valueOf(userMap.get("userStatus")).equals("3") || Integer.valueOf(String.valueOf(userMap.get("loginFailCnt"))) >= 5) {
				rtn.put("errorCode", "91");
				rtn.put("errorMessage", "잠긴 계정입니다.\n비밀번호를 초기화 하시기 바랍니다.");				
			}
			else {								
				// 비밀번호 체크
				if (!passwd.equals(userMap.get("password"))) {
					loginDAO.loginUserPwdErr(param);
					rtn.put("errorCode", "98");
					rtn.put("errorMessage", "아이디 또는 비밀번호를 확인해주세요.");
				}
				else {
					// 비밀번호 만료일 체크
					if ("Y".equals(userMap.get("isExpired"))) {
						rtn.put("errorCode", "92");
						rtn.put("errorMessage", "비밀번호가 만료되었습니다.\n비밀번호를 변경하시기 바랍니다.");
					}					
					else {				
						HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession(true);						
						String clientIp = (String) param.get("userIP");
						
						session.setAttribute("isLogin", true); // 로그인 여부						
						session.setAttribute("loginTime", String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))); // 로그인 시간
						session.setAttribute("userId", String.valueOf(userMap.get("userId"))); // 사용자 ID
						session.setAttribute("userNm", String.valueOf(userMap.get("userName"))); // 사용자 이름				
						session.setAttribute("lastIp", clientIp);// 최종접속IP
						session.setAttribute("lastDt", String.valueOf(LocalDateTime.now()));// 최종접속일시
						
						// 권한 및 메뉴목록을 여기서 설정할 것인지 확인 후 추가
//						List<HashMap<String, Object>> menuList;
//						session.setAttribute("rid", String.valueOf(rollMap.get("rid")));
//						param.put("rid", String.valueOf(rollMap.get("rid")));
//						param.put("sysType", String.valueOf(userMap.get("sysType")));
//						menuList = commonService.getAdMenuList(param);
//						session.setAttribute("menuList", menuList);
//						session.setMaxInactiveInterval(60*10); // 세션유지 시간 10분
						
						// 쿠키 필요성 확인 후 추가
//						Cookie storeIdCookie = new Cookie("storeIdCookie", String.valueOf(userMap.get("userId")));
//					    storeIdCookie.setPath(serverType + "/adLogin.do");
//					    storeIdCookie.setMaxAge(60 * 60 * 24 * 30);					    
//					    ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse().addCookie(storeIdCookie);
						
						param.put("lastIp", clientIp);
						loginDAO.loginUserPwdInit(param);
						
						rtn.put("errorCode", "00");
						rtn.put("errorMessage", "로그인 정상");
					}
				}
			}			
		}
		
		return rtn;
	}
	
}
