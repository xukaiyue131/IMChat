package com.xky.imchat.service;

import com.xky.imchat.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.*;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-02-03
 */
public interface UserService extends IService<User> {
        public String login(String email,String password);
        public User Search(String content,String id);
}
