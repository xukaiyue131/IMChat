package com.xky.imchat.service;

import com.xky.imchat.entity.UserMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xky.imchat.entity.chat.ChatVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-24
 */
public interface UserMessageService extends IService<UserMessage> {

    List<UserMessage> findAll(String userId, String friendId);

    List<UserMessage> getMessage(String friendId);
    //添加离线消息到数据库
    public void add(UserMessage userMessage);


}
