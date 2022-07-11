package com.sktelecom.checkit.core.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sktelecom.checkit.core.property.PropertyService;
import com.sktelecom.checkit.core.util.DateUtils;
import com.sktelecom.checkit.core.util.Session;

/**
 * 파일업로드
 * @author devkimsj
 *
 */
@Component("fileUpload")
public class FileUpload{

	private final static Log log = LogFactory.getLog(FileUpload.class);

	@Resource
	protected PropertyService propertyService;

	/**
	 * 템프파일 업로드
	 * @param req
	 * @param keyStr
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> tmpUpload(HttpServletRequest req, String keyStr, String uuid, String attachId) throws Exception{

		int fileIndex = 1;
		List<HashMap<String, Object>> fileList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> result = new HashMap<String, Object>();
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) req;
		final Map<String, MultipartFile> files = multi.getFileMap();

		// 파일의 정보 취득
		if(!files.isEmpty()){
			String fileDir = propertyService.getString(keyStr) + File.separator + uuid; // 파일 디렉토리 경로
			File saveFolder = new File(fileDir);  // 폴더 확인

			// 폴더가 없을경우 폴더 생성
			if(!saveFolder.exists() || saveFolder.isFile()){
				saveFolder.mkdirs();
			}

			Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
			MultipartFile file;
			String filePath    = ""; // 파일경로
			String newFileName = ""; // 변경된 파일명
			HashMap<String, Object> fileMap;

			while(itr.hasNext()){
				Entry<String, MultipartFile> entry = itr.next();

				file = entry.getValue();                                        // 파일정보
				String orginFileName = file.getOriginalFilename();              // 원본 파일명
				if("".equals(orginFileName)) {
					return result;
				}

				int index       = orginFileName.lastIndexOf(".");
//					String fileName = orginFileName.substring(0, index);            // 확장자를 뺀 파일명
				String fileExt  = orginFileName.substring(index).toLowerCase(); // 확장자
				byte[] fileSize = file.getBytes();                              // 파일사이즈

				// 확장자로 첨부 파일여부체크
				if(!fileExtException(fileExt)){
					throw new Exception("파일업로드시 [" + fileExt + "]확장자는 첨부하실수 없습니다.");
				}

				if(fileSize.length > 20000000){
					throw new Exception("파일사이즈는 20MB를 초과해서는 안됩니다.");
				}

				if(!"".equals(orginFileName)){

					String newfilename = "";
					SecureRandom random = new SecureRandom();
					for(int i=0; i < 20; i++){
						newfilename += random.nextInt(9);
					}
					int saveIndex  = 0;
					newFileName    = newfilename + "[" + Integer.toString(saveIndex) + "]"; // 변경된 파일명
					filePath       = fileDir + File.separator + newFileName + fileExt;   // 변경된 모든경로와 파일명
					File fileEmpty = new File(filePath);                                 // 파일 여부확인

					// 파일이 있을경우 파일명을 계속 변경한다.
					while(fileEmpty.isFile()){
						saveIndex  += 1;
						newFileName    = newfilename + "[" + Integer.toString(saveIndex) + "]"; // 변경된 파일명
						filePath       = fileDir + File.separator + newFileName + fileExt;   // 변경된 모든경로와 파일명
						fileEmpty   = new File(filePath);                                    // 파일 여부확인
					}

					file.transferTo(new File(filePath));

				}

				fileMap = new HashMap<String, Object>();
				fileMap.put("inputName", entry.getKey());			  // input name명
				fileMap.put("fileIndex", fileIndex);				  // 파일별 첨부순서
				fileMap.put("filePath", fileDir);					  // 파일저장경로
				fileMap.put("fileName", orginFileName);				  // 원본파일명
				fileMap.put("fileChangeName", newFileName + fileExt); // 변경된 파일명
				fileMap.put("fileContentType", fileExt);			  // 파일 확장자
				fileMap.put("fileSize", fileSize.length);			  // 파일사이즈
				fileMap.put("cud", "I");							  // 파일 구분
				fileMap.put("attachId", attachId);					  // 파일 구분

				fileList.add(fileMap);

				fileIndex++;
			}
		}

		result.put("errorCode", "00");
		result.put("errorMessage", "정상");
		result.put("fileList", fileList);

		return result;

	}

	/**
	 * 템프파일 실제 경로 업로드
	 * @param req
	 * @param keyStr
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void upload(List<HashMap<String, Object>> fileList, String keyStr) throws Exception{
		String fileGbn = "";
		if(fileList != null && fileList.size() > 0) {
			fileGbn = String.valueOf(fileList.get(0).get("fileGbn"));
		}

		DateUtils dateUtils = new DateUtils();

		String fileDir = propertyService.getString(keyStr) + File.separator + fileGbn + File.separator + dateUtils.getToday(); // 파일 디렉토리 경로

		File saveFolder = new File(fileDir);  // 폴더 확인

		// 폴더가 없을경우 폴더 생성
		if(!saveFolder.exists() || saveFolder.isFile()){
			saveFolder.mkdirs();
		}

		Session session = new Session();
		if(fileList != null && fileList.size() > 0) {
			HashMap<String, Object> fileMap = new HashMap<String, Object>();

			for(int i = 0; i < fileList.size(); i++) {
				fileMap = fileList.get(i);

				// 등록일경우
				if("I".equals(String.valueOf(fileMap.get("cud")))){
					String orginFileName = String.valueOf(fileMap.get("fileName"));        // 파일명
					String fileExt       = String.valueOf(fileMap.get("fileContentType")); // 파일확장자
					String newFileName = ""; // 변경된 파일명
					String filePath    = ""; // 파일경로
					String tempFilePath = String.valueOf(fileMap.get("filePath")) + File.separator + String.valueOf(fileMap.get("fileChangeName"));

					if(!"".equals(orginFileName)){

						String newfilename = "";
						SecureRandom random = new SecureRandom();
						for(int j=0; j < 20; j++){
							newfilename += random.nextInt(9);
						}

						int saveIndex  = 0;
						newFileName    = newfilename + "[" + Integer.toString(saveIndex) + "]"; // 변경된 파일명
						filePath       = fileDir + File.separator + newFileName + fileExt;      // 변경된 모든경로와 파일명
						File fileEmpty = new File(filePath);                                    // 파일 여부확인

						// 파일이 있을경우 파일명을 계속 변경한다.
						while(fileEmpty.isFile()){
							saveIndex  += 1;
							newFileName    = newfilename + "[" + Integer.toString(saveIndex) + "]"; // 변경된 파일명
							filePath       = fileDir + File.separator + newFileName + fileExt;   // 변경된 모든경로와 파일명
							fileEmpty   = new File(filePath);                                    // 파일 여부확인
						}

						copyFile(tempFilePath, filePath); // 파일복사

						if(!fileEmpty.isFile()) {
							throw new Exception("파일업로드시 실패");
							//throw new Exception("파일업로드시 실패.", "ERR_FILE03");
						}

					}

					fileMap.put("userId", String.valueOf(session.getUserId())); // 파일경로
					fileMap.put("filePath", fileDir); // 파일경로
					fileMap.put("fileChangeName", newFileName + fileExt); // 변경된 파일명
				}
			}
		}
	}

	/**
	 * 파일 다이렉트 업로드(단건)
	 * @param req
	 * @param keyStr
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> directUpload(HttpServletRequest req, String keyStr) throws Exception{

		int fileIndex = 1;
		HashMap<String, Object> fileMap = new HashMap<String, Object>();
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) req;
		final Map<String, MultipartFile> files = multi.getFileMap();

		// 파일의 정보 취득
		if(!files.isEmpty()){

			String fileDir = propertyService.getString(keyStr); // 파일 디렉토리 경로

			File saveFolder = new File(fileDir);  // 폴더 확인

			// 폴더가 없을경우 폴더 생성
			if(!saveFolder.exists() || saveFolder.isFile()){
				saveFolder.mkdirs();
			}

			Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
			MultipartFile file;
			String filePath    = ""; // 파일경로
			String newFileName = ""; // 변경된 파일명

			while(itr.hasNext()){
				Entry<String, MultipartFile> entry = itr.next();

				file = entry.getValue();                                        // 파일정보
				String orginFileName = file.getOriginalFilename();              // 원본 파일명
				if("".equals(orginFileName)) {
					return fileMap;
				}

				int index       = orginFileName.lastIndexOf(".");
//					String fileName = orginFileName.substring(0, index);            // 확장자를 뺀 파일명
				String fileExt  = orginFileName.substring(index).toLowerCase(); // 확장자
				byte[] fileSize = file.getBytes();                              // 파일사이즈

				// 확장자로 첨부 파일여부체크
				if(!fileExtException(fileExt)){
					throw new Exception("파일업로드시 [" + fileExt + "]확장자는 첨부하실수 없습니다.");
				}

				if(fileSize.length > 20000000){
					throw new Exception("파일사이즈는 20MB를 초과해서는 안됩니다.");
				}

				if(!"".equals(orginFileName)){

					String newfilename = "";
					SecureRandom random = new SecureRandom();
					for(int i=0; i < 20; i++){
						newfilename += random.nextInt(9);
					}
					int saveIndex  = 0;
					newFileName    = newfilename + "[" + Integer.toString(saveIndex) + "]"; // 변경된 파일명
					filePath       = fileDir + File.separator + newFileName + fileExt;   // 변경된 모든경로와 파일명
					File fileEmpty = new File(filePath);                                 // 파일 여부확인

					// 파일이 있을경우 파일명을 계속 변경한다.
					while(fileEmpty.isFile()){
						saveIndex  += 1;
						newFileName    = newfilename + "[" + Integer.toString(saveIndex) + "]"; // 변경된 파일명
						filePath       = fileDir + File.separator + newFileName + fileExt;   // 변경된 모든경로와 파일명
						fileEmpty   = new File(filePath);                                    // 파일 여부확인
					}

					file.transferTo(new File(filePath));

				}

				fileMap.put("inputName", entry.getKey());			  // input name명
				fileMap.put("fileIndex", fileIndex);				  // 파일별 첨부순서
				fileMap.put("filePath", fileDir);					  // 파일저장경로
				fileMap.put("fileName", orginFileName);				  // 원본파일명
				fileMap.put("fileChangeName", newFileName + fileExt); // 변경된 파일명
				fileMap.put("fileContentType", fileExt);			  // 파일 확장자
				fileMap.put("fileSize", fileSize.length);			  // 파일사이즈

			}
		}

		fileMap.put("errorCode", "00");
		fileMap.put("errorMessage", "정상");

		return fileMap;

	}

	/**
     * 파일복사
     * @param fileNm
     * @param sourceDir
     * @param targetDir
     * @return
     * @throws Exception
     */
    public void copyFile(String tmpFile, String originFile) throws Exception{
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try{

            fis = new FileInputStream(tmpFile);
            fos = new FileOutputStream(originFile);

            byte[] buffer = new byte[1024];
            int cnt = 0;
            while((cnt = fis.read(buffer)) != -1){
                fos.write(buffer, 0, cnt);
            }
        }catch(Exception e){
            log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }finally{
            if(fis != null){
                fis.close();
            }
            if(fos != null){
                fos.close();
            }
        }
    }

