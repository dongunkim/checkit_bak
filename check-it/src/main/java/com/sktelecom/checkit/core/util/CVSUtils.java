package com.sktelecom.checkit.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sktelecom.checkit.core.common.service.CommonService;
import com.sktelecom.checkit.core.property.PropertyService;


/**
 * 엑셀 유틸
 * @author sjkim
 */
@Component("cvsUtils")
public final class CVSUtils{

	private final static Log log = LogFactory.getLog(CVSUtils.class);

	private static final String XML_ENCODING  = "UTF-8";

	@Resource
    private PropertyService propertyService;

	@Resource
	private CommonService commonService;

	/**
	 * csv 읽기
	 * @param fileName
	 * @param excelList
	 * @param headList
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> csvRead(HashMap<String, Object> param, HttpServletRequest req) throws Exception{

		HashMap<String, Object> rtn = new HashMap<String, Object>();
		List<LinkedMap> csvList = new ArrayList<LinkedMap>();
		BufferedReader bufferedReader = null;

		String tableName    = null;
		String[] headList   = null;
		String fullFilePath = null;
		int index           = 1;

		try {

			MultipartHttpServletRequest multi = (MultipartHttpServletRequest) req;
			final Map<String, MultipartFile> files = multi.getFileMap();
			// 파일의 정보 취득
	    	if(!files.isEmpty()){

	    		// 테이블명
				tableName = String.valueOf(param.get("tableName"));

	    		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
	    		while(itr.hasNext()){
	    			Entry<String, MultipartFile> entry = itr.next();
	    			MultipartFile mFile = entry.getValue();

	    			String fileDir = propertyService.getString("filePath.upload.tmp"); // 파일 디렉토리 경로

	    			File saveFolder = new File(fileDir);  // 폴더 확인

		        	// 폴더가 없을경우 폴더 생성
		        	if(!saveFolder.exists() || saveFolder.isFile()){
		        		saveFolder.mkdirs();
		        	}

					String fileName = mFile.getOriginalFilename();
					fullFilePath    = fileDir + File.separator + fileName;

					// 파일 복사
					mFile.transferTo(new File(fullFilePath));
					String line = "";

					if(fileName.indexOf(".csv") > -1 || fileName.indexOf(".CSV") > -1){

						bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fullFilePath),"euc-kr"));

						while ((line = bufferedReader.readLine()) != null) {

							String array[] = line.split(",", -1);

							LinkedMap tmpMap = new LinkedMap();

							// 컬럼명
							if(index == 1) {
								headList = new String[array.length];
							}

							for(int i = 0; i < array.length; i++) {

								// 두번째 열은 colum 명
								if(index == 1) {
									headList[i] = array[i];
								}else if(index > 1){
									tmpMap.put(headList[i], array[i]);
								}

							}

							if(index > 1) {
								csvList.add(tmpMap);
							}

							index++;
			            }

						bufferedReader.close();

						rtn.put("errorCode", "00");
						rtn.put("errorMessage", "정상처리되었습니다.");
						rtn.put("tableName", tableName);
						rtn.put("headerList", headList);
			    		rtn.put("csvList", csvList);

					}else{

						rtn.put("errorCode", "01");
						rtn.put("errorMessage", "CSV 파일만 업로드 가능합니다.");

					}

	    		}

	    	}

		}catch(Exception ex) {
			throw new Exception("잘못된 포멧의 csv 파일입니다." + (index + 1) + "행 또는 주위행을 확인해 보세요.");
		}finally{

			// 파일이 있을경우 삭제
			File file = new File(fullFilePath);
			if(file.isFile()){
				file.delete();
			}

			if(bufferedReader != null) {
				bufferedReader.close();
			}

		}

		return rtn;

	}
	
	/**
	 * CSV 다운로드
	 * @param fileName
	 * @param excelList
	 * @param headList
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public File csvDownload(String fileName, List<HashMap<String, Object>> csvList, List<HashMap<String, Object>> headList, HttpServletRequest req, HttpServletResponse res
			,String searchTextBox) throws Exception{

		File file = null;
		BufferedWriter bufferedWriter = null;

		try{

			String fileDir = propertyService.getString("filePath.csv"); // 파일 디렉토리 경로
			File fileDirectory = new File(fileDir) ;
			if (!fileDirectory.exists()) {
				fileDirectory.mkdirs() ;
			}

			bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir + File.separator + fileName + ".csv"), "euc-kr"));

			if(StringUtils.isNotEmpty(searchTextBox)) {
				bufferedWriter.write("검색조건 : "+searchTextBox); 
				bufferedWriter.newLine();
				bufferedWriter.newLine();
			}
			
			String[] sort = new String[headList.size()];
			HashMap<String, Object> checkDataMap = new HashMap<String, Object>();

			int sortIndex = 0;
			String HeaderLineWriter = "";
			for(HashMap<String, Object> map : headList){
				String key   = String.valueOf(map.get("key"));
				String label = String.valueOf(map.get("label"));

				HeaderLineWriter += StringUtils.defaultString(label, "-").replace(",", ".") + ",";

				sort[sortIndex] = key;
				checkDataMap.put(key, map);
				sortIndex++;
			}

			bufferedWriter.write(HeaderLineWriter.substring(0, HeaderLineWriter.length() - 1));
			bufferedWriter.newLine();

			for(HashMap<String, Object> map : csvList){
				String lineWriter = "";

				HashMap<String, Object> checkMap = new HashMap<String, Object>();
				for(int i = 0; i < sort.length; i++){

					checkMap = (HashMap<String, Object>) checkDataMap.get(sort[i]);

					Boolean code = Boolean.valueOf(String.valueOf(checkMap.get("code")));
					String type  = (String)checkMap.get("type");
					String value = String.valueOf(StringUtils.defaultString(map.get(sort[i]), ""));

					if(code && value != null){
						String codeValue = commonService.getCommonCsvCode(value);
						if(null != codeValue){
							value = codeValue;
						}
					}

					if(null != type) {
						Date dateType = new Date();
						String changeTypeData = null;
						SimpleDateFormat defaultDate = new SimpleDateFormat("yyyyMMddhhmmss"), date, datetime;

						int len = StringUtils.defaultString(value, "-").length();

						boolean isType = true;

						if(len == 8) {
							defaultDate = new SimpleDateFormat("yyyyMMdd");
						}else if(len == 10) {
							defaultDate = new SimpleDateFormat("yyyyMMddhh");
						}else if(len == 12) {
							defaultDate = new SimpleDateFormat("yyyyMMddhhmm");
						}else if(len == 14) {
							defaultDate = new SimpleDateFormat("yyyyMMddhhmmss");
						}else {
							isType = false;
						}

						if(isType) {

							if("date".equals(type)) {
								date = new SimpleDateFormat("yyyy-MM-dd");
								dateType = defaultDate.parse(value);
								changeTypeData = date.format(dateType);
							}else if("datetime".equals(type)) {
								datetime = new SimpleDateFormat("yyyy-MM-dd hh mm:ss");
								dateType = defaultDate.parse(value);
								changeTypeData = datetime.format(dateType);
							}

						}

						if(null != changeTypeData){
							value = changeTypeData;
						}
					}

					lineWriter += StringUtils.defaultString(value, "-").replace(",", ".") + ",";
				}
				bufferedWriter.write(lineWriter.substring(0, lineWriter.length() - 1));
				bufferedWriter.newLine();
			}

			bufferedWriter.flush();

			file = new File(fileDir + File.separator + fileName + ".csv"); // 파일취득

		}catch (Exception e) {
			log.error("[CSV파일을 만드는중 오류가 발생하였습니다.]:" + e.getMessage());
		}finally {
			if(bufferedWriter != null){
				bufferedWriter.close();
			}
		}

		return file;

	}

	/**
	 * CSV 다운로드
	 * @param fileName
	 * @param excelList
	 * @param headList
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public File csvDownload(String fileName, List<HashMap<String, Object>> csvList, List<HashMap<String, Object>> headList, HttpServletRequest req, HttpServletResponse res) throws Exception{

		File file = null;
		BufferedWriter bufferedWriter = null;

		try{

			String fileDir = propertyService.getString("filePath.csv"); // 파일 디렉토리 경로
			File fileDirectory = new File(fileDir) ;
			if (!fileDirectory.exists()) {
				fileDirectory.mkdirs() ;
			}
			
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir + File.separator + fileName + ".csv"), "euc-kr"));
			
			String[] sort = new String[headList.size()];
			HashMap<String, Object> checkDataMap = new HashMap<String, Object>();

			int sortIndex = 0;
			String HeaderLineWriter = "";
			for(HashMap<String, Object> map : headList){
				String key   = String.valueOf(map.get("key"));
				String label = String.valueOf(map.get("label"));

				HeaderLineWriter += StringUtils.defaultString(label, "-").replace(",", ".") + ",";

				sort[sortIndex] = key;
				checkDataMap.put(key, map);
				sortIndex++;
			}

			bufferedWriter.write(HeaderLineWriter.substring(0, HeaderLineWriter.length() - 1));
			bufferedWriter.newLine();

			for(HashMap<String, Object> map : csvList){
				String lineWriter = "";

				HashMap<String, Object> checkMap = new HashMap<String, Object>();
				for(int i = 0; i < sort.length; i++){

					checkMap = (HashMap<String, Object>) checkDataMap.get(sort[i]);

					Boolean code = Boolean.valueOf(String.valueOf(checkMap.get("code")));
					String type  = (String)checkMap.get("type");
					String value = String.valueOf(StringUtils.defaultString(map.get(sort[i]), ""));

					if(code && value != null){
						String codeValue = commonService.getCommonCsvCode(value);
						if(null != codeValue){
							value = codeValue;
						}
					}

					if(null != type) {
						Date dateType = new Date();
						String changeTypeData = null;
						SimpleDateFormat defaultDate = new SimpleDateFormat("yyyyMMddhhmmss"), date, datetime;

						int len = StringUtils.defaultString(value, "-").length();

						boolean isType = true;

						if(len == 8) {
							defaultDate = new SimpleDateFormat("yyyyMMdd");
						}else if(len == 10) {
							defaultDate = new SimpleDateFormat("yyyyMMddhh");
						}else if(len == 12) {
							defaultDate = new SimpleDateFormat("yyyyMMddhhmm");
						}else if(len == 14) {
							defaultDate = new SimpleDateFormat("yyyyMMddhhmmss");
						}else {
							isType = false;
						}

						if(isType) {

							if("date".equals(type)) {
								date = new SimpleDateFormat("yyyy-MM-dd");
								dateType = defaultDate.parse(value);
								changeTypeData = date.format(dateType);
							}else if("datetime".equals(type)) {
								datetime = new SimpleDateFormat("yyyy-MM-dd hh mm:ss");
								dateType = defaultDate.parse(value);
								changeTypeData = datetime.format(dateType);
							}

						}

						if(null != changeTypeData){
							value = changeTypeData;
						}
					}

					lineWriter += StringUtils.defaultString(value, "-").replace(",", ".") + ",";
				}
				bufferedWriter.write(lineWriter.substring(0, lineWriter.length() - 1));
				bufferedWriter.newLine();
			}

			bufferedWriter.flush();

			file = new File(fileDir + File.separator + fileName + ".csv"); // 파일취득

		}catch (Exception e) {
			log.error("[CSV파일을 만드는중 오류가 발생하였습니다.]:" + e.getMessage());
		}finally {
			if(bufferedWriter != null){
				bufferedWriter.close();
			}
		}

		return file;

	}

	/**
	 * 엑셀 다운로드
	 * @param fileName
	 * @param excelList
	 * @param headList
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public File excelDownload(String fileName, List<HashMap<String, Object>> excelList, List<HashMap<String, Object>> headList, HttpServletRequest req, HttpServletResponse res) throws Exception{

		HashMap<String, Object> excelMap = null;
		HashMap<String, Object>  headMap = null;

		int headSize = 0;

		FileOutputStream fos  = null;
		XSSFWorkbook workbook = null;
		File file             = null;

		try{

			if(excelList != null && excelList.size() > 0){

				workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet(fileName);
				XSSFCellStyle numberStyle = workbook.createCellStyle();
				XSSFCellStyle doubleStyle = workbook.createCellStyle();
				XSSFCellStyle textStyle   = workbook.createCellStyle();
				XSSFDataFormat format   = workbook.createDataFormat();

				XSSFRow row   = null;
				XSSFCell cell = null;
				headSize = headList.size();

				for(int i = 0; i < excelList.size(); i++){

					excelMap = excelList.get(i);

					// 헤더생성
					if(i == 0){
						row = sheet.createRow(i);
						headMap = new HashMap<String, Object>();
						for(int j = 0; j < headSize; j++){
							cell = row.createCell(j);
							headMap = headList.get(j);
							cell.setCellValue(String.valueOf(headMap.get("label")));
						}
					}

					row = sheet.createRow(i + 1);
					headMap = new HashMap<String, Object>();
					for(int j = 0; j < headSize; j++){

						cell = row.createCell(j);
						headMap = headList.get(j);

						if("number".equals(StringUtils.defaultString(String.valueOf(headMap.get("formatter")), ""))){
							String data = String.valueOf(excelMap.get(String.valueOf(headMap.get("key"))));
							cell.setCellValue(Double.valueOf(data));
							String chnageData = "";
							if(data.indexOf(".") > -1){
								chnageData = data.substring(data.indexOf(".") + 1, data.length());
								String zero = "0.";
								for(int k = 0; k < chnageData.length(); k++){
									zero += "0";
								}
								doubleStyle.setDataFormat(format.getFormat(zero));
								cell.setCellStyle(doubleStyle);
							}else{
								numberStyle.setDataFormat(format.getFormat("0"));
								cell.setCellStyle(numberStyle);
							}

						}else{
							cell.setCellValue(String.valueOf(StringUtils.nvl(excelMap.get(String.valueOf(headMap.get("key"))),"")));
							textStyle.setDataFormat(format.getFormat("@"));
							cell.setCellStyle(textStyle);
						}


					}

				}

				String fileDir = propertyService.getString("filePath.excel"); // 파일 디렉토리 경로
				File fileDirectory = new File(fileDir) ;
				if (!fileDirectory.exists()) {
					fileDirectory.mkdirs() ;
				}

				fos = new FileOutputStream(fileDir + File.separator + fileName + ".xlsx");

				workbook.write(fos);
				fos.flush();

				file = new File(fileDir + File.separator + fileName + ".xlsx"); // 파일취득

			}else{
				throw new Exception("읽어들일 엑셀 데이터가 없습니다.");
			}

		}catch(Exception e){
			log.error(e.getMessage());
			throw new Exception("엑셀을 만드는중 오류가 발생하였습니다.");
		}finally{
			if(workbook != null){
				workbook.close();
			}
			if(fos != null){
				fos.close();
			}
		}

		return file;

	}

	/**
	 * 엑셀 읽기
	 * @param int - 엑셀을 읽기 시작하는 행의 시작
	 * @param HttpServletRequest
	 * @return List<HashMap<String, String>>
	 */
	@SuppressWarnings({ "deprecation" })
	public List<HashMap<String, Object>> excelRead(HashMap<String, Object> param, HttpServletRequest req) throws Exception{

		int rowIndex      = 1;
		int cellIndex     = 1;
		String[] headList = null;

		ArrayList<HashMap<String, Object>> rtnList = new ArrayList<HashMap<String, Object>>();
		String fullFilePath = null;

		ObjectMapper mapper = new ObjectMapper();

		try{

		    // 오피스 설정에 따라 압축률이 0.1이하인 경우가 발생하여 최소 압축률을 낮춰줌.
            ZipSecureFile.setMinInflateRatio(0.001);

			rowIndex  = Integer.valueOf((String)param.get("rowIndex"));
			cellIndex = Integer.valueOf((String)param.get("cellIndex"));
			String headListParam  = (String)param.get("headList");

			headList  = mapper.readValue(headListParam, new TypeReference<String[]>(){});

			MultipartHttpServletRequest multi = (MultipartHttpServletRequest) req;
			final Map<String, MultipartFile> files = multi.getFileMap();
			// 파일의 정보 취득
	    	if(!files.isEmpty()){

	    		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
	    		while(itr.hasNext()){
	    			Entry<String, MultipartFile> entry = itr.next();
	    			MultipartFile mFile = entry.getValue();

	    			String fileDir = propertyService.getString("filePath.excel"); // 파일 디렉토리 경로

	    			File saveFolder = new File(fileDir);  // 폴더 확인

		        	// 폴더가 없을경우 폴더 생성
		        	if(!saveFolder.exists() || saveFolder.isFile()){
		        		saveFolder.mkdirs();
		        	}

					String fileName = mFile.getOriginalFilename();
					fullFilePath    = fileDir + File.separator + fileName;

					// 파일 복사
					mFile.transferTo(new File(fullFilePath));

					if (fileName.indexOf(".xlsx") > -1 || fileName.indexOf(".XLSX") > -1){

						XSSFWorkbook workBook  = null;
						XSSFSheet sheet        = null;
						XSSFRow row            = null;
						XSSFCell cell          = null;

						try{

							workBook  = new XSSFWorkbook(new FileInputStream(new File(fullFilePath)));
							int sheetNum = workBook.getNumberOfSheets();

							for(int i = 0; i < sheetNum; i++){

								// 시트는 하나로 통일(혹 이후 시트가 많아도 처리해야할때 풀것)
								if(i == 1){
									break;
								}

								sheet    = workBook.getSheetAt(i);
								// 엑셀 업로드시 업로드 카운트 제한 시
								int maxUploadCnt = Integer.parseInt(StringUtils.nvl(param.get("maxUploadCnt"), "0"));
								int rows = sheet.getPhysicalNumberOfRows();

								if( maxUploadCnt > 0 && maxUploadCnt < rows ){
									rows = maxUploadCnt;
								}

								boolean isBreak = false;
								for(int j = (rowIndex -1); j < rows; j++){

									row       = sheet.getRow(j);
									int cells = row.getPhysicalNumberOfCells();
									HashMap<String, Object> map = new HashMap<String, Object>();

									if(isBreak){
										break;
									}

									for(int c = (cellIndex -1), h = 0; c < cells; c++, h++){

										cell = row.getCell(c);
										if(h == 0 && "".equals(String.valueOf(cell))){
											isBreak = true;
											break;
										}

										if(headList != null && headList.length > h){

											String headItemName = headList[h];

											if(null != cell){

												switch(cell.getCellType()){
												case 0:
													map.put(headItemName, String.valueOf(cell.getNumericCellValue()));
													break;
												case 1:
													map.put(headItemName, cell.getStringCellValue());
													break;
												case Cell.CELL_TYPE_FORMULA :
													try{
														map.put(headItemName, cell.getStringCellValue());
													}catch(Exception e){
														log.error("not String!! headItemName:"+headItemName);
														map.put(headItemName, String.valueOf(cell.getNumericCellValue()));
													}
													break;
												default:
													map.put(headItemName, "");
												}

											}else{
												map.put(headItemName, "");
											}
										}else{
											throw new Exception("엑셀 데이터와 그리드 헤더의 수가 다릅니다.");
										}

									}

									if(!map.isEmpty()){
									    map.put("rowIndex", j);
	                                    rtnList.add(map);
									}

								}

						    }

						}catch(Exception e){
							log.error("ExcelUtils workBook parsing Error[" + e.getMessage() + "]");
							throw new Exception(e.getMessage());
						}finally {
							if(workBook != null){
								workBook.close();
							}
						}

					}else{

						HSSFWorkbook workBook  = null;
						HSSFSheet sheet        = null;
						HSSFRow row            = null;
						HSSFCell cell          = null;
						try{

							workBook  = new HSSFWorkbook(new FileInputStream(new File(fullFilePath)));
							int sheetNum = workBook.getNumberOfSheets();

							for(int i = 0, h = 0; i < sheetNum; i++, h++){

								// 시트는 하나로 통일(혹 이후 시트가 많아도 처리해야할때 풀것)
								if(i == 1){
									break;
								}

								sheet    = workBook.getSheetAt(i);
								int rows = sheet.getPhysicalNumberOfRows();

								boolean isBreak = false;
								for(int j = (rowIndex - 1); j < rows; j++){
									row       = sheet.getRow(j);
									int cells = row.getPhysicalNumberOfCells();

									HashMap<String, Object> map = new HashMap<String, Object>();

									if(isBreak){
										break;
									}

									for(int c = (cellIndex -1); c < cells; c++){

										cell = row.getCell(c);

										if(h == 0 && "".equals(String.valueOf(cell))){
											isBreak = true;
											break;
										}

										if(headList != null && headList.length > h){

											String headItemName = headList[h];

											if(cell != null){
												switch(cell.getCellType()){

												case 0:
													map.put(headItemName, Double.toString(cell.getNumericCellValue()));
													break;
												case 1:
													map.put(headItemName, cell.getStringCellValue());
													break;
												case Cell.CELL_TYPE_FORMULA :
													try{
														map.put(headItemName, cell.getStringCellValue());
													}catch(Exception e){
														log.error("not String!! headItemName:"+headItemName);
														map.put(headItemName, String.valueOf(cell.getNumericCellValue()));
													}
													break;
												default:
													map.put(headItemName, "");
												}
											}else{
												map.put(headItemName, "");
											}

										}else{
											throw new Exception("엑셀 데이터와 그리드 헤더의 수가 다릅니다.");
										}

									}

									rtnList.add(map);

								}

						    }

						}catch(Exception e){
							log.error("ExcelUtils workBook parsing Error[" + e.getMessage() + "]");
						}finally{
							if(workBook != null){
								workBook.close();
							}
						}

					}

	    		}
	    	}

		}catch(Exception e){
			log.debug(e.getMessage());
			throw new Exception(e.getMessage());
		}finally{

			// 파일이 있을경우 삭제
			File file = new File(fullFilePath);
			if(file.isFile()){
				file.delete();
			}

			ZipSecureFile.setMinInflateRatio(0.01);
		}

		return rtnList;

	}

