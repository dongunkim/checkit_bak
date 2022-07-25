package com.sktelecom.checkit.core.common.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sktelecom.checkit.core.common.dao.CommonDAO;
import com.sktelecom.checkit.core.file.FileUpload;
import com.sktelecom.checkit.core.util.StringUtils;
import com.sktelecom.checkit.core.util.TypeCastUtils;

/**
 * 공통 serviceImpl
 * @author devkimsj
 *
 */
@Service
public class CommonService  {
	private final static Log log = LogFactory.getLog(CommonService.class.getName());

	/* 공통 DAO */
	@Resource
	private CommonDAO commonDAO;

	/* 파일업로드 */
	@Resource
	private FileUpload fileUpload;

	@Resource
	private TypeCastUtils typeCastUtils;

	final String SEPERATOR = "";

	/**
	 * 메뉴를 조회후 캐시에 저장
	 * @return
	 */
	public List<HashMap<String, Object>> selAdMenuList(HashMap<String, Object> param) throws Exception{
		List<HashMap<String, Object>> menuList = null;
		menuList = commonDAO.selAdMenuList(param);
		return menuList;
	}

	/**
	 * 메뉴를 조회후 캐시에 저장
	 * @return
	 */
	@Cacheable(value="menuList", key="#param")
	public List<HashMap<String, Object>> selMenuList(HashMap<String, Object> param) throws Exception{
		List<HashMap<String, Object>> menuList = null;
		menuList = commonDAO.selMenuList(param);
		return menuList;
	}

	/**
	 * 메뉴 캐시 삭제후 갱신
	 * @return
	 */
	@CacheEvict(value="menuList", key="#param")
	public List<HashMap<String, Object>> evictMenuList(HashMap<String, Object> param) throws Exception{
		List<HashMap<String, Object>> menuList = null;
		menuList = commonDAO.selMenuList(param);

		return menuList;
	}

	/**
	 * 메뉴 캐시 추가
	 * @return
	 */
	@CachePut(value="menuList", key="#param")
	public List<HashMap<String, Object>> putMenuList(HashMap<String, Object> param) throws Exception{
		List<HashMap<String, Object>> menuList = null;
		menuList = commonDAO.selMenuList(param);
		return menuList;
	}

