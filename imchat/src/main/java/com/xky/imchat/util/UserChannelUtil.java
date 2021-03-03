package com.xky.imchat.util;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserChannelUtil {

    //将用户id和channel连接
    private static Map<String, Channel> online = new ConcurrentHashMap<>();

    //添加
    public static void put(String id,Channel channel){
        online.put(id,channel);
    }

    //获取channel
    public static  Channel getChannel(String id){
        System.out.println(online.get(id));
        return online.get(id);
    }

    //判断用户是否在线
    public static boolean isContain(String id){
        return online.containsKey(id);
    }

}
