package com.travel.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.entity.Images;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片信息表 服务层。
 *
 * @author ascrm
 * @since V1.0
 */
public interface ImagesService extends IService<Images> {
    String uploadImage(MultipartFile file);
}