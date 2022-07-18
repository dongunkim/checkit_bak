package com.sktelecom.checkit.admin.board.dao;

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
		return (HashMap<String, Object>)super.selectList("admin.board.dao.selBoardList", param);
	}

	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selBoardDetail(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectOne("admin.board.dao.selBoardDetail", param);
	}

	/**
	 *
	 */
	public int updBoardViewCnt(HashMap<String, Object> param) throws Exception{
		return super.update("admin.board.dao.updBoardViewCnt", param);
	}

	/**
	 *
	 */
	public int insBoard(HashMap<String, Object> param) throws Exception{
		return super.insert("admin.board.dao.insBoard", param);
	}

	/**
	 *
	 */
	public int updBoard(HashMap<String, Object> param) throws Exception{
		return super.update("admin.board.dao.updBoard", param);
	}

	/**
	 *
	 */
	public int delBoard(HashMap<String, Object> param) throws Exception{
		return super.update("admin.board.dao.delBoard", param);
	}
}
