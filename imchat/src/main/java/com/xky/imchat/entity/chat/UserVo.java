package com.xky.imchat.entity.chat;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String UserId;
    private String friendId;
    private String groupId;
    private String content;
    private String nickname;
    private Integer num;
}
