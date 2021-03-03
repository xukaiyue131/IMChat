package com.xky.imchat.entity.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class FriendVo {
    private String friendId;
    private String avater;
    private String content;
    private String nickname;
    private Integer number;
}
