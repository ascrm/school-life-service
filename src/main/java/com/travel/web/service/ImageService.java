package com.travel.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 图片信息表 服务层。
 *
 * @author ascrm
 * @since V1.0
 */
public interface ImageService extends IService<Image> {
    Image uploadImage(MultipartFile file);

    List<Image> uploadImage(List<MultipartFile> files);
}