package com.itheima.demo12306.Service;

import com.itheima.demo12306.Entity.Ticket;
import com.itheima.demo12306.Mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    public List<Ticket> list_tickets(String id_card) {
        return orderMapper.list_tickets(id_card);
    }
    
    public void cancel_ticket(String train_id, String start_station, String end_station, String start_time,
                              String seat_type, String seat_id, String passenger_name, String id_card, String verify_telephone) {
        // log.info("train_id={}, start_station={}, end_station={}, start_time={}, 
        //         seat_type={}, seat_id={}, passenger_name={}, id_card={}, verify_telephone={}",
        //         train_id, start_station, end_station, start_time, seat_type, seat_id, passenger_name, id_card, verify_telephone);
        
        // 这里可以添加更多的业务逻辑，比如验证手机号、检查订单状态等
        // 目前直接调用mapper取消订单
        orderMapper.cancel_ticket(train_id, start_station, end_station, start_time, 
                               seat_type, seat_id, passenger_name, id_card);
        
        // 更新座位状态为可用
        orderMapper.update_seat_status(seat_id, "AVAILABLE");
        
        // 更新座位数量
        orderMapper.update_seat_count(train_id, seat_type, 1);
    }
}
