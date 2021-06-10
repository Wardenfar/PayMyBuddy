package com.wardenfar.paymybuddy.controller;

import com.wardenfar.paymybuddy.entity.User;
import com.wardenfar.paymybuddy.model.BankTransferForm;
import com.wardenfar.paymybuddy.model.PayForm;
import com.wardenfar.paymybuddy.repository.UserRepository;
import com.wardenfar.paymybuddy.service.TransferService;
import com.wardenfar.paymybuddy.service.UserService;
import com.wardenfar.paymybuddy.util.MoneyUtil;
import com.wardenfar.paymybuddy.util.RedirectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.math.BigDecimal;
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
    public RedirectView pay(@ModelAttribute("payForm") @Valid PayForm form, BindingResult bindingResult) {
        User current = userService.getCurrentUser().orElseThrow();

        Optional<User> connectionOpt = userRepository.findById(form.getConnectionId());

        String msg = null;
        String error = null;

        if (connectionOpt.isEmpty()) {
            error = "Error connection not found";
        } else {
            User connection = connectionOpt.get();
            try {
                transferService.transfer(current, connection, form.getAmount());
                msg = "Success";
            } catch (Exception e) {
                error = e.getMessage();
            }
        }

        return RedirectUtil.redirectTo("/transfer", msg, error);
    }
}