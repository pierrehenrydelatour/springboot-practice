package com.zxy.demo.service.mappers;

import com.zxy.demo.model.Answer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value="am")
public interface answerMapper {
    @Select("SELECT * FROM answers where q_id = #{qId}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "qId", column = "q_id"),
            @Result(property = "aId", column = "a_id"),
            @Result(property = "text", column = "content")
    })
    public List<Answer> getAnswersByQuestion(int qId);

    @Select("SELECT * FROM answers where a_id = #{aId}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "qId", column = "q_id"),
            @Result(property = "aId", column = "a_id"),
            @Result(property = "text", column = "content"),
            @Result(property = "like",column = "likes")
    })
    public Answer getAnswerByAId(int aId);

    @Insert("INSERT INTO answers(user_id,q_id,content) values(#{userId},#{qId},#{text})")
    public void answer(int qId, String text, int userId);

    @Update("DELETE FROM answers where a_id = #{aId}")
    public void deleteAnswer(int aId);

    @Update("Update answers SET content = #{text} likes = #{like}")
    public void updateAnswers(Answer answer);

    @Update("Update answers SET likes = #{cnt} where a_id = #{aId}")
    public void updateOneAnswer(int aId, int cnt);
}
