package com.sktelecom.checkit.core.resolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 * 파일 다운로드를 위한 ViewResorver
 * @author devkimsj
 */
public class CsvDownloadViewResolver extends AbstractView{
    Log log = LogFactory.getLog(CsvDownloadViewResolver.class);


	/**
	 * 파일 다운로드 ViewResolver
	 * @param param 다운로드 할 파일 정보 Map
	 *               "file" : File - 다운로드 할 대상 파일 객체
	 *               "fileOriginName" : String - 클라이언트에 다운로드 될 때 표시될(저장될) 파일명
	 * @param req
	 * @param res
	 * @throws Exception
	 */

	@Override
	protected void renderMergedOutputModel(Map<String, Object> param, HttpServletRequest req, HttpServletResponse res) throws Exception {

		File file           = null;
		FileInputStream fis = null;
		OutputStream out    = null;

		try {

			file = (File)param.get("file");

			res.setContentLength((int)file.length());

			String downloadFileName = file.getName();

			if (param.get("fileOriginName") != null) {
				downloadFileName = (String)param.get("fileOriginName");
			}
			setDisposition(downloadFileName, req, res);

			out = res.getOutputStream();

			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);

			if(out != null){
				out.flush();
			}

		}catch(Exception e){
			log.error(e.getMessage());
		}finally{
			if(fis != null){
				fis.close();
			}
			if(out != null){
				out.close();
			}
			if(file.isFile()){
				file.delete();
			}
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
	 * @param filename String
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws Exception
	 */
	private void setDisposition(String filename, HttpServletRequest req, HttpServletResponse res) throws Exception{
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
			throw new IOException("지원하지 않는 브라우저 입니다.");
		}

		res.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);

		if("Opera".equals(browser)){
			res.setContentType("application/octet-stream;charset=UTF-8");
		}
	}

}
