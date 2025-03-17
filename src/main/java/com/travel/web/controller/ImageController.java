package com.travel.web.controller;

import com.travel.web.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图片信息表 控制层。
 *
 * @author ascrm
 * @since V1.0
 */
@RestController
@RequestMapping("/school/web")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
}