package com.baizhi.gr;

import com.baizhi.gr.dao.AdminDAO;
import com.baizhi.gr.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminTest {

    @Autowired
    private AdminDAO adminDAO;


    @Test
    public void selectAll(){
        /*Admin admin = new Admin();*/
        Example example = new Example(Admin.class);
        example.createCriteria().andBetween("id","1","5");
        List<Admin> admins = adminDAO.selectByExample(example);
        /*admin.setPassword("123456");
        admin = adminDAO.selectOne(admin);*/
        admins.forEach(admin -> System.out.println("admin = " + admin));

    }
}
