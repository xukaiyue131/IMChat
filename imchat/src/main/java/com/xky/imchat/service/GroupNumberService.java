package com.xky.imchat.service;

import com.xky.imchat.entity.GroupNumber;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.*;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-24
 */
public interface GroupNumberService extends IService<GroupNumber> {
    public List<GroupNumber> getAll(String id,String numberId);

}
