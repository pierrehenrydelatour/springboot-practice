package com.zxy.service.mappers;

import com.zxy.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "qm")
public interface questionMapper {

    @Select("SELECT * FROM questions where user_id = #{userId}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "id", column = "q_id"),
            @Result(property = "text", column = "text")
    })
    public List<Question> getQuestionByUser(int userId);

    @Select("SELECT * FROM questions where q_id = #{qId}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "id", column = "q_id"),
            @Result(property = "text", column = "text")
    })
    public Question getQuestionByQId(int qId);

    @Select("SELECT * FROM questions where user_id = #{userId} AND title = #{title} AND text = #{text}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "id", column = "q_id"),
            @Result(property = "text", column = "text")
    })
    public Question getQuestionByCentent(int userId,String title, String text);

    @Insert("INSERT INTO questions(user_id,title,text) values(#{userId},#{title},#{text})")
    public void poseQuestion(String title, String text, int userId);

    @Update("DELETE FROM questions where id = #{id}")
    public void deleteQuestion(int id);

    @Update("Update questions SET title = #{title}, text = #{text}")
    public void updateQuestion(Question question);
}
