package com.itheima.demo12306.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatCount {
    private String train_id;
    private Integer bussine_seat;
    private Integer first_seat;
    private Integer second_seat;
    private Integer no_seat;
}
