package com.school.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.school.entity.Follow;
import com.school.entity.vo.FollowVo;

import java.util.List;
import java.util.Map;

/**
 * 关注服务层
 *
 */
public interface FollowService extends IService<Follow> {
    List<FollowVo> getFollowList();

    List<FollowVo> getFollowFansList();

    Boolean ChangeStatus(Integer followeeId);

    List<FollowVo> getMutualFollows();

    Map<String, Integer> getFollowCount();

    Map<String, Boolean> getFollowStatus(Integer userId);

    Boolean isMutual(Integer userId, Integer targetId);
}
