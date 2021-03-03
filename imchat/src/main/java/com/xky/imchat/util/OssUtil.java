package com.xky.imchat.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OssUtil implements InitializingBean {
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String key_id;

    @Value("${aliyun.oss.file.keysecret}")
    private String secret;
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    public static  String ENDPOINT;
    public static  String KEYID;
    public static  String KEYSECRET;
    public static  String BUCKNAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT=this.endpoint;
        KEYID = this.key_id;
        KEYSECRET = this.secret;
        BUCKNAME = this.bucketName;
    }
}
