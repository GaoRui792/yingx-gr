package com.baizhi.gr.util;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

public class AliyunUploadUtil {
    /*
    *  上传到阿里云的基础属性
    *   endpoint beijing可以更改  为Bucket的所在区域
    *   accessKeyId  阿里云密钥的id
    *   accessKeySecret 阿里云密钥的Secret
    * */
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "";
    private static String accessKeySecret = "";


    /*
    *  通过bytes数组的方式上传文件至阿里云
    *   参数含义: bucketName:上传至哪个列表中
    *   file:上传文件信息
    * */
    public static void AliyunUploadByBytes(String bucketName, String fileName,MultipartFile file){

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);

        try {
            byte[] bytes = file.getBytes();
            ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 关闭OSSClient。
        ossClient.shutdown();
    }

     /*
     * 删除阿里云文件
     * 参数：
     *   bucker: 存储空间名
     *   fileName:文件名    目录名/文件名
     * */
    public static void delete(String bucket,String fileNme){

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucket, fileNme);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

     /*
     * 视频截取  并上传至阿里云
     * 参数：
     *   bucker: 存储空间名
     *   fileName:远程文件文件名    目录名/文件名
     *   coverName：截取的封面名
     * */
    public static void videoCoverIntercept(String bucketName,String fileName,String coverName){

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 设置视频截帧操作。
        String style = "video/snapshot,t_1000,f_jpg,w_0,h_0";
        // 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, fileName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);

        //输出图片网络路径
        System.out.println(signedUrl);

        //文件上传 阿里云
        // 上传网络流。
        InputStream inputStream = null;
        try {
            inputStream = new URL(signedUrl.toString()).openStream();
            ossClient.putObject(bucketName, coverName, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 关闭OSSClient。
        ossClient.shutdown();
        //sClient.shutdown();
    }


}
