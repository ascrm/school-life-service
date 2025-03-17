package com.travel.utils;

import com.travel.common.entity.Result;
import com.travel.common.properties.MinioProperty;
import io.minio.*;
import io.minio.messages.Bucket;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ascrm
 * @Description: (minio工具类)
 */
@Component
@Slf4j
public class MinioUtils {

    @Resource
    private MinioProperty minioProperty;

    @Resource
    private MinioClient minioClient;

    /**
     * 创建Bucket
     */
    @SneakyThrows
    public void createBucket(String bucketName) {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build()
            );
        }
    }


    /**
     * 创建Bucket
     */
    @SneakyThrows
    private void createBucket() {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperty.getBucketName()).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperty.getBucketName())
                    .build()
            );
        }
    }


    /**
     * 判断文件是否存在
     *
     * @param bucketName 存储桶
     * @param objectName 对象
     * @return true：存在
     */
    public  boolean doesObjectExist(String bucketName, String objectName) {
        boolean exist = true;
        try {
            minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build()
            );
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    /**
     * 通过MultipartFile，上传文件
     *
     * @param bucketName 存储桶
     * @param file       文件
     * @param objectName 对象名
     */
    @SneakyThrows
    public ObjectWriteResponse putObject(String bucketName, MultipartFile file, String objectName, String contentType) {
        InputStream inputStream = file.getInputStream();
        return minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .contentType(contentType)
                .stream(inputStream, inputStream.available(), -1)
                .build()
        );
    }

    /**
     * 通过流上传文件
     *
     * @param bucketName  存储桶
     * @param objectName  文件对象
     * @param inputStream 文件流
     */
    @SneakyThrows
    public void putObject(String bucketName, String objectName, InputStream inputStream) {
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(inputStream, inputStream.available(), -1)
                .build()
        );
    }

    /**
     * 上传本地文件
     *
     * @param bucketName 存储桶
     * @param objectName 对象名称
     * @param fileName   本地文件路径
     */
    @SneakyThrows
    public ObjectWriteResponse putObject(String bucketName, String objectName, String fileName) {
        return minioClient.uploadObject(UploadObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .filename(fileName)
                .build()
        );
    }

    /**
     * 创建文件夹或目录
     *
     * @param bucketName 存储桶
     * @param objectName 目录路径
     */
    @SneakyThrows
    public ObjectWriteResponse putDirObject(String bucketName, String objectName) {
        return minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                .build()
        );
    }


    /**
     * @Title: uploadFile
     * @Description: (获取上传文件信息上传文件)
     * [file 上传文件（MultipartFile）, bucketName 桶名]
     */
    public Result<String> uploadFile(MultipartFile file, String bucketName) throws Exception {
        //判断文件是否为空
        if (null == file || 0 == file.getSize()) {
           return Result.fail("上传失败");
        }
        //判断存储桶是否存在,不存在则创建
        createBucket(bucketName);
        //文件名
        String originalFilename = file.getOriginalFilename();
        //新的文件名 = 存储桶文件名_时间戳.后缀名
        String fileName = bucketName + "_" +
                System.currentTimeMillis() +
                originalFilename.substring(originalFilename.lastIndexOf("."));
        //开始上传
        putObject(bucketName, fileName, file.getInputStream());
        return Result.success( bucketName + "/" + fileName);
    }

    /**
     * @Title: getAllBuckets
     * @Description: (获取全部bucket)
     */
    @SneakyThrows
    public List<Bucket> getAllBuckets() {
        return minioClient.listBuckets();
    }

    /**
     * @Title: removeBucket
     * @Description: (根据bucketName删除信息)
     */
    @SneakyThrows
    public void removeBucket(String bucketName){
        minioClient.removeBucket(RemoveBucketArgs.builder()
                .bucket(bucketName)
                .build()
        );
    }

    /**
     * @param expires    过期时间 <=7
     * @Title: getObjectURL
     * @Description: (获取文件外链)
     */
    @SneakyThrows
    public String getObjectUrl(String bucketName, String objectName, Integer expires) {
        // 设置过期时间，默认为 7 天
        int expiryTime = (expires != null && expires > 0) ? expires : 7;

        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .expiry(expiryTime, TimeUnit.DAYS)
                .build()
        );
    }

    /**
     * @Title: getObject
     * @Description: (获取文件)
     */
    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName){
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build()
        );
    }

    /**
     * @Title: getObjectInfo
     * @Description: (获取文件信息)
     */
    public StatObjectResponse getObjectInfo(String bucketName, String objectName) throws Exception {
        return minioClient.statObject(StatObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build()
        );
    }

    /**
     * @Title: removeObject
     * @Description: (删除文件)
     */
    public void removeObject(String bucketName, String objectName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build()
        );
    }
}