package com.itheima.demo12306.Service;

import com.itheima.demo12306.Entity.Seat;
import com.itheima.demo12306.Entity.TrainType;
import com.itheima.demo12306.Mapper.SeatMapper;
import com.itheima.demo12306.Mapper.TrainTicketMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TicketService {
    @Autowired
    private TrainTicketMapper trainTypeMapper;
    
    @Autowired
    private SeatMapper seatMapper;

    public List<TrainType> find_train_list(int qryCurrentPage, int qryPageSize) {
        int pos = (qryCurrentPage - 1) * qryPageSize;
        log.info("查询车次列表");
        List<TrainType> trainList = trainTypeMapper.find_train_list(pos, qryPageSize);
        return trainList;
    }

    public List<TrainType> list_booking(String train_id) {
        log.info("确认预订");
        List<TrainType> trainList = trainTypeMapper.list_booking(train_id);
        return trainList;
    }

    public void check_booking(String train_id, String start_station, String end_station, String start_time,
                              String[] seat_type, String[] passenger_names, String[] id_cards, String[] telephones, String[] selectedSeats) {
        log.info("车次id:{},出发站:{},到达站:{},出发时间:{},座位类型:{}", train_id, start_station, end_station, start_time, seat_type);
        log.info("乘客姓名:{},证件号:{},手机号:{}", passenger_names, id_cards, telephones);
        log.info("选中座位:{}", selectedSeats);
        
        // 验证参数完整性
        if (passenger_names == null || selectedSeats == null || seat_type == null || seat_type.length == 0) {
            log.error("乘客姓名、选中座位或座位类型为空");
            throw new IllegalArgumentException("乘客姓名、选中座位或座位类型为空");
        }
        
        // 验证座位数量与乘客数量匹配
        int passengerCount = passenger_names.length;
        int seatCount = selectedSeats.length;
        if (passengerCount != seatCount) {
            log.error("座位数量与乘客数量不匹配，乘客数:{},座位数:{}", passengerCount, seatCount);
            throw new IllegalArgumentException("座位数量与乘客数量不匹配");
        }
        
        // 确保所有乘客使用相同的座位类型
        String firstSeatType = seat_type[0];
        String[] uniformSeatType = new String[passengerCount];
        for (int i = 0; i < passengerCount; i++) {
            uniformSeatType[i] = firstSeatType;
        }
        
        // 将参数包装在Map中，以便MyBatis正确处理
        Map<String, Object> params = new HashMap<>();
        params.put("train_id", train_id);
        params.put("start_station", start_station);
        params.put("end_station", end_station);
        params.put("start_time", start_time);
        params.put("seat_type", uniformSeatType);
        params.put("passenger_names", passenger_names);
        params.put("id_cards", id_cards);
        params.put("telephones", telephones);
        params.put("selectedSeats", selectedSeats);
        
        trainTypeMapper.check_booking(params);
    }

    public void update_seat_count(String train_id, String[] seat_type, String[] passenger_names) {
        int bussiness_seat = 0;
        int first_seat = 0;
        int second_seat = 0;
        int no_seat = 0;
        for (int i = 0; i < seat_type.length; i++){
            if (seat_type[i].equals("商务座")){
                bussiness_seat++;
            }
            if (seat_type[i].equals("一等座")){
                first_seat++;
            }
            if (seat_type[i].equals("二等座")){
                second_seat++;
            }
            if (seat_type[i].equals("无座")){
                no_seat++;
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("train_id", train_id);
        params.put("seat_type", seat_type);
        params.put("bussiness_seat", bussiness_seat);
        params.put("first_seat", first_seat);
        params.put("second_seat", second_seat);
        params.put("no_seat", no_seat);
        trainTypeMapper.update_seat_count(params);
    }

    public List<TrainType> find_train_list_by_condition(int qryCurrentPage, int qryPageSize,
                                                        String start_station,String end_station,
                                                        String start_time,String end_time,
                                                         String[] seat_types, String[] train_types) {
        int pos = (qryCurrentPage - 1) * qryPageSize;
        List<TrainType> trainList =trainTypeMapper.find_train_list_by_condition(pos, qryPageSize, start_station,
                                                                                end_station, start_time,
                                                                                end_time,seat_types, train_types);
        return trainList;
    }
    
    // 根据车次ID和座位类型获取座位列表
    public List<Seat> getSeatsByTrainIdAndType(String train_id, String seat_type) {
        log.info("获取车次:{} 座位类型:{}的座位列表", train_id, seat_type);
        return seatMapper.getSeatsByTrainIdAndType(train_id, seat_type);
    }
    
    // 批量更新座位状态
    public void updateSeatsStatus(String train_id, List<String> seat_nos, String status) {
        log.info("更新车次:{} 座位:{} 的状态为:{}", train_id, seat_nos, status);
        for (String seat_no : seat_nos) {
            seatMapper.updateSeatStatus(train_id, seat_no, status);
        }
    }
}
