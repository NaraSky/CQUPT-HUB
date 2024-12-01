package com.lb.oss.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.lb.oss.service.FileService;
import com.lb.oss.util.MinioUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
