package com.llb.imgtoword.qiniu;

import com.llb.imgtoword.utils.PropertiesUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import java.io.*;

/**
 * 七牛云
 * @Author llb
 * Date on 2019/12/15
 */
public class Qiniu {
    // 七牛云秘钥
    private static final String ACCESS_KEY = PropertiesUtils.getProperty("access_key");
    private static final String SECRET_KEY = PropertiesUtils.getProperty("secret_key");
    //文件保存路径
    private static final String QINIU_URl = PropertiesUtils.getProperty("qiniu_url");
    //存储空间名称
    private static final String BUCKET_NAME = PropertiesUtils.getProperty("bucket_name");
    //本地保存路径
    private static final String LOCAL_PATH = PropertiesUtils.getProperty("file_path");

    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //上传的目标对象（我这里地区是华东）
    Configuration cfg = new Configuration(Region.huadong());
    UploadManager uploadManager = new UploadManager(cfg);

    /**
     * 保存的存储空间以及保存名称
     * @param bucketName 空间名称
     * @param key 文件名称
     * @return
     */
    public String getUpToken(String bucketName, String key) {
        return auth.uploadToken(bucketName, key);
    }

    /**
     * 文件上传
     * @param file 上传的文件
     * @param filename 上传的文件名
     * @param bucketName 空间名称
     */
    public void fileUploadToQiniu(File file, String filename, String bucketName) {
        filename = "img2words/" + filename;
        System.out.println("fileUploadToQiniu" + file +  filename);
        try {
            // 调用put方法上传
            Response res = uploadManager.put(file, filename, getUpToken(bucketName,filename));
            // 打印返回的信息
            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取下载文件路径，即：donwloadUrl
     * @return
     */
    public String getDownloadUrl(String targetUrl) {
        String downloadUrl = auth.privateDownloadUrl(targetUrl);
        return downloadUrl;
    }


    /**
     * 通过发送http get 请求下载文件资源
     *
     * @param url 图片地址
     * @param filepath 文件保存路径
     * @return
     */
    public static void downloadForQiniu(String url, String filepath) {
        OkHttpClient client = new OkHttpClient();
        url = QINIU_URl + url;
        System.out.println(url);
        Request req = new Request.Builder().url(url).build();
        okhttp3.Response resp = null;
        File file = new File(filepath);

        FileInputStream in = null;
        FileOutputStream out = null;
        BufferedOutputStream buffout = null;
        try {
            resp = client.newCall(req).execute();
            System.out.println(resp);
            System.out.println(resp.isSuccessful());
            if(resp.isSuccessful()) {
                if(!file.exists()) {
                    file.createNewFile();
                }
                ResponseBody body = resp.body();
                InputStream bin = body.byteStream();
                out = new FileOutputStream(file + "/a.doc");
                buffout = new BufferedOutputStream(out);
                //缓存区
                byte[] buffer = new byte[1024];
                int len = 0;
                //循环将输入流中的内容读取到缓冲区中
                while((len = bin.read(buffer)) > 0) {
                    buffout.write(buffer, 0, len);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unexpected code " + resp);
        } finally {
            try {
                if(in != null) {
                    in.close();
                }
                if(buffout != null) {
                    buffout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Qiniu qiniu = new Qiniu();
        qiniu.fileUploadToQiniu(new File("E:\\images\\1577341981.jpg"), "1577341981.jpg", "images");
//        downloadForQiniu("1577341981.jpg", "E:/images/");

    }

}
