package com.example.demo.contrller;



import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.FindbugsService;

@Controller
public class FileUploadController {
    private final static Logger logger = LoggerFactory.getLogger(FileUploadController.class);

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
        System.out.println("文件夹路径：" + folder.getAbsolutePath());


        String dirPath = FileUploadController.class.getResource("/").getFile();//SimplePOJO是当前类名
        logger.info(dirPath);
        File rootDir = new File(dirPath);
        File[] fs = rootDir.listFiles(); // 遍历path下的文件和目录，放在File数组中
        for (File f : fs) { // 遍历File[]数组
            if (!f.isDirectory()) {// 若非目录(即文件)，则打印
                System.out.println(f);//遍历当前文件夹下面的文件
            } else {
                System.out.println(f.getAbsolutePath());
            }
        }

        String oldName = multipartFile.getOriginalFilename();

        String uuid = UUID.randomUUID().toString();
        String newName = uuid + oldName.substring(oldName.lastIndexOf("."), oldName.length());
        try {
            File file = new File(folder, newName);
            multipartFile.transferTo(file);
            String filePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/uploadFile/"
                    + format + newName;
            String htmlName = oldName + uuid;
            String htmlStr=htmlName+ ".html";
//            String htmlPath = rootDir.getAbsolutePath()+"/static/"+htmlStr;
            String htmlPath = rootDir.getAbsolutePath()+"/templates/"+htmlStr;

            //开始分析文件
            String[] aString = {"-low", "-html", "-output", htmlPath, file.getAbsolutePath()};

            FindbugsService findbugsService = new FindbugsService();
            findbugsService.analysis(aString);

            String htmlBack = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()+"/"+htmlStr;
            return htmlName;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "上传失败";
    }

}