	/**
	 * 스타일 정의
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	private static Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb){

		Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
		XSSFDataFormat fmt = wb.createDataFormat();

		XSSFCellStyle style0 = wb.createCellStyle();
		style0.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style0.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style0.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style0.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style0.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style0.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style0.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		styles.put("default", style0);

		XSSFCellStyle style1 = wb.createCellStyle();
		style1.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style1.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style1.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style1.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style1.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style1.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style1.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		style1.setDataFormat(fmt.getFormat("0.0%"));
		styles.put("percent", style1);

		XSSFCellStyle style2 = wb.createCellStyle();
		style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style2.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style2.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style2.setDataFormat(fmt.getFormat("0.0X"));
		styles.put("coeff", style2);

		XSSFCellStyle style3 = wb.createCellStyle();
		style3.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style3.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style3.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style3.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style3.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		style3.setDataFormat(fmt.getFormat("$#,##0.00"));
		styles.put("currency", style3);

		XSSFCellStyle style4 = wb.createCellStyle();
		style4.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style4.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style4.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style4.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style4.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style4.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style4.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		style4.setDataFormat(fmt.getFormat("mmm dd"));
		styles.put("date", style4);

		XSSFCellStyle style5 = wb.createCellStyle();
		XSSFFont headerFont = wb.createFont();
		headerFont.setBold(true);
		style5.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style5.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style5.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style5.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style5.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style5.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style5.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style5.setFont(headerFont);
		styles.put("header", style5);

		return styles;

	}

	/**
	 * 탬프 xml 파일 생성
	 * @param out
	 * @param styles
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private static void generateXml(String headerArr, List<HashMap<String, Object>> list, Writer out, Map<String, XSSFCellStyle> styles) throws Exception{

		SpreadsheetWriter sw = new SpreadsheetWriter(out);
		sw.beginSheet();

		// 헤더 정의
		String[] header = StringUtils.split(headerArr, "|");

		sw.insertRow(0);
		int headerIndex = styles.get("header").getIndex();
		for(int i = 0; i < header.length; i++) {

			sw.createCell(i, String.valueOf(header[i]), headerIndex);

		}

		sw.endRow();

		// 실제 내용 생성 및 스타일 지정
		HashMap<String, Object> map = null;
		int styleIndex = styles.get("default").getIndex();

		for(int i = 0; i < list.size(); i++){

			map = list.get(i);
			sw.insertRow(i + 1);

			for (int j = 0; j < header.length; j++) {

				// createCell(arg1, arg2, arg3); arg1에는 cell 번호, arg2에는 데이터, arg3 스타일
				sw.createCell(j, "<![CDATA[" + String.valueOf(map.get(header[j]) + "]]>"), styleIndex);

			}

			sw.endRow();

		}

		sw.endSheet();

	}

	/**
	 * 스트림 복사
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private static void copyStream(InputStream in, OutputStream out) throws IOException{
		byte[] chunk = new byte[1024];
		int count;
		while((count = in.read(chunk)) >= 0){
			out.write(chunk,0,count);
		}
	}

    /**
     * Writes spreadsheet data in a Writer.
     * (YK: in future it may evolve in a full-featured API for streaming data in Excel)
     */
	public static class SpreadsheetWriter{
		private final Writer _out;
		private int _rownum;

