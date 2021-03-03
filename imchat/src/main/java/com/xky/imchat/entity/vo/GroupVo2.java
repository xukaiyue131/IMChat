package com.xky.imchat.entity.vo;
import lombok.Data;

import java.util.*;
@Data
public class GroupVo2 {
    private String nickname;
    List<GroupVO> members;
}
