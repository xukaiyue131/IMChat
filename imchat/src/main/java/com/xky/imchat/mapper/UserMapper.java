package com.xky.imchat.mapper;

import com.xky.imchat.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-02-03
 */
public interface UserMapper extends BaseMapper<User> {
        User Search(String content);
}
