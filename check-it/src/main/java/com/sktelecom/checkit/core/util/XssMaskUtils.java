package com.sktelecom.checkit.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * 크로스사이트 스크립트방여용 유틸
 * @author devkimsj
 *
 */
@Component("xssMaskUtils")
public class XssMaskUtils {

    private static final Log log = LogFactory.getLog(XssUtils.class);

    /**
     * HTML 특수문자셋
     */
    public static String[][] numericHtml = new String[][] { { "(&#32;)", " " },
        { "(&#33;)", "!" }, { "(&#34;)", "\"" }, { "(&#35;)", "#" }, { "(&#36;)", "$" }, { "(&#37;)", "%" }, { "(&#38;)", "&" },
        { "(&#39;)", "'" }, { "(&#40;)", "(" }, { "(&#41;)", ")" }, { "(&#42;)", "*" }, { "(&#43;)", "+" }, { "(&#44;)", "," },
        { "(&#45;)", "-" }, { "(&#46;)", "." }, { "(&#47;)", "/" }, { "(&#48;)", "0" }, { "(&#49;)", "1" }, { "(&#50;)", "2" },
        { "(&#51;)", "3" }, { "(&#52;)", "4" }, { "(&#53;)", "5" }, { "(&#54;)", "6" }, { "(&#55;)", "7" }, { "(&#56;)", "8" },
        { "(&#57;)", "9" }, { "(&#58;)", ":" }, { "(&#59;)", ";)" }, { "(&#60;)", "<" }, { "(&#61;)", "=" }, { "(&#62;)", ">" },
        { "(&#63;)", "?" }, { "(&#64;)", "@" }, { "(&#65;)", "A" }, { "(&#66;)", "B" }, { "(&#67;)", "C" }, { "(&#68;)", "D" },
        { "(&#69;)", "E" }, { "(&#70;)", "F" }, { "(&#71;)", "G" }, { "(&#72;)", "H" }, { "(&#73;)", "I" }, { "(&#74;)", "J" },
        { "(&#75;)", "K" }, { "(&#76;)", "L" }, { "(&#77;)", "M" }, { "(&#78;)", "N" }, { "(&#79;)", "O" }, { "(&#80;)", "P" },
        { "(&#81;)", "Q" }, { "(&#82;)", "R" }, { "(&#83;)", "S" }, { "(&#84;)", "T" }, { "(&#85;)", "U" }, { "(&#86;)", "V" },
        { "(&#87;)", "W" }, { "(&#88;)", "X" }, { "(&#89;)", "Y" }, { "(&#90;)", "Z" }, { "(&#91;)", "[" }, { "(&#92;)", "\\" },
        { "(&#93;)", "]" }, { "(&#94;)", "^" }, { "(&#95;)", "_" }, { "(&#96;)", "`" }, { "(&#97;)", "a" }, { "(&#98;)", "b" },
        { "(&#99;)", "c" }, { "(&#100;)", "d" }, { "(&#101;)", "e" }, { "(&#102;)", "f" }, { "(&#103;)", "g" }, { "(&#104;)", "h" },
        { "(&#105;)", "i" }, { "(&#106;)", "j" }, { "(&#107;)", "k" }, { "(&#108;)", "l" }, { "(&#109;)", "m" }, { "(&#110;)", "n" },
        { "(&#111;)", "o" }, { "(&#112;)", "p" }, { "(&#113;)", "q" }, { "(&#114;)", "r" }, { "(&#115;)", "s" }, { "(&#116;)", "t" },
        { "(&#117;)", "u" }, { "(&#118;)", "v" }, { "(&#119;)", "w" }, { "(&#120;)", "x" }, { "(&#121;)", "y" }, { "(&#122;)", "z" },
        { "(&#123;)", "{" }, { "(&#124;)", "|" }, { "(&#125;)", "}" }, { "(&#126;)", "~" } };

