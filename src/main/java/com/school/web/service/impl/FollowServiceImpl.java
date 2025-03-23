package com.school.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.common.entity.Result;
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

import java.util.HashMap;
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
     * @return
     */
    @Override
    public Result<List<FollowVo>> getFollowList() {
        //获取用户id
        Integer userId = getUserId();

        //获取关注的人的列表
        List<Integer> ids = followMapper.getFollowerIds(userId);

        List<FollowVo> list = getUsersVo(ids);

        //根据用户id查询关注列表
        return Result.success(list);
    }


    /**
     * 返回粉丝列表
     * @return
     */
    @Override
    public Result<List<FollowVo>> getFollowFansList() {
        Integer userId = getUserId();
        //获取粉丝列表
        List<Integer> ids = followMapper.getUserIds(userId);

        List<FollowVo> list = getUsersVo(ids);
        return Result.success(list);
    }

    /**
     * 封装用户VO返回
     * @param ids
     * @return
     */
    public List<FollowVo> getUsersVo(List<Integer> ids) {

        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        List<User> users = userMapper.selectBatchIds(ids);

        List<FollowVo> list = users.stream()
                .map(user -> new FollowVo(user.getId(),user.getNickName(), user.getAvatar()))
                .collect(Collectors.toList());

        return list;

    }

    /**
     * 修改状态   关注/取消
     * @param followeeId
     * @return
     */
    @Override
    public Result ChangeStatus(Integer followeeId) {

        Integer userId = getUserId();

        //判断是否有过关注该用户的记录
        Boolean flag = hasFollowBefore(userId, followeeId);

        if (flag) {

            //有过关注记录,接下来不管请求关注还是取消,is_delete取反就行
            Integer rs = followMapper.change(userId, followeeId);

            return rs > 0 ? Result.success("操作成功") : Result.fail("操作失败");

        }
        //首次关注该用户
        Follow follow = new Follow();
        follow.setFolloweeId(followeeId);
        follow.setFollowerId(userId);
        follow.setIsDelete(false);

        return followMapper.insert(follow) > 0 ? Result.success("关注成功") : Result.fail("关注失败");
    }

    /**
     * 获取互相关注列表
     * @return
     */
    @Override
    public Result<List<FollowVo>> getMutualFollows() {
        Integer userId = getUserId();
        List<Integer> ids = followMapper.getMutualFollowIds(userId);
        List<FollowVo> list = getUsersVo(ids);
        return Result.success(list);
    }

    /**
     * 获取关注数和粉丝数
     * @return
     */
    @Override
    public Result<Map<String, Integer>> getFollowCount() {
        Integer userId = getUserId();
        Integer followCount = followMapper.countFollowees(userId);
        Integer fansCount = followMapper.countFollowers(userId);

        Map<String, Integer> data = new HashMap<>();
        data.put("followCount", followCount);
        data.put("fansCount", fansCount);

        return Result.success(data);
    }

    /**
     * 是否关注
     * @param userId
     * @return
     */
    @Override
    public Result getFollowStatus(Integer userId) {
        //获取当前用户id
        Integer currentId = getUserId();
        Integer flag = followMapper.getFollowStatus(currentId,userId);
        return Result.success(flag>0?true:false);
    }


    /**
     * 判断之前是否关注过该用户
     * @param userId,followeeId
     * @return
     */
    private Boolean hasFollowBefore(Integer userId,Integer followeeId) {

        Integer flag = followMapper.hasFollowBefore(userId, followeeId);

        return flag > 0 ? true : false;
    }


    /**
     * 获取用户id
     * @return
     */
    private Integer getUserId() {

        //获取标识
        String loginId = UserHolder.getLoginId();

        //获取用户id
        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>().eq(UserAuth::getIdentifier, loginId));

        return userAuth != null ? userAuth.getUserId() : null;
    }


}
