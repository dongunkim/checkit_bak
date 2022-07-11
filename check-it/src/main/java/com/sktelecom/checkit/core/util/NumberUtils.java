package com.sktelecom.checkit.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * Number 관련 유틸.
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
public class NumberUtils extends org.apache.commons.lang.math.NumberUtils {
	
	private final static Log log = LogFactory.getLog(NumberUtils.class.getName());
	/**
	 * toInteger.
	 *
	 * @param src src
	 * @param defaultValue defaultValue
	 * @return integer
	 * @author Kim Donghyeong
	 */
	public static Integer toInteger(String src, Integer defaultValue) {
		if (src == null) {
			return defaultValue;
		}
		
		try {
			return Integer.valueOf(src);
		} catch (Exception e) {
			return defaultValue;
		}
		
	}
    
    /**
     * <pre>
     * Parses the int.
     * </pre>
     * 
     * @param src the src
     * 
     * @return the int
     */
    public static int parseInt(String src) {
        return parseInt(src, -1);
    }
    
    /**
     * <pre>
     * Parses the int.
     * </pre>
     * 
     * @param src the src
     * @param dft the dft
     * 
     * @return the int
     */
    public static int parseInt(String src, int dft) {
        int result = dft;
        try {
            result = Integer.parseInt(src);
        } catch (NumberFormatException e) {
        	log.debug(e.getMessage());
            result = dft;
        }
        return result;
    }
    
    /**
     * <pre>
     * Parses the long.
     * </pre>
     * 
     * @param src the src
     * 
     * @return the long
     */
    public static long parseLong(String src) {
        return parseLong(src, -1L);
    }
    
    /**
     * <pre>
     * Parses the long.
     * </pre>
     * 
     * @param src the src
     * @param dft the dft
     * 
     * @return the long
     */
    public static long parseLong(String src, long dft) {
        long result = dft;
        try {
            result = Long.parseLong(src);
        } catch (NumberFormatException e) {
        	log.debug(e.getMessage());
            result = dft;
        }
        return result;
    }
    
    /**
     * <pre>
     * Parses the double.
     * </pre>
     * 
     * @param src the src
     * 
     * @return the double
     */
    public static double parseDouble(String src) {
        return parseDouble(src, -1D);
    }
    
    /**
     * <pre>
     * Parses the double.
     * </pre>
     * 
     * @param src the src
     * @param dft the dft
     * 
     * @return the double
     */
    public static double parseDouble(String src, double dft) {
        double result = dft;
        try {
            result = Double.parseDouble(src);
        } catch (NumberFormatException e) {
        	log.debug(e.getMessage());
            result = dft;
        }
        return result;
    }
    
    /**
     * <pre>
     * Floor.
     * </pre>
     * 
     * @param a the a
     * 
     * @return the double
     */
    public static double floor(float a) {
        return Math.floor(a);
    }

    /**
     * <pre>
     * Floor.
     * </pre>
     * 
     * @param a the a
     * 
     * @return the double
     */
    public static double floor(double a) {
        return Math.floor(a);
    }

    /**
     * <pre>
     * Round.
     * </pre>
     * 
     * @param a the a
     * 
     * @return the int
     */
    public static int round(float a) {
        return Math.round(a);
    }
    
    /**
     * <pre>
     * Round.
     * </pre>
     * 
     * @param a the a
     * 
     * @return the long
     */
    public static long round(double a) {
        return Math.round(a);
    }

    /**
     * <pre>
     * Ceil.
     * </pre>
     * 
     * @param a the a
     * 
     * @return the double
     */
    public static double ceil(float a) {
        return Math.ceil(a);
    }
    
    /**
     * <pre>
     * Ceil.
     * </pre>
     * 
     * @param a the a
     * 
     * @return the double
     */
    public static double ceil(double a) {
        return Math.ceil(a);
    }
    
    /**
     * <pre>
     * Max.
     * </pre>
     * 
     * @param a the a
     * @param b the b
     * 
     * @return the int
     */
    public static int max(int a, int b) {
        return Math.max(a, b);
    }
    
    /**
     * <pre>
     * Max.
     * </pre>
     * 
     * @param a the a
     * @param b the b
     * 
     * @return the long
     */
    public static long max(long a, long b) {
        return Math.max(a, b);
    }
    
    /**
     * <pre>
     * Max.
     * </pre>
     * 
     * @param a the a
     * @param b the b
     * 
     * @return the float
     */
    public static float max(float a, float b) {
        return Math.max(a, b);
    }
    
    /**
     * <pre>
     * Max.
     * </pre>
     * 
     * @param a the a
     * @param b the b
     * 
     * @return the double
     */
    public static double max(double a, double b) {
        return Math.max(a, b);
    }
    
    /**
     * <pre>
     * Min.
     * </pre>
     * 
     * @param a the a
     * @param b the b
     * 
     * @return the int
     */
    public static int min(int a, int b) {
        return Math.min(a, b);
    }
    
    /**
     * <pre>
     * Min.
     * </pre>
     * 
     * @param a the a
     * @param b the b
     * 
     * @return the long
     */
    public static long min(long a, long b) {
        return Math.min(a, b);
    }
    
    /**
     * <pre>
     * Min.
     * </pre>
     * 
     * @param a the a
     * @param b the b
     * 
     * @return the float
     */
    public static float min(float a, float b) {
        return Math.min(a, b);
    }
    
    /**
     * <pre>
     * Min.
     * </pre>
     * 
     * @param a the a
     * @param b the b
     * 
     * @return the double
     */
    public static double min(double a, double b) {
        return Math.min(a, b);
    }    
}
