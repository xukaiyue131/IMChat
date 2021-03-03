package com.xky.imchat.core;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xky.imchat.entity.GroupMessage;
import com.xky.imchat.entity.GroupNumber;
import com.xky.imchat.entity.UserMessage;
import com.xky.imchat.entity.chat.ChatVo;
import com.xky.imchat.service.GroupMessageService;
import com.xky.imchat.service.GroupNumberService;
import com.xky.imchat.service.UserMessageService;
import com.xky.imchat.util.UserChannelUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@ChannelHandler.Sharable
@Component
@Slf4j
public class ImHandler extends SimpleChannelInboundHandler<Object> {
    //方便群组
   public  static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    //本类静态对象
   private static ImHandler handler;
   @PostConstruct
   public  void init(){
       handler=this;
   }
    @Autowired
    UserMessageService usermessageservice;

    @Autowired
    GroupNumberService groupNumberService;

    @Autowired
    GroupMessageService groupMessageService;
    
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("有用户连接");
        group.add(ctx.channel());
    }
//检测通道是否活跃
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    //主要逻辑处理
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        //文本消息
        if(msg instanceof TextWebSocketFrame ){
            String message = ((TextWebSocketFrame) msg).text();
            log.info(message);
            ChatVo chat = null;
            try{
                JSONObject Msg;
                //将消息转换成json
                Msg = JSONObject.parseObject(message);
                chat = JSON.toJavaObject(Msg, ChatVo.class);
                System.out.println(chat);

                //-1代表用户刚刚连接，0代表私聊消息，1代表群聊消息
                switch (chat.getType()){
                    case -1 :
                        UserChannelUtil.put(chat.getUserId(),ctx.channel());
                        //将离线之后的聊天消息发给
                        log.info(chat.getUserId()+"登录了");
                        break;
                    case 0 :
                        //看好友是否在线
                        boolean contain = UserChannelUtil.isContain(chat.getFriendId());
                        System.out.println(contain);
                        if(contain){
                            Channel channel = UserChannelUtil.getChannel(chat.getFriendId());
                            String string = JSON.toJSONString(chat);
                            channel.writeAndFlush(new TextWebSocketFrame(string));
                        }else{
                            //好友不在线，将消息保存到数据库,格式化时间
                            System.out.println(1111);
                            UserMessage uMsg = new UserMessage();
                            uMsg.setUserId(chat.getUserId());
                            uMsg.setContent(chat.getContent());
                            uMsg.setFriendId(chat.getFriendId());
                            uMsg.setType(chat.getType());
                            handler.usermessageservice.add(uMsg);
                            log.info("好友不在线插入数据库");
                        }
                        break;
                    case 1 :
                    //群聊消息

                        List<GroupNumber> list = handler.groupNumberService.getAll(chat.getUserId(),chat.getFriendId());//根据该群聊的所有用户
                        System.out.println(list);
                        //转发给除发送者之外的所有用户
                        for(GroupNumber g :list){
                            boolean b = UserChannelUtil.isContain(g.getNumberId());
                            if(b){
                                //用户在线,转发消息
                                log.info("成功转发消息");
                                Channel c = UserChannelUtil.getChannel(g.getNumberId());
                                String s = JSON.toJSONString(chat);
                                System.out.println(s);
                                c.writeAndFlush(new TextWebSocketFrame(s));
                            }else {
                                //消息保存到数据库，用户上线就推送
                                GroupMessage gMsg = new GroupMessage();
                                gMsg.setContent(chat.getContent());
                                gMsg.setGroupId(chat.getFriendId());
                                gMsg.setUserId(g.getNumberId());
                                System.out.println(gMsg);
                                handler.groupMessageService.save(gMsg);
                                log.info("插入数据库成功");
                            }
                        }
                        break;
                    case 2:
                        //视频语音通话
                        //看好友是否在线 video-offer
                       Boolean contains =  UserChannelUtil.isContain(chat.getFriendId());
                       //好友存在则发送video-offer,不在线则告诉用户好友不在线
                       if(contains){
                           Channel channel = UserChannelUtil.getChannel(chat.getFriendId());
                           String string = JSON.toJSONString(chat);
                           channel.writeAndFlush(new TextWebSocketFrame(string));
                       }else{
                           Channel channel = UserChannelUtil.getChannel(chat.getUserId());
                           channel.writeAndFlush(new TextWebSocketFrame("用户不在线"));
                       }
                       break;
                    default:
                        //video_answer
                        Boolean contain3 =  UserChannelUtil.isContain(chat.getFriendId());
                        //好友存在则发送video-offer,不在线则告诉用户好友不在线
                        if(contain3){
                            Channel channel = UserChannelUtil.getChannel(chat.getFriendId());
                            String string = JSON.toJSONString(chat);
                            channel.writeAndFlush(new TextWebSocketFrame(string));
                        }
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //二进制消息
        if(msg instanceof BinaryWebSocketFrame){
            BinaryWebSocketFrame binaryWebSocketFrame = new BinaryWebSocketFrame(Unpooled.buffer().writeBytes("现在还没有实现这个功能".getBytes()));
            ctx.channel().writeAndFlush(binaryWebSocketFrame);
        }
        //ping消息
        if (msg instanceof PongWebSocketFrame){
            log.info("ping成功");
        }
        //关闭消息
        if(msg instanceof CloseWebSocketFrame){
            log.info("客户端关闭");
            ctx.channel().close();
        }
    }

    //异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        group.remove(ctx.channel());
    }
//心跳检测
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if(evt instanceof IdleStateEvent){
                IdleStateEvent event = (IdleStateEvent)evt;
                String type=null;
                switch (event.state()){
                    case READER_IDLE:
                        type="读空闲";
                        break;
                    case WRITER_IDLE:
                        type="写空闲";
                        break;
                    case ALL_IDLE:
                        type="读写空闲";
                }
                if(type.equals("读写空闲")){
                    ctx.channel().close();
                }
            }
    }
}
