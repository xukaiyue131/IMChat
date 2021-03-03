package com.xky.imchat.entity.chat;

public enum MsgAction {
    CONNECT("0", "表示第一次连接或者重连"),
    CHAT("1", "表示聊天信息");
    public final String type;
    public final String content;


    private MsgAction(String type,String content){
        this.type = type;
        this.content = content;
    }

}
