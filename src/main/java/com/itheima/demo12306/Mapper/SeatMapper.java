package com.itheima.demo12306.Mapper;

import com.itheima.demo12306.Entity.Seat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SeatMapper {
    // 根据车次ID和座位类型获取座位列表
    List<Seat> getSeatsByTrainIdAndType(@Param("train_id") String train_id,
                                        @Param("seat_type") String seat_type);
    
    // 更新座位状态
    void updateSeatStatus(@Param("train_id") String train_id, @Param("seat_no") String seat_no,
                          @Param("status") String status);
}