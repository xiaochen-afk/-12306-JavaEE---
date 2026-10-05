package com.itheima.demo12306.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private String train_id;
    private String start_station;
    private String end_station;
    private String start_time;
    private String seat_type;
    private String seat_id;
    private String passenger_name;
    private String status;
    private String id_card;
}
