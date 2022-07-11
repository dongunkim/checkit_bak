package com.sktelecom.checkit.core.common.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.map.ListOrderedMap;
import org.mybatis.spring.SqlSessionTemplate;

import com.sktelecom.checkit.core.property.PropertyService;
import com.sktelecom.checkit.core.util.Session;
import com.sktelecom.checkit.core.util.CommMap;
import com.sktelecom.checkit.core.util.StringUtils;

/**
* Spring의  연동 지원 공통 parent DAO 클래스
*
*/
public abstract class AbstractMapper{

	@Resource(name="sqlSessionTemplate")
    private SqlSessionTemplate sqlSession;

	@Resource
    protected PropertyService propertyService;

	public int lastIndex(){
		int lastIndex = 0;
		lastIndex = ((Integer)this.sqlSession.selectOne("common.mapper.lastIndex")).intValue();
		return lastIndex;
	}

	/**
	 * 리스트
	 * @param queryId String
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public List<HashMap<String, Object>> list(String queryId, Object object) throws Exception{

		List<HashMap<String, Object>> list = null;

		if(object instanceof String){

			list = sqlSession.selectList(queryId, object);

		}else{

			HashMap params = (HashMap)object;
			list = sqlSession.selectList(queryId, object);

		}

		return list;

	}

	/**
	 * 리스트
	 * @param queryId String
	 * @param object Object
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object selectList(String queryId, Object object) throws Exception{

		List list = null;
		HashMap<String, Object> total = new HashMap<String, Object>();
		HashMap<String, Object> rtnMap = new HashMap<String, Object>();
		Session session = new Session();

		try{

			if(session.isPaging()) {

				if(object instanceof String){

					list = sqlSession.selectList(queryId, object);

					rtnMap.put("list", list);
					rtnMap.put("param", object);
					return rtnMap;

				}else{
					HashMap params = (HashMap)object;
					
					String pageSize = StringUtils.nvl(String.valueOf(params.get("pageSize")), "10");
					int pageNo = Integer.valueOf(StringUtils.nvl(String.valueOf(params.get("pageNo")), "1"));
					params.put("startPageNo", (pageNo-1)*Integer.parseInt(pageSize));
					params.put("pageSize", pageSize);
					params.put("pageNo", pageNo);
					
					/*
					if("".equals(StringUtils.nvl(String.valueOf(params.get("pageNo")), ""))){
						params.put("startPageNo", 1);
						params.put("endPageNo", pageSize);
						params.put("pageNo", 1);
					}else{
						int pageNo = Integer.valueOf(StringUtils.nvl(String.valueOf(params.get("pageNo")), "1"));
						params.put("startPageNo", (pageNo * Integer.parseInt(pageSize)) - (Integer.parseInt(pageSize)-1));
						params.put("endPageNo", pageNo * Integer.parseInt(pageSize));
					}
					*/

					params.put("isPaging", true);
					list = sqlSession.selectList(queryId, params);
					total = new HashMap<String, Object>();

					if(list.size() == 0){
					    total.put("cnt", 0);
					} else {
						if(list.get(0) instanceof CommMap) {
							total.put("cnt", Integer.parseInt(StringUtils.defaultString(((ListOrderedMap)list.get(0)).get("rnumCnt"), "0").toString()));
						}else if(list.get(0) instanceof HashMap) {
							total.put("cnt", Integer.parseInt(StringUtils.defaultString(((HashMap<String, Object>)list.get(0)).get("RNUM_CNT"), "0").toString()));
						}
					}

					rtnMap.put("list", list);
					rtnMap.put("total", total);
					rtnMap.put("param", params);

