package com.sktelecom.checkit.core.common.service;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.checkit.core.common.dao.IdGenDAO;

/**
 * 공통 serviceImpl
 * @author devkimsj
 *
 */
@Service
public class IdGenService  {
	private final static Log log = LogFactory.getLog(IdGenService.class.getName());

	/* 공통 DAO */
	@Resource
	private IdGenDAO idGenDAO;

	/**
	 * SEQ 확인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> selSeq(String seqGb) throws Exception {
		HashMap<String, Object> seq = idGenDAO.selSeq(seqGb);
		return seq;
	}

}
