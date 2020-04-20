package com.baizhi.gr.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Aspect
@Slf4j
public class RedisAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Around("@annotation(com.baizhi.gr.annotation.Select)")
    public Object insertCache(ProceedingJoinPoint proceedingJoinPoint){
        /*
        *   环绕通知
        *     在进行查询操作前先查看缓存中是否存在需要查询的数据
        *     如果存在从缓存中获取数据,不存在则放行进行查询操作,并将查询到的数据添加进缓存中
        * */
        //1.序列化解决乱码问题
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        /*
        *  缓存方式采用hash类型
        *  以双键值对的形式存在  大键  小键和值
        *  大键负责存放类的全限定名  小键存放方法名和参数(实参,以确保所拿数据的准确性)  值存放该方法的返回值
        * */

        //2.获取类的全限定名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        //3.获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        //4.获取参数
        Object[] args = proceedingJoinPoint.getArgs();
        //5.拼接方法名和参数
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(methodName);
        for (Object arg : args) {
            stringBuilder.append(arg);
        }
        //6.判断大键和小键是否存在
        String key = stringBuilder.toString();
        HashOperations hashOperations = redisTemplate.opsForHash();
        Boolean hasKey = hashOperations.hasKey(className,key);
        Object result = null;
        if (hasKey){
            //7.说明存在,那么数据从缓存中获取
            result = hashOperations.get(className, key);
        }else{
            //7.不存在,放行,并获取查询方法的返回值
            try {
                result = proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            //8.将获取到的返回值添加进缓存
            hashOperations.put(className,key,result);
        }
        return result;
    }

    @After("@annotation(com.baizhi.gr.annotation.CUD)")
    public void deleteCache(JoinPoint joinPoint){
        /*
        *   进行增删改操作之后,数据发生改变,所以需要清空缓存的数据
        * */
        //1.获取操作的类的全限定名
        String className = joinPoint.getTarget().getClass().getName();
        log.info("删除缓存.类的全限定名 ==> {}",className);

        //2.删除该类下缓存的数据
        redisTemplate.delete(className);
    }
}
