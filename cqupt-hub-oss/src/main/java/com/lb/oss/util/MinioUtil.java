package com.lb.oss.util;

import com.lb.oss.entity.FileInfo;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MinioUtil {

    @Resource
    private MinioClient minioClient;

    public void createBucket(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!bucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    public void uploadFile(InputStream inputStream, String bucketName, String objectName) throws Exception {
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName)
                .stream(inputStream, -1, Integer.MAX_VALUE).build());
    }

    /**
     * 列出所有桶
     */
    public List<String> getAllBucket() throws Exception {
        List<Bucket> buckets = minioClient.listBuckets();
        return buckets.stream().map(Bucket::name).collect(Collectors.toList());
    }

    /**
     * 列出当前桶及文件
     */
    public List<FileInfo> getAllFile(String bucket) throws Exception {
        // 调用MinIO客户端的listObjects方法列出桶中的对象
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucket).build());

        // 创建一个LinkedList来存储FileInfo对象
        List<FileInfo> fileInfoList = new LinkedList<>();

        for (Result<Item> result : results) {
            // 创建一个FileInfo对象
            FileInfo fileInfo = new FileInfo();

            // 从结果中获取Item对象
            Item item = result.get();

            // 设置文件名
            fileInfo.setFileName(item.objectName());

            // 设置是否是目录
            fileInfo.setDirectoryFlag(item.isDir());

            // 设置ETag值
            fileInfo.setEtag(item.etag());

            // 将FileInfo对象添加到列表中
            fileInfoList.add(fileInfo);
        }

        // 返回FileInfo列表
        return fileInfoList;
    }


    /**
     * 下载文件
     */
    public InputStream downLoad(String bucket, String objectName) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder().bucket(bucket).object(objectName).build()
        );
    }

    /**
     * 删除桶
     */
    public void deleteBucket(String bucket) throws Exception {
        minioClient.removeBucket(
                RemoveBucketArgs.builder().bucket(bucket).build()
        );
    }

    /**
     * 删除文件
     */
    public void deleteObject(String bucket, String objectName) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder().bucket(bucket).object(objectName).build()
        );
    }

}
