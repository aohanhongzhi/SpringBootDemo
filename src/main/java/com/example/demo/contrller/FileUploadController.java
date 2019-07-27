package com.example.demo.contrller;

import java.util.Date;
import java.util.UUID;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	@PostMapping("/upload")
	//下面方法的MultipartFile参数的变量名需要与input的name值保持一致。
	public String upload(MultipartFile multipartFile, HttpServletRequest req) {
		String realPath = req.getSession().getServletContext().getRealPath("uploadFile");
		String format = sdf.format(new Date());
		File folder = new File(realPath + format);
		if (!folder.isDirectory()) {
			folder.mkdirs();
			//文件夹路径：/tmp/tomcat-docbase.3038460815160185847.8080/uploadFile2019/07/27
			
		}
		System.out.println("文件夹路径："+folder.getAbsolutePath());
		String oldName = multipartFile.getOriginalFilename();
		String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."), oldName.length());
		try {
			multipartFile.transferTo(new File(folder, newName));
			String filePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/uploadFile/"
					+ format + newName;
			return filePath;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "上传失败";
	}

}
