package com.llb.imgtoword.utils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 图片文件工具类
 * @Author llb
 * Date on 2019/12/6
 */
public class FileUtil {

   //图片文件的基本格式
     static String[] arr = {"bmp", "jpg", "png", "tif", "gif", "pcx", "tga", "exif", "fpx", "svg", "psd", "cdr", "pcd", "dxf", "ufo", "eps", "ai", "raw", "WMF", "webp"};

     /**
     * 判断是否是图片格式
     * @param fileType
     * @return
     */
    public static boolean isImage(String fileType) {
        for (int i=0; i<arr.length; i++) {
            if(arr[i].equals(fileType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 写入文档
     * @param data 写入文档的数据
     * @param file 写入的文件
     */
    public static void writeContent(String data, File file) {
        BufferedWriter writer = null;
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            //append:原文件内容追加
            FileWriter fileWriter = new FileWriter(file.getAbsolutePath(), true);
            writer = new BufferedWriter(fileWriter);
            writer.write(data);
//            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从服务器下载文件
     * @param downloadfile 上传的文件
     * @param path 保存的文件的路径
     */
    public static void downloadNet(File downloadfile, String path) {
        String downloadpath = downloadfile.getAbsolutePath();
        FileInputStream in = null;
//        OutputStream out = null;
        System.out.println("downloadNet==========要下载的文件是"+ downloadpath);
        FileOutputStream fileOut = null;
        BufferedOutputStream bos = null;
        File file = new File(path);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            //读取要下载的文件，保存到文件输入流
            in= new FileInputStream(downloadpath);
            //创建输出流
//            out= response.getOutputStream();
            fileOut = new FileOutputStream(file);
            bos = new BufferedOutputStream(fileOut);
            //缓存区
            byte[] buffer = new byte[1024];
            int len = 0;
            //循环将输入流中的内容读取到缓冲区中
            while((len = in.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
            System.out.println("downloadNet========下载的文件将要保存到"+file.getAbsolutePath());
            //删除服务器上的临时文件
//            downloadfile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(in != null) {
                    in.close();
                }
                if(bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 从服务器下载文件
     * @param downloadfile 上传的文件
     * @param path 保存的文件的路径
     * @param response
     */
    public static void downloadNet1(File downloadfile, String path, HttpServletResponse response, HttpServletRequest request) {
        String downloadpath = downloadfile.getAbsolutePath();
        FileInputStream in = null;
        //        OutputStream out = null;
        System.out.println("downloadNet==========要下载的文件是"+ downloadpath);
        FileOutputStream fileOut = null;
        BufferedOutputStream bos = null;
        File file = new File(path);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            //读取要下载的文件，保存到文件输入流
            in= new FileInputStream(downloadpath);
            //创建输出流
            //            out= response.getOutputStream();
            fileOut = new FileOutputStream(file);
            bos = new BufferedOutputStream(fileOut);
            //缓存区
            byte[] buffer = new byte[1024];
            int len = 0;
            //循环将输入流中的内容读取到缓冲区中
            while((len = in.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
            System.out.println("downloadNet========下载的文件将要保存到"+file.getAbsolutePath());
            //删除服务器上的临时文件
            //            downloadfile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(in != null) {
                    in.close();
                }
                if(bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
