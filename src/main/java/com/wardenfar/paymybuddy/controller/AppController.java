package com.wardenfar.paymybuddy.controller;

import com.wardenfar.paymybuddy.entity.User;
import com.wardenfar.paymybuddy.model.AddConnectionForm;
import com.wardenfar.paymybuddy.model.PayForm;
import com.wardenfar.paymybuddy.repository.TransactionRepository;
import com.wardenfar.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AppController {

    @Autowired
    UserService userService;

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("/home")
    public String home(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("page", "home");
        return "home";
    }

    @GetMapping("/transfer")
    public String transfer(Model model, @RequestParam Optional<String> msg, @RequestParam Optional<String> error) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("page", "transfer");
        model.addAttribute("msg", msg.orElse(null));
        model.addAttribute("error", error.orElse(null));
        model.addAttribute("payForm", new PayForm());
        model.addAttribute("transactions", transactionRepository.findByUserAny(user));
        return "transfer";
    }

    @GetMapping("/connections")
    public String connections(Model model, @RequestParam Optional<String> msg, @RequestParam Optional<String> error) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("page", "connections");
        model.addAttribute("msg", msg.orElse(null));
        model.addAttribute("error", error.orElse(null));
        model.addAttribute("addConnectionForm", new AddConnectionForm());
        return "connections";
    }
}
