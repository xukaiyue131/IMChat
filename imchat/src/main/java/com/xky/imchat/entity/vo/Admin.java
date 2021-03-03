package com.xky.imchat.entity.vo;

import lombok.Data;


import java.io.Serializable;

@Data
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userID;

    private String userName;
}
