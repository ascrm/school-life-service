package com.school.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.school.common.entity.Result;
import com.school.entity.Follow;
import com.school.entity.vo.FollowVo;

import java.util.List;
import java.util.Map;

/**
 * 关注服务层
 *
 */
public interface FollowService extends IService<Follow> {
    Result<List<FollowVo>> getFollowList();

    Result<List<FollowVo>> getFollowFansList();


    Result ChangeStatus(Integer followeeId);

    Result<List<FollowVo>> getMutualFollows();

    Result<Map<String, Integer>> getFollowCount();

    Result getFollowStatus(Integer userId);

    Result isMutual(Integer userId, Integer targetId);
}
