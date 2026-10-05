package com.itheima.demo12306.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainType {
    private Integer id;
    private String train_id;
    private String train_type;
    private String start_station;
    private String end_station;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_time;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_time;
    private String run_tate;
    private String is_running;
    private String other;
    
    private Integer bussiness_seat;
    private Integer first_seat;
    private Integer second_seat;
    private Integer no_seat;
}
