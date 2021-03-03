package com.xky.imchat.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;

//redis工具类
@Component
public class RedisUtil {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    //根据key获取过期时间
    public long getExpire(String key){
        return redisTemplate.getExpire(key);
    }
    //判断key是否存在
    public boolean hasKey(String key){
        try{
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    //删除缓存
    @SuppressWarnings("unchecked")
    public void delete(String...key){
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    //缓存获取
    public Object get(String key){
        return  key==null?null:redisTemplate.opsForValue().get(key);
    }

    //缓存放入
    public boolean set(String key,Object value){
        try{
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //放入缓存并设置时间
    public boolean set(String key,Object value,long time){
        try{
            if(time>0){
                redisTemplate.opsForValue().set(key,value,time, TimeUnit.SECONDS);
            }else{
                set(key,value);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
