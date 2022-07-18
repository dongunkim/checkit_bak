package com.sktelecom.checkit.core.common.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 * 공통 DAO
 * @author devkimsj
 *
 */
@Repository
public class CommonDAO extends AbstractMapper {

	private final static Log log = LogFactory.getLog(CommonDAO.class);

	/**
	 * 메뉴를 조회후 캐시에 저장
	 */
	public List<HashMap<String, Object>> selAdMenuList(HashMap<String, Object> param) throws Exception{
		return super.list("common.dao.selAdMenuList", param);
	}

	/**
	 * 메뉴를 조회후 캐시에 저장
	 */
	public List<HashMap<String, Object>> selMenuList(HashMap<String, Object> param) throws Exception{
		return super.list("common.dao.selMenuList", param);
	}

	/**
	 * 공통코드를 조회후 캐시에 저장
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selCode(String code) throws Exception{
		return (HashMap<String, Object>)super.selectList("common.dao.selCode", code);
	}

	/**
	 * 공통코드를 조회후 캐시에 저장
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selCodeRoot(String code) throws Exception{
		return (HashMap<String, Object>)super.selectList("common.dao.selCodeRoot", code);
	}

	/**
	 * 공통코드를 조회후 캐시에 저장
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selCodeSubDepth(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("common.dao.selCodeSubDepth", param);
	}

	/**
	 *  File Upload 정보 반영
	 */
	public int updUploadFile(List<HashMap<String, Object>> fileList) throws Exception{
		int attachId = 0;
		for(int i = 0; i < fileList.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map = fileList.get(i);
			// 신규 등록일경우
			if("0".equals(String.valueOf(map.get("attachId")))) {
				if(i != 0) {
					attachId = lastIndex();
				}

				// 등록 후 삭제된 경우가 아니라면
				if(!"ID".equals(String.valueOf(map.get("cud")))){
					map.put("attachId", attachId);
					insAttachFile(map);
					attachId = lastIndex();
				}
			}else{
				// 등록일경우
				if("I".equals(String.valueOf(map.get("cud")))){
					insAttachFile(map);
					// 삭제일경우
				}else if("D".equals(String.valueOf(map.get("cud")))){
					delAttachFile(map);
				}
			}
		}

		return attachId;
	}

	public int insAttachFile(HashMap<String, Object> param) throws Exception{
		return super.insert("common.dao.insAttachFile", param);
	}

	public int delAttachFile(HashMap<String, Object> param) throws Exception{
		return super.delete("common.dao.delAttachFile", param);
	}

	/**
	 * 파일 리스트조회
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selAttachFileList(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectList("common.dao.selAttachFileList", param);

	}

	/**
	 * 파일 조회
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selAttachFileDetail(HashMap<String, Object> param) throws Exception{
		return (HashMap<String, Object>)super.selectOne("common.dao.selAttachFileDetail", param);
	}

}
