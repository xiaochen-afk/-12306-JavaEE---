package com.itheima.demo12306.Mapper;

import com.itheima.demo12306.Entity.TrainType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TrainTicketMapper {

    @Select("SELECT t.*, s.bussiness_seat, s.first_seat, s.second_seat,s.no_seat " +
            "FROM train t " +
            "LEFT JOIN seat_count s ON t.train_id = s.train_id limit #{pos} , #{qryPageSize}")
    List<TrainType> find_train_list(int pos, int qryPageSize);

    @Select("SELECT * FROM train WHERE train_id = #{train_id}")
    List<TrainType> list_booking(String train_id);

    void check_booking(Map<String, Object> params);

    void update_seat_count(Map params);

    List<TrainType> find_train_list_by_condition(int pos, int qryPageSize,
                                                 String start_station, String end_station,
                                                 String start_time, String end_time,
                                                 String[] seat_types, String[] train_types);
}
