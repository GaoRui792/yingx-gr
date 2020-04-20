package com.baizhi.gr;

import com.baizhi.gr.dao.AdminDAO;
import com.baizhi.gr.dao.UserDAO;
import com.baizhi.gr.entity.Admin;
import com.baizhi.gr.entity.User;
import com.baizhi.gr.vo.UserCountVo;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserDAO userDAO;


    @Test
    public void pageUser(){
        User user = new User();
        user.setId("2");
        user.setPhone("121");
        int i = userDAO.updateByPrimaryKeySelective(user);
        System.out.println(i);
    }

    @Test
    public void selectAll(){
        List<UserCountVo> userCountVos = userDAO.selectUserByDate("女");
        for (UserCountVo userCountVo : userCountVos) {
            System.out.println("userCountVo = " + userCountVo);
        }
    }
}
