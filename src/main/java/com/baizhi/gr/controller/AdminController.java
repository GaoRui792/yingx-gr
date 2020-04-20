package com.baizhi.gr.controller;

import com.baizhi.gr.entity.Admin;
import com.baizhi.gr.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("admin")
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    @ResponseBody
    public HashMap<String,String> adminLogin(Admin admin, String enCode){
        log.info("adminLogin的登录页面");
        log.info("admin ==> {}",admin);
        HashMap<String, String> login = adminService.login(admin, enCode);
        return login;
    }

    @RequestMapping("exit")
    public String AdminExit(HttpServletRequest request){
        log.info("退出登录页面");
        request.getSession().removeAttribute("Admin");
        return "redirect:/login/login.jsp";
    }


}
