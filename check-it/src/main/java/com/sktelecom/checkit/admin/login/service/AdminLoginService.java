package com.sktelecom.checkit.admin.login.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sktelecom.checkit.admin.login.dao.AdminLoginDAO;
import com.sktelecom.checkit.core.common.SingLoginService;
import com.sktelecom.checkit.core.common.service.CommonService;
import com.sktelecom.checkit.core.util.CommMaskUtils;
import com.sktelecom.checkit.core.util.DateUtils;
import com.sktelecom.checkit.core.util.EncryptUtils;
import com.sktelecom.checkit.core.util.StringUtils;

/**
 * 업무지원시스템 로그인 ServiceImpl
 * @author devkimsj
 *
 */
@Service
public class AdminLoginService {

	private final static Log log = LogFactory.getLog(AdminLoginService.class.getName());

	/* 로그인  DAO */
	@Resource
	private AdminLoginDAO adminLoginDAO;
		
	@Autowired
    private CommonService commonService;

	/**
	 * 로그인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> loginUserInfo(HashMap<String, Object> param) throws Exception{

		String passwd = EncryptUtils.getHash(String.valueOf(param.get("passwd")));

		HashMap<String, Object> rtn = new HashMap<String, Object>();
		HashMap<String, Object> userMap = new HashMap<String, Object>();
		HashMap<String, Object> rollMap = new HashMap<String, Object>();

		param.put("passwd", passwd);
		String answer = "";
		userMap = adminLoginDAO.loginUserInfo(param);

		if(!userMap.isEmpty()){
			if(String.valueOf(userMap.get("userStatus")).equals("L") || Integer.valueOf(String.valueOf(userMap.get("loginFailCnt"))) >= 10){
				throw new Exception("잠긴 계정입니다.\n아래 전화번호로 문의 주세요. \n02-6266-3166");
			}
			
			if(Integer.valueOf(String.valueOf(userMap.get("loginFailCnt"))) >= 5) {
				answer = String.valueOf(((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute("captcha"));
			}
			
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ConfigurableEnvironment env = ctx.getEnvironment();
			String serverType = env.getProperty("deploy.server.type");
			if(answer.equals(String.valueOf(param.get("sndAnswer")))) {
				if(passwd.equals(userMap.get("password"))){
					
					HttpSession session = null;
					List<HashMap<String, Object>> menuList;

					rollMap = adminLoginDAO.getRid(param);

					if(rollMap.get("rid") == null) {
						throw new Exception("로그인 권한이 없습니다.\n관리자에게 문의하세요.");
					}
					
					session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession(true);
					
					//String clientIp = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getRemoteAddr();
					String clientIp = (String) param.get("userIP");
					
					session.setAttribute("isLogin", true); // 로그인 여부
					session.setAttribute("sysType", String.valueOf(userMap.get("sysType")));
					session.setAttribute("loginTime", String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))); // 로그인 시간
					session.setAttribute("userId", String.valueOf(userMap.get("userId"))); // 사용자 ID
					session.setAttribute("userNm", String.valueOf(userMap.get("userName"))); // 사용자 이름
					session.setAttribute("hpNo", String.valueOf(userMap.get("hpNo"))); // 휴대폰번호
					session.setAttribute("phoneNo", String.valueOf(userMap.get("phoneNo"))); // 전화번호
					session.setAttribute("email", String.valueOf(userMap.get("email"))); // 이메일
					session.setAttribute("userStatus", String.valueOf(userMap.get("userStatus")));// 사용자상태
					session.setAttribute("lastIp", clientIp);// 최종접속IP
					session.setAttribute("lastDt", String.valueOf(LocalDateTime.now()));// 최종접속일시
					session.setAttribute("loginFailCnt", String.valueOf(userMap.get("loginFailCnt")));
					
					session.setAttribute("rid", String.valueOf(rollMap.get("rid")));

					param.put("rid", String.valueOf(rollMap.get("rid")));
					param.put("sysType", String.valueOf(userMap.get("sysType")));
					menuList = commonService.selAdMenuList(param);
					
					Cookie storeIdCookie = new Cookie("storeIdCookie", String.valueOf(userMap.get("userId")));
				    storeIdCookie.setPath(serverType + "/adLogin.do");
				    storeIdCookie.setMaxAge(60 * 60 * 24 * 30);
				    //response.addCookie(storeIdCookie); 
				    ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse().addCookie(storeIdCookie);
					
					session.setAttribute("menuList", menuList);
					session.setMaxInactiveInterval(60*10); // 세션유지 시간 10분
					if("P".equals(userMap.get("cngpwDt"))) {
						rtn.put("errorCode", "00");
						rtn.put("errorMessage", "로그인");
					}else {
						rtn.put("errorCode", "01");
						rtn.put("errorMessage", "비밀번호 변경");
					}
					param.put("lastIp", clientIp);
					adminLoginDAO.loginAdminUserPwdInit(param);
				}else{
					adminLoginDAO.loginAdminUserPwdErr(param);
					int failCnt = Integer.valueOf(String.valueOf(userMap.get("loginFailCnt")))+1;
					rtn.put("errorCode", "98");
					rtn.put("errorMessage", "아이디 또는 비밀번호를 확인해주세요.");
					rtn.put("failCnt", failCnt);
				}
			}else {
				rtn.put("errorCode", "99");
				rtn.put("errorMessage", "입력하신 번호와 캡차가\n일치하지 않습니다.\n정확한 번호를 입력하여 주시기 바랍니다.");
				rtn.put("failCnt", Integer.valueOf(String.valueOf(userMap.get("loginFailCnt"))));
			}

		}else {
			rtn.put("errorCode", "ERR_Logn_login_01");
			rtn.put("errorMessage", "아이디 또는 비밀번호를 확인해주세요.");
		}

		return rtn;
	}

	/**
	 * 신규 ID 체크
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> newIdCheck(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = adminLoginDAO.newIdCheck(param);
		return rtn;
	}

	/**
	 * 아이디 찾기
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public HashMap<String, Object> idSearch(HashMap<String, Object> param) throws Exception{

		HashMap<String, Object> rtn = new HashMap<String, Object>();

		try{
			rtn = adminLoginDAO.idSearch(param);

			if(String.valueOf(rtn.get("userYn")).equals("Y")) {
				CommMaskUtils commMaskUtils = new CommMaskUtils();

				rtn.put("userId", commMaskUtils.getMaskId(String.valueOf(rtn.get("userId"))));

			}

		}catch(Exception e){
			log.debug(e.getMessage());
		}

		return rtn;
	}

	/**
	 * 비밀번호 찾기
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> pwdSearch(HashMap<String, Object> param) throws Exception{

		HashMap<String, Object> rtn = new HashMap<String, Object>();
		int chkCnt = 0;
		int nmsCnt = 1;
		
		rtn = adminLoginDAO.pwdSearch(param);

		// 입력정보가 맞을경우 확인경로(sms, email 로 임시 비밀번호를 발송한다.)
		if(String.valueOf(rtn.get("userYn")).equals("Y")) {

			SecureRandom random = new SecureRandom();
			int numLength = 10;
			String tmpPasswd = "";
			String encPasswd = "";

			for (int i = 0; i < numLength; i++) {
				int tempNum = random.nextInt(35);
				if (tempNum < 10) {
					tmpPasswd += tempNum;
				} else if (tempNum > 10) {
					tmpPasswd += (char)(tempNum + 86);
				}
			}

			encPasswd = EncryptUtils.getHash(tmpPasswd);

			param.put("tmpPasswd", encPasswd);
			chkCnt = adminLoginDAO.updatePwd(param);
			if(chkCnt != 1) {
				throw new Exception("임시비밀번호 발급증 오류가 발생하였습니다.");
			}

			if(String.valueOf(param.get("confirmType")).equals("sms")) {

				HashMap<String, Object> smsInfo = new HashMap<String, Object>();

				smsInfo.put("sndPhnId", "15663210");												 // 보내는 사람 휴대폰번호
				smsInfo.put("rcvPhnId", String.valueOf(param.get("hpNo")));							 // 받는 사람 휴대폰번호
				smsInfo.put("callback", "15663210");		                                         // 회신 번호
				smsInfo.put("subject", "[SKB IDC] 임시비밀번호");											 // 제목
				smsInfo.put("sndMsg", "[SKB IDC] 임시비밀번호\n" + tmpPasswd + "\n로그인후 비밀번호를 변경해 주세요."); // 메시지 내용
				smsInfo.put("rsrvdId", "BIZ^SYSTEM");
				//smsUtils.sendSmsAndLms(smsInfo);

			}else if(String.valueOf(param.get("confirmType")).equals("email")) {

				try {

					HashMap<String, Object> mailInfo = new HashMap<String, Object>();
					mailInfo.put("targetHtml", "sendTmpPasswd");					// html
					mailInfo.put("subject", "[SKB IDC] 임시비밀번호가 발급되었습니다.");			// 제목
					mailInfo.put("toMail", String.valueOf(param.get("email")));		// 보내는 사람 메일주소
					mailInfo.put("toName", String.valueOf(param.get("userNm")));	// 받는 사람 이름

					HashMap<String, Object> mailParam = new HashMap<String, Object>();
					mailParam.put("userNm", String.valueOf(param.get("userNm")));	// html의 파라메터 고객명
					mailParam.put("tmpPasswd", tmpPasswd);							// html의 파라메터 임시비밀번호

					//mailUtils.sendMail(mailInfo, mailParam);
				}catch(Exception e) {
					log.error(e.getMessage());
				}

			}else{
				throw new Exception("임시비밀번호 발급증 오류가 발생하였습니다.");
			}

			rtn.put("errorCode", "00");
			rtn.put("errorMessage", "정상처리되었습니다.");
		}

		return rtn;
	}

	/**
	 * 사용자 등록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> insertNewUser(HashMap<String, Object> param) throws Exception{

		HashMap<String, Object> rtn = new HashMap<String, Object>();
		int chkCnt = 1;
		String formationCd_2 = StringUtils.defaultString(param.get("formationCd_2"));

		chkCnt *= adminLoginDAO.insertUserInfo(param);
		if(!"".equals(formationCd_2)) {
			param.put("formationCd", formationCd_2);
		}

		chkCnt *= adminLoginDAO.insertUserIdc(param);
		if(chkCnt != 1) {
			throw new Exception("사용자 등록 요청중 오류가 발생하였습니다.");
		}

		rtn.put("errorCode", "00");
		rtn.put("errorMessage", "요청되었습니다.");

		// 알림 처리
		try {
			HashMap<String, Object> sendMap = new HashMap<String, Object>();
			sendMap.put("sendType", "P"); 	// 알림 구분 코드
			sendMap.put("code", "54");   	// 알림 메뉴 코드

			// 전화 상담 제목을 입력한다.
			String summary = "센터 : " + StringUtils.defaultString(param.get("manageCidText"));
			summary += " | 부서 : " + StringUtils.defaultString(param.get("formationCdText"));
			summary += " | 성명 : " + StringUtils.defaultString(param.get("userNm"));
			sendMap.put("summary", summary); // 알림제목
			sendMap.put("linkData", param);
			//sendUtils.send(sendMap);

		}catch(Exception e){
			log.error(e.getMessage());
		}

		return rtn;
	}
	
	/**
	 * SSO 로그인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> ssoLogin(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		HashMap<String, Object> userMap = new HashMap<String, Object>();
		
		userMap = adminLoginDAO.loginUserInfo(param);
		String userId = StringUtils.defaultString(userMap.get("userId"));
		String passwd = StringUtils.defaultString(userMap.get("passwd"));
		String today = DateUtils.getToday("yyyy-MM-dd");
		
		String check = "";
		check = EncryptUtils.getHash128(userId+"|"+today+"|"+passwd);
		
		rtn.put("userId", userId);
		rtn.put("check", check);
		
		return rtn;
	}

}