					return rtnMap;
				}

			} else {

				if(object instanceof String){

					list = sqlSession.selectList(queryId, object);

					rtnMap.put("list", list);
					rtnMap.put("param", object);
					return rtnMap;

				}else{
					HashMap params = (HashMap)object;
					params.put("isPaging", false);

					list = sqlSession.selectList(queryId, params);

					if(list.size() == 0){
						total.put("cnt", 0);
					} else {
						if(list.get(0) instanceof CommMap) {
							total.put("cnt", Integer.parseInt(StringUtils.defaultString(((ListOrderedMap)list.get(0)).get("rnumCnt"), "0").toString()));
						}else if(list.get(0) instanceof HashMap) {
							total.put("cnt", Integer.parseInt(StringUtils.defaultString(((HashMap<String, Object>)list.get(0)).get("RNUM_CNT"), "0").toString()));
						}
					}

					rtnMap.put("list", list);
					rtnMap.put("total", total);
					rtnMap.put("param", params);
					return rtnMap;

				}

			}

		}catch(Exception e){
			throw new Exception(e);
		}

	}

	/**
	 * 단건조회
	 * @param queryId String
	 * @return Object
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object selectOne(String queryId) throws Exception{
		HashMap<String, Object> rtnMap = new HashMap<String, Object>();
		Object object = sqlSession.selectOne(queryId);

		try{
			if(object instanceof CommMap) {
				rtnMap.putAll((CommMap)object);
			}else if(object instanceof HashMap) {
				rtnMap.putAll((HashMap)object);
			}
		}catch(Exception e){
			throw new Exception(e);
		}

		return rtnMap;
	}

	/**
	 * 단건조회
	 * @param queryId String
	 * @param object Object
	 * @return Object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object selectOne(String queryId, Object param) throws Exception{
		HashMap<String, Object> rtnMap = new HashMap<String, Object>();
		Object object = new Object();

		try{
			if(param instanceof String){
				object = sqlSession.selectOne(queryId, param);
				if(object instanceof CommMap) {
					rtnMap.putAll((CommMap)object);
					return rtnMap;
				}else if(object instanceof HashMap) {
					rtnMap.putAll((HashMap)object);
					return rtnMap;
				}else if(object instanceof String) {
					return object;
				}
			}else{
				HashMap params = (HashMap)param;
				object = sqlSession.selectOne(queryId, params);
				if(object instanceof CommMap) {
					rtnMap.putAll((CommMap)object);
					return rtnMap;
				}else if(object instanceof HashMap) {
					rtnMap.putAll((HashMap)object);
					return rtnMap;
				}else if(object instanceof String) {
					return object;
				}
			}
		}catch(Exception e){
			throw new Exception(e);
		}
		return rtnMap;
	}

	/**
	 * 단건조회
	 * @param queryId String
	 * @param object Object
	 * @return Object
	 */
	@SuppressWarnings({ "rawtypes" })
	public String selectStringOne(String queryId, Object param) throws Exception{
		String object = null;

		try{
			if(param instanceof String){
				object = sqlSession.selectOne(queryId, param);
				return object;
			}else{
				HashMap params = (HashMap)param;
				object = sqlSession.selectOne(queryId, params);
				return object;
			}
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	/**
	 * 단건조회
	 * @param queryId String
	 * @return Object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object selectString(String queryId) throws Exception{
		HashMap<String, Object> rtnMap = new HashMap<String, Object>();
		Object object = sqlSession.selectOne(queryId);

		try{
			if(object instanceof CommMap) {
				rtnMap.putAll((CommMap)object);
			}else if(object instanceof HashMap) {
				rtnMap.putAll((HashMap)object);
			}
		}catch(Exception e){
			throw new Exception(e);
		}

		return rtnMap;
	}

	/**
	 * 단건조회
	 * @param queryId String
	 * @param object Object
	 * @return Object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object selectString(String queryId, Object param) throws Exception{
		HashMap<String, Object> rtnMap = new HashMap<String, Object>();
		Object object = new Object();

		try{
			if(param instanceof String){
				object = sqlSession.selectOne(queryId, param);
				if(object instanceof CommMap) {
					rtnMap.putAll((CommMap)object);
				}else if(object instanceof HashMap) {
					rtnMap.putAll((HashMap)object);
				}
			}else{
				HashMap params = (HashMap)param;
				object = sqlSession.selectOne(queryId, params);
				if(object instanceof CommMap) {
					rtnMap.putAll((CommMap)object);
				}else if(object instanceof HashMap) {
					rtnMap.putAll((HashMap)object);
				}
			}
		}catch(Exception e){
			throw new Exception(e);
		}

		return rtnMap;
	}

	/**
	 * 등록
	 * @param queryId String
	 * @param object Object
	 * @return int
	 */
	@SuppressWarnings({ "rawtypes" })
	public int insert(String queryId, Object object) throws Exception{
		try{
			if(object instanceof String){
				return sqlSession.insert(queryId, object);
			}else{
				HashMap params = (HashMap)object;
				return sqlSession.insert(queryId, params);
			}
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	/**
	 * 수정
	 * @param queryId String
	 * @param object Object
	 * @return int
	 */
	@SuppressWarnings({ "rawtypes" })
	public int update(String queryId, Object object) throws Exception{

		try{
			if(object instanceof String){
				return sqlSession.update(queryId, object);
			}else{
				HashMap params = (HashMap)object;
				return sqlSession.update(queryId, params);
			}
		}catch(Exception e){
			throw new Exception(e);
		}

	}

	/**
	 * 삭제
	 * @param queryId String
	 * @param object Object
	 * @return int
	 */
	@SuppressWarnings({ "rawtypes" })
	public int delete(String queryId, Object object) throws Exception{

		try{
			if(object instanceof String){
				return sqlSession.delete(queryId, object);
			}else{
				HashMap params = (HashMap)object;
				return sqlSession.delete(queryId, params);
			}
		}catch(Exception e){
			throw new Exception(e);
		}

	}

	/**
	 * 트리 리스트
	 * @param queryId String
	 * @param object Object
	 * @return HashMap
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap<String, Object> treeList(String queryId, Object object) throws Exception{

		HashMap rtnMap = new HashMap();
		List list = null;
		List treeList = new ArrayList();

		try{

			list = sqlSession.selectList(queryId, object);

			if(list != null && list.size() > 0){
				ListOrderedMap treeMap = new ListOrderedMap();

				List oneList = new ArrayList();
				List twoList = new ArrayList();
				List threeList = new ArrayList();
				List fourList = new ArrayList();
				List fiveList = new ArrayList();
				// 1레벨
				for(int i = 0; i < list.size(); i++){
//					com.sktelecom.checkit.core.util.CommMap cannot be cast to java.util.HashMap
					treeMap = (ListOrderedMap)list.get(i);
					String LEVEL = String.valueOf(treeMap.get("lvl"));

					if("1".equals(LEVEL)){
						oneList.add(treeMap);
					}

					if("2".equals(LEVEL)){
						twoList.add(treeMap);
					}

					if("3".equals(LEVEL)){
						threeList.add(treeMap);
					}

					if("4".equals(LEVEL)){
						threeList.add(treeMap);
					}

				}

				ListOrderedMap oneMap = new ListOrderedMap();
				for(int i = 0; i < oneList.size(); i++){
					oneMap = (ListOrderedMap)oneList.get(i);

					String oneId = String.valueOf(oneMap.get("code"));

					if(twoList != null && twoList.size() > 0){
						ListOrderedMap twoMap = new ListOrderedMap();
						List addTwoList = new ArrayList();
						for(int j = 0; j < twoList.size(); j++){
							twoMap = (ListOrderedMap)twoList.get(j);
							String twoUpId = String.valueOf(twoMap.get("upCode"));
							String twoId = String.valueOf(twoMap.get("code"));

//							if(oneId.equals(twoUpId)){
//								addTwoList.add(twoMap);
//							}

							if(threeList != null && threeList.size() > 0){
								ListOrderedMap threeMap = new ListOrderedMap();
								List addThreeList = new ArrayList();
								for(int k = 0; k < threeList.size(); k++){
									threeMap = (ListOrderedMap)threeList.get(k);
									String threeUpId = String.valueOf(threeMap.get("upCode"));

									if(twoId.equals(threeUpId)){
										addThreeList.add(threeMap);
									}

								}

								if(addThreeList.size() > 0){
									twoMap.put("subTree", addThreeList);
									if(oneId.equals(twoUpId)){
										addTwoList.add(twoMap);
									}
								}else{
									if(oneId.equals(twoUpId)){
										addTwoList.add(twoMap);
									}
								}

							}else{
								if(oneId.equals(twoUpId)){
									addTwoList.add(twoMap);
								}
							}

						}

						if(addTwoList.size() > 0){
							oneMap.put("subTree", addTwoList);
							treeList.add(oneMap);
						}

					}

				}

			}

			rtnMap.put("result", "ok");
			rtnMap.put("msg", "조회되었습니다.");
			rtnMap.put("tree", treeList);

		}catch(Exception e){
			rtnMap.put("msg", "데이터를 가져 오는 중 오류가 발생하였습니다.");
			rtnMap.put("result", "error");
		}

		return rtnMap;
	}

}
