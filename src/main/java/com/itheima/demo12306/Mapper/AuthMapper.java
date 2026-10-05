package com.itheima.demo12306.Mapper;

import com.itheima.demo12306.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AuthMapper {

    @Select("select * from user where username=#{username} and password=#{password}")
    User login(String username, String password);

    @Insert("insert into user(username,password,email,telephone) values(#{username},#{password},#{email},#{telephone})")
    void register(String username, String password, String email, String telephone);

    @Select("SELECT * from user where telephone = #{telephone} and username = #{username}")
    User check_telephone_unique(String username ,String telephone);
}
