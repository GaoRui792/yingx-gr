package com.baizhi.gr.serviceimpl;

import com.baizhi.gr.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class LogServiceimpl implements LogService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, String> pageLog() {
        return null;
    }
}
