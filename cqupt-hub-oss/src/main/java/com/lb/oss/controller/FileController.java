package com.lb.oss.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.lb.oss.service.FileService;
import com.lb.oss.util.MinioUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class FileController {

    @Resource
    private FileService fileService;

    @Value(value="${storage.service.type}")
    private String storageType;

    @RequestMapping("/testGetAllBuckets")
    public String testGetAllBuckets() throws Exception {
        List<String> allBucket = fileService.getAllBucket();
        return allBucket.get(0);
    }

    @PostMapping("/testNacos")
    public String testNacos() throws Exception {
        System.out.println("storageType:" + storageType);
        return storageType;
    }

    @RequestMapping("/getUrl")
    public String getUrl(String bucketName,String objectName) throws Exception {
        return fileService.getUrl(bucketName,objectName);
    }

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    public String upload(MultipartFile uploadFile, String bucket, String objectName) throws Exception {
        return fileService.uploadFile(uploadFile, bucket, objectName);
    }

}
