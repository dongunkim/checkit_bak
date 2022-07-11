package com.sktelecom.checkit.core.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpUtils{

	private final static Log log = LogFactory.getLog(HttpUtils.class);
	final static double VERSION = 1.20141016;
	static final int ERROR_CODE_HTTP_NOT_FOUND = -404;
	static final int ERROR_CODE_HTTP_UNAUTHORIZED = -401;
	static final int ERROR_CODE_HTTP_ELSE = -1;
	static final int ERROR_CODE_HTTP_EXCEPTION = -1000;
	static final int ERROR_CODE_NOERROR = 0;
	byte[] htmlByte = null;
	int errorCode = 0;
	boolean bUseCache = true;
	boolean m_session = false;
	String m_cookies = "";

	public String getString() {
		try {
			return new String(htmlByte, "UTF-8");
		} catch (Exception e) {
			log.debug(e);
		}
		return null;
	}

	public byte[] getByte() {
		return htmlByte;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public boolean execute(String addr) {
		return execute(addr, 5, 10);
	}
	public void saveCookie(HttpURLConnection conn){

		Map<String, List<String>> imap = conn.getHeaderFields( ) ;
		if(imap.containsKey("Set-Cookie")){
			List<String> lString = imap.get( "Set-Cookie" ) ;
			for( int i = 0 ; i < lString.size() ; i++ ) {
				m_cookies += lString.get( i ) ;
			}
			m_session = true ;
		} else {
			m_session = false ;
		}
	}

	public boolean execute(String addr, int connTimeOutSec, int readTimeOutSec) {
		boolean retval = true;
		errorCode = ERROR_CODE_NOERROR;
		try {
			URL url = new URL(addr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			if( conn != null ) {
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
				if( m_session ) {
					conn.setRequestProperty( "Cookie", m_cookies );
				}
				conn.setConnectTimeout(1000*connTimeOutSec);
				conn.setReadTimeout(1000*readTimeOutSec);
				conn.setUseCaches(bUseCache);
				conn.setDoInput(true);

				int resCode = conn.getResponseCode();
				saveCookie(conn);
				if( resCode == HttpURLConnection.HTTP_OK ) {
					htmlByte = inputStreamToByte(conn.getInputStream());
					if(htmlByte == null || htmlByte.length == 0){
						errorCode = ERROR_CODE_HTTP_ELSE;
						retval = false;
					}
				} else if( resCode == HttpURLConnection.HTTP_NOT_FOUND ) {
					errorCode = ERROR_CODE_HTTP_NOT_FOUND;
					retval = false;
				} else if( resCode == HttpURLConnection.HTTP_UNAUTHORIZED ) {
					errorCode = ERROR_CODE_HTTP_UNAUTHORIZED;
					retval = false;
				} else {
					errorCode = ERROR_CODE_HTTP_ELSE;
					retval = false;
				}
				// DISCONNECT
				conn.disconnect();
			} else {
				errorCode = ERROR_CODE_HTTP_ELSE;
				retval = false;
			}
		} catch(Exception e) {
			log.error(e.getMessage());
			errorCode = ERROR_CODE_HTTP_EXCEPTION;
			retval = false;
		}
		return retval;
	}

	private byte[] inputStreamToByte(InputStream in){
		final int BUF_SIZE = 1024;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[BUF_SIZE];
		try {
			int length;
			while ((length = in.read(buffer)) != -1) out.write(buffer, 0, length);
		} catch (IOException e) {
			log.error(e.getMessage());
			return null;
		}
		return out.toByteArray();
	}

}