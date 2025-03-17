package com.travel.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.web.service.TagService;
import org.springframework.stereotype.Service;
import com.travel.entity.Tag;
import com.travel.web.mapper.TagMapper;


/**
 * 标签信息表 服务层实现。
 *
 * @author ascrm
 * @since V1.0
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}