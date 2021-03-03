package com.xky.imchat.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xky.imchat.entity.Apply;
import com.xky.imchat.entity.chat.ApplyVo;
import com.xky.imchat.resulttype.Result;
import com.xky.imchat.service.ApplyService;
import com.xky.imchat.service.UserService;
import com.xky.imchat.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import java.util.*;
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
@RequestMapping("/imchat/apply")
public class ApplyController {
    Logger logger = LoggerFactory.getLogger(ApplyController.class);
    @Autowired
    ApplyService service;

    @GetMapping("add")
    public Result add(String id, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String applyId = JwtUtil.getAudience(token);
        //将申请记录放到数据库
        logger.info(id+"被人申请添加为好友");
        Apply apply = new Apply();
        apply.setApplyId(applyId);
        apply.setTargetId(id);
        boolean save = service.save(apply);
        return save?Result.success():Result.error();
    }

    //显示当前用户的申请记录
    @GetMapping("getApply")
    public Result getApply(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String id = JwtUtil.getAudience(token);
        List<ApplyVo> list= service.getAll(id);
        return Result.success().data("list",list);
    }
    //修改申请记录的状态
    @GetMapping("accept")
    public Result accept(String id,String status,String applyId,HttpServletRequest request){
        //根据id查询记录并修改记录的状态
        int s = Integer.parseInt(status);
        String token = request.getHeader("Authorization");
        String userId = JwtUtil.getAudience(token);
        service.acceptApply(id,s,applyId,userId);
        return Result.success();
    }
    //拒绝好友申请
    @GetMapping("reject")
    public Result reject(String id,String status){
        int d = Integer.parseInt(status);
        Apply byId = service.getById(id);
        byId.setStatus(d);
        service.updateById(byId);
        return  Result.success();
    }
    //查询用户是否已经申请好友了
    @GetMapping("search")
    public Result search(String id,HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String applyId = JwtUtil.getAudience(token);
        QueryWrapper<Apply> query = new QueryWrapper<>();
        query.eq("target_id",id);
        query.eq("apply_id",applyId);
        Apply one = service.getOne(query);
        return Result.success().data("apply",one);
    }
}

