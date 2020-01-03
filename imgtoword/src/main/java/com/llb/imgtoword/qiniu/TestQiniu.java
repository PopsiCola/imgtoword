package com.llb.imgtoword.qiniu;

import com.llb.imgtoword.utils.PropertiesUtils;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.File;

/**
 * 七牛云上传下载测试
 * @Author llb
 * Date on 2019/12/13
 */
public class TestQiniu {

    // 七牛云秘钥
    private static final String ACCESS_KEY = PropertiesUtils.getProperty("access_key");
    private static final String SECRET_KEY = PropertiesUtils.getProperty("secret_key");
    //存储空间名称
    private static final String BUCKET_NAME = PropertiesUtils.getProperty("bucket_name");
    //存储区域（默认华东）
    private static final String REGION = PropertiesUtils.getProperty("region_huadong");
    //七牛路径
    private static final String BUCKET = PropertiesUtils.getProperty("bucket");

    static Auth auth = null;
    static {
        if(auth == null) {
            auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        }
    }
    //上传的目标对象（我这里地区是华东）
    Configuration cfg = new Configuration(Region.huadong());
    UploadManager uploadManager = new UploadManager(cfg);

    /**
     *
     * @param bucketName 空间名称
     * @param key 文件名称
     * @return
     */
    public String getUpToken(String bucketName, String key) {
        return auth.uploadToken(bucketName, key);
    }


 /*   *//**
     * 创建存储空间
     * @param bucketName  空间名称
     * @param storageArea 存储区域
     *//*
    public void createBucket(String bucketName,String storageArea) {
        String path = "/mkbucketv2/" + encode(bucketName.getBytes()) + "/region/"+ storageArea +"\n";
        String access_token = auth.sign(path);
        String url = BUCKET + encode(bucketName.getBytes()) + "/region/" + storageArea;
        OkHttpClient client = new OkHttpClient();
        //设置请求头
        Request request = new Request.Builder().url(url).addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "QBox " + access_token).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            //判断情况
            if(response.isSuccessful()) {
                System.out.println("TestQiniu：创建存储空间成功：" + response.code() + response.toString());
            } else if(response.code() == 614) {

                System.out.println("TestQiniu：目标资源已存在:" + response.code() + response.toString());
            } else if(response.code() == 400) {

                System.out.println("TestQiniu：invalid arguments:" + response.code() + response.toString());
            } else if(response.code() == 630) {

                System.out.println("TestQiniu：too many buckets。" + response.code() + response.toString());
            } else {
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    *//**
     * 编码
     *
     * @param bstr
     * @return String
     *//*
    public static String encode(byte[] bstr) {
        return new BASE64Encoder().encode(bstr);
    }

    *//**
     * 测试
     *
     * @param args
     *//*
    public static void main(String[] args) {

        new CreateBucket().createBucket("llb",REGION);
    }
*/


    public static void main(String[] args) {

/*        //上传文件路径
        String loclFilePath = "C:\\Users\\ASUS\\Desktop\\P2JG53B5(%~``$M]818~NOL.png";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "";

        File file = new File(loclFilePath);

        Qiniu qiniu = new Qiniu();

        qiniu.fileUpload(file, key, BUCKET_NAME);
        TestQiniu qiniu = new TestQiniu();
        qiniu.createBucket("llb", "");*/
    }
}
