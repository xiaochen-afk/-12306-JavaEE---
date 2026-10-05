package com.itheima.demo12306.Mapper;

import com.itheima.demo12306.Entity.Passenger;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PassengerMapper {

    @Select("select * from passenger limit #{pos},#{qryPageSize}")
    List<Passenger> find_passenger_list(int pos, int qryPageSize);

    @Select("select * from passenger where name = #{name} limit #{pos},#{qryPageSize}")
    List<Passenger> find_passenger_by_name(String name, int pos, int qryPageSize);

    @Insert("insert into passenger(name,telephone,id_card) values(#{name},#{telephone},#{id_card})")
    int add_passenger(String name, String telephone, String id_card);

    @Delete("delete from passenger where id_card = #{id_card}")
    int delete_passenger(String id_card);
}
