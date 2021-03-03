package com.xky.imchat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xky.imchat.entity.GroupNumber;
import com.xky.imchat.mapper.GroupNumberMapper;
import com.xky.imchat.service.GroupNumberService;
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
public class GroupNumberServiceImpl extends ServiceImpl<GroupNumberMapper, GroupNumber> implements GroupNumberService {

    @Override
    public List<GroupNumber> getAll(String id,String numberId) {
        //根据群聊id查询所有用户

        List<GroupNumber> list = baseMapper.get(id,numberId);
        return list;
    }
}
