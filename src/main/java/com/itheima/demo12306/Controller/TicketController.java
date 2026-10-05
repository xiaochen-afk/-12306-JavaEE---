package com.itheima.demo12306.Controller;

import com.itheima.demo12306.Entity.Passenger;
import com.itheima.demo12306.Entity.Seat;
import com.itheima.demo12306.Entity.TrainType;
import com.itheima.demo12306.Service.PassengerService;
import com.itheima.demo12306.Service.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class TicketController {
    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private PassengerService passengerService;

    @RequestMapping("/success")
    public String success(HttpServletRequest request, Model model) {
        log.info("登录成功");
        int qry_current_page = Integer.parseInt(request.getParameter("qry_current_page"));
        int qry_page_size = Integer.parseInt(request.getParameter("qry_page_size"));
        List<TrainType> trainList = ticketService.find_train_list(qry_current_page,qry_page_size);
        model.addAttribute("trainList",trainList);
        model.addAttribute("qry_current_page",qry_current_page);
        model.addAttribute("qry_page_size",qry_page_size);
        return "/success";
    }

    @RequestMapping("/find_train_list")
    public String find_train_list(HttpServletRequest request, Model model){
        Result result = getResult(request, model);
        List<TrainType> trainList;
        String start_station = request.getParameter("start_station");
        String end_station = request.getParameter("end_station");
        String start_time = request.getParameter("start_time");
        String end_time = request.getParameter("end_time");
        String[] seat_types = request.getParameterValues("seat_type");
        String[] train_types = request.getParameterValues("train_type");
        //可以把这个判断逻辑写进service，这样逻辑就清晰很多
        // 也保证了controller层只负责获取参数，service层只负责处理参数
        // 返回结果，controller层只负责返回结果
        if (start_station == null && end_station == null && seat_types == null && train_types == null
                && start_time == null && end_time == null){
            trainList = ticketService.find_train_list(result.qry_current_page(), result.qry_page_size());
        }else {
            trainList = ticketService.find_train_list_by_condition(result.qry_current_page(), result.qry_page_size(),
                                                                   start_station,end_station,start_time,end_time,
                                                                   seat_types, train_types);
        }
        log.info("当前页码:{},每页显示数量:{}", result.qry_current_page(), result.qry_page_size());
        model.addAttribute("trainList",trainList);

        return "/success";
    }

    @RequestMapping("/book")
    public String book(HttpServletRequest request, Model model){
        String train_id = request.getParameter("train_id");
        log.info("车次id:{}",train_id);
        log.info("预订车票");
        List<TrainType> trainList = ticketService.list_booking(train_id);
        model.addAttribute("trainList",trainList);
        model.addAttribute("train_id", train_id);
        return "/book";
    }
    
    // 获取座位信息接口
    @RequestMapping("/get_seats")
    @ResponseBody
    public List<Seat> getSeats(HttpServletRequest request){
        String train_id = request.getParameter("train_id");
        String seat_type = request.getParameter("seat_type");
        log.info("获取车次:{} 座位类型:{}的座位信息", train_id, seat_type);
        return ticketService.getSeatsByTrainIdAndType(train_id, seat_type);
    }
    @RequestMapping("/confirm_booking")
    public String confirm_booking(HttpServletRequest request,RedirectAttributes redirectAttributes,Model model){
        //获取订单参数
        String train_id = request.getParameter("train_id");
        String start_station = request.getParameter("start_station");
        String end_station = request.getParameter("end_station");
        String start_time = request.getParameter("start_time");
        String[] seat_type = request.getParameterValues("seat_type[]");
        String[] passenger_names = request.getParameterValues("passenger_name[]");
        String[] id_cards = request.getParameterValues("id_card[]");
        String[] telephones = request.getParameterValues("telephone[]");
        String selectedSeats = request.getParameter("selected_seats");
        
        // 调试信息：输出原始参数
        log.info("原始selected_seats参数值: {}", selectedSeats);
        log.info("原始selected_seats参数类型: {}", selectedSeats != null ? selectedSeats.getClass() : "null");
        Result result = getanotherResult(request, redirectAttributes);
        log.info("车次id:{},出发站:{},到达站:{},出发时间:{},座位类型:{}", train_id, start_station, end_station, start_time, seat_type);
        log.info("乘客姓名:{},证件号:{},手机号:{}", passenger_names, id_cards, telephones);
        log.info("选中座位:{}", selectedSeats);
        List<TrainType> trainList = ticketService.find_train_list(result.qry_current_page(),result.qry_page_size());
        model.addAttribute("trainList", trainList);
        redirectAttributes.addAttribute("qry_current_page",result.qry_current_page());
        redirectAttributes.addAttribute("qry_page_size",result.qry_page_size());
        
        // 处理座位选择
        String[] selectedSeatsArray = null;
        int passengerCount = passenger_names != null ? passenger_names.length : 0;
        
        // 检查是否所有乘客都是无座
        boolean allNoSeat = true;
        if (seat_type != null) {
            for (String type : seat_type) {
                if (!"无座".equals(type)) {
                    allNoSeat = false;
                    break;
                }
            }
        }
        
        if (allNoSeat) {
            // 如果都是无座，为每个乘客分配"无座"座位
            selectedSeatsArray = new String[passengerCount];
            for (int i = 0; i < passengerCount; i++) {
                selectedSeatsArray[i] = "无座";
            }
        } else if (selectedSeats != null && !selectedSeats.isEmpty()) {
            List<String> seatNos = new ArrayList<>();
            String[] seatArray = selectedSeats.split(",");
            for (String seatNo : seatArray) {
                seatNos.add(seatNo.trim());
            }
            
            // 验证座位数量是否与乘客数量匹配
            if (seatNos.size() != passengerCount) {
                log.error("座位数量与乘客数量不匹配，座位数:{},乘客数:{}", seatNos.size(), passengerCount);
                redirectAttributes.addFlashAttribute("errorMessage", "座位数量与乘客数量不匹配，请重新选择");
                return "redirect:/book?train_id=" + train_id;
            }
            
            // 更新座位状态为已占用
            ticketService.updateSeatsStatus(train_id, seatNos, "OCCUPIED");
            // 转换为数组以便传递给service
            selectedSeatsArray = seatNos.toArray(new String[0]);
        } else {
            // 如果不是无座且没有选择座位，返回错误
            log.error("非无座票必须选择座位");
            redirectAttributes.addFlashAttribute("errorMessage", "请为所有乘客选择座位");
            return "redirect:/book?train_id=" + train_id;
        }
        
        ticketService.check_booking(train_id, start_station, end_station, start_time, seat_type, passenger_names, id_cards, telephones, selectedSeatsArray);
        ticketService.update_seat_count(train_id, seat_type,passenger_names);
        return "redirect:/find_train_list";
    }



    private static Result getResult(HttpServletRequest request, Model model) {
        int qry_current_page = Integer.parseInt(request.getParameter("qry_current_page"));
        int qry_page_size = Integer.parseInt(request.getParameter("qry_page_size"));
        model.addAttribute("qry_current_page",qry_current_page);
        model.addAttribute("qry_page_size",qry_page_size);
        Result result = new Result(qry_current_page, qry_page_size);
        return result;
    }
    private static Result getanotherResult(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        int qry_current_page = Integer.parseInt(request.getParameter("qry_current_page"));
        int qry_page_size = Integer.parseInt(request.getParameter("qry_page_size"));
        redirectAttributes.addAttribute("qry_current_page",qry_current_page);
        redirectAttributes.addAttribute("qry_page_size",qry_page_size);
        Result result = new Result(qry_current_page, qry_page_size);
        return result;
    }

    // 获取所有乘车人列表
    @RequestMapping("/get_all_passengers")
    @ResponseBody
    public List<Passenger> getAllPassengers() {
        log.info("获取所有乘车人列表");
        // 获取所有乘车人，不分页
        return passengerService.find_passenger_list(1, Integer.MAX_VALUE);
    }
    
    private record Result(int qry_current_page, int qry_page_size) {
    }
}