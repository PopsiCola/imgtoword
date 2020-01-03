package com.llb.imgtoword.controller;

import com.llb.imgtoword.qiniu.Qiniu;
import com.llb.imgtoword.utils.BaiduOrc;
import com.llb.imgtoword.utils.FileUtil;
import com.llb.imgtoword.utils.PropertiesUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传
 * @Author llb
 * Date on 2019/12/7
 */
@Controller
public class UploadController {

    //文件保存到本地位置
    private static final String FILE_PATH = PropertiesUtils.getProperty("file_path");
    //文件上传时，保存在服务器的地址
    private static final String UPLOAD_PATH = PropertiesUtils.getProperty("upload_path");
    //文件保存路径
    private static final String QINIU_URl = PropertiesUtils.getProperty("qiniu_url");

    /**
     * 文件上传首页
     * @return
     */
    @GetMapping("/toUploadFile")
    public String toUploadFile(){
        return "index";
    }

    /**
     * 上传文件，并提取图片文字
     * 一个文件一个文件上传
     * @return
     */
    @RequestMapping(value = "/imagetoword", method = RequestMethod.POST)
    public String imagetoword(HttpServletRequest request,
                               @RequestParam(value = "uploadfile", required = false) MultipartFile file) throws IOException {
        //判断是否是图片
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        if(!FileUtil.isImage(fileType)) {
            request.setAttribute("msg", "请上传文件格式。如：.bmp, .jpg, .png ");
            return "index";
        }
        //        MultipartFile是对当前上传文件的封装
        if(!file.isEmpty()) {
            //生成文档
            //获取上传的文件名
            String originalFilename = file.getOriginalFilename();
            //新建文档，将上传的文件保存到指定目录
            String destPath = UPLOAD_PATH + originalFilename;
            File destFile = new File(destPath);
            if(!new File(UPLOAD_PATH).exists()) {
                new File(UPLOAD_PATH).mkdirs();
            }
            file.transferTo(destFile);
            //提取图片中的文字
            String words = BaiduOrc.getWords(destPath);
            //将提取的文字保存到上传图片的同一目录，并且保存到doc文档中
            String docPath = UPLOAD_PATH + "words.doc";
            File wordFile = new File(docPath);
            FileUtil.writeContent(words, wordFile);
            //将文件自动下载到用户本地电脑
            request.setAttribute("msg", "提取文字保存在"+ docPath);
        } else {
            request.setAttribute("msg", "请选择上传的文件");
            return "index";
        }
        return "index";
    }

    /**
     * 上传文件，并提取图片文字到一个文档中
     * 多个文件一次上传
     * @return
     */
    @RequestMapping(value = "/imagetowords", method = RequestMethod.POST)
    public String imagetowords(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取文件
        MultiValueMap<String, MultipartFile> maps = request.getMultiFileMap();
        List<MultipartFile> files = maps.get("uploadfiles");
        //上传的文件
        File destFile = null;
        //生成的文件
        File wordFile = null;
        if(!files.isEmpty()) {
            //上传文件的目录
            List<String> fileNames = new ArrayList<String>();
            //保存图片
            for (MultipartFile file : files) {
                //判断是否是图片
                String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
                if(!FileUtil.isImage(fileType)) {
                    request.setAttribute("msg", "请上传文件格式。如：.bmp, .jpg, .png ");
                }
                //获取文件路径名称，并且保存到指定目录
                String filePath = UPLOAD_PATH + file.getOriginalFilename();
                destFile = new File(filePath);
                if (!new File(UPLOAD_PATH).exists()) {
                    new File(UPLOAD_PATH).mkdirs();
                }
                file.transferTo(destFile);
                //提取图片中的文字
                String words = BaiduOrc.getWords(filePath);
                //将提取的文字保存到上传图片的同一目录，并且保存到doc文档中
//                String docPath = filePath.substring(0, filePath.lastIndexOf("."));
                String docPath = UPLOAD_PATH + "words.doc";
                wordFile = new File(docPath);
                FileUtil.writeContent(words, wordFile);
            }
//            FileUtil.downloadNet(wordFile, FILE_PATH);
            request.setAttribute("msg", "提取文字保存在" + wordFile.getAbsolutePath());
        } else {
            request.setAttribute("msg", "请选择上传的文件");
            return "index";
        }
        return "index";
    }

    /**
     * 普通版：上传文件，并提取图片文字
     * @return
     */
    @RequestMapping(value = "/imagetoOrd", method = RequestMethod.POST)
    public String imagetoOrdinaryWords(HttpServletRequest request,
                              @RequestParam(value = "uploadfile", required = false) MultipartFile file) throws IOException {
        //判断是否是图片
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        if(!FileUtil.isImage(fileType)) {
            request.setAttribute("msg", "请上传文件格式。如：.bmp, .jpg, .png ");
            return "index";
        }
        //        MultipartFile是对当前上传文件的封装
        if(!file.isEmpty()) {
            //生成文档
            //获取上传的文件名
            String originalFilename = file.getOriginalFilename();
            //新建文档，将上传的文件保存到指定目录
            String destPath = UPLOAD_PATH + originalFilename;
            File destFile = new File(destPath);
            if(!new File(UPLOAD_PATH).exists()) {
                new File(UPLOAD_PATH).mkdirs();
            }
            file.transferTo(destFile);
            //提取图片中的文字
            String words = BaiduOrc.getOrdinaryWords(destPath);
            //将提取的文字保存到上传图片的同一目录，并且保存到doc文档中
            //            String docPath = destPath.substring(0, destPath.lastIndexOf("."));
            //            docPath = docPath+ ".doc";
            String docPath = UPLOAD_PATH + "words.doc";
            File wordFile = new File(docPath);
            FileUtil.writeContent(words, wordFile);

            request.setAttribute("msg", "提取文字保存在"+ docPath);
        } else {
            request.setAttribute("msg", "请选择上传的文件");
            return "index";
        }
        return "index";
    }

