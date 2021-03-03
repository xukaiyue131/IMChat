package com.xky.imchat.controller;


import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Equivalence;
import com.xky.imchat.annotation.PassToken;
import com.xky.imchat.entity.User;
import com.xky.imchat.oss.ImageService;
import com.xky.imchat.resulttype.Result;
import com.xky.imchat.service.FriendService;
import com.xky.imchat.service.UserService;
import com.xky.imchat.util.JwtUtil;
import com.xky.imchat.util.RedisUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @author ky_x
 * @since 2021-01-24
 */

@RestController
@RequestMapping("/imchat/user")
public class UserController {
    private static Logger logger  = LoggerFactory.getLogger(UserController.class);
    @Autowired
    ImageService service;
    @Autowired
    UserService userservice;
    @Autowired
    FriendService friendService;
//
//    @Autowired
//    private RedisTemplate<Object,Object> redisTemplate;
    //注册
    @PassToken
    @PostMapping("register")
    public Result register(@RequestBody User user){
            String number = UUID.randomUUID().toString().replace("-","");
            number = number.substring(20);
            user.setAccount(number);
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            boolean save = userservice.save(user);
            logger.info("名为"+user.getNickname()+"注册到系统中了");
        return save?Result.success():Result.error();
    }
//登录
    @PassToken
    @GetMapping("login")
    public Result login( String email, String password){

          String login =  userservice.login(email, password);


        return login==null? Result.error().data("error","用户不存在"):Result.success().data("token",login);
    }

    //上传头像
    @PassToken
    @PostMapping("image")
    public Result upload(MultipartFile file){
        String upload = service.upload(file);
        return Result.success().data("url",upload);
    }


    //获取用户信息
    @GetMapping("getUser")
    public Result getUser(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String id = JwtUtil.getAudience(token);
        User user = userservice.getById(id);
        return  Result.success().data("user",user);
    }
//根据id得到用户的信息
    @GetMapping("getFriend")
    public Result getFriend(String id){
        User user = userservice.getById(id);
        return Result.success().data("friend",user);
    }
    //更改用户信息
    @PostMapping("update")
    public Result updateUser(@RequestBody User user){
        boolean b = userservice.updateById(user);
        return b? Result.success():Result.error();
    }

    //根据邮箱或者账号搜索用户
    @GetMapping("search")
    public Result search(String content,HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String id = JwtUtil.getAudience(token);
        User search = userservice.Search(content,id);
        System.out.println(friendService.ExistFriend(id,search.getId()));
        return friendService.ExistFriend(id,search.getId())?Result.success().data("user",search):Result.success().data("user",search).data("status",1);
    }
}
