package com.xky.imchat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xky.imchat.entity.User;
import com.xky.imchat.entity.UserMessage;
import com.xky.imchat.entity.chat.ChatVo;
import com.xky.imchat.mapper.UserMessageMapper;
import com.xky.imchat.service.UserMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements UserMessageService {

    @Override
    public List<UserMessage> findAll(String userId, String friendId) {
        //预留给redis缓存聊天记录
        List<UserMessage> list = baseMapper.findAll(friendId, userId);
        return list;
    }

    @Override
    public List<UserMessage> getMessage(String friendId) {
        QueryWrapper<UserMessage> query = new QueryWrapper<>();
        query.eq("friend_id",friendId);
        System.out.println("不知道为什么空指针"+friendId);
        List<UserMessage> list = baseMapper.selectList(query);
        return list;
    }

    @Override
    public void add(UserMessage userMessage) {
        baseMapper.insert(userMessage);
    }
}
