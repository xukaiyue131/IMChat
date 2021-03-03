package com.xky.imchat.service;

import com.xky.imchat.entity.Friend;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xky.imchat.entity.vo.FriendVo;
import net.sf.jsqlparser.statement.select.First;

import java.util.*;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-24
 */
public interface FriendService extends IService<Friend> {
    public List<FriendVo> getAllFriend(String id);
    public List<FriendVo> findFriend(String id);
    public Boolean ExistFriend(String userId,String friendId);
}