    /**
     * Image to Base64
     * @param param
     * @return rtn
     * @throws Exception
     */
	public HashMap<String, Object> imageToBase64(HashMap<String, Object> param)throws Exception{
		HashMap<String, Object> rtn = new HashMap<String, Object>();

		String arrayData = "";
		String fileData = "";
		String filePath = "";
		String fileNm = "";
		String fileExtNm = "";

		File file = null;
		FileInputStream fis = null;
		ByteArrayOutputStream baos = null;

		try{
			filePath = (String) param.get("FILE_PATH");
			fileData = (String) param.get("FILE_DATA");
			fileNm = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
			fileExtNm = fileNm.substring(fileNm.lastIndexOf(".") + 1);

			// 로컬이 아닌 경우
			if(fileData == null || StringUtils.isBlank(fileData)){
				file = new File(filePath);
				if(file.exists()){
					fis = new FileInputStream(file);
					baos = new ByteArrayOutputStream();

					byte[] buffer = new byte[1024];
					int cnt = 0;
					while((cnt = fis.read(buffer)) != -1){
						baos.write(buffer, 0, cnt);
					}

					byte[] fileArray = baos.toByteArray();
					arrayData = new String(Base64.encodeBase64(fileArray));

					fileData = "data:image/" + fileExtNm + ";base64," + arrayData;
				}
			}

		}catch(Exception e){
			log.error(e.getMessage());
			throw new Exception("이미지 생성 실패");
		}finally{
			if(fis != null){
				fis.close();
			}
			if(baos != null){
				baos.close();
			}
		}
		rtn.put("fileNm", fileNm);
		rtn.put("fileData", fileData);

		return rtn;
	}

