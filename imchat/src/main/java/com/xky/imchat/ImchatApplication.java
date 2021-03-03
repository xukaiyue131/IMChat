package com.xky.imchat;

import com.xky.imchat.core.ImServer;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Component;

@SpringBootApplication
@MapperScan("com.xky.imchat.mapper")
public class ImchatApplication {

    @Autowired
    ImServer server;
    public static void main(String[] args) {
        SpringApplication.run(ImchatApplication.class, args);
    }

    private Logger logger = LoggerFactory.getLogger(ImchatApplication.class);


}
