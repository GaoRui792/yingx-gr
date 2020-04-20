package com.baizhi.gr.controller;

import com.baizhi.gr.entity.User;
import com.baizhi.gr.service.UserService;
import com.baizhi.gr.util.AliyunSendPhoneUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@Controller
@RequestMapping("user")
@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("pageUser")
    public Map<String,Object> pageUser(Integer rows,Integer page){
        /*
        * 用户的分页展示
        * */
        log.info("用户分页的页面 rows ==> {},page ==> {}",rows,page);
        Map<String, Object> pageUser = userService.showUserByPage(rows, page);
        return pageUser;
    }

    @RequestMapping("cud")
    public String CUDUser(User user,String oper){
        /*
        * 用户的增删改操作
        * */
        log.info("user ==> {},oper ==> {}",user,oper);
        String uid = userService.CUDUser(user, oper);
        return uid;
    }

    @RequestMapping("upload")
    public void uploadImage(MultipartFile headImg, String id){
        /*
        * 文件上传
        * */
        userService.AliyunUplocdImage(headImg,id);
    }

    @RequestMapping("sendPhone")
    public String sendPhone(String phone){
        log.info("发送短信验证码phone ==> {}",phone);
        String code = AliyunSendPhoneUtil.randomCode(6);
        log.info("code ==> {}",code);
        String message = AliyunSendPhoneUtil.sendPhone(phone, "应学APP", "SMS_187570809", code);
        return message;
    }

    @RequestMapping("userPoi")
    public String userPoi(){
        log.info("导出用户信息");
        String message = userService.userPoi();
        return message;
    }

    @RequestMapping("userCountByDate")
    public Map<String,Object> userCount(){
        log.info("树状图统计男生女生每个月的注册人数");
        return null;
    }
}
