package com.xky.imchat.interceptor;

import com.xky.imchat.annotation.PassToken;
import com.xky.imchat.entity.vo.Admin;
import com.xky.imchat.util.JwtUtil;
import com.xky.imchat.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class JwtInterceptor implements HandlerInterceptor {
    Logger logger  = LoggerFactory.getLogger(JwtInterceptor.class);
   private  RedisUtil redisUtil;
   public JwtInterceptor(RedisUtil redisUtil){
       this.redisUtil = redisUtil;
   }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求头里面获取token
        String token = request.getHeader("Authorization");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注解
        if(method.isAnnotationPresent(PassToken.class)){
            PassToken annotation = method.getAnnotation(PassToken.class);
            if(annotation.required()){
                return  true;
            }
        }else{
            logger.info("要进行token检验");
            if(token==null){
                logger.info("token无效，需要重新登录");
                return false;
            }

            if(!JwtUtil.verity(token)){

                if(redisUtil.hasKey(token)){
                    response.setStatus(201);
                    String new_token =(String) redisUtil.get(token);
                    //移除原来的缓存
                    redisUtil.delete(token);
                    logger.info(new_token);
                    String refresh = JwtUtil.refresh(new_token);
                    response.setHeader("new_token",refresh);
                    //更新缓存
                    logger.info("开始更新缓存");
                    Admin content = JwtUtil.getContent(new_token);
                    redisUtil.set(content.getUserName(),refresh,900);
                    String access_token = JwtUtil.refresh(content.getUserName(), content.getUserID());
                    System.out.println(redisUtil.hasKey(content.getUserName()));
                    redisUtil.set(refresh,access_token,1800);
                    return true;
                }else{
                    response.setStatus(202);
                    return false;
                }
            }else{
                //token还在有效期，但是其他客户端登录了要退出登录
                Admin content = JwtUtil.getContent(token);

              String new_token = (String)redisUtil.get(content.getUserName());
                System.out.println("开始排挤对方");
                logger.info(token);
                logger.info(new_token);
                System.out.println(new_token.equals(token));
              if(!new_token.equals(token)){
                  logger.info("开始挤掉对方");
                  response.setStatus(203);
                  return false;
              }

            }
        }
        return JwtUtil.verity(token);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
