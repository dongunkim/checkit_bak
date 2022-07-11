package com.sktelecom.checkit.core.util;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

/**
 * 메시지 유틸
 * @author devkimsj
 *
 */
@Component("messageUtils")
public class MessageUtils implements MessageSourceAware {

	private static MessageSource messageSource;

	@Override
	public void setMessageSource (MessageSource messageSource) {
		MessageUtils.messageSource = messageSource;
	}

	/**
	* 메지시를 리턴한다.
	*/
	public static String msg(String key){
		Session session = new Session();
		return messageSource.getMessage(key, null, session.getLocale());
	}

	/**
	* 메지시를 리턴한다.
	*/
	public static String msg(String key, Object[] param){
		Session session = new Session();
		return messageSource.getMessage(key, param, session.getLocale());
	}
}
