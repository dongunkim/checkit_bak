package com.sktelecom.checkit.admin.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.checkit.admin.system.dao.RoleDAO;
import com.sktelecom.checkit.core.common.service.CommonService;
import com.sktelecom.checkit.core.util.CommMap;
import com.sktelecom.checkit.core.util.StringUtils;
import com.sktelecom.checkit.core.util.TypeCastUtils;

/**
 * 공통권한관리 ServiceImpl
 * @author jhBang
 */
@Service
public class RoleService {

	private final static Log log = LogFactory.getLog(RoleService.class.getName());

	/* 시스템관리 DAO */
	@Resource
	private RoleDAO roleDAO;

	@Resource
	private CommonService commonService;

	/**
	 * 공통권한관리 목록 화면
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> roleList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try {
			rtn = roleDAO.roleList(param);
		} catch(Exception e) {
			log.error(e.getMessage());
			throw(e);
		}
		return rtn;
	}

	/**
	 * 공통권한관리 목록 화면
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> roleTreeList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try {
			rtn = roleDAO.roleTreeList(param);
		} catch(Exception e) {
			log.error(e.getMessage());
		}
		return rtn;
	}

	/**
	 * 등록/수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> roleInsert(HashMap<String, Object> param) throws Exception{

		HashMap<String, Object> rtn = new HashMap<String, Object>();
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();

		try {
			String rid = StringUtils.defaultString(param.get("rid"));
			String rname = StringUtils.defaultString(param.get("rname"));
			String description = StringUtils.defaultString(param.get("description"));
			String usedYn = StringUtils.defaultString(param.get("usedYn"));
			String userId = StringUtils.defaultString(param.get("userId"));
			String flag = StringUtils.defaultString(param.get("flag"));
			String sysType = StringUtils.defaultString(param.get("sysType"));
	
			if("U".equals(flag)) {
				paramsMap.clear();
				paramsMap.put("rid", rid);
				paramsMap.put("rname", rname);
				paramsMap.put("description", description);
				paramsMap.put("usedYn", usedYn);
				paramsMap.put("userId", userId);
				roleDAO.roleUpdate(paramsMap);
			} else {
				paramsMap.clear();
				paramsMap.put("rname", rname);
				paramsMap.put("description", description);
				paramsMap.put("usedYn", usedYn);
				paramsMap.put("sysType", sysType);
				paramsMap.put("userId", userId);
				roleDAO.roleInsert(paramsMap);
			}
			rtn.clear();
			rtn.put("sysType", sysType);
			rtn.put("errorCode", "00");
			rtn.put("errorMessage", "정상 처리 되었습니다.");
		} catch(Exception e) {
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_System_roleInsert_01");
			rtn.put("errorMessage", "등록 중 오류가 발생하였습니다. ");
		}

		return rtn;
	}

	/**
	 * 공통메뉴권한 insert
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> rolePgmInsert(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		TypeCastUtils typeCastUtils = new TypeCastUtils();
		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		try {
			String[] paramsA = typeCastUtils.stringToArray(StringUtils.defaultString(param.get("paramsAList")));
			String[] paramsB = typeCastUtils.stringToArray(StringUtils.defaultString(param.get("paramsBList")));
	
			String rid = StringUtils.defaultString(param.get("rid"));
			String userId = StringUtils.defaultString(param.get("userId"));
	
			paramMap.clear();
			paramMap.put("rid", rid);
			roleDAO.rolePgmDelete(paramMap);
	
			if(paramsA.length > 0) {
				paramMap.clear();
				paramMap.put("methodType", "R");
				paramMap.put("menuList", paramsA);
				rtn = roleDAO.pgmPidList(paramMap);
				List < CommMap > aa = (List < CommMap >)rtn.get("list");
				if(aa.size() > 0) {
					for(CommMap map : aa) {
						paramMap.clear();
						paramMap.put("userId", userId);
						paramMap.put("rid", rid);
						paramMap.put("pid", map.get("pid"));
						roleDAO.rolePgmInsert(paramMap);
					}
				}
			}
	
			if(paramsB.length > 0) {
				paramMap.clear();
				paramMap.put("methodType", "W");
				paramMap.put("menuList", paramsB);
				rtn = roleDAO.pgmPidList(paramMap);
				List < CommMap > bb = (List < CommMap >)rtn.get("list");
				if(bb.size() > 0) {
					for(CommMap map : bb) {
						paramMap.clear();
						paramMap.put("userId", userId);
						paramMap.put("rid", rid);
						paramMap.put("pid", map.get("pid"));
						roleDAO.rolePgmInsert(paramMap);
					}
				}
			}
			rtn.clear();
			rtn.put("errorCode", "00");
			rtn.put("errorMessage", "정상 처리 되었습니다.");
		} catch(Exception e) {
			log.error(e.getMessage());
			rtn.put("errorCode", "ERR_System_rolePgmInsert_01");
			rtn.put("errorMessage", "등록 중 오류가 발생하였습니다. ");
		}

		return rtn;
	}
}