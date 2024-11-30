package com.lb.oss.adapter;

import com.lb.oss.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface StorageAdapter {

    /**
     * 创建bucket桶
     */
    void createBucket(String bucket) throws Exception;

    /**
     * 上传文件
     */
    void uploadFile(MultipartFile uploadFile, String bucket, String objectName) throws Exception;

    /**
     * 列出所有桶
     */
    List<String> getAllBucket() throws Exception;

    /**
     * 列出当前桶及文件
     */
    List<FileInfo> getAllFile(String bucket) throws Exception;

    /**
     * 下载文件
     */
    InputStream downLoad(String bucket, String objectName) throws Exception;

    /**
     * 删除桶
     */
    void deleteBucket(String bucket) throws Exception;

    /**
     * 删除文件
     */
    void deleteObject(String bucket, String objectName) throws Exception;


}
