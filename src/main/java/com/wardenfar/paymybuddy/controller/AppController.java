package com.wardenfar.paymybuddy.controller;

import com.wardenfar.paymybuddy.entity.User;
import com.wardenfar.paymybuddy.model.PayForm;
import com.wardenfar.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @Autowired
    UserService userService;

    @GetMapping("/home")
    public String home(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/transfer")
    public String transfer(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("payForm", new PayForm());
        return "transfer";
    }
}
