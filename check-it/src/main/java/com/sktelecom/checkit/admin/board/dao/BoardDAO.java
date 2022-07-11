package com.sktelecom.checkit.admin.board.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.sktelecom.checkit.core.common.dao.AbstractMapper;


/**
 * 내부 공지 DAO
 * @author devbaekgh
 *
 */
@Repository
public class BoardDAO extends AbstractMapper {

	private final static Log log = LogFactory.getLog(BoardDAO.class);

	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> boardList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectList("admin.board.dao.boardList", param);
		return rtn;
	}

	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> boardDetail(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectOne("admin.board.dao.boardDetail", param);
		return rtn;
	}

	/**
	 *
	 */
	public int updateViewCntBoard(HashMap<String, Object> param) throws Exception{
		int cnt = super.update("admin.board.dao.updateViewCntBoard", param);
		return cnt;
	}

	/**
	 *
	 */
	public int insertBoard(HashMap<String, Object> param) throws Exception{
		int cnt = super.insert("admin.board.dao.insertBoard", param);
		return cnt;
	}

	/**
	 *
	 */
	public int updateBoard(HashMap<String, Object> param) throws Exception{
		int cnt = super.update("admin.board.dao.updateBoard", param);
		return cnt;
	}

	/**
	 *
	 */
	public int deleteBoard(HashMap<String, Object> param) throws Exception{
		int cnt = super.update("admin.board.dao.deleteBoard", param);
		return cnt;
	}
}
