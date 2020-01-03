package com.llb.imgtoword.qiniu;

import com.llb.imgtoword.utils.PropertiesUtils;
import com.qiniu.util.Auth;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * 创建存储空间，每个用户对应一个
 * @Author llb
 * Date on 2019/12/15
 */
public class CreateBucket {
    //七牛存储空间路径
    private static final String BUCKET = PropertiesUtils.getProperty("bucket");
    // 七牛云秘钥
    private static final String ACCESS_KEY = PropertiesUtils.getProperty("access_key");
    private static final String SECRET_KEY = PropertiesUtils.getProperty("secret_key");
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    /**
     * 创建存储空间
     * @param bucketName  空间名称
     * @param storageArea 存储区域
     */
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

    /**
     * 编码
     *
     * @param bstr
     * @return String
     */
    public static String encode(byte[] bstr) {
        return new BASE64Encoder().encode(bstr);
    }

}
