package com.xky.imchat.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CannedAccessControlList;
import com.xky.imchat.util.OssUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class ImageServiceImpl implements  ImageService {
    @Override
    public String upload(MultipartFile file) {
        String endpoint = OssUtil.ENDPOINT;
        String key_id = OssUtil.KEYID;
        String buckName = OssUtil.BUCKNAME;
        String secret = OssUtil.KEYSECRET;
        String uploadUrl="";
        try{
            //初始化上传的客户端
            OSSClient client = new OSSClient(endpoint,key_id,secret);
            if(!client.doesBucketExist(buckName)){
                client.createBucket(buckName);
                client.setBucketAcl(buckName, CannedAccessControlList.PublicRead);
            }
            InputStream inputStream = file.getInputStream();
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String filename = file.getOriginalFilename();
            filename=uuid+filename;
            String time = new DateTime().toString("yyyy/MM/dd");
            filename = time+"/"+filename;
            //将图片上传到阿里云
            client.putObject(buckName,filename,inputStream);
            client.shutdown();
            uploadUrl = "https://"+buckName+"."+endpoint+"/"+filename;
            return uploadUrl;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
