package com.sktelecom.checkit.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 
 * 서버 정보 유틸 클래스
 * 
 * @author devkimsj
 * @since 2018.08.05
 * @version 1.0
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일				수정자						수정내용
 *  ------------	----------------		---------------------------
 *   2018.08.05		devkimsj				최초 생성
 *
 * </pre>
 * 
 */
public class CommServerInfoUtils {

	/**
	 * Sever 호스트명를 가져온다.
	 * 
     * @return String 호스트명
     */
    public static String getServerHostName() throws UnknownHostException{

    	InetAddress ip		= null;
    	String hostName		= "";
    	
    	ip = InetAddress.getLocalHost();
    	hostName = ip.getHostName();

        return hostName;
    }
}
