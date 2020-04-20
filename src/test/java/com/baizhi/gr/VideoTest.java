package com.baizhi.gr;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.baizhi.gr.dao.CategoryDAO;
import com.baizhi.gr.dao.VideoDAO;
import com.baizhi.gr.entity.Category;
import com.baizhi.gr.entity.Video;
import com.baizhi.gr.vo.VideoCateUserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoTest {


    @Autowired
    private VideoDAO videoDAO;

    @Test
    public void ss(){
        List<VideoCateUserVo> video = videoDAO.selectVideoByTitle("水");
        System.out.println("video = " + video);
    }

    @Test
    //创建Bucket存储空间
    public void createBucket(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        //AccessKey密钥
        String accessKeyId = "LTAI4FjTNK5cUF4UA5nRFbPQ";
        String accessKeySecret = "Sp1QzAN7Ki2Beb9IissTiCdg3AE9Yl";
        //名字重复则不会创建,不存在创建
        String bucketName = "yingx-adssa";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建存储空间。
        ossClient.createBucket(bucketName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    //向空间中上传文件
    public void uploadVideo(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4FjTNK5cUF4UA5nRFbPQ";
        String accessKeySecret = "Sp1QzAN7Ki2Beb9IissTiCdg3AE9Yl";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //存储空间名
        String bucketName = "yingx-gr";
        //指定上传到bucket中的文件名
        String objectName = "image/girl.jpg";
        //指定本地文件路径
        String localFile = "C:\\Users\\A\\Pictures\\壁纸\\girl.jpg";

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new File(localFile));

        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
