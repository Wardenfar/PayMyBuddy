package com.wardenfar.paymybuddy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wardenfar.paymybuddy.entity.User;
import com.wardenfar.paymybuddy.model.AddConnectionForm;
import com.wardenfar.paymybuddy.model.BankTransferForm;
import com.wardenfar.paymybuddy.model.PayForm;
import com.wardenfar.paymybuddy.repository.TransactionRepository;
import com.wardenfar.paymybuddy.service.ChartService;
import com.wardenfar.paymybuddy.service.TransferService;
import com.wardenfar.paymybuddy.service.UserService;
import com.wardenfar.paymybuddy.util.RedirectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class AppController {

    @Autowired
    UserService userService;

    @Autowired
    TransferService transferService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ChartService chartService;

    @GetMapping("/")
    public RedirectView index() {
        if(userService.isConnected()){
            return RedirectUtil.redirectTo("/home", null, null);
        }else{
            return RedirectUtil.redirectTo("/login", null, null);
        }
    }

    @GetMapping("/home")
    public String home(Model model) throws JsonProcessingException {
        model.addAttribute("page", "home");
        User user = userService.getCurrentUser().orElseThrow();
        model.addAttribute("chartValues", chartService.moneyChartToJson(user));
        return "home";
    }

    @GetMapping("/transfer")
    public String transfer(Model model, @RequestParam Optional<String> msg, @RequestParam Optional<String> error) {
        User user = userService.getCurrentUser().orElseThrow();
        model.addAttribute("maxAmount", transferService.maxTransferAmountForUser(user));
        model.addAttribute("page", "transfer");
        model.addAttribute("msg", msg.orElse(null));
        model.addAttribute("error", error.orElse(null));
        model.addAttribute("payForm", new PayForm());
        model.addAttribute("transactions", transactionRepository.findByUserAny(user));
        return "transfer";
    }

    @GetMapping("/profile")
    public String profile(Model model, @RequestParam Optional<String> msg, @RequestParam Optional<String> error) {
        User user = userService.getCurrentUser().orElseThrow();
        model.addAttribute("maxAmount", transferService.maxTransferAmountForUser(user));
        model.addAttribute("page", "profile");
        model.addAttribute("msg", msg.orElse(null));
        model.addAttribute("error", error.orElse(null));
        model.addAttribute("bankTransferForm", new BankTransferForm());
        model.addAttribute("bankTransfers", user.getBankTransfers());
        return "profile";
    }

    @GetMapping("/connections")
    public String connections(Model model, @RequestParam Optional<String> msg, @RequestParam Optional<String> error) {
        model.addAttribute("page", "connections");
        model.addAttribute("msg", msg.orElse(null));
        model.addAttribute("error", error.orElse(null));
        model.addAttribute("addConnectionForm", new AddConnectionForm());
        return "connections";
    }
}