    @RequestMapping("/downloadFile")
    public void downloadFile(HttpServletRequest req, HttpServletResponse res) {
        //文件路径
        String filepath = req.getParameter("filename");
        //文件名称
        String filename = filepath.substring(filepath.lastIndexOf("."));
        DataInputStream in = null;
        OutputStream out = null;

        try {
            //清空输出流
            res.reset();
            res.setCharacterEncoding("UTF-8");
            res.setHeader("Content-disposition", "attachment;filename=" + filename);
            // 定义输出类型
            res.setContentType("application/msword");
            //输入流：本地文件的路径
            in = new DataInputStream(new FileInputStream(new File(FILE_PATH)));
            //输出流
            out = res.getOutputStream();
            //输出文件

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    /**
     * 将文件保存到七牛云，并下载提取文字保存的文档
     * @return
     */
    @RequestMapping(value = "/imgToWordsQiniu", method = RequestMethod.POST)
    public String imgToWordsQiniu(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取文件
        MultiValueMap<String, MultipartFile> maps = request.getMultiFileMap();
        List<MultipartFile> files = maps.get("uploadQiniu");
        //上传的文件
        File destFile = null;
        //生成的文件
        File wordFile = null;
        if(!files.isEmpty()) {
            //上传文件的目录
            List<String> fileNames = new ArrayList<String>();
            //保存图片
            for (MultipartFile file : files) {
                //判断是否是图片
                String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
                if(!FileUtil.isImage(fileType)) {
                    request.setAttribute("msg", "请上传文件格式。如：.bmp, .jpg, .png ");
                }
                //获取文件路径名称，并且保存到指定目录
                String filePath = UPLOAD_PATH + file.getOriginalFilename();
                destFile = new File(filePath);
                if (!new File(UPLOAD_PATH).exists()) {
                    new File(UPLOAD_PATH).mkdirs();
                }
                file.transferTo(destFile);
                //提取图片中的文字
                String words = BaiduOrc.getWords(filePath);
                //将提取的文字保存到上传图片的同一目录，并且保存到doc文档中
                //                String docPath = filePath.substring(0, filePath.lastIndexOf("."));
                String docPath = UPLOAD_PATH + "words.doc";
                wordFile = new File(docPath);
                FileUtil.writeContent(words, wordFile);
            }
            FileUtil.downloadNet(wordFile, FILE_PATH);
            request.setAttribute("msg", "提取文字保存在" + wordFile.getAbsolutePath());
        } else {
            request.setAttribute("msg", "请选择上传的文件");
            return "index";
        }
        return "index";
    }

    /**
     * 多个文件一次上传到七牛云，并且下载下来
     * @return
     */
    @RequestMapping(value = "/imagetoqiniu", method = RequestMethod.POST)
    public String imagetoqiniu(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {
        Qiniu qiniu = new Qiniu();
        //获取文件
        MultiValueMap<String, MultipartFile> maps = request.getMultiFileMap();
        List<MultipartFile> files = maps.get("uploadfilestoQiniu");
        //上传的文件
        File destFile = null;
        //生成的文件
        File wordFile = null;
        if(!files.isEmpty()) {
            //上传文件的目录
            List<String> fileNames = new ArrayList<String>();
            //保存图片
            for (MultipartFile file : files) {

                //        qiniu.fileUpload(new File("F:\\mysys\\毕业设计-垃圾分类(智能图片识别垃圾分类）\\测试\\a80754b6c3545dce2df106812674f4a.jpg"), "img2words/a80754b6c3545dce2df106812674f4a.jpg", "images");
//                downloadForQiniu("1577341981.jpg", "E:/images/");

                //判断是否是图片
                String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
                if(!FileUtil.isImage(fileType)) {
                    request.setAttribute("msg", "请上传文件格式。如：.bmp, .jpg, .png ");
                    return "index";
                }
                //获取文件路径名称，并且保存到指定目录
                String filePath = UPLOAD_PATH + file.getOriginalFilename();
                destFile = new File(filePath);
                if (!new File(UPLOAD_PATH).exists()) {
                    new File(UPLOAD_PATH).mkdirs();
                }
                file.transferTo(destFile);
                //提取图片中的文字
                String words = BaiduOrc.getWords(filePath);
                //将提取的文字保存到上传图片的同一目录，并且保存到doc文档中
                String docPath = UPLOAD_PATH + "words.doc";
                wordFile = new File(docPath);
                System.out.println(wordFile.getName());
                FileUtil.writeContent(words, wordFile);

                System.out.println("destFile" + destFile + "filename " + destFile.getName());
                //将图片上传到七牛云
                qiniu.fileUploadToQiniu(destFile, destFile.getName(), "images");
            }
            qiniu.fileUploadToQiniu(wordFile, wordFile.getName(), "images");
//            qiniu.downloadForQiniu(wordFile.getName(), "E://images");
            request.setAttribute("downfile", QINIU_URl + wordFile.getName());
            request.setAttribute("down", "点击下载识别的文字");
            request.setAttribute("msg", "提取文字保存在" + QINIU_URl + wordFile.getName());
        } else {
            request.setAttribute("msg", "请选择上传的文件");
            return "index";
        }
        return "index";
    }

}