	/**
	 * 코드 캐시를 조회 후 저장
	 * @return
	 */
	@Cacheable(value="commonCode", key="#code")
	public HashMap<String, Object> selCode(String code) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = commonDAO.selCode(code);
		return rtn;
	}

	/**
	 * 코드캐시를 조회 후 저장
	 * @return
	 */
	@Cacheable(value="commonCode", key="#code")
	public HashMap<String, Object> selCodeRoot(String code) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = commonDAO.selCodeRoot(code);
		return rtn;
	}
	
	/**
	 * 코드 캐시를 조회 후 저장
	 * @return
	 */
	@Cacheable(value="commonCode", key="#param")
	public HashMap<String, Object> selCodeSubDepth(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = commonDAO.selCodeSubDepth(param);
		return rtn;
	}
	
	/**
	 * 캐시를 삭제후 다시 생성
	 * @return
	 */
	@CacheEvict("commonCode")
	public HashMap<String, Object> evictCode(String code) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = commonDAO.selCode(code);
		return rtn;
	}

	/**
	 * 공통코드 캐시 갱신
	 * @return
	 */
	@CachePut("commonCode")
	public HashMap<String, Object> putCode(String code) throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		rtn = commonDAO.selCode(code);
		return rtn;
	}


	/**
	 * 파일업로드 정보 등록
	 * @return
	 * @throws JsonMappingException
	 */
	/*
	public int commonFileUplod(HashMap<String, Object> param) throws Exception{
		// File Upload
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, Object>> fileList = mapper.readValue(String.valueOf(param.get("attachFileList")), new TypeReference<List<HashMap<String, Object>>>(){});
		String keyStr = "filePath.upload";
		fileUpload.upload(fileList, keyStr);
		
		// File Upload DB 반영
		int trCk = 1;
		int rtn = 0;
		if(fileList != null && fileList.size() > 0) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			for(int i = 0; i < fileList.size(); i++) {
				map = fileList.get(i);
				// 신규 등록일경우
				if("0".equals(String.valueOf(map.get("attachId")))) {
					if(i != 0) {
						rtn = commonDAO.lastIndex();
					}

					// 등록후 삭제되었을경우
					if(!"ID".equals(String.valueOf(map.get("cud")))){
						map.put("attachId", rtn);
						commonDAO.commonFileUplodInsert(map);
						rtn = commonDAO.lastIndex();
					}

				}else{
					// 등록일경우
					if("I".equals(String.valueOf(map.get("cud")))){
						trCk *= commonDAO.commonFileUplodInsert(map);
						// 삭제일경우
					}else if("D".equals(String.valueOf(map.get("cud")))){
						commonDAO.commonFileUplodDelete(map);
					}
				}
			}
		}

		return rtn;
	}
	*/

	/**
	 * 파일 리스트조회
	 * @return
	 */
	public HashMap<String, Object> selAttachFileList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = commonDAO.selAttachFileList(param);
		return rtn;
	}

	/**
	 * 파일 조회
	 * @return
	 */
	public HashMap<String, Object> selAttachFileDetail(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = commonDAO.selAttachFileDetail(param);
		return rtn;
	}

	/**
	 * 시퀀스 조회
	 */
	public String getNextId(String tableName) throws Exception {
		String nextId = null;
		commonDAO.updIdGenNextNo(tableName);
		HashMap<String, Object> rtn = commonDAO.selIdGen(tableName);

		String seqType = (String)rtn.get("seqType");
		String prefix = (String)rtn.get("prefix");
		String currYear = (String)rtn.get("currYear");
		String currMonth = (String)rtn.get("currMonth");
		String currDay = (String)rtn.get("currDay");
		int currNo = (int)rtn.get("currNo");
		int cipers = (int)rtn.get("cipers");
		String fillChar = (String)rtn.get("fillChar");

		switch(seqType) {
			case "SIMPLE":
				nextId = StringUtils.lpad(String.valueOf(currNo), cipers, fillChar);				
				break;
			case "PREFIX":
				nextId = prefix + SEPERATOR + StringUtils.lpad(String.valueOf(currNo), cipers, fillChar);
				break;
			case "YEAR":
				currNo = checkYear(tableName, currYear) ? currNo : 1;
				nextId = currYear + SEPERATOR + StringUtils.lpad(String.valueOf(currNo), cipers, fillChar);
				break;
			case "MONTH":
				currNo = checkMonth(tableName, currMonth) ? currNo : 1;
				nextId = currYear + currMonth + SEPERATOR + StringUtils.lpad(String.valueOf(currNo), cipers, fillChar);
				break;
			case "DAY":
				currNo = checkDate(tableName, currDay) ? currNo : 1;
				nextId = currYear + currMonth + currDay + SEPERATOR + StringUtils.lpad(String.valueOf(currNo), cipers, fillChar);				
				break;
			case "MIX-Y":
				currNo = checkYear(tableName, currYear) ? currNo : 1;
				nextId = prefix + SEPERATOR + currYear + currMonth + SEPERATOR + StringUtils.lpad(String.valueOf(currNo), cipers, fillChar);
				break;
			case "MIX-M":
				currNo = checkMonth(tableName, currMonth) ? currNo : 1;
				nextId = prefix + SEPERATOR + currYear + currMonth + SEPERATOR + StringUtils.lpad(String.valueOf(currNo), cipers, fillChar);
				break;
			case "MIX-D":
				currNo = checkDate(tableName, currDay) ? currNo : 1;
				nextId = prefix + SEPERATOR + currYear + currMonth + currDay + SEPERATOR + StringUtils.lpad(String.valueOf(currNo), cipers, fillChar);
				break;
		}

		return nextId;
	}

	/*
	 * 현재 년도와 시퀀스 테이블의 년도 비교
	 * @Return true  동일한 경우
	 *         false 동일하지 않은 경우(DB 업데이트)
	 *          
	 */
	private boolean checkYear(String tableName, String year) throws Exception {
		Calendar today = Calendar.getInstance();
		
		if(today.get(Calendar.YEAR) != Integer.parseInt(year)){
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("tableName", tableName);
			param.put("currYear", String.valueOf(today.get(Calendar.YEAR)));
			param.put("currNo", 1);
			commonDAO.updIdGenCycle(param);
			return false;
		}
		return true;
	}
	
	/*
	 * 현재 월과 시퀀스 테이블의 월 비교
	 * @Return true  동일한 경우
	 *         false 동일하지 않은 경우(DB 업데이트)
	 *          
	 */
	private boolean checkMonth(String tableName, String month) throws Exception {
		Calendar today = Calendar.getInstance();
		
		if((today.get(Calendar.MONTH) + 1) != Integer.parseInt(month)){
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("tableName", tableName);
			param.put("currYear", String.valueOf(today.get(Calendar.YEAR)));
			param.put("currMonth", String.valueOf(today.get(Calendar.MONTH) + 1));
			param.put("currNo", 1);
			commonDAO.updIdGenCycle(param);
			return false;
		}
		return true;
	}
	
	/*
	 * 현재 일자와 시퀀스 테이블의 일자 비교
	 * @Return true  동일한 경우
	 *         false 동일하지 않은 경우(DB 업데이트)
	 *          
	 */
	private boolean checkDate(String tableName, String day) throws Exception {
		Calendar today = Calendar.getInstance();
				
		if(today.get(Calendar.DATE) != Integer.parseInt(day)){
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("tableName", tableName);
			param.put("currYear", String.valueOf(today.get(Calendar.YEAR)));
			param.put("currMonth", String.valueOf(today.get(Calendar.MONTH) + 1));
			param.put("currDay", String.valueOf(today.get(Calendar.DATE)));
			param.put("currNo", 1);
			commonDAO.updIdGenCycle(param);
			return false;
		}
		return true;
	}

}
