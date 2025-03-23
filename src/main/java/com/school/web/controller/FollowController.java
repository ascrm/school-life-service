package com.school.web.controller;

import com.school.common.entity.Result;
import com.school.entity.vo.FollowVO;
import com.school.web.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/school/web")
@Slf4j
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    /**
     * 查询用户的关注列表
     * @return 关注的用户ID列表
     */
    @GetMapping("/follow/followees")
    public Result<List<FollowVO>> getFolloweesList() {
        return followService.getFollowList();
    }

    /**
     * 查询用户的粉丝列表
     * @return 粉丝ID列表
     */
    @GetMapping("follow/fans")
    public Result<List<FollowVO>> getFollowFansList() {
        return followService.getFollowFansList();
    }

    /**
     * 关注/取消关注某个用户
     * @param followeeId 被关注者ID
     * @return 操作成功/失败
     */
    @PostMapping("follow/change")
    public Result followUser(@RequestParam Integer followeeId) {
        return followService.ChangeStatus(followeeId);
    }


    /**
     * 获取互相关注列表
     * @return
     */
    @GetMapping("follow/mutual")
    public Result<List<FollowVO>> getMutualList() {
        return followService.getMutualFollows();
    }

    /**
     * 获取关注数和粉丝数
     * @return
     */
    @GetMapping("follow/count")
    public Result<Map<String, Integer>> getFollowCount(){
        return followService.getFollowCount();
    }


    /**
     * 点进帖子,判断是否关注
     */
    @GetMapping("follow/status")
    public Result getFollowStatus(@RequestParam Integer userId) {
        return followService.getFollowStatus(userId);
    }

}
