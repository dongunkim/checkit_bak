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
	public HashMap<String, Object> selectBoardList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("admin.board.dao.selectBoardList", param);
	}

	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectBoardDetail(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectOne("admin.board.dao.selectBoardDetail", param);
	}

	/**
	 *
	 */
	public int updateBoardViewCnt(HashMap<String, Object> param) throws Exception{
		return super.update("admin.board.dao.updateBoardViewCnt", param);
	}

	/**
	 *
	 */
	public int insertBoard(HashMap<String, Object> param) throws Exception{
		return super.insert("admin.board.dao.insertBoard", param);
	}

	/**
	 *
	 */
	public int updateBoard(HashMap<String, Object> param) throws Exception{
		return super.update("admin.board.dao.updateBoard", param);
	}

	/**
	 *
	 */
	public int deleteBoard(HashMap<String, Object> param) throws Exception{
		return super.update("admin.board.dao.deleteBoard", param);
	}
}
