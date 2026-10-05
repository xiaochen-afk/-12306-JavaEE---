package com.itheima.demo12306.Controller;

import com.itheima.demo12306.Entity.Passenger;
import com.itheima.demo12306.Service.PassengerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class PassengerController {
    @Autowired
    private PassengerService passengerService;

    @RequestMapping("/passenger")
    public String passenger(HttpServletRequest request, Model model) {
        int qry_current_page = Integer.parseInt(request.getParameter("qry_current_page"));
        int qry_page_size = Integer.parseInt(request.getParameter("qry_page_size"));
        List<Passenger> passengerList = passengerService.find_passenger_list(qry_current_page, qry_page_size);
        model.addAttribute("passengerList", passengerList);
        model.addAttribute("qry_current_page", qry_current_page);
        model.addAttribute("qry_page_size", qry_page_size);
        return "/passenger";
    }
    
    @RequestMapping("/passengers")
    public String passengers(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        int qry_current_page = Integer.parseInt(request.getParameter("qry_current_page"));
        int qry_page_size = Integer.parseInt(request.getParameter("qry_page_size"));
        log.info("当前页码:{},每页显示数量:{}", qry_current_page, qry_page_size);
        List<Passenger> passengerList;
        String name = request.getParameter("name");
        redirectAttributes.addAttribute("qry_current_page", qry_current_page);
        redirectAttributes.addAttribute("qry_page_size", qry_page_size);
        if (name != "") {
            log.info("根据姓名查询乘客:{}", name);
            passengerList = passengerService.find_passenger_by_name(name, qry_current_page, qry_page_size);
        }else{
            passengerList = passengerService.find_passenger_list(qry_current_page, qry_page_size);
        }
        model.addAttribute("passengerList", passengerList);
        model.addAttribute("qry_current_page", qry_current_page);
        model.addAttribute("qry_page_size", qry_page_size);
        log.info("查询乘客信息");
        return "/passenger";
    }

    @RequestMapping("/addPassenger")
    public String addPassenger() {
        log.info("添加乘客");
        return "/addPassenger";
    }

    @RequestMapping("/add_passenger")
    public String add_passenger(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String name = request.getParameter("name");
        String telephone = request.getParameter("telephone");
        String id_card = request.getParameter("id_card");
        log.info("添加乘客:{},{},{}", name, telephone, id_card);
        int flag = passengerService.add_passenger(name, telephone, id_card);
        if (flag == 1) {
            redirectAttributes.addAttribute("qry_current_page", 1);
            redirectAttributes.addAttribute("qry_page_size", 5);
            return "redirect:/passenger";
        }
        return "redirect:/add_passenger";
    }

    @RequestMapping("/deletePassenger")
    public String deletePassenger(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String id_card = request.getParameter("id_card");
        String qry_current_page = request.getParameter("qry_current_page");
        redirectAttributes.addAttribute("qry_current_page", qry_current_page);
        String qry_page_size = request.getParameter("qry_page_size");
        redirectAttributes.addAttribute("qry_page_size", qry_page_size);
        log.info("删除乘客:{}", id_card);
        int flag = passengerService.delete_passenger(id_card);
        if (flag == 1) {
            return "redirect:/passenger";
        }
        return "redirect:/passenger";
    }
}
