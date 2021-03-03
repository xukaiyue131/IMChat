package com.xky.imchat.service;

import com.xky.imchat.entity.Apply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xky.imchat.entity.chat.ApplyVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-24
 */
public interface ApplyService extends IService<Apply> {

    List<ApplyVo> getAll(String id);
    Boolean acceptApply(String id,Integer status,String applyId,String userId);
}
