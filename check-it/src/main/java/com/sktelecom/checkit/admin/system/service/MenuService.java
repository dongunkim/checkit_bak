package com.sktelecom.checkit.admin.system.service;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.checkit.admin.system.dao.MenuDAO;
import com.sktelecom.checkit.core.common.service.CommonService;

/**
 * 메뉴 관리 Service
 */
@Service
public class MenuService{

	private final static Log log = LogFactory.getLog(MenuService.class.getName());

	@Resource
	private MenuDAO menuDAO;

	@Resource
	private CommonService commonService;

	/**
	 * 메뉴 Tree 목록
	 */
	public HashMap<String, Object> menuTreeList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = menuDAO.menuTreeList(param);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}
		return rtn;
	}

	/**
	 * 
	 */
	public HashMap<String, Object> menuPgmList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			rtn = menuDAO.menuPgmList(param);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}
		return rtn;
	}

	/**
	 * 
	 */
	public HashMap<String, Object> menuInsert(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			menuDAO.menuInsert(param);
			commonService.evictMenuList(param);
			rtn.put("errorCode", "00");
			rtn.put("errorMessage", "정상 등록 되었습니다.");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_System_menuInsert_01");
			rtn.put("errorMessage", "처리중 오류가 발생하였습니다.");
		}
		return rtn;
	}

	/**
	 * 
	 */
	public HashMap<String, Object> menuUpdate(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			menuDAO.menuUpdate(param);
			commonService.evictMenuList(param);
			rtn.put("errorCode", "00");
			rtn.put("errorMessage", "정상 등록 되었습니다.");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_System_menuUpdate_01");
			rtn.put("errorMessage", "처리중 오류가 발생하였습니다.");
		}
		return rtn;
	}
	
	/**
	 * 
	 */
	public HashMap<String, Object> menuDelete(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			menuDAO.menuDelete(param);
			menuDAO.downMenuDelete(param);
			commonService.evictMenuList(param);
			rtn.put("errorCode", "00");
			rtn.put("errorMessage", "정상 삭제 되었습니다.");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_System_menuDelete_01");
			rtn.put("errorMessage", "처리중 오류가 발생하였습니다.");
		}
		return rtn;
	}

	/**
	 * 
	 */
	public HashMap<String, Object> menuPgmInsert(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			menuDAO.menuPgmInsert(param);
			commonService.evictMenuList(param);
			rtn.put("errorCode", "00");
			rtn.put("errorMessage", "정상 등록 되었습니다.");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_System_menuPgmInsert_01");
			rtn.put("errorMessage", "처리중 오류가 발생하였습니다.");
		}
		return rtn;
	}

	/**
	 * 
	 */
	public HashMap<String, Object> menuPgmUpdate(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			menuDAO.menuPgmUpdate(param);
			commonService.evictMenuList(param);
			rtn.put("errorCode", "00");
			rtn.put("errorMessage", "정상 등록 되었습니다.");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_System_menuPgmUpdate_01");
			rtn.put("errorMessage", "처리중 오류가 발생하였습니다.");
		}
		return rtn;
	}

	/**
	 * 
	 */
	public HashMap<String, Object> menuPgmDelete(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try{
			menuDAO.menuPgmDelete(param);
			menuDAO.downMenuDelete(param);
			commonService.evictMenuList(param);
			rtn.put("errorCode", "00");
			rtn.put("errorMessage", "정상 삭제 되었습니다.");
		}catch(Exception e){
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_System_menuPgmDelete_01");
			rtn.put("errorMessage", "처리중 오류가 발생하였습니다.");
		}
		return rtn;
	}
}