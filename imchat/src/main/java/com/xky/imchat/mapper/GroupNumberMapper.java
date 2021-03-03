package com.xky.imchat.mapper;

import com.xky.imchat.entity.GroupNumber;
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
public interface GroupNumberMapper extends BaseMapper<GroupNumber> {
    //获取群聊中的成员
    public List<GroupNumber> get(String id,String numberId);

}