		public SpreadsheetWriter(Writer out){
			_out = out;
		}

		public void beginSheet() throws IOException{
			_out.write("<?xml version=\"1.0\" encoding=\""+XML_ENCODING+"\"?>" +
					"<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">" );
			_out.write("<sheetData>\n");
		}

		public void endSheet() throws IOException{
			_out.write("</sheetData>");
			_out.write("</worksheet>");
		}

        public void insertRow(int rownum) throws IOException{
        	_out.write("<row r=\""+(rownum+1)+"\">\n");
        	this._rownum = rownum;
        }

        public void endRow() throws IOException{
        	_out.write("</row>\n");
        }

        public void createCell(int columnIndex, String value, int styleIndex) throws IOException{
        	String ref = new CellReference(_rownum, columnIndex).formatAsString();
        	_out.write("<c r=\""+ref+"\" t=\"inlineStr\"");
        	if(styleIndex != -1) _out.write(" s=\""+styleIndex+"\"");
        	_out.write(">");
            _out.write("<is><t>"+value+"</t></is>");
            _out.write("</c>");
        }

        public void createCell(int columnIndex, String value) throws IOException{
        	createCell(columnIndex, value, -1);
        }

        public void createCell(int columnIndex, double value, int styleIndex) throws IOException{
        	String ref = new CellReference(_rownum, columnIndex).formatAsString();
        	_out.write("<c r=\""+ref+"\" t=\"n\"");
        	if(styleIndex != -1) _out.write(" s=\""+styleIndex+"\"");
        	_out.write(">");
        	_out.write("<v>"+value+"</v>");
        	_out.write("</c>");
        }

        public void createCell(int columnIndex, double value) throws IOException{
        	createCell(columnIndex, value, -1);
        }

        public void createCell(int columnIndex, Calendar value, int styleIndex) throws IOException{
        	createCell(columnIndex, DateUtil.getExcelDate(value, false), styleIndex);
        }
	}

	/**
	 * 브라우저 구분 얻기.
	 * @param request
	 * @return String
	 */
	private String getBrowser(HttpServletRequest request){
		String header = request.getHeader("User-Agent");
		if(header.indexOf("Trident") > -1){
			return "MSIE";
		}else if (header.indexOf("Chrome") > -1){
			return "Chrome";
		}else if (header.indexOf("Opera") > -1){
			return "Opera";
		}
		return "Firefox";
	}

	/**
	 * Disposition 지정하기.
	 * @param filename
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void setDisposition(String filename, HttpServletRequest req, HttpServletResponse res) throws Exception{
		String browser = getBrowser(req);

		String dispositionPrefix = "attachment; filename=";
		String encodedFilename   = null;

		if(browser.equals("MSIE")){
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		}else if (browser.equals("Firefox")){
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		}else if (browser.equals("Opera")){
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		}else if (browser.equals("Chrome")){
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < filename.length(); i++){
				char c = filename.charAt(i);
				if(c > '~'){
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				}else{
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();
		}else{
			throw new IOException("Not supported browser");
		}

		res.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);

		if("Opera".equals(browser)){
			res.setContentType("application/octet-stream;charset=UTF-8");
		}
	}

}