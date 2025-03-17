package com.travel.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.utils.MinioUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.travel.web.service.ImagesService;
import com.travel.entity.Images;
import com.travel.web.mapper.ImagesMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static io.netty.util.internal.PlatformDependent.putObject;


/**
 * 图片信息表 服务层实现。
 *
 * @author ascrm
 * @since V1.0
 */
@Service
@RequiredArgsConstructor
public class ImagesServiceImpl extends ServiceImpl<ImagesMapper, Images> implements ImagesService {

    private final MinioUtils minioUtils;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Override
    public String uploadImage(MultipartFile file) {
        //判断文件是否为空
        if (null == file || 0 == file.getSize()) return null;
        //判断存储桶是否存在,不存在则创建
        minioUtils.createBucket(bucketName);
        //文件名
        String originalFilename = file.getOriginalFilename();
        //新的文件名 = 存储桶文件名_时间戳.后缀名
        String fileName = bucketName + "_" +
                System.currentTimeMillis() +
                originalFilename.substring(originalFilename.lastIndexOf("."));
        //开始上传
        try {
            minioUtils.putObject(bucketName, fileName, file.getInputStream());
        } catch (IOException e) {
            log.error("文件上传失败",e);
            return null;
        }

        return minioUtils.getObjectUrl(bucketName, fileName, 365);
    }
}