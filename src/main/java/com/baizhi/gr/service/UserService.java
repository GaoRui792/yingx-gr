package com.baizhi.gr.service;

import com.baizhi.gr.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService {
    //分页展示的用户信息
    Map<String,Object> showUserByPage(int rows, int page);
    //用户的增删改操作
    String CUDUser(User user,String oper);
    //文件上传到本地
    void uplocdImage(MultipartFile headImg, String id, HttpServletRequest request);
    //文件上传到阿里云
    void AliyunUplocdImage(MultipartFile headImg, String id);
    //导出用户信息
    String userPoi();
    //统计男生女生每个月的注册人数
    Map<String,Object> queryCount();
}
