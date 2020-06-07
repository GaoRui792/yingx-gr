package com.baizhi.gr;

import com.baizhi.gr.dao.AdminDAO;
import com.baizhi.gr.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminTest {

    @Autowired
    private AdminDAO adminDAO;

    @Autowired
    private RedisTemplate redisTemplate;


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

    @Test
    public void testRedis(){
        HashOperations hashOperations = redisTemplate.opsForHash();
        //hashOperations.put("test","time","123");
        redisTemplate.expire("test",30, TimeUnit.DAYS);
        /*List test = hashOperations.values("test");
        System.out.println("test = " + test);*/
        Long test = redisTemplate.getExpire("test");
        System.out.println("test = " + test);

    }

}
