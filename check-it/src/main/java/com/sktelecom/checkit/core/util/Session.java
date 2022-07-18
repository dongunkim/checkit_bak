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
	 * 세션 아이디
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
	 * 페이징 설정
	 */
	public void setPaging(boolean isPaging){
		this.session.setAttribute("isPaging", isPaging);
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
	 * 파라메터정보
	 * @return
	 */
	public String getParam(){
		return (String)this.session.getAttribute("param");
	}
	
	/**
	 * 파라메터정보
	 */
	public void setParam(Map<String, String[]> param){

		if(param != null) {
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
	 * 세션 소멸
	 */
	public void removeSession(){
		this.session.invalidate();
	}

}