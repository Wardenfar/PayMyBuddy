package com.wardenfar.paymybuddy.controller;

import com.wardenfar.paymybuddy.entity.User;
import com.wardenfar.paymybuddy.model.PayForm;
import com.wardenfar.paymybuddy.repository.UserRepository;
import com.wardenfar.paymybuddy.service.TransferService;
import com.wardenfar.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Controller
public class TransferController {

    final UserService userService;
    final TransferService transferService;
    final UserRepository userRepository;

    @Autowired
    public TransferController(UserService userService, TransferService transferService, UserRepository userRepository) {
        this.userService = userService;
        this.transferService = transferService;
        this.userRepository = userRepository;
    }

    @PostMapping("/pay")
    public RedirectView pay(@ModelAttribute("payForm") PayForm form, BindingResult bindingResult) {
        System.out.println(form.toString());
        System.out.println(bindingResult.getAllErrors());
        User current = userService.getCurrentUser();

        Optional<User> contactOpt = userRepository.findById(form.getContactId());

        String msg = null;
        String error = null;

        if (contactOpt.isEmpty()) {
            error = "Error contact not found";
        } else {

            User contact = contactOpt.get();

            try {
                transferService.transfer(current, contact, form.getAmount());
                msg = "Success";
            } catch (Exception e) {
                error = e.getMessage();
            }
        }

        UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/transfer");

        if(error != null){
            builder.queryParam("error", error);
        }
        if(msg != null){
            builder.queryParam("msg", msg);
        }

        String redirectUri = builder.toUriString();
        return new RedirectView(redirectUri);
    }
}
