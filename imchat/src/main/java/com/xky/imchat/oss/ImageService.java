package com.xky.imchat.oss;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    public String upload(MultipartFile file);
}
