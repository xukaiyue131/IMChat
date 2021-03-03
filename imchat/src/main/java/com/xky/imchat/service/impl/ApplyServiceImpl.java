package com.xky.imchat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xky.imchat.entity.Apply;
import com.xky.imchat.entity.Friend;
import com.xky.imchat.entity.User;
import com.xky.imchat.entity.chat.ApplyVo;
import com.xky.imchat.mapper.ApplyMapper;
import com.xky.imchat.service.ApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xky.imchat.service.FriendService;
import com.xky.imchat.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-24
 */
@Service
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, Apply> implements ApplyService {
    @Autowired
    UserService userService;

    @Autowired
    FriendService friendService;

    @Override
    public List<ApplyVo> getAll(String id) {
        QueryWrapper<Apply> query = new QueryWrapper<>();
        query.eq("target_id",id);
        query.orderByDesc("create_time");
        List<Apply> applies = baseMapper.selectList(query);
        List<ApplyVo> list = new ArrayList<>();
        //封装申请类
        for(Apply apply:applies){
            ApplyVo applyVo = new ApplyVo();
            User user = userService.getById(apply.getApplyId());
            BeanUtils.copyProperties(user,applyVo);
            BeanUtils.copyProperties(apply,applyVo);
            list.add(applyVo);
        }
        return list;
    }
//需要添加事务
    @Override
    public Boolean acceptApply(String id, Integer status, String applyId,String userId) {
        //修改申请记录的状态
        Apply apply = baseMapper.selectById(id);
        apply.setStatus(status);
       baseMapper.updateById(apply);
        //添加好友
        Friend friend = new Friend();
        Friend f = new Friend();
        friend.setUserId(userId);
        friend.setFriendId(applyId);
        friendService.save(friend);
        f.setFriendId(userId);
        f.setUserId(applyId);
        friendService.save(f);
        return true;
    }


}
