package com.travel.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.web.service.TagsService;
import org.springframework.stereotype.Service;
import com.travel.entity.Tags;
import com.travel.web.mapper.TagsMapper;


/**
 * 标签信息表 服务层实现。
 *
 * @author ascrm
 * @since V1.0
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {

}