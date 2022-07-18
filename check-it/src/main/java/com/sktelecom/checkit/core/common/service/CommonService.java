package com.sktelecom.checkit.core.common.service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sktelecom.checkit.core.common.dao.CommonDAO;
import com.sktelecom.checkit.core.file.FileUpload;
import com.sktelecom.checkit.core.util.Session;
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
	 * 랜덤 숫자 생성
	 * @return
	 */
	private String makeRandomNum() {
		SecureRandom random = new SecureRandom();
		int rIndex = random.nextInt(999999);
		return String.valueOf(rIndex);
	}

	/**
	 * 랜덤 문자열 생성
	 * @return
	 */
	
	private String makeRandomStr() {
		StringBuffer randomStr = new StringBuffer();
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < 10; i++) {
			int rIndex = random.nextInt(2);
			switch (rIndex) {
			case 0:
				// a-z
				randomStr.append((char) ((random.nextInt(26)) + 97));
				break;
			case 1:
				// A-Z
				randomStr.append((char) ((random.nextInt(26)) + 65));
				break;
			}
		}

		return randomStr.toString();
	}

	/**
	 * 인증키 확인
	 * @return
	 */
	public HashMap<String, Object> commCertKeyCheck(HashMap<String, Object> param) throws Exception{

		HashMap<String, Object> rtn = new HashMap<String, Object>();
		String certKey = "";
		String checkCertKey = "";

		try{

			Session session = new Session();
			certKey = session.getCertKey();
			checkCertKey = String.valueOf(param.get("checkCertKey"));

			if(certKey.equals(checkCertKey)) {
				rtn.put("errorCode", "00");
				rtn.put("errorMessage", "인증에 성공하였습니다.");
//				session.setMarskRelease(true);
			}else {
				rtn.put("errorCode", "01");
				rtn.put("errorMessage", "인증에 실패하였습니다.\n인증키를 확인하세요.");
			}

		}catch(Exception e){
			log.error(e.getMessage());
		}
		return rtn;
	}

}