	/**
	 * 파일업로드 파일 삭제
	 * @param List<FileVO>
	 */
	public void fileRemove(List<HashMap<String, Object>> list) throws Exception{

		try{
			String fileDir = "";
			HashMap<String, Object> fileMap = new HashMap<String, Object>();
			File file;
			if(list != null && list.size() > 0){
				for(int i = 0; i < list.size(); i++){
					fileMap = list.get(i);
					fileDir = String.valueOf(fileMap.get("FILE_DIR")) + File.separator + String.valueOf(fileMap.get("NEW_FILE_NAME")); // 파일경로
					file = new File(fileDir);                          // 파일
					file.delete();                                     // 파일 삭제
				}
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
	}

	/**
	 * 파일업로드 확장자 확인
	 * @param String
	 * @return boolean
	 */
	private boolean fileExtException(String fileExt){

		boolean rtn = true;

		String[] ExceptionFileExt  = {"htm","html","jsp","php","php3","php5","phtml","asp","aspx","ascx","cfm","cfc","pl","bat","exe","dll","reg","cgi","java"};

		for(int i = 0; i < ExceptionFileExt.length; i++){
			if(fileExt.indexOf(ExceptionFileExt[i]) != -1){
				rtn = false;
				break;
			}
		}

		return rtn;
	}

}