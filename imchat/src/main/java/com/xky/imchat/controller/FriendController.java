package com.xky.imchat.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xky.imchat.entity.Friend;
import com.xky.imchat.entity.vo.FriendVo;
import com.xky.imchat.resulttype.Result;
import com.xky.imchat.service.FriendService;
import com.xky.imchat.util.JwtUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-03
 */
@RestController
@RequestMapping("/imchat/friend")
public class FriendController {
    private static Logger logger =  LoggerFactory.getLogger(FriendController.class);
    @Autowired
    FriendService friendService;
//返回该用户的所有好友,包括群聊
    @GetMapping("findAll")
    public Result findAll(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String Id = JwtUtil.getAudience(token);
        List<FriendVo> list = friendService.getAllFriend(Id);
        return Result.success().data("list",list);
    }

    //删除好友
    @GetMapping("delete")
    public Result delete(String id){
        boolean b = friendService.removeById(id);
        return b?Result.success():Result.error();
    }
    //返回用户的所有好友，不包括群聊
    @GetMapping("findfriend")
    public Result findFriend(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String Id = JwtUtil.getAudience(token);
        List<FriendVo> friend = friendService.findFriend(Id);
        return Result.success().data("list",friend);
    }
    //批量删除好友
    @PostMapping("BatchDelete")
    public Result BatchDelete(List<String> list){
        boolean b = friendService.removeByIds(list);
        return b?Result.success():Result.error();
    }
}

