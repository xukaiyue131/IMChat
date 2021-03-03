package com.xky.imchat.service.impl;

import com.xky.imchat.entity.GroupNumber;
import com.xky.imchat.entity.UserGroup;
import com.xky.imchat.entity.vo.GroupVO;
import com.xky.imchat.mapper.UserGroupMapper;
import com.xky.imchat.service.GroupNumberService;
import com.xky.imchat.service.UserGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements UserGroupService {

    @Autowired
    GroupNumberService service;
    @Override
    public List<UserGroup> get(String id) {
        List<UserGroup> groups = baseMapper.getGroup(id);
        return groups;
    }

    //添加群聊,需要添加事务
    @Override
    public Boolean add(String id, List<GroupVO> list,String nickname) {
        UserGroup group = new UserGroup();
        int number = list.size();
        group.setNickname(nickname);
        group.setNumber(number);
        baseMapper.insert(group);
        String groupId = group.getId();
        System.out.println(groupId);
        List<GroupNumber> l = new ArrayList<>();
        for(GroupVO groupvo : list){
            GroupNumber groupNumber = new GroupNumber();
            groupNumber.setId(groupId);
            groupNumber.setNumberId(groupvo.getId());
            groupNumber.setContent(groupvo.getNickname());
            if(groupvo.getId().equals(id)){
                groupNumber.setLevel(2);
            }
            l.add(groupNumber);
        }
        boolean b = service.saveBatch(l);
        return b;
    }
}
