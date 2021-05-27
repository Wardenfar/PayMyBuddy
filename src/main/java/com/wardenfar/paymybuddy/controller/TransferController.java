package com.wardenfar.paymybuddy.controller;

import com.wardenfar.paymybuddy.entity.User;
import com.wardenfar.paymybuddy.model.PayForm;
import com.wardenfar.paymybuddy.repository.UserRepository;
import com.wardenfar.paymybuddy.service.TransferService;
import com.wardenfar.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class TransferController {

    @Autowired
    UserService userService;

    @Autowired
    TransferService transferService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/pay")
    public String pay(Model model, @ModelAttribute("payForm") PayForm form, BindingResult bindingResult) {
        System.out.println(form.toString());
        System.out.println(bindingResult.getAllErrors());
        User current = userService.getCurrentUser();

        model.addAttribute("user", current);

        Optional<User> contactOpt = userRepository.findById(form.getContactId());

        if (contactOpt.isEmpty()) {
            model.addAttribute("error", "Error contact not found");
            return "transfer";
        }

        User contact = contactOpt.get();

        try {
            transferService.transfer(current, contact, form.getAmount());
            model.addAttribute("msg", "Success");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "transfer";
    }
}
