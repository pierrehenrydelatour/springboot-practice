package com.zxy.demo.service.mappers;

import com.zxy.demo.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component(value="userMapper")
public interface userMapper {

    @Select("SELECT * FROM user_login WHERE user_email = #{email}")
    @Results({
            @Result(property = "id", column = "user_id"),
            @Result(property = "email",column = "user_email"),
            @Result(property = "password",column = "user_password")
    })
    public User findByMail(String email);

    @Select("SELECT * FROM user_login WHERE user_id = #{id}")
    @Results({
            @Result(property = "id", column = "user_id"),
            @Result(property = "email",column = "user_email"),
            @Result(property = "password",column = "user_password")
    })
    public User findById(int id);

    @Insert("INSERT INTO user_login(user_email,user_password) values(#{email},#{password})")
    public void Insert(String email, String password);

    @Update("DELETE FROM user_login where user_id = #{id}")
    public void Delete(int id);

    @Update("UPDATE user_login SET user_id = #{id}, user_email = #{email}, user_password=#{password}")
    public void Update(User user);
}
