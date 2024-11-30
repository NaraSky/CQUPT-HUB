package com.lb.oss.adapter;

import com.lb.oss.entity.FileInfo;
import com.lb.oss.util.MinioUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

public class MinioStorageAdapter implements StorageAdapter {

    @Resource
    private MinioUtil minioUtil;

    @Override
    public void createBucket(String bucket) throws Exception {
        minioUtil.createBucket(bucket);
    }

    @Override
    public void uploadFile(MultipartFile uploadFile, String bucket, String objectName) throws Exception {
        minioUtil.createBucket(bucket);
        if (objectName != null) {
            minioUtil.uploadFile(uploadFile.getInputStream(), bucket, objectName + "/" + uploadFile.getName());
        } else {
            minioUtil.uploadFile(uploadFile.getInputStream(), bucket, uploadFile.getName());
        }
    }

    @Override
    public List<String> getAllBucket() throws Exception {
        return minioUtil.getAllBucket();
    }

    @Override
    public List<FileInfo> getAllFile(String bucket) throws Exception {
        return minioUtil.getAllFile(bucket);
    }

    @Override
    public InputStream downLoad(String bucket, String objectName) throws Exception {
        return minioUtil.downLoad(bucket, objectName);
    }

    @Override
    public void deleteBucket(String bucket) throws Exception {
        minioUtil.deleteBucket(bucket);
    }

    @Override
    public void deleteObject(String bucket, String objectName) throws Exception {
        minioUtil.deleteObject(bucket, objectName);
    }

}
