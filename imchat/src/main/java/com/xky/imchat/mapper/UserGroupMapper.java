package com.xky.imchat.mapper;

import com.xky.imchat.entity.UserGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-01-24
 */
public interface UserGroupMapper extends BaseMapper<UserGroup> {
    public List<UserGroup> getGroup(String id);
}
