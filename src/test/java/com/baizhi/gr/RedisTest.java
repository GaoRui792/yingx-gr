package com.baizhi.gr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void redis(){
        ListOperations<String, String> list = stringRedisTemplate.opsForList();
        // list.leftPushAll("name","xiaohei","xiaobai");
        List<String> names = list.range("name", 0, -1);
        for (String name : names) {
            System.out.println("name = " + name);
        }
    }
}
