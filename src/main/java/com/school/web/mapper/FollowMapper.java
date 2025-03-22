package com.school.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.entity.Follow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FollowMapper extends BaseMapper<Follow> {

    @Select("select follower_id from tb_follow where followee_id=#{userId} and is_delete=0")
    List<Integer> getUserIds(Integer userId);

    @Select("select followee_id from tb_follow where follower_id=#{userId} and is_delete=0")
    List<Integer> getFollowerIds(Integer userId);

    @Select("select count(*) from tb_follow where follower_id=#{userId} and followee_id=#{followeeId}")
    Integer hasFollowBefore(Integer userId, Integer followeeId);

    @Update("update tb_follow set is_delete=1-is_delete where follower_id=#{userId} and followee_id=#{followeeId}")
    Integer change(Integer userId, Integer followeeId);

    List<Integer> getMutualFollowIds(Integer userId);

    @Select("select count(*) from tb_follow where follower_id = #{userId} and is_delete = 0; -- 关注数")
    Integer countFollowees(Integer userId);
    @Select("select count(*) from tb_follow where followee_id = #{userId} and is_delete = 0")
    Integer countFollowers(Integer userId);

    @Select("select count(*) from tb_follow where follower_id=#{currentId} and followee_id=#{userId} and is_delete=0")
    Integer getFollowStatus(Integer currentId, Integer userId);
}
