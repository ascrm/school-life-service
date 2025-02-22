package com.travel.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import com.travel.admin.service.AdminsService;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员表 控制层。
 *
 * @author ascrm
 * @since V1.0
 */
@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminsController {

    private final AdminsService adminsService;
}