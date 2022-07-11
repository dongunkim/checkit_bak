package com.sktelecom.checkit.core.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 마스크 유틸
 * @author devkimsj
 * @since 2018.12.21
 * @version 1.0
 *
 */
public class CommMaskUtils {

	private static Logger log = LoggerFactory.getLogger(CommStringUtils.class.getName());

	/**
	 * 휴대폰번호 마스크(010****5678)
	 * @param str
	 * @return
	 */
	public static String getMaskId(String str) {
		String replaceString = str;

		int cutSize = 0;
		if(str.length() < 5) {
			cutSize = 2;
		}else {
			cutSize = 3;
		}

		replaceString = str.substring(0, str.length() - cutSize);

		for(int i = 0; i < cutSize; i++) {
			replaceString += "*";
		}

		return replaceString;
	}

	/**
	 * 휴대폰번호 마스크(010****5678)
	 * 일반전화번호 마스크(02***5678)
	 * @param str
	 * @return
	 */
	public static String getMaskHpNo(String str) {
		String replaceString = str;
		
		Matcher matcher = Pattern.compile("^(\\d{2,3})-?(\\d{3,4})-?(\\d{4})(\\S{0,10})$").matcher(str);
		Matcher matcher1 = Pattern.compile("^(\\S{0,5})(\\d{2,3})-?(\\d{3,4})-?(\\d{4})$").matcher(str);
		Matcher matcher2 = Pattern.compile("^(\\S{0,5})(\\d{2,3})-?(\\d{3,4})-?(\\d{4})(\\S{0,10})$").matcher(str);
		Matcher matcher3 = Pattern.compile("^(\\d{3})-?(\\d{3,4})-?(\\d{4})$").matcher(str);

		boolean isHyphen = false;
		if(str.indexOf("-") > -1) {
			isHyphen = true;
		}
		
		if(matcher.matches()) {
			replaceString = getMask(matcher, 2, isHyphen);
		}else if(matcher1.matches()) {
			replaceString = getMask(matcher1, 2, isHyphen);
		}else if(matcher2.matches()) {
			replaceString = getMask(matcher2, 2, isHyphen);
		}else if(matcher3.matches()) {
			replaceString = getMask(matcher3, 2, isHyphen);
		}
		
System.out.println(replaceString);
		return replaceString;
	}
	
	/**
	 * 이름 마스크(홍*동)
	 * @param str
	 * @return
	 */
	public static String getMaskName(String str) {
		String replaceString = str;

		String pattern = "";
		if(str.length() == 2) {
			pattern = "^(.)(.+)$";
		}else{
			pattern = "^(.)(.+)(.)$";
		}

		Matcher matcher = Pattern.compile(pattern).matcher(str);

		if(matcher.matches()) {
			replaceString = getMask(matcher, 2, false);
		}

		return replaceString;
	}
	
	/**
	 * 센터, 소속 마스크 (**터, *당) (**실,*설,**지원)
	 * @param str
	 * @return
	 */
	public static String getMaskManageCnm (String str) {
		String replaceString = str;

		String pattern = "";
		if(str.length() == 2) {
			pattern = "^(.)(.+)$";
		}else{
			pattern = "^(.+)(.)$";
		}

		Matcher matcher = Pattern.compile(pattern).matcher(str);

		if(matcher.matches()) {
			replaceString = getMask(matcher, 1, false);
		}

		return replaceString;
	}
	
	/**
	 * 차량번호 마스크(1**4)
	 * @param str
	 * @return
	 */
	public static String getMaskCarNo(String str) {
		String replaceString = str;
		
		Matcher matcher = Pattern.compile("^(\\d{1})(\\d{2})(\\d{1})$").matcher(str);

		if(matcher.matches()) {
			replaceString = getMask(matcher, 2, false);
		}

		return replaceString;
	}

	/**
	 * 이메일 마스크(ab*****@test.com)
	 * @param str
	 * @return
	 */
	public static String getMaskEmail(String str) {
		String replaceString = str;

		Matcher matcher = Pattern.compile("^(..)(.*)([@]{1})(.*)$").matcher(str);
		if(matcher.matches()) {
			replaceString = getMask(matcher, 2, false);
		}
		return replaceString;
	}

	/**
	 * IP 마스크(127.0.*.1)
	 * @param str
	 * @return
	 */
	public static String getMaskIp(String str) {

		String replaceString = str;
		
		Matcher matcher = Pattern.compile("^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$").matcher(str);

		if(matcher.matches()) {
			replaceString = getMask(matcher, 3, true);
		}

		return replaceString;
	}
	
	protected static String getMask(Matcher matcher, int cnt, boolean isHyphen) {
		
		String replaceString = "";
		
		for(int i = 1; i <= matcher.groupCount(); i++) {
			String replaceTarget = matcher.group(i);
			if(i == cnt) {
				char[] c = new char[replaceTarget.length()];
				Arrays.fill(c, '*');

				replaceString = replaceString + String.valueOf(c);
			}else{
				replaceString = replaceString + replaceTarget;
			}
			
			if(isHyphen && i < matcher.groupCount()) {
				replaceString = replaceString + "-";
			}
		}
		
		return replaceString;
	}
}