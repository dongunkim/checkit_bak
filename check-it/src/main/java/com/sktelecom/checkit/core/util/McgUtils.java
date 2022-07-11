package com.sktelecom.checkit.core.util;

import java.lang.Character.UnicodeBlock;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * MCG 유틸
 * @author devkimsj
 *
 */


public class McgUtils { 

	public static String makeString(String srcStr, int length) {
        if(srcStr == null) {
            srcStr = " ";
        }

        byte[] src = srcStr.getBytes();
        byte[] dest = new byte[length];

        if(src.length == length) {
            //log.debug("Make String : "+length);
            //log.debug("["+srcStr+"]");
            return srcStr;
        }else if(src.length < length) {
            for ( int i = 0; i < length; i++ ) {
                dest[i] = ' ';
            }
        	System.arraycopy( src, 0, dest, 0, src.length );

            //log.debug("Make String : "+length);
            //log.debug("[" + new String(dest) + "]");
            
            return new String( dest );
        }else{
            System.arraycopy( src, 0, dest, 0, length );
            //log.debug("Make String : "+length);
            //log.debug("[" + new String(dest) + "]");
            return new String( dest );
        }
    }
	
	public static String makeNumber(int num, int length) {
        String src = "" + num;
        return makeNumber(src,length);
    }

    public static String makeNumber(String src, int length) {
        if (src == null) {
        	src = "0";
        }

        if (src.length() == length) {
            return src;
        }
        
        byte[] dest = new byte[length];

        if (src.length() == length) {
            //log.debug("Make Number : "+length);
            //log.debug("[" + src + "]");
            return src;
        }else if (src.length() < length) {

            for ( int i = 0; i < length; i++ ) {
                dest[i] = '0';
            }
            
            System.arraycopy( src.getBytes(), 0, dest, length - src.length(), src.length() );

            //log.debug("Make Number : "+length);
            //log.debug("[" + new String(dest) + "]");
        }else {
            System.arraycopy( src.getBytes(), 0, dest, 0, length );
            //log.debug("Make Number : "+length);
            //log.debug("[" + new String(dest) + "]");
        }
        return new String( dest );
    }
    
    /**
     * Byte의 단위로 substring을 한다.
     * @param str
     * @param beginIndex
     * @param endIndex
     * @return String
     */
    public static String byteSubstr(String str ,int beginIndex, int endIndex) {
    	String result = "";
    	if(str != null) {
    		byte[] temp1 = str.getBytes();
    		byte[] temp2 = new byte[endIndex];
    		
    		System.arraycopy( temp1, beginIndex, temp2, 0, endIndex );
    		result = new String(temp2);
    	}
    	return result.trim();
    }
}