package com.xky.imchat.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xky.imchat.entity.vo.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Stack;

public class JwtUtil {

    //设置过期时间
    private static final long EXPIRE_TIME = 15*60*1000;
    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    //token私钥，尽量还是不统一为好
    private static  final  String TOKEN_SECRET = "8ae0d24822ef59d9e75745449b3501bc";
//    生成签名，有效时间为15分钟
    public static  String sign(String username,String userId){
        //设置过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //私钥加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        //设置头信息
        HashMap<String,Object> header= new HashMap<>(2);
        header.put("type","jwt");
        header.put("alg","HS256");
        return JWT.create()
                .withAudience(userId)
                .withClaim("username",username)
                .withClaim("userId",userId)
                .withExpiresAt(date)
                .sign(algorithm);
    }

//校验token是否正确
    public static  boolean verity(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier build = JWT.require(algorithm).build();
            DecodedJWT verify = build.verify(token);
            return true;
        }catch (Exception e){
            return  false;
        }
    }

    //获取签发对象
    public static  String getAudience(String token){
        String audience = null;
        try{
            audience = JWT.decode(token).getAudience().get(0);
        }catch (Exception e){

        }
        return  audience;
    }

    //获取载荷的内容
    public static Claim getClaimByName(String token,String name){
        return  JWT.decode(token).getClaim(name);
    }

    //提取相应的内容
    public static Admin getContent(String token){
      try{
          Algorithm algorithm=Algorithm.HMAC256(TOKEN_SECRET);
          JWTVerifier verifier=JWT.require(algorithm).build();
          DecodedJWT jwt=verifier.verify(token);
         Admin a = new Admin();
         a.setUserID(jwt.getClaim("userId").asString());
         a.setUserName(jwt.getClaim("username").asString());
          System.out.println(a);
         return a;
      }catch (Exception e){
          e.printStackTrace();
          logger.info("token出错了");
      }
      return null;
    }
//刷新token对象
    public static  String refresh(String username,String userId){
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME*2);
        //私钥加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        //设置头信息
        HashMap<String,Object> header= new HashMap<>(2);
        header.put("type","jwt");
        header.put("alg","HS256");
        return JWT.create()
                .withAudience(userId)
                .withClaim("username",username)
                .withClaim("userId",userId)
                .withExpiresAt(date)
                .sign(algorithm);
    }
    public static String refresh(String token){
        Admin content = JwtUtil.getContent(token);
        String refresh = JwtUtil.sign(content.getUserName(), content.getUserID());
        return refresh;
    }
}
