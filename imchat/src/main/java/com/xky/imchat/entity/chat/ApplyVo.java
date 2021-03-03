package com.xky.imchat.entity.chat;


import lombok.Data;

import java.io.Serializable;

@Data
public class ApplyVo implements Serializable {
    private static final long serialVersionUID = 1L;
    //申请记录的id
    private String id;
    //申请人的id
    private String applyId;
    private String nickname;
    private String avater;
    private Integer status;
}
