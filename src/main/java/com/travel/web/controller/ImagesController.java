package com.travel.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import com.travel.web.service.ImagesService;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图片信息表 控制层。
 *
 * @author ascrm
 * @since V1.0
 */
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImagesController {

    private final ImagesService imagesService;
}