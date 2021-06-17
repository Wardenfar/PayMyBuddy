package com.wardenfar.paymybuddy.controller;

import com.wardenfar.paymybuddy.entity.User;
import com.wardenfar.paymybuddy.model.PayForm;
import com.wardenfar.paymybuddy.repository.UserRepository;
import com.wardenfar.paymybuddy.service.TransferService;
import com.wardenfar.paymybuddy.service.UserService;
import com.wardenfar.paymybuddy.util.RedirectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Transfer controller (pay)
 */
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

    /**
     * Pay another user with an amount
     */
    @PostMapping("/pay")
    public RedirectView pay(@ModelAttribute("payForm") @Valid PayForm form, BindingResult bindingResult) {
        // Get the current user
        User current = userService.getCurrentUser().orElseThrow();

        // find the target with it's id
        Optional<User> connectionOpt = userRepository.findById(form.getConnectionId());

        String msg = null;
        String error = null;

        if (connectionOpt.isEmpty()) {
            // Target user was not found
            error = "Error connection not found";
        } else {
            User connection = connectionOpt.get();
            try {
                // Try to transfer the money
                transferService.transfer(current, connection, form.getAmount());
                // Success
                msg = "Success";
            } catch (Exception e) {
                error = e.getMessage();
            }
        }

        // Redirect with success or error message
        return RedirectUtil.redirectTo("/transfer", msg, error);
    }
}