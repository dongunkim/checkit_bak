package com.sktelecom.checkit.core.util;

import java.lang.reflect.Constructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 객체의 로딩을 지원하는 유틸 클래스
 * Class 로딩 등의 객체관련 기능을 제공하는 유틸이다.
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
public class CommObjectUtils {

	private static Logger log = LoggerFactory.getLogger(CommObjectUtils.class.getName());

    private CommObjectUtils() {}

    /**
     * 클래스명으로 객체를 로딩한다.
     * @param className
     * @return
     * @throws ClassNotFoundException
     * @throws Exception
     */
    public static Class<?> loadClass(String className)
            throws ClassNotFoundException, Exception {

        Class<?> clazz = null;
        try {
            clazz =
                Thread.currentThread().getContextClassLoader().loadClass(
                    className);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException();
        }

        if (clazz == null) {
            clazz = Class.forName(className);
        }

        return clazz;

    }

    /**
     * 클래스명으로 객체를 로드한 후 인스턴스화 한다.
     * @param className
     * @return
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws Exception
     */
    public static Object instantiate(String className)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, Exception {
        Class<?> clazz;

        try {
            clazz = loadClass(className);
            return clazz.newInstance();
        } catch (ClassNotFoundException e) {
            log.error(className + " : Class is can not instantialized.");
            throw new ClassNotFoundException();
        } catch (InstantiationException e) {
            log.error(className + " : Class is can not instantialized.");
            throw new InstantiationException();
        } catch (IllegalAccessException e) {
            log.error(className + " : Class is not accessed.");
            throw new IllegalAccessException();
        }
    }

    /**
     * 클래스명으로 파라매터가 있는 클래스의 생성자를 인스턴스화 한다. 예) Class <?>
     * clazz = CommObjectUtil.loadClass(this.mapClass);
     * Constructor <?> constructor =
     * clazz.getConstructor(new Class
     * []{DataSource.class, String.class}); Object []
     * params = new Object []{getDataSource(),
     * getUsersByUsernameQuery()};
     * this.usersByUsernameMapping =
     * (CommUsersByUsernameMapping)
     * constructor.newInstance(params);
     * @param className
     * @return
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws Exception
     */
    public static Object instantiate(String className, String[] types,
            Object[] values) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException, Exception {
        Class<?> clazz;
        Class<?>[] classParams = new Class[values.length];
        Object[] objectParams = new Object[values.length];

        try {
            clazz = loadClass(className);

            for (int i = 0; i < values.length; i++) {
                classParams[i] = loadClass(types[i]);
                objectParams[i] = values[i];
            }

            Constructor<?> constructor = clazz.getConstructor(classParams);
            return constructor.newInstance(values);

        } catch (ClassNotFoundException e) {
            log.error(className + " : Class is can not instantialized.");
            throw new ClassNotFoundException();
        } catch (InstantiationException e) {
            log.error(className + " : Class is can not instantialized.");
            throw new InstantiationException();
        } catch (IllegalAccessException e) {
            log.error(className + " : Class is not accessed.");
            throw new IllegalAccessException();
        }
    }

    /**
     * 객체가 Null 인지 확인한다.
     * @param object
     * @return Null인경우 true / Null이 아닌경우 false
     */
    public static boolean isNull(Object object) {
        return ( object == null );
    }
    
}
