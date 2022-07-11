package com.sktelecom.checkit.core.util;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class Session{

	private final static Log log = LogFactory.getLog(Session.class);

	private HttpSession session = null;

	public Session(){
		this(false);
	}

	/**
	 * 세션을 가져온다.
	 * @param isNew true인경우 세션을 새로 생성한다.
	 */
	public Session(boolean isNew){

		try{
			if(isNew){
				this.session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(true);
			}else{
				this.session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
	}

	/**
	 * 로그인여부
	 */
	public boolean isLogin(){
		if(null == this.session.getAttribute("isLogin")){
			return false;
		}else if("false".equals(String.valueOf(this.session.getAttribute("isLogin")))){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 로그인시간
	 * @return
	 */
	public String loginTime(){
		return ((String)this.session.getAttribute("loginTime"));
	}

	/**
	 * 사용자ID
	 * @return
	 */
	public String getSysType(){
		return (String)this.session.getAttribute("sysType");
	}

	/**
	 * 사용자ID
	 * @return
	 */
	public String getUserId(){
		return (String)this.session.getAttribute("userId");
	}

	/**
	 * 사용자이름
	 * @return
	 */
	public String getUserNm(){
		return (String)this.session.getAttribute("userNm");
	}

	/**
	 * 전화번호
	 * @return
	 */
	public String getTelNo(){
		return (String)this.session.getAttribute("telNo");
	}

	/**
	 * 휴대폰번호
	 * @return
	 */
	public String getHpNo(){
		return (String)this.session.getAttribute("hpNo");
	}

	/**
	 * 이메일
	 * @return
	 */
	public String getEmail(){
		return (String)this.session.getAttribute("email");
	}

	/**
	 * 관리자 센터코드
	 * @return
	 */
	public String getManageCid(){
		return (String)this.session.getAttribute("manageCid");
	}

	/**
	 * 관리자 센터코드 명
	 * @return
	 */
	public String getManageCidNm(){
		return (String)this.session.getAttribute("manageCidNm");
	}


	/**
	 * 관리자 소속 명
	 * @return
	 */
	public String getManageCnm(){
		return (String)this.session.getAttribute("manageCnm");
	}


	/**
	 * 관리자 업무 명
	 * @return
	 */
	public String getFormationNm(){
		return (String)this.session.getAttribute("formationNm");
	}

	/**
	 * 관리자 센터코드 명
	 * @return
	 */
	public String getFormationCd(){
		return (String)this.session.getAttribute("formationCd");
	}

	/**
	 * CS번호
	 * @return
	 */
	public String getCsNo(){
		return (String)this.session.getAttribute("csNo");
	}

	/**
	 * CS명
	 * @return
	 */
	public String getCsName(){
		return (String)this.session.getAttribute("csName");
	}

	/**
	 *
	 * @return
	 */
	public String getChorusNo(){
		return (String)this.session.getAttribute("chorusNo");
	}

	/**
	 * 사용자번호
	 * @return
	 */
	public String getUkeyNo(){
		return (String)this.session.getAttribute("ukeyNo");
	}

	/**
	 * 상태
	 * @return
	 */
	public String getStatus(){
		return (String)this.session.getAttribute("status");
	}

	/**
	 * 최종접속IP
	 * @return
	 */
	public String getLastIp(){
		return (String)this.session.getAttribute("lastIp");
	}

	/**
	 * 최종접속일시
	 * @return
	 */
	public String getLastDt(){
		return (String)this.session.getAttribute("lastDt");
	}

	/**
	 * 담당 AM ID
	 * @return
	 */
	public String getCsAgentUserid(){
		return (String)this.session.getAttribute("csAgentUserid");
	}

	/**
	 *
	 * @return
	 */
	public String getCsAgentUserid2(){
		return (String)this.session.getAttribute("csAgentUserid2");
	}

	/**
	 *담담 AM 이름
	 * @return
	 */
	public String getCsAgentUserNm(){
		return (String)this.session.getAttribute("csAgentUserNm");
	}

	/**
	 * 담담 AM 전화
	 * @return
	 */
	public String getCsAgentTelNo(){
		return (String)this.session.getAttribute("csAgentTelNo");
	}

	/**
	 * 담담 AM 휴대폰번호
	 * @return
	 */
	public String getCsAgentHpNo(){
		return (String)this.session.getAttribute("csAgentHpNo");
	}

	/**
	 * 담담 AM 메일주소
	 * @return
	 */
	public String getCsAgentEmail(){
		return (String)this.session.getAttribute("csAgentEmail");
	}

	/**
	 * 관리자여부
	 * @return
	 */
	public String getAdminYn(){
		return (String)this.session.getAttribute("adminYn");
	}

	/**
	 * 로그인 실패횟수
	 * @return
	 */
	public String getLoginFailCnt(){
		return (String)this.session.getAttribute("loginFailCnt");
	}

	/**
	 * 스마트 IDC 비밀번호
	 * @return
	 */
	public String getSmartIdcPwd(){
		return (String)this.session.getAttribute("smartIdcPwd");
	}

	/**
	 * 이전 URL
	 * @return
	 */
	public void setPreUrl(String preUrl){
		this.session.setAttribute("preUrl", preUrl);
	}

	/**
	 * 이전 URL
	 * @return
	 */
	public String getPreUrl(){
		return (String)this.session.getAttribute("preUrl");
	}

	/**
	 * locale
	 * @return
	 */
	public void setLocale(Locale locale){
		this.session.setAttribute("locale", locale);
	}

	/**
	 * locale
	 * @return
	 */
	public Locale getLocale(){
		if(null == this.session.getAttribute("locale")){
			return Locale.KOREAN;
		}else{
			return (Locale)this.session.getAttribute("locale");
		}
	}

	/**
	 * csvFile
	 * @return
	 */
	public void setCsvFile(File csvFile){
		this.session.setAttribute("csvFile", csvFile);
	}

	/**
	 * csvFile
	 * @return
	 */
	public File getCsvFile(){
		return (File)this.session.getAttribute("csvFile");
	}

	/**
	 * 인증키
	 * @return
	 */
	public void setCertKey(String certKey){
		this.session.setAttribute("certKey", certKey);
	}

	/**
	 * 인증키
	 * @return
	 */
	public String getCertKey(){
		return (String)this.session.getAttribute("certKey");
	}

	/**
	 * 마스킹
	 * @return
	 */
	public void setMarskRelease(boolean maskRelease){
		this.session.setAttribute("maskRelease", maskRelease);
	}

	/**
	 * 마스킹
	 * @return
	 */
	public boolean getMarskRelease(){
		if(null == this.session.getAttribute("maskRelease")){
			return false;
		}else if("false".equals(String.valueOf(this.session.getAttribute("maskRelease")))){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 마스킹 해제 인증번호 중복 전송 방지
	 * @param isComplete
	 */
	public void setMaskIsComplete(boolean isComplete){
		this.session.setAttribute("maskIsComplete", isComplete);
	}
	
	public boolean getMaskIsComplete(){
		if(null == this.session.getAttribute("maskIsComplete")){
			return false;
		}else if("false".equals(String.valueOf(this.session.getAttribute("maskIsComplete")))){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 세션 아이디를 가져옵니다
	 * @return
	 */
	public String getSessionId(){
		return session.getId();
	}

	/**
	 * 페이징 여부
	 */
	public boolean isPaging(){
		if(null == this.session.getAttribute("isPaging")){
			return false;
		}else if("false".equals(String.valueOf(this.session.getAttribute("isPaging")))){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 페이징 여부
	 */
	public void setPaging(boolean isPaging){
		this.session.setAttribute("isPaging", isPaging);
	}

	/**
	 * 파라메터정보
	 * @return
	 */
	public String getParam(){
		return (String)this.session.getAttribute("param");
	}

	/**
	 * 메뉴리스트
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CommMap> getMenuList(){
		return (List<CommMap>)this.session.getAttribute("menuList");
	}

	/**
	 * 권한 ID
	 * @return
	 */
	public String getRid(){
		return (String)this.session.getAttribute("rid");
	}

	/**
	 * 메뉴ID
	 */
	public void setMenuId(String menuId){
		this.session.setAttribute("menuId", menuId);
	}

	/**
	 * 메뉴ID
	 */
	public String getMenuId(){
		return (String)this.session.getAttribute("menuId");
	}

	/**
	 * 세션을 사용해야할경우 여기 담아서 사용
	 */
	public void setEtc(String etc){
		this.session.setAttribute("etc", etc);
	}

	/**
	 * 메뉴ID
	 */
	public String getEtc(){
		return (String)this.session.getAttribute("etc");
	}

	/**
	 * 파라메터정보
	 */
	public void setParam(Map<String, String[]> param){

		if(param != null) {
			//			final HashMap<String, Object> mpvs = new HashMap<String, Object>();
			final StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("{");
			param.forEach(
					(k, v) -> {
						String key = k.replaceAll("\\[]", "")
								.replaceAll("\\[(\\D+)", ".$1")
								.replaceAll("]\\[(\\D)", ".$1")
								.replaceAll("(\\.\\w+)]", "$1");
						stringBuffer.append("\"" + key + "\":\"" + v[0].toString().replaceAll("\\[]", "") + "\",");
					}
					);
			stringBuffer.append("}");
			this.session.setAttribute("param", stringBuffer.toString());
		}

	}

	/**
	 * 시스템 세션생성시간
	 * @return
	 */
	//	private Long getSystemAccessedTime(){
	//		return (Long)this.session.getAttribute("systemAccessedTime");
	//	}
	//	public void setSystemAccessedTime(Long systemAccessedTime){
	//		this.session.setAttribute("systemAccessedTime", systemAccessedTime);
	//	}

	/**
	 * 유저 마지막 Access 시간
	 * @return
	 */
	private Long getUserLastAccessedTime(){
		return (Long)this.session.getAttribute("userLastAccessedTime");
	}
	public void setUserLastAccessedTime(Long userLastAccessedTime){
		this.session.setAttribute("userLastAccessedTime", userLastAccessedTime);
	}

	/**
	 * 세션 유지 시간지정
	 * @return
	 */
	public int getMaxInactiveInterval(){
		int rtnTime = 0;
		int freeTime = this.session.getMaxInactiveInterval() - Integer.parseInt(Long.toString((System.currentTimeMillis() - this.getUserLastAccessedTime())/1000));

		log.debug("1 = " + this.session.getMaxInactiveInterval());
		log.debug("2 = " + System.currentTimeMillis());
		log.debug("3 = " + this.getUserLastAccessedTime());
		log.debug("4 = " + Integer.parseInt(Long.toString((System.currentTimeMillis() - this.getUserLastAccessedTime())/1000)));

		if(freeTime <= 60){
			this.setMaxInactiveInterval(63);
		}else{
			rtnTime = freeTime;
		}
		return rtnTime;
	}
	public void setMaxInactiveInterval(int maxInactiveInterval){
		this.session.setMaxInactiveInterval(maxInactiveInterval);
	}

	/**
	 * 세션 소멸
	 */
	public void removeSession(){
		this.session.invalidate();
	}

}