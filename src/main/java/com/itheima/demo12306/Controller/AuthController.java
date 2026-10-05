package com.itheima.demo12306.Controller;

import com.itheima.demo12306.Entity.TrainType;
import com.itheima.demo12306.Entity.User;
import com.itheima.demo12306.Service.AuthService;
import com.itheima.demo12306.Service.TicketService;
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
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private TicketService ticketService;

    @RequestMapping("/login")
    public String login() {
        log.info("登录");
        return "login";
    }

    @RequestMapping("/check_login")
    public String login(HttpServletRequest request, Model model,RedirectAttributes redirectAttributes) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int qry_current_page = Integer.parseInt(request.getParameter("qry_current_page"));
        int qry_page_size = Integer.parseInt(request.getParameter("qry_page_size"));
        log.info("当前页码:{},每页显示数量:{}", qry_current_page, qry_page_size);
        log.info("用户尝试登录: {}", username);
        User user = authService.login(username, password);
        if (user != null) {
//            List<TrainType> trainList = ticketService.find_train_list(qry_current_page,qry_page_size);
//            model.addAttribute("trainList",trainList);
            redirectAttributes.addAttribute("qry_current_page",qry_current_page);
            redirectAttributes.addAttribute("qry_page_size",qry_page_size);
            return "redirect:/success";
        } else {
            return "login";
        }
    }

    @RequestMapping("/register")
    public String register() {
        log.info("注册");
        return "register";
    }

    @RequestMapping("/check_register")
    public String register(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        log.info("用户尝试注册: {}", username);
        int flag = authService.register(username, password, email, telephone);
        if (flag == 1) {
            return "/login";
        }
        return "/register";
    }
}
