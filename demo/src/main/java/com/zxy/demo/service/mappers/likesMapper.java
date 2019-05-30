package com.zxy.demo.service.mappers;

import com.zxy.demo.model.Like;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "lm")
public interface likesMapper {

    @Insert("INSERT INTO likes(user_id,a_id,value) values(#{userId},#{aId},#{value})")
     void save(Like like);

    @Update("DELETE from likes where a_id = #{aId} AND user_id = #{userId}")
    void delete(Like like);

    @Select("SELECT * FROM likes where a_id = #{aId}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "aId",column = "a_id"),
            @Result(property = "value",column = "value")
    })
    List<Like> getLikeListByAId(int aId);

    @Select("SELECT * FROM likes where a_id = #{aId} AND user_id = #{userId}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "aId",column = "a_id"),
            @Result(property = "value",column = "value")
    })
    Like getLikeByAidAndUserId(int aId, int userId);

    @Select("SELECT * FROM likes where user_id = #{userId}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "aId",column = "a_id"),
            @Result(property = "value",column = "value")
    })
    List<Like> getLikeListByUserId(int userId);

    @Update("UPDATE likes SET value = #{value} where user_id = #{userId} AND aId = #{a_id}")
    void upDateLike(int userId, int aId,int value);
}
