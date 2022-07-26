package com.sktelecom.checkit.portal.board.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.sktelecom.checkit.core.common.dao.AbstractMapper;


/**
 *
 */
@Repository
public class BoardDAO extends AbstractMapper {

	private final static Log log = LogFactory.getLog(BoardDAO.class);

	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selBoardList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("portal.board.dao.selBoardList", param);
	}

	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selBoardDetail(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectOne("portal.board.dao.selBoardDetail", param);
	}

	/**
	 *
	 */
	public int updBoardViewCnt(HashMap<String, Object> param) throws Exception{
		return super.update("portal.board.dao.updBoardViewCnt", param);
	}

}
