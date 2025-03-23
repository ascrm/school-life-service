package com.school.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.school.common.entity.Result;
import com.school.entity.Follow;
import com.school.entity.vo.FollowVO;

import java.util.List;
import java.util.Map;

/**
 * 关注服务层
 *
 */
public interface FollowService extends IService<Follow> {
    Result<List<FollowVO>> getFollowList();

    Result<List<FollowVO>> getFollowFansList();


    Result ChangeStatus(Integer followeeId);

    Result<List<FollowVO>> getMutualFollows();

    Result<Map<String, Integer>> getFollowCount();

    Result getFollowStatus(Integer userId);

    Result isMutual(Integer userId, Integer targetId);
}
