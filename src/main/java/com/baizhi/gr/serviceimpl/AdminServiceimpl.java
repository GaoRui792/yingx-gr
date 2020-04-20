package com.baizhi.gr.serviceimpl;

import com.baizhi.gr.dao.AdminDAO;
import com.baizhi.gr.entity.Admin;
import com.baizhi.gr.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;
import java.util.HashMap;


@Service
@Transactional
@Slf4j
public class AdminServiceimpl implements AdminService {

    @Autowired
    private AdminDAO adminDAO;

    @Autowired
    private HttpSession session;

    @Override
    public HashMap<String, String> login(Admin admin, String enCode) {
        //取出存在session中的验证码
        String securityCode = (String) session.getAttribute("loginCode");
        HashMap<String, String> hashMap = new HashMap<>();
        //判断验证码是否正确
        if (securityCode.equals(enCode)){
            //根据用户名查询用户
            Admin admin1 = new Admin();
            admin1.setUsername(admin.getUsername());
            admin1 = adminDAO.selectOne(admin1);
            log.info("AdminService.admin ==> {}",admin);
            if (admin1 != null){
                if (admin.getPassword().equals(admin1.getPassword())){
                    session.setAttribute("Admin",admin1);
                    hashMap.put("status","200");
                }else{
                    hashMap.put("status","400");
                    hashMap.put("message","密码错误");
                }
            }else{
                hashMap.put("status","400");
                hashMap.put("message","用户不存在");
            }
        }else {
            hashMap.put("status","400");
            hashMap.put("message","验证码输入错误");
        }
        return hashMap;
    }
}
