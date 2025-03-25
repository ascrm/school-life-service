package com.school.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.entity.Follow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FollowMapper extends BaseMapper<Follow> {

    @Select("select follower_id from tb_follow where followee_id=#{userId}")
    List<Integer> getUserIds(Integer userId);

    @Select("select followee_id from tb_follow where follower_id=#{userId}")
    List<Integer> getFollowerIds(Integer userId);

    @Select("select count(*) from tb_follow where follower_id=#{userId} and followee_id=#{followeeId}")
    Integer hasFollowBefore(Integer userId, Integer followeeId);

    List<Integer> getMutualFollowIds(Integer userId);

    @Select("select count(*) from tb_follow where follower_id = #{userId}")
    Integer countFollowees(Integer userId);
    @Select("select count(*) from tb_follow where followee_id = #{userId}")
    Integer countFollowers(Integer userId);

    @Select("select count(*) from tb_follow where follower_id=#{currentId} and followee_id=#{userId}")
    Integer getFollowStatus(Integer currentId, Integer userId);
}
