package com.school.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.web.service.TagService;
import org.springframework.stereotype.Service;
import com.school.entity.Tag;
import com.school.web.mapper.TagMapper;


/**
 * 标签信息表 服务层实现。
 *
 * @author ascrm
 * @since V1.0
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}