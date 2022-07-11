package com.sktelecom.checkit.core.util;

import java.math.BigDecimal;

/**
 * 
 * 숫자의 연산을 처리하는 유틸 클래스
 * 숫자와 관련된 여러 기능을 제공하는 유틸이다. 숫자의 연산 및 올림,
 * 내림 등의 기능을 제공한다.
 * --------------------------------------
 * --------------------------------- BigDecimal의 Field
 * ----------------------------------------------------
 * ------------------- ROUND_FLOOR : 무조건 내림 (음수일 경우에
 * 무조건 올림) ROUND_DOWN : 무조건 내림 ROUND_HALF_DOWN : 반올림
 * (5보다 크면 올림) ROUND_HALF_UP : 반올림 (5보다 크거나 같으면 올림)
 * ROUND_HALF_EVEN : 반올림 (좌측의 수가 홀수면 HALF_UP, 짝수면
 * HALF_DOWN) ROUND_UP : 무조건 올림 ROUND_CEILING : 무조건 올림
 * (음수일 경우에 무조건 내림)
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
public class CommNumericUtils {
    // /**
    // * <p>
    // * 에러나 이벤트와 관련된 각종 메시지를 로깅하기 위한 Log 오브젝트
    // * </p>
    // */
    // private static Log log =
    // LogFactory.getLog(CommNumericUtil.class.getName());

    /**
     * <p>
     * 올림
     * </p>
     */
    public static final int ROUND_UP = BigDecimal.ROUND_UP;

    /**
     * <p>
     * 내림(절사)
     * </p>
     */
    public static final int ROUND_DOWN = BigDecimal.ROUND_DOWN;

    /**
     * <p>
     * 반올림
     * </p>
     */
    public static final int ROUND_HALF_UP = BigDecimal.ROUND_HALF_UP;

    /**
     * <p>
     * 사용하지 않음
     * </p>
     */
    public static final int ROUND_UNNECESSARY = BigDecimal.ROUND_UNNECESSARY;

    /**
     * <p>
     * <strong>NumericHelper</strong>의 default
     * 컨스트럭터(Constructor).
     * </p>
     */
    protected CommNumericUtils() {
    }

    /**
     * <p>
     * 문자열의 Number형 문자열인지 여부 (- 기호나 소수점도 포함)
     * </p>
     * @param source
     *        검증 하고자 하는 문자열
     * @return 숫자형 문자열 여부 (true : 숫자형)
     */
    public static boolean isNumber(String source) {

        if (CommStringUtils.isNull(source)) {
            return false;
        }

        try {
            Double db = new Double(source);
            if (db.isNaN()) {
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * <p>
     * String형 값의 기본덧셈을 실행한다.
     * </p>
     * @param thisVal
     *        <code>String</code>
     * @param addVal
     *        <code>String</code>
     * @return 결과 값
     * @see #isPlus(String, String, int)
     * @see #isPlus(String, String, int, int)
     */
    public static String plus(String thisVal, String addVal) {
        String result = null;
        BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
        BigDecimal two = new BigDecimal(addVal == null ? "0" : addVal);
        result = one.add(two).toString();
        return result;
    }

    /**
     * <p>
     * 덧셈한 결과값의 소숫점 자릿수만 지정한다.
     * </p>
     * @param thisVal
     *        <code>String</code>
     * @param addVal
     *        <code>String</code>
     * @param scale
     *        <code>int</code> 자리수지정
     * @return 결과 값
     * @see #isPlus(String, String, int, int)
     * @see #isPlus(String, String)
     */
    public static String plus(String thisVal, String addVal, int scale) {
        return plus(thisVal, addVal, scale, ROUND_UNNECESSARY);
    }

    /**
     * <p>
     * 덧셈한 결과값의 자릿수와 올림,내림,절삭여부 지정한다.
     * </p>
     * @param thisVal
     *        <code>String</code>
     * @param addVal
     *        <code>String</code>
     * @param scale
     *        <code>int</code> 자리수지정
     * @param roundMode
     *        <code>int</code> Round 여부 <br>
     *        올 림 : {@link #ROUND_UP} <br>
     *        내 림 : {@link #ROUND_DOWN} <br>
     *        반올림 : {@link #ROUND_HALF_UP}
     * @return 결과 값
     * @see #isPlus(String, String, int)
     * @see #isPlus(String, String)
     */
    public static String plus(String thisVal, String addVal, int scale,
            int roundMode) {
        String result = null;
        BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
        BigDecimal two = new BigDecimal(addVal == null ? "0" : addVal);
        result = one.add(two).setScale(scale, roundMode).toString();
        return result;
    }

    /**
     * <p>
     * 기본뺄셈을 실행한다.
     * </p>
     * @param thisVal
     *        <code>String</code>
     * @param addVal
     *        <code>String</code>
     * @return 결과 값
     * @see #isMinus(String, String, int)
     * @see #isMinus(String, String, int, int)
     */
    public static String minus(String thisVal, String addVal) {
        String result = null;
        BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
        BigDecimal two = new BigDecimal(addVal == null ? "0" : addVal);
        result = one.subtract(two).toString();
        return result;

    }

    /**
     * <p>
     * 뺄셈한 결과값의 자릿수만 지정한다.
     * </p>
     * @param thisVal
     *        <code>String</code>
     * @param addVal
     *        <code>String</code>
     * @param scale
     *        <code>int</code> 자리수지정
     * @return 결과 값
     * @see #isMinus(String, String, int, int)
     * @see #isMinus(String, String)
     */
    public static String minus(String thisVal, String addVal, int scale) {
        return minus(thisVal, addVal, scale, ROUND_UNNECESSARY);
    }

    /**
     * <p>
     * 뺄셈한 결과값의 자릿수와 올림,내림,절삭여부 지정한다.
     * </p>
     * @param thisVal
     *        <code>String</code>
     * @param addVal
     *        <code>String</code>
     * @param scale
     *        <code>int</code> 자리수지정
     * @param roundMode
     *        <code>int</code> Round 여부 <br>
     *        올 림 : {@link #ROUND_UP} <br>
     *        내 림 : {@link #ROUND_DOWN} <br>
     *        반올림 : {@link #ROUND_HALF_UP}
     * @return 결과 값
     * @see #isMinus(String, String, int)
     * @see #isMinus(String, String)
     */
    public static String minus(String thisVal, String addVal, int scale,
            int roundMode) {
        String result = null;
        BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
        BigDecimal two = new BigDecimal(addVal == null ? "0" : addVal);
        result = one.subtract(two).setScale(scale, roundMode).toString();
        return result;
    }

    /**
     * <p>
     * 기본곱셈을 실행한다.
     * </p>
     * @param thisVal
     *        <code>String</code>
     * @param addVal
     *        <code>String</code>
     * @return 결과 값
     * @see #isMultiply(String, String, int)
     * @see #isMultiply(String, String, int, int)
     */
    public static String multiply(String thisVal, String addVal) {
        String result = null;
        BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
        BigDecimal two = new BigDecimal(addVal == null ? "0" : addVal);
        result = one.multiply(two).toString();
        return result;
    }

    /**
     * <p>
     * 곱셈한 결과값의 자릿수만 지정한다.
     * </p>
     * @param thisVal
     *        <code>String</code>
     * @param addVal
     *        <code>String</code>
     * @param scale
     *        <code>int</code> 자리수지정
     * @return 결과 값
     * @see #isMultiply(String, String, int, int)
     * @see #isMultiply(String, String)
     */
    public static String multiply(String thisVal, String addVal, int scale) {
        return multiply(thisVal, addVal, scale, ROUND_UNNECESSARY);
    }

    /**
     * <p>
     * 곱셈한 결과값의 자릿수와 올림,내림,절삭여부 지정한다.
     * </p>
     * @param thisVal
     *        <code>String</code>
     * @param addVal
     *        <code>String</code>
     * @param scale
     *        <code>int</code> 자리수지정
     * @param roundMode
     *        <code>int</code> Round 여부 <br>
     *        올 림 : {@link #ROUND_UP} <br>
     *        내 림 : {@link #ROUND_DOWN} <br>
     *        반올림 : {@link #ROUND_HALF_UP}
     * @return 결과 값
     * @see #isMultiply(String, String, int)
     * @see #isMultiply(String, String)
     */
    public static String multiply(String thisVal, String addVal, int scale,
            int roundMode) {
        String result = null;
        BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
        BigDecimal two = new BigDecimal(addVal == null ? "0" : addVal);
        result = one.multiply(two).setScale(scale, roundMode).toString();
        return result;
    }

    /**
     * <p>
     * 기본나눗셈을 실행한다.
     * </p>
     * @param thisVal
     *        <code>String</code>
     * @param addVal
     *        <code>String</code>
     * @return 결과 값
     * @see #isDivide(String, String, int)
     * @see #isDivide(String, String, int, int)
     */
    public static String divide(String thisVal, String addVal) {
        String result = null;
        BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
        BigDecimal two = new BigDecimal(addVal == null ? "0" : addVal);
        result = one.divide(two).toString();
        return result;
    }

    /**
     * <p>
     * 나눗셈의 결과값이 정수
     * </p>
     * @param thisVal
     *        <code>String</code>
     * @param addVal
     *        <code>String</code>
     * @param roundMode
     *        <code>int</code> Round 여부 <br>
     *        올 림 : {@link #ROUND_UP} <br>
     *        내 림 : {@link #ROUND_DOWN} <br>
     *        반올림 : {@link #ROUND_HALF_UP}
     * @return 결과 값
     * @see #isDivide(String, String, int, int)
     */
    public static String divide(String thisVal, String addVal, int roundMode) {
        String result = null;
        BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
        BigDecimal two = new BigDecimal(addVal == null ? "0" : addVal);
        result = one.divide(two, roundMode).toString();
        return result;
    }

    /**
     * <p>
     * 나눗셈 결과값의 자릿수와 올림,내림,절삭여부 지정한다
     * </p>
     * @param thisVal
     *        <code>String</code>
     * @param addVal
     *        <code>String</code>
     * @param scale
     *        <code>int</code> 자리수지정
     * @param roundMode
     *        <code>int</code> Round 여부 <br>
     *        올 림 : {@link #ROUND_UP} <br>
     *        내 림 : {@link #ROUND_DOWN} <br>
     *        반올림 : {@link #ROUND_HALF_UP}
     * @return String
     * @see #isDivide(String, String, int)
     */
    public static String divide(String thisVal, String addVal, int scale,
            int roundMode) {
        String result = null;
        BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
        BigDecimal two = new BigDecimal(addVal == null ? "0" : addVal);
        result = one.divide(two, scale, roundMode).toString();
        return result;
    }

    /**
     * <p>
     * 기본값을 scale 자릿수만큼 ROUND한다.
     * </p>
     * @param thisVal
     *        <code>String</code>
     * @param scale
     *        <code>int</code> 자리수지정
     * @param roundMode
     *        <code>int</code> Round 여부 <br>
     *        올 림 : {@link #ROUND_UP} <br>
     *        내 림 : {@link #ROUND_DOWN} <br>
     *        반올림 : {@link #ROUND_HALF_UP}
     * @return String
     */
    public static String setScale(String thisVal, int scale, int roundMode) {
        BigDecimal one = new BigDecimal(thisVal == null ? "0" : thisVal);
        return one.setScale(scale, roundMode).toString();
    }

}
