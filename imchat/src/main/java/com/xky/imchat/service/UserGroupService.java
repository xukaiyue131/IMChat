package com.xky.imchat.service;

import com.xky.imchat.entity.UserGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xky.imchat.entity.vo.GroupVO;

import java.util.*;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-24
 */
public interface UserGroupService extends IService<UserGroup> {

    public List<UserGroup> get(String id);
    public Boolean add(String id, List<GroupVO> list,String nickname);
}
