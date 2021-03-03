package com.xky.imchat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xky.imchat.entity.Friend;
import com.xky.imchat.entity.User;
import com.xky.imchat.entity.UserGroup;
import com.xky.imchat.entity.vo.FriendVo;
import com.xky.imchat.mapper.FriendMapper;
import com.xky.imchat.service.FriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xky.imchat.service.UserGroupService;
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
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {

    @Autowired
    UserService service;
    @Autowired
    UserGroupService userGroupService;
    //获取好友列表（带有群组）
    @Override
    public List<FriendVo> getAllFriend(String id) {
        List<FriendVo> list = new ArrayList<>();
        QueryWrapper<Friend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",id);
        List<Friend> friends = baseMapper.selectList(queryWrapper);
        for(Friend f :friends){
            FriendVo friend = new FriendVo();
            User user = service.getById(f.getFriendId());
            BeanUtils.copyProperties(user,friend);
            BeanUtils.copyProperties(f,friend);
            list.add(friend);
        }
        List<UserGroup> groups = userGroupService.get(id);
        for(UserGroup user:groups){
            FriendVo f = new FriendVo();
            BeanUtils.copyProperties(user,f);
            f.setFriendId(user.getId());
            list.add(f);
        }

            return list;
    }
//获取好友列表（没有群组）
    @Override
    public List<FriendVo> findFriend(String id) {
        List<FriendVo> list = new ArrayList<>();
        QueryWrapper<Friend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",id);
        List<Friend> friends = baseMapper.selectList(queryWrapper);
        for(Friend f :friends){
            FriendVo friend = new FriendVo();
            User user = service.getById(f.getFriendId());
            BeanUtils.copyProperties(user,friend);
            BeanUtils.copyProperties(f,friend);
            list.add(friend);
        }
        return list;
    }

    @Override
    public Boolean ExistFriend(String userId, String friendId) {
        QueryWrapper<Friend> query = new QueryWrapper<>();
        query.eq("user_id",userId);
        query.eq("friend_id",friendId);
        Friend friend = baseMapper.selectOne(query);
        if(friend!=null){
            return false;
        }else {
            return true;
        }

    }
    //查询是否存在好友关系

}
