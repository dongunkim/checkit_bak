package com.sktelecom.checkit.core.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * unicode 유틸
 * @author devkimsj
 *
 */


public class ConvertUtils {

	private final static Log log = LogFactory.getLog(ConvertUtils.class);

	/**
	 * 유니코드를 한글로 변환
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String unicodeToKor(String str) throws UnsupportedEncodingException{

		return new String (str.getBytes("8859_1"), "KSC5601");

	}

	/**
	 * 한글을 유니코드로 변환
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String korToUnicode(String str) throws UnsupportedEncodingException{

		return new String (str.getBytes("KSC5601"), "8859_1");

	}

	/**
     * 문자열을 escape 한다.
     * @param str
     * @return
     */
    public static String escape(String str) {

    	String result = str;
    	try {
        	if(result != null) {
        		result = result.replace("&", "&amp;");
    	    	result = result.replace("${amp}", "&amp;");

    	    	result = result.replace("#", "&#35;");
    	    	result = result.replace("${@35}", "&#35;");

    	    	//result = result.replace(";", "&#59;");
    	    	result = result.replace("${@59}", "&#59;");

    	    	result = result.replace("%", "&#37;");
    	    	result = result.replace("${@37}", "&#37;");

    	    	result = result.replace("'", "&#39;");
    	    	result = result.replace("${@39}", "&#39;");

    	    	result = result.replace("(", "&#40;");
    	    	result = result.replace("${@40}", "&#40;");

    	    	result = result.replace(")", "&#41;");
    	    	result = result.replace("${@41}", "&#41;");

    	    	result = result.replace("+", "&#43;");
    	    	result = result.replace("${@43}", "&#43;");

    	    	result = result.replace("`", "&#96;");
    	    	result = result.replace("${@96}", "&#96;");

    	    	result = result.replace("\"", "&quot;");
    	    	result = result.replace("${quot}", "&quot;");

    	    	result = result.replace("<", "&lt;");
    	    	result = result.replace("${lt}", "&lt;");

    	    	result = result.replace(">", "&gt;");
    	    	result = result.replace("${gt}", "&gt;");
        	}
        	//System.out.println("#inner3_escape value ["+result+"]");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
    	return result;
    }

    /**
     * 문자열을 deEscape 한다.
     * @param str
     * @return
     */
    public static String deEscape(String str) {

    	String result = str;
    	try {
			if(result != null) {

				result = result.replace("&#38;&#38;#35;39;", "\'");
				result = result.replace("&#38;#35;", "#");
				result = result.replace("&#38;nbsp;", "&nbsp;");
				result = result.replace("&#38;amp;", "&amp");

				result = result.replace("&amp;", "&");
		    	result = result.replace("${amp}", "&");

		    	result = result.replace("&#35;", "#");
		    	result = result.replace("${@35}", "#");

		    	result = result.replace("&#59;", ";");
		    	result = result.replace("${@59}", ";");

		    	result = result.replace("&#37;", "%");
		    	result = result.replace("${@37}", "%");

		    	result = result.replace("&#38;", "&'");
		    	result = result.replace("${@38}", "&'");

		    	result = result.replace("&#39;", "\'");
		    	result = result.replace("${@39}", "\'");

		    	result = result.replace("&#40;", "(");
		    	result = result.replace("${@40}", "(");

		    	result = result.replace("&#41;", ")");
		    	result = result.replace("${@41}", ")");

		    	result = result.replace("&#43;", "+");
		    	result = result.replace("${@43}", "+");

		    	result = result.replace("&#96;", "`");
		    	result = result.replace("${@96}", "`");

		    	result = result.replace("&quot;", "\"");
		    	result = result.replace("${quot}", "\"");

		    	result = result.replace("&lt;", "<");
		    	result = result.replace("${lt}", "<");

		    	result = result.replace("&gt;", ">");
		    	result = result.replace("${gt}", ">");

		    	result = result.replace("&'#40;", "(");
				result = result.replace("&'#41;", ")");
			}
			//System.out.println("#input_inner3_deEscape value ["+str+"]");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
    	return result;
    }
}