package com.itheima.demo12306.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    private Integer id;
    private String train_id;
    private String seat_no;
    private String seat_type;
    private String status;
}