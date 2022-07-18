package com.sktelecom.checkit.core.common.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.sktelecom.checkit.core.util.StringUtils;

/**
 *
 */
@Repository
public class IdGenDAO extends AbstractMapper {

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selSeq(String seqGb) throws Exception{
		super.update("common.dao.updNextSeq", seqGb);
		HashMap<String, Object> seq = (HashMap<String, Object>) super.selectOne("common.dao.selSeq", seqGb);
		return seq;
	}

}
