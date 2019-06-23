package com.zxy.service.mappers;

import com.zxy.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component(value="userMapper")
public interface userMapper {

    @Select("SELECT * FROM user_login WHERE user_email = #{email}")
    @Results({
            @Result(property = "id", column = "user_id"),
            @Result(property = "email",column = "user_email"),
            @Result(property = "password",column = "user_password"),
            @Result(property = "url",column = "user_url")
    })
    public User findByMail(String email);

    @Select("SELECT * FROM user_login WHERE user_id = #{id}")
    @Results({
            @Result(property = "id", column = "user_id"),
            @Result(property = "email",column = "user_email"),
            @Result(property = "password",column = "user_password"),
            @Result(property = "url",column = "user_url")
    })
    public User findById(int id);

    @Insert("INSERT INTO user_login(user_email,user_password, user_url) " +
            "values(#{email},#{password},#{url})")
    public void Insert(String email, String password, String url);

    @Update("DELETE FROM user_login where user_id = #{id}")
    public void Delete(int id);

    @Update("UPDATE user_login SET user_id = #{id}, user_email = #{email}, user_password=#{password}, user_url=#{url}")
    public void Update(User user);
}
