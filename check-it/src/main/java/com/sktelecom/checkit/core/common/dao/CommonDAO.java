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
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, Object>> getAdMenuList(HashMap<String, Object> param) throws Exception{
		List<HashMap<String, Object>> menuList = super.list("common.mapper.getAdMenuList", param);
		return menuList;
	}

	/**
	 * 메뉴를 조회후 캐시에 저장
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, Object>> getMenuList(HashMap<String, Object> param) throws Exception{
		List<HashMap<String, Object>> menuList = super.list("common.mapper.getMenuList", param);
		return menuList;
	}

	/**
	 * 공통코드를 조회후 캐시에 저장
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getCommonCode(String code) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectList("common.mapper.getCommonCode", code);
		return rtn;
	}

	/**
	 * 공통코드를 조회후 캐시에 저장
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getCommonCodeRoot(String code) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectList("common.mapper.getCommonCodeRoot", code);
		return rtn;
	}

	/**
	 * 공통코드를 조회후 캐시에 저장
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getCommonCodeSubDepth(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectList("common.mapper.getCommonCodeSubDepth", param);
		return rtn;
	}
	
	/**
	 * 공통코드를 조회후 캐시에 저장
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String getCommonCsvCode(String code) throws Exception{
		String rtn = super.selectStringOne("common.mapper.getCommonCsvCode", code);
		return rtn;
	}

	/**
	 * 파일업로드 정보 등록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int commonFileUplod(List<HashMap<String, Object>> fileList) throws Exception{

		int trCk = 1;
		int rtnId = 0;

		if(fileList != null && fileList.size() > 0) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			for(int i = 0; i < fileList.size(); i++) {
				map = fileList.get(i);
				// 신규 등록일경우
				if("0".equals(String.valueOf(map.get("attachId")))) {
					if(i != 0) {
						rtnId = super.lastIndex();
					}

					// 등록후 삭제되었을경우
					if(!"ID".equals(String.valueOf(map.get("cud")))){
						map.put("attachId", rtnId);
						trCk *= super.insert("common.mapper.commonFileUplodInsert", map);
						rtnId = super.lastIndex();
					}

				}else{
					// 등록일경우
					if("I".equals(String.valueOf(map.get("cud")))){
						trCk *= super.insert("common.mapper.commonFileUplodInsert", map);
						// 삭제일경우
					}else if("D".equals(String.valueOf(map.get("cud")))){
						trCk *= super.delete("common.mapper.commonFileUplodDelete", map);
					}
				}
			}

			if(trCk != 1) {
				throw new Exception("파일정보 등록오류");
			}
		}

		return rtnId;
	}

	/**
	 * 파일 리스트조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> commFileList(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectList("common.mapper.commFileList", param);
		return rtn;

	}

	/**
	 * 파일 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> commFileInfo(HashMap<String, Object> param) throws Exception{
		HashMap<String, Object> rtn = (HashMap<String, Object>)super.selectOne("common.mapper.commFileInfo", param);
		return rtn;
	}

	/**
	 * SEQ 확인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getSeq(String seqGb) throws Exception{
		HashMap<String, Object> seq = (HashMap<String, Object>) super.selectOne("common.mapper.getSeq", seqGb);
		return seq;
	}

}
