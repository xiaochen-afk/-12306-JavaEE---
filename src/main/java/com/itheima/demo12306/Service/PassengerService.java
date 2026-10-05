package com.itheima.demo12306.Service;

import com.itheima.demo12306.Entity.Passenger;
import com.itheima.demo12306.Mapper.PassengerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {
    @Autowired
    private PassengerMapper PassengerMapper;
    public List<Passenger> find_passenger_list(int qryCurrentPage, int qryPageSize) {
        int pos = (qryCurrentPage - 1) * qryPageSize;
        List<Passenger> passengerList = PassengerMapper.find_passenger_list(pos, qryPageSize);
        return passengerList;
    }

    public List<Passenger> find_passenger_by_name(String name, int qryCurrentPage, int qryPageSize) {
        int pos = (qryCurrentPage - 1) * qryPageSize;
        List<Passenger> passengerList = PassengerMapper.find_passenger_by_name(name, pos, qryPageSize);
        return passengerList;
    }

    public int add_passenger(String name, String telephone, String id_card) {
        return PassengerMapper.add_passenger(name, telephone, id_card);
    }

    public int delete_passenger(String id_card) {
        return PassengerMapper.delete_passenger(id_card);
    }
}
