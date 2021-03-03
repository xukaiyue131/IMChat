package com.xky.imchat.controller;


import com.xky.imchat.entity.vo.GroupVO;
import com.xky.imchat.entity.vo.GroupVo2;
import com.xky.imchat.resulttype.Result;
import com.xky.imchat.service.UserGroupService;
import com.xky.imchat.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/imchat/user_group")
public class UserGroupController {
    @Autowired
    UserGroupService userGroupService;
    //添加群聊
    @PostMapping("add")
    public Result  add(HttpServletRequest request, @RequestBody GroupVo2 groupVo){
        System.out.println(groupVo);
        String token = request.getHeader("Authorization");
        String userId = JwtUtil.getAudience(token);
        List<GroupVO> members = groupVo.getMembers();
        String nickname = groupVo.getNickname();
        Boolean add = userGroupService.add(userId, members, nickname);
        return add?Result.success():Result.error();
    }
}