    /**
     * 실행가능한 태그 패턴
     */
    public static Pattern [] tagPtn1 = {
        Pattern.compile("<([^/]?)(script)([^<>]*)(.)*</([^<>]*)(script)([^<>]*)>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("<([^>/]*)(object)([^<>]*)(.)*</([^<>]*)(object)([^<>]*)>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("<([^>/]*)(embed)([^<>]*)(.)*</([^<>]*)(embed)([^<>]*)>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("<([^>/]*)(applet)([^<>]*)(.)*</([^<>]*)(applet)([^<>]*)>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("<([^>/]*)(form)([^<>]*)(.)*</([^<>]*)(form)([^<>]*)>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("<([^>/]*)(iframe)([^<>]*)(.)*</([^<>]*)(iframe)([^<>]*)>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("<([^/]?)(script)([^<>]*)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("<([^>/]*)(object)([^<>]*)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("<([^>/]*)(embed)([^<>]*)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("<([^>/]*)(applet)([^<>]*)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("<([^>/]*)(form)([^<>]*)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("<([^>/]*)(iframe)([^<>]*)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL) };

    public static Pattern [] tagPtnEx = {
        Pattern.compile("(&)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("(#)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("(\")", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("(')", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("(<)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("(>)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("(\\()", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("(\\))", Pattern.CASE_INSENSITIVE | Pattern.DOTALL)
    };

    public static String [] tegPtnExReplace = {
        "&amp;",
        "&#35;",
        "&#34;",
        "&#39;",
        "&lt;",
        "&gt;",
        "&#40;",
        "&#41;"
    };

    /**
     * 스크립트 문자열
     */
    public static Pattern [] scriptPtn = {
            Pattern.compile("(javascript)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
            Pattern.compile("(jscript)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
            Pattern.compile("(vbscript)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
            Pattern.compile("(alert)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL) };

    /**
     * 스크립트 문자열을 치환할 문자열
     */
    public static String [] scriptPtnChng = new String[] {"zabasklibt", "ksklibt", "m\\$sklibt", "zlert"};

    /**
     * 비교할 특수문자
     */
    public static char [] spclChar = new char[] {'&', '#', '(', ')'};

    /**
     * 특수문자 패턴
     */
    public static Pattern [] spclCharPtn = {
        Pattern.compile("&(?!#?\\w+;)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL), /* & */
        Pattern.compile("#(?!\\d+;)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),   /* # */
        Pattern.compile("(\")", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),         /* \ */
        Pattern.compile("(')", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),          /* ' */
        Pattern.compile("(<)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),          /* < */
        Pattern.compile("(>)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),          /* > */
        Pattern.compile("(\\()", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),        /* ( */
        Pattern.compile("(\\))", Pattern.CASE_INSENSITIVE | Pattern.DOTALL)         /* ) */
    };

    /**
     * HTML 코드
     */
    public static String [] symbloicHtml = new String [] {
        "&amp;",
        "&#35;",
        "&#34;",
        "&#39;",
        "&lt;",
        "&gt;",
        "&#40;",
        "&#41;"};

    /**
     * HTML코드 패턴
     */
    public static Pattern [] symbloicHtmlPtn = {
        Pattern.compile("(&amp;)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("(&#35;)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("(&#40;)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
        Pattern.compile("(&#41;)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL) };

    /**
     * Xss String
     * @param str
     * @return
     */
    public static String stripXSS(String str) {

        try {
            if (str == null || "".equals(str))
                return "";
        } catch(Exception e) {
        	log.error("XssUtil 문자열 치환 에러 발생");
            return "";
        }

        str = numericHtmlToNormalChar(str);
        str = typeScriptReplace(str);
        str = tagReplace(str);
        //str = tagReplaceEx(str);
        str = spclCharToHtmlChar(str);
        str = convertHtmlCharToSpChar(str);

        return str;
    }

    /**
     * Xss int
     * @param str
     * @return
     */
    public static int stripXSS(int str) {
        return str;
    }

    /**
     * Xss 배열
     * @param strArr
     * @return
     */
    public static String [] stripXSS(String [] strArr) {

        try {
            if (strArr == null || strArr.length == 0)
                return new String[]{};
        } catch(Exception e) {
        	log.error("XssUtil 문자열 Array 치환 에러 발생");
            return new String[]{};
        }

        String [] retStr = new String[strArr.length];
        int len = retStr.length;

        for (int i = 0; i < len; i++) {
            retStr[i] = stripXSS(strArr[i]);
        }

        return retStr;
    }

    /**
     * Xss arrayList
     * @param arrayList
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList stripXSS(ArrayList arrayList) {

        ArrayList rtnArrayList = new ArrayList();

        if(arrayList != null && arrayList.size() > 0){

            for (int i = 0; i < arrayList.size(); i++) {
            	if(arrayList.get(i) instanceof HashMap){
            		rtnArrayList.add(i, stripXSS((HashMap)arrayList.get(i)));
            	}else if(arrayList.get(i) instanceof LinkedMap){
            		rtnArrayList.add(i, stripXSS((LinkedMap)arrayList.get(i)));
    			}else if(arrayList.get(i) instanceof CommMap){
            		rtnArrayList.add(i, stripXSS((CommMap)arrayList.get(i)));
    			}else{
            		rtnArrayList.add(i, stripXSS((String)arrayList.get(i)));
            	}
            }
        }

        return rtnArrayList;
    }

    /**
     * Xss HashMap
     * @param hashMap
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap stripXSS(HashMap hashMap) {

    	HashMap rtnMap = new HashMap();
        try {

        	if(hashMap != null){
        		Set setMap = hashMap.entrySet();
    			Iterator iteratorMap = setMap.iterator();

    			while(iteratorMap.hasNext()){

    				Map.Entry entryMap = (Map.Entry)iteratorMap.next();

    				String key   = String.valueOf(entryMap.getKey());
//    				String value = String.valueOf(entryMap.getValue());
    				if(entryMap.getValue() instanceof ArrayList){
    					ArrayList result = (ArrayList)entryMap.getValue();
	    				rtnMap.put(key, stripXSS(result));
	    			}else if(entryMap.getValue() instanceof CommMap){
	    				CommMap result = (CommMap)entryMap.getValue();
	    				rtnMap.put(key, stripXSS(result));
	    			}else if(entryMap.getValue() instanceof HashMap){
	    				HashMap result = (HashMap)entryMap.getValue();
	    				rtnMap.put(key, stripXSS(result));
	    			}else if(entryMap.getValue() instanceof String[]){
	    				String[] result = (String[])entryMap.getValue();
	    				rtnMap.put(key, stripXSS(result));
	    			}else if(entryMap.getValue() instanceof String){
	    				String marskingString = marsking(key, String.valueOf(entryMap.getValue()));
	    				rtnMap.put(key, stripXSS(marskingString));
	    			}else{
	    				rtnMap.put(key, entryMap.getValue());
	    			}

    			}
        	}

        } catch(Exception e){
        	log.error(e.getMessage(), e);
        }
        return rtnMap;

    }

    /**
     * Xss HashMap
     * @param hashMap
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap stripXSS(LinkedMap linkedMap) {

    	HashMap rtnMap = new HashMap();
        try {

        	if(linkedMap != null){
        		Set setMap = linkedMap.entrySet();
    			Iterator iteratorMap = setMap.iterator();

    			while(iteratorMap.hasNext()){

    				Map.Entry entryMap = (Map.Entry)iteratorMap.next();

    				String key   = String.valueOf(entryMap.getKey());
//    				String value = String.valueOf(entryMap.getValue());
    				if(entryMap.getValue() instanceof ArrayList){
    					ArrayList result = (ArrayList)entryMap.getValue();
	    				rtnMap.put(key, stripXSS(result));
	    			}else if(entryMap.getValue() instanceof CommMap){
	    				CommMap result = (CommMap)entryMap.getValue();
	    				rtnMap.put(key, stripXSS(result));
	    			}else if(entryMap.getValue() instanceof HashMap){
	    				HashMap result = (HashMap)entryMap.getValue();
	    				rtnMap.put(key, stripXSS(result));
	    			}else if(entryMap.getValue() instanceof String[]){
	    				String[] result = (String[])entryMap.getValue();
	    				rtnMap.put(key, stripXSS(result));
	    			}else if(entryMap.getValue() instanceof String){
	    				String marskingString = marsking(key, String.valueOf(entryMap.getValue()));
	    				rtnMap.put(key, stripXSS(marskingString));
	    			}else{
	    				rtnMap.put(key, entryMap.getValue());
	    			}

    			}
        	}

        } catch(Exception e){
        	log.error(e.getMessage(), e);
        }
        return rtnMap;

    }

    /**
     * Xss commMap
     * @param hashMap
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap stripXSS(CommMap commMap) {

    	HashMap rtnMap = new HashMap();
        try {

        	if(commMap != null){
        		Set setMap = commMap.entrySet();
    			Iterator iteratorMap = setMap.iterator();

    			while(iteratorMap.hasNext()){

    				Map.Entry entryMap = (Map.Entry)iteratorMap.next();

    				String key   = String.valueOf(entryMap.getKey());
//    				String value = String.valueOf(entryMap.getValue());
    				if(entryMap.getValue() instanceof ArrayList){
    					ArrayList result = (ArrayList)entryMap.getValue();
	    				rtnMap.put(key, stripXSS(result));
	    			}else if(entryMap.getValue() instanceof CommMap){
	    				CommMap result = (CommMap)entryMap.getValue();
	    				rtnMap.put(key, stripXSS(result));
	    			}else if(entryMap.getValue() instanceof HashMap){
	    				HashMap result = (HashMap)entryMap.getValue();
	    				rtnMap.put(key, stripXSS(result));
	    			}else if(entryMap.getValue() instanceof String[]){
	    				String[] result = (String[])entryMap.getValue();
	    				rtnMap.put(key, stripXSS(result));
	    			}else if(entryMap.getValue() instanceof String){
	    				String marskingString = marsking(key, String.valueOf(entryMap.getValue()));
	    				rtnMap.put(key, stripXSS(marskingString));
	    			}else{
	    				rtnMap.put(key, entryMap.getValue());
	    			}

    			}
        	}

        } catch(Exception e){
        	log.error(e.getMessage(), e);
        }
        return rtnMap;

    }

    /**
     * NumericHtml을 일반 문자열로 변환
     * @param str
     * @return
     */
    public static String numericHtmlToNormalChar(String str) {
        int numericHtmlLen = numericHtml.length;
        for (int i = 0; i < numericHtmlLen; i++) {
            str = str.replaceAll(numericHtml[i][0], numericHtml[i][1]);
        }
        return str;
    }

    public static String typeScriptReplace(String str) {
        int scriptLen = scriptPtn.length;

        for (int s = 0; s < scriptLen; s++) {
            Matcher ms = scriptPtn[s].matcher(str);
            if (ms.find()) {
                str = ms.replaceAll(scriptPtnChng[s]);
            }
        }
        return str;
    }

    public static String tagReplace(String str) {
        int ptnLen = tagPtn1.length;
        for (int i = 0; i < ptnLen; i++) {
            Matcher m = tagPtn1[i].matcher(str);
            if (m.find()) {
                str = m.replaceAll("");
            }
        }
        return str;
    }

    public static String tagReplaceEx(String str) {
        int ptnLen = tagPtnEx.length;
        for (int i = 0; i < ptnLen; i++) {
            Matcher m = tagPtnEx[i].matcher(str);
            if (m.find()) {
                str = m.replaceAll(tegPtnExReplace[i]);
            }
        }
        return str;
    }

    public static String spclCharToHtmlChar(String str) {

        int ptnLen = spclCharPtn.length;

        for (int i = 0; i < ptnLen; i++) {
            Matcher m = spclCharPtn[i].matcher(str);
            if (m.find()) {
                str = m.replaceAll(symbloicHtml[i]);
            }
        }
        return str;
    }

    public static String convertHtmlCharToSpChar(String str) {

        if (str == null || str.equals(""))
            return "";

        String ret = str;
        ret = ret.replaceAll("(&amp;)", "&"); // &amp; -> &
        ret = ret.replaceAll("(&lt;)", "<"); // &lt; -> <
        ret = ret.replaceAll("(&gt;)", ">"); // &gt; -> >
        ret = ret.replaceAll("(&#39;)", "\'"); // &#39; -> '
        ret = ret.replaceAll("(&#34;)", "\""); // &#34; -> \"
        ret = ret.replaceAll("(&#35;)", "#"); // &#35; -> #
        ret = ret.replaceAll("(&#40;)", "("); // &#40; -> (
        ret = ret.replaceAll("(&#41;)", ")"); // &#41; -> )
        ret = ret.replaceAll("(&quot;)","\"");

        return ret;
    }

    public static String [] convertHtmlCharToSpChar(String [] strArr) {

        try {
            if (strArr == null || strArr.length == 0)
                return new String[]{};
        } catch(Exception e) {
        	log.error("XssUtil 문자열 Array 치환 에러 발생");
            return new String[]{};
        }

        String [] retStr = new String[strArr.length];
        int len = retStr.length;

        for (int i = 0; i < len; i++) {
            retStr[i] = convertHtmlCharToSpChar(strArr[i]);
        }

        return retStr;
    }

    /**
     * 마스킹 처리
     * @param strArr
     * @return
     */
    @SuppressWarnings("static-access")
	public static String marsking(String key, String value) {
    	value = value.trim();
    	// 마스킹 처리 key
    	String[][] marskingArr = {
    							 {"userNm", "name"}
    							,{"empName", "name"}
    							,{"userId", "name"}
    							,{"hpNo", "phone"}
    							,{"telNo", "phone"}
    							,{"rptTel", "phone"}
    							,{"email", "email"}
    							,{"EMail", "email"}
    							,{"csCeoName", "name"}
    							,{"carNo", "car"}
    							,{"rtnCarNo", "car"}
    							,{"manageCnm", "mng"}
    							,{"formationNm", "mng"}
    							,{"rptUseridEdit", "name"}
    							,{"reqUserIdEdit", "name"}
    							,{"regIdEdit", "name"}
    							,{"regNm", "name"}
    							,{"confirmNm", "name"}
    							,{"creId", "name"}
    							,{"reqUserId", "name"}
    							,{"csUserName", "name"}
    							,{"recUsernms", "name"}
    							,{"recUserIdEdit", "name"}
    							,{"workUsernms", "name"}
    							,{"workUserIdEdit", "name"}
    							,{"updIdEdit", "name"}
    							,{"AUserIdEdit", "name"}
    							,{"jobEndIdEdit", "name"}
    							,{"chkUseridEdit", "name"}
    							,{"chkOutUserEdit", "name"}
    							,{"csAgentUseridEdit", "name"}
    							,{"csAgentUsernm", "name"}
    							,{"id", "name"} 
    							,{"jobPersonId", "name"} 
    							,{"updId", "name"} 
    							,{"reqTel","phone"} 
    							,{"reqEmail", "email"} 
    							,{"creNm", "name"} 
    							,{"gongjiSendUser","name"} 
    							,{"rptJobUserEdit", "name"} 
    							,{"jobUseridEdit", "name"}
    							,{"telPersonIdEdit", "name"}
    							,{"afIoChkUserNm", "name"}
    							,{"afIoChkUser", "name"}
    							,{"csTel", "phone"}
    							,{"workerIdEdit", "name"}
    							,{"workerTel","phone"}
    							,{"workerEmail", "email"}
    							,{"chkInUser", "name"}
    							,{"chkOutUser", "name"}
    							};
    	String marskingStr = null;

        try {
            if(key == null || value == null)
                return new String();
        } catch(Exception e) {
        	log.error("XssUtil Marsking 치환 에러 발생");
            return new String();
        }
        boolean isMarsking = false;
        String marskingType = "";
        for (int i = 0; i < marskingArr.length; i++) {
        	if(marskingArr[i][0].equals(key)) {
        		isMarsking = true;
        		marskingType = marskingArr[i][1];
        	}
        }

        if(isMarsking) {
        	CommMaskUtils commMaskUtils = new CommMaskUtils();

        	// 이름 마스킹
        	if("name".equals(marskingType)) {
        		marskingStr = commMaskUtils.getMaskName(value);
        	}

        	// 휴대폰 마스킹
        	if("phone".equals(marskingType)) {
        		marskingStr = commMaskUtils.getMaskHpNo(value);
        	}

        	// 이메일 마스킹
			if("email".equals(marskingType)) {
				marskingStr = commMaskUtils.getMaskEmail(value);
			}
			
			// 차량번호 마스킹
			if("car".equals(marskingType)) {
				marskingStr = commMaskUtils.getMaskCarNo(value);
			}
			
			// 센터, 소속 마스킹
			if("mng".equals(marskingType)) {
				marskingStr = commMaskUtils.getMaskManageCnm(value);
			}

        }else {
        	marskingStr = value;
        }

        return marskingStr;
    }

}
