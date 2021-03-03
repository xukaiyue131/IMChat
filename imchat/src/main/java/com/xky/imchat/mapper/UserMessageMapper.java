package com.xky.imchat.mapper;

import com.xky.imchat.entity.UserMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.*;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-01-24
 */
public interface UserMessageMapper extends BaseMapper<UserMessage> {

    public List<UserMessage> findAll(String friendId,String userId);
}
