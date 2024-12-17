package com.lb.oss.service;

import com.lb.oss.adapter.StorageAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileService {

    private final StorageAdapter storageAdapter;

    public FileService(StorageAdapter storageAdapter) {
        this.storageAdapter = storageAdapter;
    }

    /**
     * 列出所有桶
     */
    public List<String> getAllBucket() throws Exception {
        return storageAdapter.getAllBucket();
    }

    /**
     * 获取文件路径
     */
    public String getUrl(String bucketName,String objectName) throws Exception {
        return storageAdapter.getUrl(bucketName,objectName);
    }

    /**
     * 上传文件
     */
    public String uploadFile(MultipartFile uploadFile, String bucket, String objectName) throws Exception {
        // 调用storageAdapter的uploadFile方法上传文件
        storageAdapter.uploadFile(uploadFile, bucket, objectName);

        // 拼接新的objectName，包括原始的文件名
        objectName = objectName + "/" + uploadFile.getOriginalFilename();

        // 调用storageAdapter的getUrl方法获取文件的URL
        return storageAdapter.getUrl(bucket, objectName);
    }

}
