package com.sktelecom.checkit.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;


/**
 * 이미지 유틸
 * @author sjkim
 */
@Component("imageUtils")
public final class ImageUtils{

	private final static Log log = LogFactory.getLog(ImageUtils.class);

	/**
	 * 이미지 리더
	 * @param imagePath
	 * @param res
	 * @throws Exception
	 */
	public void imageReader(String imagePath, HttpServletResponse res) throws Exception{

		FileInputStream fileInputStream             = null;
		BufferedInputStream bufferedInputStream     = null;
		ByteArrayOutputStream byteArrayOutputStream = null;

		try {

			File file = new File(imagePath); // 파일취득

			fileInputStream = new FileInputStream(file);
			bufferedInputStream = new BufferedInputStream(fileInputStream);
			byteArrayOutputStream = new ByteArrayOutputStream();

			int imgByte;
			while((imgByte = bufferedInputStream.read()) != -1) {
				byteArrayOutputStream.write(imgByte);
			}

			String fileExt = imagePath.substring(imagePath.lastIndexOf(".")); // 파일 확장자
			String imageType = ""; // 파일 종류
			if("jpg".equals(fileExt)) {
				imageType = "image/jpeg";
			} else {
				imageType = "image/"+ fileExt.toLowerCase();
			}

			res.setHeader("Content-Type", imageType);
			res.setContentLength(byteArrayOutputStream.size());
			byteArrayOutputStream.writeTo(res.getOutputStream());
			res.getOutputStream().flush();
			res.getOutputStream().close();

		} catch(Exception e) {
			log.error("[imageRead:Error]"+ e.toString());
		} finally {
			if(byteArrayOutputStream != null) {
				byteArrayOutputStream.close();
			}
			if(bufferedInputStream != null) {
				bufferedInputStream.close();
			}
			if(fileInputStream != null) {
				fileInputStream.close();
			}
		}

	}

	/**
	 * 이미지 다운로드
	 * @param imagePath
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	public void imageDownload(String imagePath, String fileName, HttpServletRequest req, HttpServletResponse res) throws Exception{
		File file    = new File(imagePath); // 파일취득
		int fileSize = (int)file.length(); // 파일사이즈

		BufferedInputStream in   = null;
		BufferedOutputStream out = null;
		PrintWriter printout     = null;

		try {

			if(fileName.lastIndexOf(".") == -1){
				String fileExt = imagePath.substring(imagePath.lastIndexOf(".")); // 파일 확장자
				fileName = fileName + fileExt;
			}

			fileName = URLEncoder.encode(fileName, "UTF-8");

			res.setContentType("application/x-msdownload");
			setDisposition(imagePath, req, res);
			res.setContentLength(fileSize);
			res.setHeader("Content-Disposition", "attachment;filename=" + fileName);

			in  = new BufferedInputStream(new FileInputStream(file));
			out = new BufferedOutputStream(res.getOutputStream());

			FileCopyUtils.copy(in, out);
			out.flush();

		} catch(Exception ex) {
			log.error("[imageDownload:Error]"+ ex.getMessage());
		} finally {
			if(in != null) {
				in.close();
			}
			if(out != null) {
				out.close();
			}
			if(printout != null){
				printout.close();
			}
		}

	}

	/**
	 * Disposition 지정하기
	 * @param filename
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	private void setDisposition(String filename, HttpServletRequest req, HttpServletResponse res) throws Exception {

		String browser = "";

		String header = req.getHeader("User-Agent");
		if(header.indexOf("Trident") > -1){
			browser = "MSIE";
		}else if (header.indexOf("Chrome") > -1){
			browser = "Chrome";
		}else if (header.indexOf("Opera") > -1){
			browser = "Opera";
		}else{
			browser =" Firefox";
		}

		String dispositionPrefix = "attachment; filename=";
		String encodedFilename = null;

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