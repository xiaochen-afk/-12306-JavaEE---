package com.itheima.demo12306.Mapper;

import com.itheima.demo12306.Entity.Ticket;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    List<Ticket> list_tickets(String id_card);
    
    void cancel_ticket(String train_id, String start_station, String end_station, String start_time,
                      String seat_type, String seat_id, String passenger_name, String id_card);
    
    void update_seat_status(String seat_id, String status);
    
    void update_seat_count(String train_id, String seat_type, int count);
}
