package com.itheima.demo12306.Controller;

import com.itheima.demo12306.Entity.Ticket;
import com.itheima.demo12306.Service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    
    @RequestMapping("/order")
    public String order() {
        log.info("订单");
        return "order";
    }
    
    @RequestMapping("/tickets")
    public String tickets(HttpServletRequest  request, Model model) {
        String id_card = request.getParameter("id_card");
        List<Ticket> tickets = orderService.list_tickets(id_card);
        model.addAttribute("tickets",tickets);
        log.info("查询订单");
        return "/order";
    }
    
    @RequestMapping("/cancel")
    public String cancel(HttpServletRequest request, Model model) {
        // 获取车票参数
        String train_id = request.getParameter("train_id");
        String start_station = request.getParameter("start_station");
        String end_station = request.getParameter("end_station");
        String start_time = request.getParameter("start_time");
        String seat_type = request.getParameter("seat_type");
        String seat_id = request.getParameter("seat_id");
        String passenger_name = request.getParameter("passenger_name");
        String id_card = request.getParameter("id_card");
        
        // 构建车票信息对象
        Map<String, Object> ticketInfo = new HashMap<>();
        ticketInfo.put("train_id", train_id);
        ticketInfo.put("start_station", start_station);
        ticketInfo.put("end_station", end_station);
        ticketInfo.put("start_time", start_time);
        ticketInfo.put("seat_type", seat_type);
        ticketInfo.put("seat_id", seat_id);
        ticketInfo.put("passenger_name", passenger_name);
        ticketInfo.put("id_card", id_card);
        
        // 传递到页面
        model.addAttribute("ticket", ticketInfo);
        log.info("取消订单页面，车票信息：{}", ticketInfo);
        return "/cancel";
    }
    
    @RequestMapping("/confirm_cancel")
    public String confirmCancel(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        // 获取车票参数
        String train_id = request.getParameter("train_id");
        String start_station = request.getParameter("start_station");
        String end_station = request.getParameter("end_station");
        String start_time = request.getParameter("start_time");
        String seat_type = request.getParameter("seat_type");
        String seat_id = request.getParameter("seat_id");
        String passenger_name = request.getParameter("passenger_name");
        String id_card = request.getParameter("id_card");
        
        // 获取验证信息
        String verify_id_card = request.getParameter("verify_id_card");
        String verify_telephone = request.getParameter("verify_telephone");
        
        log.info("确认取消订单，车票信息：train_id={}, passenger_name={}, id_card={}", train_id, passenger_name, id_card);
        log.info("验证信息：verify_id_card={}, verify_telephone={}", verify_id_card, verify_telephone);
        
        // 验证身份证号
        if (!id_card.equals(verify_id_card)) {
            log.error("身份证号验证失败");
            redirectAttributes.addFlashAttribute("errorMessage", "身份证号验证失败");
            redirectAttributes.addAttribute("train_id", train_id);
            redirectAttributes.addAttribute("start_station", start_station);
            redirectAttributes.addAttribute("end_station", end_station);
            redirectAttributes.addAttribute("start_time", start_time);
            redirectAttributes.addAttribute("seat_type", seat_type);
            redirectAttributes.addAttribute("seat_id", seat_id);
            redirectAttributes.addAttribute("passenger_name", passenger_name);
            redirectAttributes.addAttribute("id_card", id_card);
            return "redirect:/cancel";
        }
        
        // 调用service层取消订单
        try {
            orderService.cancel_ticket(train_id, start_station, end_station, start_time, 
                                     seat_type, seat_id, passenger_name, id_card, verify_telephone);
            log.info("订单取消成功");
            redirectAttributes.addFlashAttribute("successMessage", "订单取消成功");
        } catch (Exception e) {
            log.error("订单取消失败：{}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "订单取消失败：" + e.getMessage());
        }
        
        return "redirect:/order";
    }
}
