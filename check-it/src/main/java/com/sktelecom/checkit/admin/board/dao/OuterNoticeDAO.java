package com.sktelecom.checkit.admin.board.dao;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.sktelecom.checkit.core.common.dao.AbstractMapper;


/**
 * 고객 공지 DAO
 * @author devbaekgh
 *
 */
@Repository
public class OuterNoticeDAO extends AbstractMapper {

	private final static Log log = LogFactory.getLog(OuterNoticeDAO.class);

	/**
	 * 고객 공지 목록 화면
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> bord0301List(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = (HashMap<String, Object>)super.selectList("admin.board.dao.bord0301List", param);

		return rtn;
	}

	/**
	 * 고객 공지 상세 화면
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> bord0302Detail(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectOne("admin.board.dao.bord0302Detail", param);
		super.update("admin.board.mapper.updateViewCntBord03", param);

		return rtn;
	}
	

	/**
	 * 고객 공지 수정 화면
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> bord0304Edit(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectOne("admin.board.dao.bord0304Edit", param);

		return rtn;
	}
	
	/**
	 * 고객 공지 수정 화면
	 * @param param
	 * @return
	 * @throws CommException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> bord0304EditGonjlUser(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectList("admin.board.dao.bord0304EditGonjlUser", param);

		return rtn;
	}
	
	/**
	 * 고객 공지 등록
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int insertBord0303(HashMap<String, Object> param) throws Exception{
		int cnt = super.insert("admin.board.dao.insertBord0303", param);

		return cnt;
	}
	
	/**
	 * 고객 공지 삭제
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int deleteBordCsNo(HashMap<String, Object> param) throws Exception{
		int cnt = super.delete("admin.board.dao.deleteBordCsNo", param);

		return cnt;
	}
	
	/**
	 * 고객 공지 등록
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int insertBordCsNo(HashMap<String, Object> param) throws Exception{
		int cnt = super.insert("admin.board.dao.insertBordCsNo", param);

		return cnt;
	}

	/**
	 * 고객 공지 수정
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int updateBord0304(HashMap<String, Object> param) throws Exception{
		int cnt = super.update("admin.board.dao.updateBord0304", param);

		return cnt;
	}

	/**
	 * 고객 공지 삭제
	 * @param param
	 * @return
	 * @throws CommException
	 */
	public int deleteBord0305(HashMap<String, Object> param) throws Exception{
		int cnt = super.update("admin.board.dao.deleteBord0305", param);

		return cnt;
	}
}
