package com.xky.imchat.controller;


import com.xky.imchat.entity.UserMessage;
import com.xky.imchat.entity.chat.ChatVo;
import com.xky.imchat.resulttype.Result;
import com.xky.imchat.service.UserMessageService;
import com.xky.imchat.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.*;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-03
 */
@RestController
@RequestMapping("/imchat/user-message")
public class UserMessageController {

    @Autowired
    UserMessageService userMessageService;
    @GetMapping("findAll")
    public Result findAll(String friendId, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String userId = JwtUtil.getAudience(token);

        List<UserMessage> list = userMessageService.findAll(userId,friendId);
        System.out.println(list);
        return Result.success().data("list",list);
    }
}

