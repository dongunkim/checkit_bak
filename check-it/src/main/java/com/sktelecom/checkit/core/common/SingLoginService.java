package com.sktelecom.checkit.core.common;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.sktelecom.checkit.core.common.dao.CommonDAO;
import com.sktelecom.checkit.core.interceptor.CommInterceptor;

@Component("SingLoginService")
public class SingLoginService  implements HttpSessionBindingListener{
	
	private static final Log log = LogFactory.getLog(CommInterceptor.class.getName());
	
	/** 로그인 유저 관련 해쉬 테이블 **/
    private static Hashtable userList = new Hashtable();
    
    @Resource
    private CommonDAO commonDAO;
    
 
       /**
        * 해당 아이디가 로그인 되어있는지
        * @param sessionID
        * @return
        */
       public boolean isLogin(String sessionID){
                     boolean isLogin = false;
                     Enumeration e = userList.keys();
                     String key = "";
                     while(e.hasMoreElements()){
                                  key = (String)e.nextElement();
                                  if(sessionID.equals(key)){
                                               isLogin = true;
                                  }
                     }
                     return isLogin;
       }
 
       /**
        * 세션 생성 , user 저장
        * @param session
        * @param userID
        */
       public void setSession(HttpSession session, String userID){
			userList.put(session.getId(), userID);
			//session.setAttribute("login", this.getInstance());
			log.info("SingLoginService setSession user list " + userList);
       }
       
       /**
        * 세션 생성될때
        */
       @Override
        public void valueBound(HttpSessionBindingEvent event) {
            // TODO Auto-generated method stub
            
        }
        
        /**
         * 세션 끊길 때
         */
       @Override
        public void valueUnbound(HttpSessionBindingEvent event) {
            // TODO Auto-generated method stub
            
            userList.remove(event.getSession().getId());
        }
 
        /**
         * 중복 목록 가져오기
         * @return
         */
        public static Hashtable getUserList(){
            return userList;
        }
}
