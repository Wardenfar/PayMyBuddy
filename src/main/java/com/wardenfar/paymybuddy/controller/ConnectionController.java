package com.wardenfar.paymybuddy.controller;

import com.wardenfar.paymybuddy.entity.User;
import com.wardenfar.paymybuddy.model.AddConnectionForm;
import com.wardenfar.paymybuddy.repository.UserRepository;
import com.wardenfar.paymybuddy.service.ConnectionService;
import com.wardenfar.paymybuddy.service.UserService;
import com.wardenfar.paymybuddy.util.RedirectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ConnectionController {

    @Autowired
    ConnectionService connectionService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/connections/add")
    public RedirectView addConnection(@ModelAttribute("addConnectionForm") @Valid AddConnectionForm form) {
        User currentUser = userService.getCurrentUser().orElseThrow();

        String error = null;
        String msg = null;

        Optional<User> otherOpt = userRepository.findByEmail(form.getEmail());
        if (otherOpt.isEmpty()) {
            error = "No user found with this email";
        } else {
            User other = otherOpt.get();
            if (currentUser.getConnections().contains(other)) {
                error = "The connection already exists";
            } else {
                try {
                    connectionService.connectTwoUsers(currentUser, other);
                    msg = "Success";
                } catch (Exception e) {
                    error = e.getMessage();
                }
            }
        }

        return RedirectUtil.redirectTo("/connections", msg, error);
    }

    @PostMapping("/connections/remove/{otherId}")
    public RedirectView removeConnection(@PathVariable("otherId") Long otherId) {
        User currentUser = userService.getCurrentUser().orElseThrow();

        String error = null;
        String msg = null;

        Optional<User> otherOpt = userRepository.findById(otherId);
        if (otherOpt.isEmpty()) {
            error = "No user found with this id";
        } else {
            User other = otherOpt.get();
            if (!currentUser.getConnections().contains(other)) {
                error = "The connection does not exists";
            } else {
                try {
                    connectionService.disconnectTwoUsers(currentUser, other);
                    msg = "Success";
                } catch (Exception e) {
                    error = e.getMessage();
                }
            }
        }

        return RedirectUtil.redirectTo("/connections", msg, error);
    }
}
