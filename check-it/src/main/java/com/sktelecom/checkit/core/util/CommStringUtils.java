package com.sktelecom.checkit.core.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.core.util.Base64Encoder;

/**
 * 문자열을 처리하는 유틸 클래스
 * Apache Commons regexp 오픈소스를 활용하여 String
 * 관련 기능을 제공하는 유틸이다.
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
public class CommStringUtils extends org.apache.commons.lang.StringUtils {

    /**
     * <p>
     * 에러나 이벤트와 관련된 각종 메시지를 로깅하기 위한 Log 오브젝트
     * </p>
     */
    private static Logger log = LoggerFactory.getLogger(CommStringUtils.class.getName());

    /*
     * Space 문자
     */
    private final static char WHITE_SPACE = ' ';

    /**
     * Null 여부
     * 
     * @param str 체크할 문자열
     * @return boolean Null 여부
     */
    public static boolean isNull(String str) {
    	String changeStr = "";
    	
        if (str != null) {
        	changeStr = str.trim();
        } else {
        	changeStr = str;
        }

        return (changeStr == null || "".equals(changeStr));
    }

    /**
     * 지정된 캐릭터가 범용 캐릭터 여부
     * 
     * @param str 체크할 문자열
     * @return boolean 지정된 캐릭터가 범용 캐릭터 여부
     */
    public static boolean isAlpha(String str) {

        if (str == null) {
            return false;
        }

        int size = str.length();

        if (size == 0)
            return false;

        for (int i = 0; i < size; i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 지정된 캐릭터가 범용 캐릭터 또는 숫자인가 여부
     * 
     * @param str 체크할 문자열
     * @return boolean 지정된 캐릭터가 범용 캐릭터 또는 숫자인가 여부
     */
    public static boolean isAlphaNumeric(String str) {

        if (str == null) {
            return false;
        }

        int size = str.length();

        if (size == 0)
            return false;

        for (int i = 0; i < size; i++) {
            if (!Character.isLetterOrDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * int 변수를 캐릭터 라인으로 변환
     * 
     * @param integer int 변수
     * @return String int 변수를 캐릭터 라인으로 변환한 결과값
     */
    public static String integer2string(int integer) {
        return ("" + integer);
    }

    /**
     * long 변수를 캐릭터 라인으로 변환
     * 
     * @param longdata long 변수
     * @return String long 변수를 캐릭터 라인으로 변환한 결과값
     */
    public static String long2string(long longdata) {
        return String.valueOf(longdata);
    }

    /**
     * float 변수를 캐릭터 라인으로 변환
     * 
     * @param floatdata float 변수
     * @return String float 변수를 캐릭터 라인으로 변환한 결과값
     */
    public static String float2string(float floatdata) {
        return String.valueOf(floatdata);
    }

    /**
     * double 변수를 캐릭터 라인으로 변환
     * 
     * @param doubledata double 변수
     * @return String double 변수를 캐릭터 라인으로 변환한 결과값
     */
    public static String double2string(double doubledata) {
        return String.valueOf(doubledata);
    }

    /**
     * Null 문자열이면 공백으로 변환
     * 
     * @param str 변환 대상 문자열
     * @return String Null 문자열이면 공백으로 변환한 결과값
     */
    public static String null2void(String str) {
    	
    	String changeStr = "";
    	
        if (isNull(str)) {
        	changeStr = "";
        } else {
        	changeStr = str;
        }

        return changeStr;
    }

    /**
     * String 문자열을 int 로 변환
     * 
     * @param str 변환 대상 문자열
     * @return int String 문자열을 int 로 변환한 결과값
     */
    public static int string2integer(String str) {
    	String changeStr	= "";
    	
        if (isNull(str)) {
            return 0;
        }
        
        changeStr = str.trim(); 
        return Integer.parseInt(changeStr);
    }

    /**
     * String 문자열을 float 로 변환
     * 
     * @param str 변환 대상 문자열
     * @return float String 문자열을 float 로 변환한 결과값
     */
    public static float string2float(String str) {

        if (isNull(str)) {
            return 0.0F;
        }

        return Float.parseFloat(str);
    }

    /**
     * String 문자열을 double 로 변환
     * 
     * @param str 변환 대상 문자열
     * @return double String 문자열을 double 로 변환한 결과값
     */
    public static double string2double(String str) {

        if (isNull(str)) {
            return 0.0D;
        }

        return Double.parseDouble(str);
    }

    /**
     * String 문자열을 long 로 변환
     * 
     * @param str 변환 대상 문자열
     * @return long String 문자열을 long 로 변환한 결과값
     */
    public static long string2long(String str) {

        if (isNull(str)) {
            return 0L;
        }

        return Long.parseLong(str);
    }

    /**
     * Null 문자열이면 defaultValue 값으로 대체
     * 
     * @param str 원본 문자열
     * @param defaultValue 디폴트 문자열
     * @return String 결과값
     */
    public static String null2string(String str, String defaultValue) {

        if (isNull(str)) {
            return defaultValue;
        }

        return str;
    }

    /**
     * Null 문자열이면 defaultValue 값으로 대체하거나 int 로 변환
     * 
     * @param str 원본 문자열
     * @param defaultValue 디폴트 문자열
     * @return int 결과값
     */
    public static int string2integer(String str, int defaultValue) {

        if (isNull(str)) {
            return defaultValue;
        }

        return Integer.parseInt(str);
    }

    /**
     * Null 문자열이면 defaultValue 값으로 대체하거나 float 로 변환
     * 
     * @param str 원본 문자열
     * @param defaultValue 디폴트 문자열
     * @return float 결과값
     */
    public static float string2float(String str, float defaultValue) {

        if (isNull(str)) {
            return defaultValue;
        }

        return Float.parseFloat(str);
    }

    /**
     * Null 문자열이면 defaultValue 값으로 대체하거나 double 로 변환
     * 
     * @param str 원본 문자열
     * @param defaultValue 디폴트 문자열
     * @return double 결과값
     */
    public static double string2double(String str, double defaultValue) {

        if (isNull(str)) {
            return defaultValue;
        }

        return Double.parseDouble(str);
    }

    /**
     * Null 문자열이면 defaultValue 값으로 대체하거나 long 로 변환
     * 
     * @param str 원본 문자열
     * @param defaultValue 디폴트 문자열
     * @return long 결과값
     */
    public static long string2long(String str, long defaultValue) {

        if (isNull(str)) {
            return defaultValue;
        }

        return Long.parseLong(str);
    }

    /**
     * source 문자열과 taget 문자열을 equals 비교
     * 
     * @param source 원본 문자열
     * @param target 비교 문자열
     * @return boolean 비교 여부
     */
    public static boolean equals(String source, String target) {

        return null2void(source).equals(null2void(target));
    }

    /**
     * 문자열 자르기
     * 
     * @param str 원본 문자열
     * @param beginIndex 시작 위치
     * @param endIndex 끝 위치
     * @return String 자른 결과값
     */
    public static String toSubString(String str, int beginIndex, int endIndex) {

        if (equals(str, "")) {
            return str;
        } else if (str.length() < beginIndex) {
            return "";
        } else if (str.length() < endIndex) {
            return str.substring(beginIndex);
        } else {
            return str.substring(beginIndex, endIndex);
        }

    }

    /**
     * 문자열 자르기
     * 
     * @param source 원본 문자열
     * @param beginIndex 시작 위치
     * @return String 자른 결과값
     */
    public static String toSubString(String source, int beginIndex) {

        if (equals(source, "")) {
            return source;
        } else if (source.length() < beginIndex) {
            return "";
        } else {
            return source.substring(beginIndex);
        }

    }

    /**
     * 원본 문자열과 비교 문자열를 비교해서 같은 문자열의 개수를 반환
     * 
     * @param source 원본 문자열
     * @param target 비교 문자열
     * @return result 같은 문자열의 개수
     */
    public static int search(String source, String target) {
        int result = 0;
        String strCheck = source;
        
        for (int i = 0; i < source.length();) {
            int loc = strCheck.indexOf(target);
            if (loc == -1) {
                break;
            } else {
                result++;
                i = loc + target.length();
                strCheck = strCheck.substring(i);
            }
        }
        return result;
    }

    /**
     * 원본 문자열의 양쪽 공백을 지워서 반환
     * 
     * @param str 원본 문자열
     * @return String 원본 문자열의 양쪽 공백을 지워서 반환한 문자열
     */
    public static String trim(String str) {
        return str.trim();
    }

    /**
     * 원본 문자열의 왼쪽 공백을 지워서 반환
     * 
     * @param str 원본 문자열
     * @return changeStr 원본 문자열의 왼쪽 공백을 지워서 반환한 문자열
     */
    public static String ltrim(String str) {

        int index 			= 0;
        String changeStr	= "";
        
        while (' ' == str.charAt(index++)) {
            ;
        }
        
        if (index > 0) {
        	changeStr = str.substring(index - 1);
        } else {
        	changeStr = str;
        }
        return changeStr;
    }

    /**
     * 원본 문자열의 오른쪽 공백을 지워서 반환
     * 
     * @param str 원본 문자열
     * @return changeStr 원본 문자열의 오른쪽 공백을 지워서 반환한 문자열
     */
    public static String rtrim(String str) {

        int index = str.length();
        String changeStr	= "";
        
        while (' ' == str.charAt(--index)) {
            ;
        }
        
        if (index < str.length()) {
        	changeStr = str.substring(0, index + 1);
        } else {
        	changeStr = str;
        }
        return changeStr;
    }

    /**
     * 원본 문자열의 마지막에 연결 문자열를 연결
     * 
     * @param str1 원본 문자열
     * @param str2 연결 문자열
     * @return String 원본 문자열의 마지막에 연결 문자열를 연결한 문자열
     */
    public static String concat(String str1, String str2) {

        StringBuffer sb = new StringBuffer(str1);
        sb.append(str2);

        return sb.toString();
    }

    /**
     * 원본 문자열에 len - str.length() 길이만큼 왼쪽에 연결할 캐릭터를 연결
     * 
     * @param str 원본 문자열
     * @param len 길이
     * @param pad 연결할 캐릭터
     * @return changeStr 원본 문자열에 len - str.length() 길이만큼 왼쪽에 연결할 캐릭터를 연결한 문자열
     */
    public static String lPad(String str, int len, char pad) {
        return lPad(str, len, pad, false);
    }

    /**
     * 원본 문자열에 len - str.length() 길이만큼 왼쪽에 연결할 캐릭터를 연결
     * 
     * @param str 원본 문자열
     * @param len 길이
     * @param pad 연결할 캐릭터
     * @param isTrim trim 여부
     * @return changeStr 원본 문자열에 len - str.length() 길이만큼 왼쪽에 연결할 캐릭터를 연결한 문자열
     */
    public static String lPad(String str, int len, char pad, boolean isTrim) {
    	String changeStr	= "";
    	
        if (isNull(str)) {
            return null;
        }

        if (isTrim) {
        	changeStr = str.trim();
        } else {
        	changeStr = str;
        }

        for (int i = changeStr.length(); i < len; i++) {
        	changeStr = pad + changeStr;
        }

        return changeStr;
    }

    /**
     * 원본 문자열에 len - str.length() 길이만큼 오른쪽에 연결할 캐릭터를 연결
     * 
     * @param str 원본 문자열
     * @param len 길이
     * @param pad 연결할 캐릭터
     * @return String 원본 문자열에 len - str.length() 길이만큼 오른쪽에 연결할 캐릭터를 연결한 문자열
     */
    public static String rPad(String str, int len, char pad) {
        return rPad(str, len, pad, false);
    }

    /**
     * 원본 문자열에 len - str.length() 길이만큼 오른쪽에 연결할 캐릭터를 연결
     * 
     * @param str 원본 문자열
     * @param len 지정한 길이
     * @param pad 연결할 캐릭터
     * @param isTrim trim 여부
     * @return changeStr 원본 문자열에 len - str.length() 길이만큼 오른쪽에 연결할 캐릭터를 연결한 문자열
     */
    public static String rPad(String str, int len, char pad, boolean isTrim) {
    	String changeStr	= "";
    	
        if (isNull(str)) {
            return null;
        }

        if (isTrim) {
        	changeStr = str.trim();
        } else {
        	changeStr = str;
        }

        for (int i = changeStr.length(); i < len; i++) {
        	changeStr = changeStr + pad;
        }

        return changeStr;
    }

    /**
     * 원본 문자열의 왼쪽에 지정한 길이만큼 공백으로 채움
     *
     * @param str 원본 문자열
     * @param length 지정한 길이
     * @return String 원본 문자열의 왼쪽에 지정한 길이만큼 공백으로 채운 문자열
     */
    public static String alignLeft(String str, int length) {
        return alignLeft(str, length, false);
    }

    /**
     * 원본 문자열의 왼쪽에 지정한 길이만큼 공백으로 채움
     *
     * @param str 원본 문자열
     * @param length 지정한 길이
     * @param isEllipsis 공백으로 채울지 여부
     * @return String 원본 문자열의 왼쪽에 지정한 길이만큼 공백으로 채운 문자열
     */
    public static String alignLeft(String str, int length, boolean isEllipsis) {

        if (str.length() <= length) {

            StringBuffer temp = new StringBuffer(str);
            for (int i = 0; i < (length - str.length()); i++) {
                temp.append(WHITE_SPACE);
            }
            return temp.toString();
        } else {
            if (isEllipsis) {

                StringBuffer temp = new StringBuffer(length);
                temp.append(str.substring(0, length - 3));
                temp.append("...");

                return temp.toString();
            } else {
                return str.substring(0, length);
            }
        }
    }

    /**
     * 원본 문자열의 오른쪽에 지정한 길이만큼 공백으로 채움
     *
     * @param str 원본 문자열
     * @param length 지정한 길이
     * @return String 원본 문자열의 오른쪽에 지정한 길이만큼 공백으로 채운 문자열
     */
    public static String alignRight(String str, int length) {

        return alignRight(str, length, false);
    }

    /**
     * 원본 문자열의 오른쪽에 지정한 길이만큼 공백으로 채움
     *
     * @param str 원본 문자열
     * @param length 지정한 길이
     * @param isEllipsis 공백 여부
     * @return String 원본 문자열의 오른쪽에 지정한 길이만큼 공백으로 채운 문자열
     */
    public static String alignRight(String str, int length, boolean isEllipsis) {

        if (str.length() <= length) {

            StringBuffer temp = new StringBuffer(length);
            for (int i = 0; i < (length - str.length()); i++) {
                temp.append(WHITE_SPACE);
            }
            temp.append(str);
            return temp.toString();
        } else {
            if (isEllipsis) {

                StringBuffer temp = new StringBuffer(length);
                temp.append(str.substring(0, length - 3));
                temp.append("...");
                return temp.toString();
            } else {
                return str.substring(0, length);
            }
        }
    }

    /**
     * 원본 문자열의 중앙에 지정한 길이만큼 공백으로 채움
     *
     * @param str 원본 문자열
     * @param length 지정한 길이
     * @return String 원본 문자열의 중앙에 지정한 길이만큼 공백으로 채운 문자열
     */
    public static String alignCenter(String str, int length) {
        return alignCenter(str, length, false);
    }

    /**
     * 원본 문자열의 중앙에 지정한 길이만큼 공백으로 채움
     *
     * @param str 원본 문자열
     * @param length 지정한 길이
     * @param isEllipsis 공백 여부
     * @return String 원본 문자열의 중앙에 지정한 길이만큼 공백으로 채운 문자열
     */
    public static String alignCenter(String str, int length, boolean isEllipsis) {
        if (str.length() <= length) {

            StringBuffer temp = new StringBuffer(length);
            int leftMargin = (int) (length - str.length()) / 2;

            int rightMargin;
            if ((leftMargin * 2) == (length - str.length())) {
                rightMargin = leftMargin;
            } else {
                rightMargin = leftMargin + 1;
            }

            for (int i = 0; i < leftMargin; i++) {
                temp.append(WHITE_SPACE);
            }

            temp.append(str);

            for (int i = 0; i < rightMargin; i++) {
                temp.append(WHITE_SPACE);
            }

            return temp.toString();
        } else {
            if (isEllipsis) {

                StringBuffer temp = new StringBuffer(length);
                temp.append(str.substring(0, length - 3));
                temp.append("...");
                return temp.toString();
            } else {
                return str.substring(0, length);
            }
        }

    }

    
    /**
     * 원본  문자열의 첫자는 대문자로 나머지는 소문자로 변환
     * 
     * @param str 원본 문자열
     * @return String 원본  문자열의 첫자는 대문자로 나머지는 소문자로 변환한 문자열
     */
    public static String capitalize(String str) {
        return !isNull(str) ? str.substring(0, 1).toUpperCase()
            + str.substring(1).toLowerCase() : str;
    }

    /**
     * 원본 문자열의 pattern 을 비교
     * 
     * @param str 원본 문자열
     * @param pattern 패턴 문자열
     * @return boolean 원본 문자열의 pattern 을 비교 여부
     * @throws Exception
     */
    public static boolean isPatternMatch(String str, String pattern)
            throws Exception {
        Matcher matcher = Pattern.compile(pattern).matcher(str);
        log.debug("" + matcher);

        return matcher.matches();
    }

    /**
     * 원본 문자열을 영문 캐릭터셋으로 변환
     * 
     * @param kor 원본 문자열
     * @return String 원본 문자열을 영문 캐릭터셋으로 변환한 문자열
     * @throws UnsupportedEncodingException
     */
    public static String toEng(String kor) throws UnsupportedEncodingException {

        if (isNull(kor)) {
            return null;
        }

        return new String(kor.getBytes("KSC5601"), "8859_1");

    }

    /**
     * 원본 문자열을 한글 캐릭터셋으로 변환
     * 
     * @param en 원본 문자열 
     * @return String 원본 문자열을 한글 캐릭터셋으로 변환한 문자열
     * @throws UnsupportedEncodingException
     */
    public static String toKor(String en) throws UnsupportedEncodingException {

        if (isNull(en)) {
            return null;
        }

        return new String(en.getBytes("8859_1"), "euc-kr");
    }

    /**
     * 원본 문자열에서 대상 문자열을 비교해서 같은 문자열의 개수를 반환
     * 
     * @param str 원본 문자열
     * @param charToFind 대상 문자열
     * @return count 원본 문자열에서 대상 문자열을 비교해서 같은 문자열의 개수
     */
    public static int countOf(String str, String charToFind) {
        int findLength = charToFind.length();
        int count = 0;

        for (int idx = str.indexOf(charToFind); idx >= 0; idx =
            str.indexOf(charToFind, idx + findLength)) {
            count++;
        }

        return count;
    }

    /**
     * 원본 문자열를 Base64 인코딩하여 변환해준다.
     *
     * @param str 원본 문자열
     * @return lastStr Base64 인코딩한 문자열
     */
    public static String encodeString(String str) {
        Base64Encoder encoder = new Base64Encoder();
        return new String(encoder.encode(str.getBytes())).trim();
    }

    /**
     * 원본 문자열를 Base64 디코딩하여 변환해준다.
     *
     * @param str 원본 문자열
     * @return lastStr Base64 디코딩한 문자열
     */
    public static String decodeString(String str) {
        Base64Encoder dec = new Base64Encoder();
        return new String(dec.decode(str));
    }

    /**
     * 원본 문자열에서 첫번째 문자를 대문자 또는 소문자로 변환해준다.
     *
     * @param str 원본 문자열
     * @return lastStr 원본 문자열에서 첫번째 문자를 대문자 또는 소문자로 변환한 문자열
     */
    public static String swapFirstLetterCase(String str) {
        StringBuffer sbuf = new StringBuffer(str);
        sbuf.deleteCharAt(0);
        if (Character.isLowerCase(str.substring(0, 1).toCharArray()[0])) {
            sbuf.insert(0, str.substring(0, 1).toUpperCase());
        } else {
            sbuf.insert(0, str.substring(0, 1).toLowerCase());
        }
        return sbuf.toString();
    }

    /**
     * 원본 문자열에 포함되어 있는 trim 할 문자열을 제거한 문자열을 반환해준다.
     *
     * @param origString 원본 문자열
     * @param trimString trim 할 문자열
     * @return lastStr 원본 문자열에 포함되어 있는 trim 제거한 문자열을 뺀 문자열
     */
    public static String trim(String origString, String trimString) {
        int startPosit = origString.indexOf(trimString);
        if (startPosit != -1) {
            int endPosit = trimString.length() + startPosit;
            return origString.substring(0, startPosit)
                + origString.substring(endPosit);
        }
        return origString;
    }

    /**
     * 원본 문자열에 포함되어 있는 토큰 문자열의 이후 문자열을 반환해준다.
     *
     * @param origStr 원본 문자열
     * @param strToken 토큰 문자열
     * @return lastStr 원본 문자열에 포함되어 있는 토큰 문자열의 이전 문자열과 이후 문자열의 배열
     */
    public static String getLastString(String origStr, String strToken) {
        StringTokenizer str = new StringTokenizer(origStr, strToken);
        String lastStr = "";
        while (str.hasMoreTokens()) {
            lastStr = str.nextToken();
        }
        return lastStr;
    }

    /**
     * 원본 문자열에 포함되어 있는 토큰 문자열의 이전 문자열과 이후 문자열을 배열로 반환해준다.
     *
     * @param str 원본 문자열
     * @param strToken 토큰 문자열
     * @return stringArray 원본 문자열에 포함되어 있는 토큰 문자열의 이전 문자열과 이후 문자열의 배열
     */
    public static String[] getStringArray(String str, String strToken) {
        if (str.indexOf(strToken) != -1) {
            StringTokenizer st = new StringTokenizer(str, strToken);
            String[] stringArray = new String[st.countTokens()];
            for (int i = 0; st.hasMoreTokens(); i++) {
                stringArray[i] = st.nextToken();
            }
            return stringArray;
        }
        return new String[] {str };
    }

    /**
     * Null 또는 빈값이면 false 아니면 ture
     *
     * @param str 원본 문자열
     * @return boolean Null 또는 빈값인지 여부
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Null 또는 빈값이면 true 아니면 false
     *
     * @param str 원본 문자열
     * @return boolean Null 또는 빈값인지 여부
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 원본 문자열에 변경 대상 문자열을 변경 문자열로 치환하여 준다.
     *
     * @param str 원본 문자열
     * @param replacedStr 변경 대상 문자열
     * @param replaceStr 변경 문자열
     * @return boolean 변경 대상 문자열을 변경 문자열로 치환한 문자열
     */
    public static String replace(String str, String replacedStr,
            String replaceStr) {
        String newStr = "";
        if (str.indexOf(replacedStr) != -1) {
            String s1 = str.substring(0, str.indexOf(replacedStr));
            String s2 = str.substring(str.indexOf(replacedStr) + 1);
            newStr = s1 + replaceStr + s2;
        }
        return newStr;
    }

    /**
     * 원본 문자열에 패턴 문자열이 포함되어 있는지 여부
     *
     * @param str 원본 문자열
     * @param pattern 패턴 문자열
     * @return boolean 패턴 문자열이 포함되어 있는지 여부
     */
    public static boolean isPatternMatching(String str, String pattern)
            throws Exception {
        // if url has wild key, i.e. "*", convert it to
        // ".*" so that we can
        // perform regex matching
    	
    	String changePattern	= "";
    	
        if (pattern.indexOf('*') >= 0) {
        	changePattern = pattern.replaceAll("\\*", ".*");
        } else {
        	changePattern = pattern;
        }

        changePattern = "^" + changePattern + "$";

        return Pattern.matches(changePattern, str);
    }

    /**
     * 원본 문자열에 연속된 문자들이 최대 길이 이상 있는지 여부 
     *
     * @param str 원본 문자열
     * @param maxSeqNumber 연속된 문자의 최대 길이
     * @return boolean 연속된 문자들이 최대 길이 이상 있는지 여부 
     */
    public static boolean containsMaxSequence(String str, String maxSeqNumber) {
        int occurence = 1;
        int max = string2integer(maxSeqNumber);
        if (str == null) {
            return false;
        }

        int sz = str.length();
        for (int i = 0; i < (sz - 1); i++) {
            if (str.charAt(i) == str.charAt(i + 1)) {
                occurence++;

                if (occurence == max)
                    return true;
            } else {
                occurence = 1;
            }
        }
        return false;
    }

    /**
     * 원본 문자열에 유효하지 않는 문자 배열이 포함되어 있는지 여부
     *
     * @param str 원본 문자열
     * @param invalidChars 유효하지 않는 문자 배열
     * @return 유효하지 않는 문자열 여부
     */
    public static boolean containsInvalidChars(String str, char[] invalidChars) {
        if (str == null || invalidChars == null) {
            return false;
        }
        int strSize = str.length();
        int validSize = invalidChars.length;
        for (int i = 0; i < strSize; i++) {
            char ch = str.charAt(i);
            for (int j = 0; j < validSize; j++) {
                if (invalidChars[j] == ch) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 원본 문자열에 유효하지 않는 문자열이 포함되어 있는지 여부
     *
     * @param str 원본 문자열
     * @param invalidChars 유효하지 않는 문자열
     * @return 유효하지 않는 문자열 여부
     */
    public static boolean containsInvalidChars(String str, String invalidChars) {
        if (str == null || invalidChars == null) {
            return true;
        }
        return containsInvalidChars(str, invalidChars.toCharArray());
    }

    /**
     * 원본 문자열이 숫자인지 여부
     * 
     * @param str 원본 문자열 
     * @return boolean 숫자 여부
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        if (sz == 0)
            return false;
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Reverses a String as per
     * {@link StringBuffer#reverse()}.
     * </p>
     * <p>
     * <A code>null</code> String returns
     * <code>null</code>.
     * </p>
     * 
     * <pre>
     * CommStringUtil.reverse(null)  		   = null
     * CommStringUtil.reverse(&quot;&quot;)    = &quot;&quot;
     * CommStringUtil.reverse(&quot;bat&quot;) = &quot;tab&quot;
     * </pre>
     * @param str 원본 문자열
     * @return String the reversed String, <code>null</code>
     *         if null String input
     */

    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuffer(str).reverse().toString();
    }

    /**
     * 지정한 길이에서 원본 문자열의 길이를 뺀 길이만큼 원본 문자열 앞에 채울 문자열을 채워서 반환한다.
     * 
     * @param originalStr 원본 문자열
     * @param ch 채울 문자
     * @param cipers 지정한 길이
     * @return String 결과값
     */
    public static String fillString(String originalStr, char ch, int cipers) {
        int originalStrLength = originalStr.length();

        if (cipers < originalStrLength)
            return null;

        int difference = cipers - originalStrLength;

        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < difference; i++)
            strBuf.append(ch);

        strBuf.append(originalStr);
        return strBuf.toString();
    }

    /**
     * 원본 문자열리 Null 또는 공백 문자열인지 여부
     * 
     * @param foo 체크할 문자열
     * @return boolean Null 또는 공백 문자열인지 여부
     */
    public static final boolean isEmptyTrimmed(String foo) {
    	
    	String changeFoo	= "";
    	if ( foo == null ) return true;
    	
    	changeFoo = foo.trim();
    	if ( changeFoo.length() == 0 ) return true;
    	
    	return false;
    }

    /**
     * 구분자 로 토큰 리스트를 생성한다. 
     * 
     * @param lst 원본 문자열
     * @param separator 구분자
     * @return tokens
     */
    public static List getTokens(String lst, String separator) {
        List tokens = new ArrayList();

        if (lst != null) {
            StringTokenizer st = new StringTokenizer(lst, separator);
            while (st.hasMoreTokens()) {
                try {
                    String en = st.nextToken().trim();
                    tokens.add(en);
                } catch (Exception e) {
                    log.debug(e.getMessage());
                }
            }
        }

        return tokens;
    }

    /**
     * 구분자(,) 로 토큰 리스트를 생성한다. 
     * 
     * @param lst 원본 문자열
     * @return List 토큰 리스트
     */
    public static List getTokens(String lst) {
        return getTokens(lst, ",");
    }

    /**
     * Camel 표기법으로 문자열를 변환
     * 
     * @param targetString 원본문자열
     * @param posChar Camel 표기법 치환 문자
     * @return result Camel 표기법으로 문자열를 변환한 값 
     */
    public static String convertToCamelCase(String targetString, char posChar) {
        StringBuffer result = new StringBuffer();
        boolean nextUpper = false;
        String allLower = targetString.toLowerCase();

        for (int i = 0; i < allLower.length(); i++) {
            char currentChar = allLower.charAt(i);
            if (currentChar == posChar) {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    currentChar = Character.toUpperCase(currentChar);
                    nextUpper = false;
                }
                result.append(currentChar);
            }
        }
        return result.toString();
    }

    /**
     * Camel 표기법으로 문자열를 변환
     * 
     * @param underScore 언더스코어 문자
     * @return String Camel 표기법으로 문자열를 변환한 값 
     */
    public static String convertToCamelCase(String underScore) {
        return convertToCamelCase(underScore, '_');
    }

    /**
     * Camel 표기법으로 문자열를 변환
     * 
     * @param camelCase 원본 문자열
     * @return result Camel 표기법으로 문자열를 변환한 값 
     */
    public static String convertToUnderScore(String camelCase) {
        String result = "";
        for (int i = 0; i < camelCase.length(); i++) {
            char currentChar = camelCase.charAt(i);
            // This is starting at 1 so the result does
            // not end up with an
            // underscore at the begin of the value
            if (i > 0 && Character.isUpperCase(currentChar)) {
                result = result.concat("_");
            }
            result =
                result.concat(Character.toString(currentChar).toLowerCase());
        }
        return result;
    }
    
    /**
     * List가 String으로 되어 있을때 List로 형변환
     * @param stringList
     * @return
     */
    public static List<HashMap<String, Object>> stringToList(String stringList) {
        List<HashMap<String, Object>> rtnList = null;
        
        ObjectMapper mapper = new ObjectMapper();
        
        try{
        	
			rtnList = mapper.readValue(stringList, new TypeReference<List<HashMap<String, Object>>>(){});
			
		}catch(JsonParseException e){
			log.error("[JsonParseException Error] [String to JsonParsing Error]");
		}catch(JsonMappingException e){
			log.error("[JsonParseException Error] [String to JsonMapping Error] " + e.getMessage());
		}catch(IOException e){
			log.error("[stringToList Error] " + e.getMessage());
		}
        
        return rtnList;
    }
    
    /**
     * HashMap이 String으로 되어 있을때 HashMap로 형변환
     * @param stringHashMap
     * @return
     */
    public static HashMap<String, Object> stringToHashMap(String stringHashMap) {
        HashMap<String, Object> rtnHashMap = null;
        
        ObjectMapper mapper = new ObjectMapper();
        
        try{
        	
        	rtnHashMap = mapper.readValue(stringHashMap, new TypeReference<HashMap<String, Object>>(){});
			
		}catch(JsonParseException e){
			log.error("[JsonParseException Error] [String to JsonParsing Error]");
		}catch(JsonMappingException e){
			log.error("[JsonParseException Error] [String to JsonMapping Error] " + e.getMessage());
		}catch(IOException e){
			log.error("[stringToHashMap Error] " + e.getMessage());
		}
        
        return rtnHashMap;
    }
    
    /**
     * HashMap이 String으로 되어 있을때 Arrya로 형변환
     * @param stringHashMap
     * @return
     */
    public static String[] stringToArray(String stringArray) {
        String[] rtnArray = null;
        
        ObjectMapper mapper = new ObjectMapper();
        
        try{
        	
        	rtnArray = mapper.readValue(stringArray, new TypeReference<String[]>(){});
			
		}catch(JsonParseException e){
			log.error("[JsonParseException Error] [String to JsonParsing Error]");
		}catch(JsonMappingException e){
			log.error("[JsonParseException Error] [String to JsonMapping Error] " + e.getMessage());
		}catch(IOException e){
			log.error("[stringToArray Error] " + e.getMessage());
		}
        
        return rtnArray;
    }
}
