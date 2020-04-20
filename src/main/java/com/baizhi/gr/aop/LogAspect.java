package com.baizhi.gr.aop;

import com.baizhi.gr.entity.Admin;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;


@Aspect
@Configuration
@Slf4j
public class LogAspect {

    @Autowired
    private HttpSession session;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Around("@annotation(com.baizhi.gr.annotation.CUD)")
    public Object addLog(ProceedingJoinPoint joinPoint){
        /*
        * 日志需要保存的信息:
        * 操作管理员,操作时间,进行了什么操作,是否成功
        * */
        //管理员信息
        Admin admin = (Admin) session.getAttribute("Admin");
        System.out.println("admin = " + admin.getUsername());
        //获取类的全限定名
        String className = joinPoint.getTarget().getClass().getName();
        //操作的方法名
        String methodName = joinPoint.getSignature().getName();
        //获取参数列表
        Object[] args = joinPoint.getArgs();
        Object proceed = null;
        String message = null;
        try {
            proceed = joinPoint.proceed();
            message = "success";
        } catch (Throwable throwable) {
            message = "error";
        }
        /*
         *  将日志信息存储在redis中
         *   以hash的形式进行存储,大键为类的全限定名.方法名.进行的操作
         *   管理员名 值为时间+是否成功
         * */

        //序列化防止乱码
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        //拼接大键,小键和值
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(className);
        stringBuilder.append("."+methodName);
        stringBuilder.append("."+args[1]);
        System.out.println("stringBuilder = " + stringBuilder);

        StringBuilder value = new StringBuilder();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date());
        value.append(date);
        value.append("."+message);
        System.out.println("value = " + value);

        String key = admin.getUsername();
        //添加进入redis
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put(stringBuilder.toString(),key,value.toString());
        return  proceed;
    }
}
