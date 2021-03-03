package com.xky.imchat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xky.imchat.entity.User;
import com.xky.imchat.mapper.UserMapper;
import com.xky.imchat.service.FriendService;
import com.xky.imchat.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xky.imchat.util.JwtUtil;
import com.xky.imchat.util.RedisUtil;
import org.apache.commons.codec.digest.DigestUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    FriendService friendService;
    @Autowired
    RedisUtil redisUtil;
    @Override
    public String login(String email, String password) {
        QueryWrapper<User> query  = new QueryWrapper<>();
        query.eq("password",DigestUtils.md5Hex(password));
        query.eq("email",email);
        User user = baseMapper.selectOne(query);
        if(user==null){
            return null;
        }
        System.out.println(redisUtil.hasKey(user.getNickname())+"该用户还没有登录");
        String token = JwtUtil.sign(user.getNickname(), user.getId());
        String refresh = JwtUtil.refresh(user.getNickname(), user.getId());
        boolean set = redisUtil.set(user.getNickname(), token, 900);
        System.out.println(set+"redis保存成功");
        System.out.println(redisUtil.getExpire(user.getNickname()));
        redisUtil.set(token,refresh,1800);
        return token;
    }

    @Override
    public User Search(String content,String id) {
        User user = baseMapper.Search(content);
        return user;
    }
}
