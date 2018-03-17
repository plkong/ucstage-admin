package com.controller;

import com.utils.Constants;
import com.utils.ImgFileObj;
import com.utils.UrlTool;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping(path="/upload")
public class UploadController {
    /*
     *  获取上传的文件夹，具体路径参考application.properties中的配置
     */
    @Value("${web.path}")
    private String uploadPath;

    public static final String UPLOAD_DIR = "upload-dir";

    private final ResourceLoader resourceLoader;

    @Autowired
    public UploadController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * GET请求
     * 上传页面，也将显示已经存在的文件
     * @param model
     * @return
     */
    @GetMapping(path="/list")
    public String index(Model model) {
        //获取已存在的文件
        File[] files = new File(uploadPath).listFiles();
        model.addAttribute("files", files);
        return "web/index";
    }

    /**
     * POST请求
     * @param request
     * @param file
     * @return
     */
    @PostMapping(path="/img")
    public @ResponseBody String uploadImg(HttpServletRequest request, @RequestParam("productImg")MultipartFile file, @RequestParam("categoryId")String categoryId, @RequestParam("title")String title) {
        //可以从页面传参数过来
        //List<ImgFileObj> results = new ArrayList<ImgFileObj>();
        //这里可以支持多文件上传
        String filePathName = "";
        if(file!=null) {
            BufferedOutputStream bw = null;
            try {
                filePathName = UPLOAD_DIR + Constants.URL_SEPARATOR + categoryId + Constants.URL_SEPARATOR + title + Constants.URL_SEPARATOR + file.getOriginalFilename();
                //判断是否有文件且是否为图片文件
                if(filePathName!=null && !"".equalsIgnoreCase(filePathName.trim()) && isImageFile(filePathName)) {
                    //创建输出文件对象
                    filePathName = refactorFileName(filePathName);
                    File outFile = new File(uploadPath + Constants.URL_SEPARATOR + filePathName);
                    //拷贝文件到输出文件对象
                    FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
                    //results.add(imgFileObj);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if(bw!=null) {bw.close();}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filePathName;
    }

    /**
     * POST请求
     * @param request
     * @param files
     * @return
     */
    @PostMapping(path="/imgs")
    public @ResponseBody String uploadImgs(HttpServletRequest request, @RequestParam("productImg")MultipartFile[] files, @RequestParam("categoryId")String categoryId) {
        //可以从页面传参数过来
        System.out.println("name====="+request.getParameter("name"));
        //List<ImgFileObj> results = new ArrayList<ImgFileObj>();
        //这里可以支持多文件上传
        if(files!=null && files.length>=1) {
            BufferedOutputStream bw = null;
            try {
                String filePath = UPLOAD_DIR + File.separator + categoryId;
                String imgRootURL = UrlTool.getServerAddress(request) + UPLOAD_DIR + "/" + categoryId;
                for (MultipartFile file: files
                        ) {
                    String fileName = file.getOriginalFilename();
                    //判断是否有文件且是否为图片文件
                    if(fileName!=null && !"".equalsIgnoreCase(fileName.trim()) && isImageFile(fileName)) {
                        //创建输出文件对象
                        File outFile = new File(uploadPath + File.separator + filePath + File.separator + fileName);
                        //拷贝文件到输出文件对象
                        FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
                        ImgFileObj imgFileObj = new ImgFileObj(fileName, imgRootURL);
                        //results.add(imgFileObj);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if(bw!=null) {bw.close();}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "SUCCESS";
    }

    /**
     * 判断文件是否为图片文件
     * @param fileName
     * @return
     */
    private Boolean isImageFile(String fileName) {
        String [] img_type = new String[]{".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        if(fileName==null) {return false;}
        fileName = fileName.toLowerCase();
        for(String type : img_type) {
            if(fileName.endsWith(type)) {return true;}
        }
        return false;
    }

    /**
     * 获取文件后缀名
     * @param fileName
     * @return
     */
    private String getFileType(String fileName) {
        if(fileName!=null && fileName.indexOf(".")>=0) {
            return fileName.substring(fileName.lastIndexOf("."), fileName.length());
        }
        return "";
    }

    private String refactorFileName(String fileName) {
        return fileName.trim().replace(' ', '-');
    }
}
