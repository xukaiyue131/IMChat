package com.xky.imchat.entity.chat;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//封装消息类
@Data
public class ChatVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String friendId;

    private Integer type;

    private String content;

    private String createTime;
    //用于封装sdp
    private String sdp;
    //信令状态收集
    private String candidate;
}
