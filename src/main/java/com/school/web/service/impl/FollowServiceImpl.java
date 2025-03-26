package com.school.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.entity.Follow;
import com.school.entity.User;
import com.school.entity.UserAuth;
import com.school.entity.vo.FollowVo;
import com.school.utils.UserHolder;
import com.school.web.mapper.FollowMapper;
import com.school.web.mapper.UserAuthMapper;
import com.school.web.mapper.UserMapper;
import com.school.web.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 关注服务层实现类
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {

    private final FollowMapper followMapper;

    private final UserAuthMapper userAuthMapper;

    private final UserMapper userMapper;

    /**
     * 返回关注列表
     */
    @Override
    public List<FollowVo> getFollowList() {
        //获取用户id
        Integer userId = getUserId();

        //获取关注的人的列表
        List<Integer> ids = followMapper.getFollowerIds(userId);

        return getUsersVo(ids);
    }


    /**
     * 返回粉丝列表
     */
    @Override
    public List<FollowVo> getFollowFansList() {
        Integer userId = getUserId();
        //获取粉丝列表
        List<Integer> ids = followMapper.getUserIds(userId);

        return getUsersVo(ids);
    }

    /**
     * 封装用户VO返回
     */
    public List<FollowVo> getUsersVo(List<Integer> ids) {

        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        List<User> users = userMapper.selectBatchIds(ids);
        return users.stream()
                .map(user -> new FollowVo(user.getId(),user.getNickName(), user.getAvatar()))
                .collect(Collectors.toList());
    }

    /**
     * 修改状态   关注/取消
     */
    @Override
    public Boolean ChangeStatus(Integer followeeId) {

        Integer userId = getUserId();

        //判断是否有过关注该用户的记录
        Boolean flag = hasFollowBefore(userId, followeeId);

        if (flag) {
            return remove(Wrappers.<Follow>lambdaQuery()
                    .eq(Follow::getFollowerId, userId)
                    .eq(Follow::getFolloweeId, followeeId));
        }else{
            Follow follow = new Follow();
            follow.setFolloweeId(followeeId).setFollowerId(userId);
            return save(follow);
        }
    }

    /**
     * 获取互相关注列表
     */
    @Override
    public List<FollowVo> getMutualFollows() {
        Integer userId = getUserId();
        List<Integer> ids = followMapper.getMutualFollowIds(userId);
        return getUsersVo(ids);
    }

    /**
     * 获取关注数和粉丝数
     */
    @Override
    public Map<String, Integer> getFollowCount() {
        Integer userId = getUserId();
        Integer followCount = followMapper.countFollowees(userId);
        Integer fansCount = followMapper.countFollowers(userId);

        return Map.of("followCount", followCount, "fansCount", fansCount);
    }

    /**
     * 是否关注
     */
    @Override
    public Map<String, Boolean> getFollowStatus(Integer userId) {
        //获取当前用户id
        Integer currentId = getUserId();
        Integer iFollowThem = followMapper.getFollowStatus(currentId,userId);
        Integer theyFollowMe = followMapper.getFollowStatus(userId,currentId);
        return Map.of("iFollowThem", iFollowThem > 0, "theyFollowMe", theyFollowMe > 0);
    }

    @Override
    public Boolean isMutual(Integer userId, Integer targetId) {
        return followMapper.isMutual(userId,targetId)>1;
    }

    /**
     * 判断之前是否关注过该用户
     */
    private Boolean hasFollowBefore(Integer userId,Integer followeeId) {
        return followMapper.hasFollowBefore(userId, followeeId)>0;
    }

    /**
     * 获取用户id
     */
    private Integer getUserId() {
        //获取标识
        String loginId = UserHolder.getLoginId();
        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>().eq(UserAuth::getIdentifier, loginId));
        return userAuth != null ? userAuth.getUserId() : null;
    }
}
