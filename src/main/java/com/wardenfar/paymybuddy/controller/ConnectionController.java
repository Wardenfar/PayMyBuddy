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

/**
 * Connection Controller (add / remove)
 */
@Controller
public class ConnectionController {

    @Autowired
    ConnectionService connectionService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    /**
     * Add connection with another user with his email
     */
    @PostMapping("/connections/add")
    public RedirectView addConnection(@ModelAttribute("addConnectionForm") @Valid AddConnectionForm form) {
        // get the current user
        User currentUser = userService.getCurrentUser().orElseThrow();

        String error = null;
        String msg = null;

        // find the target user by mail
        Optional<User> otherOpt = userRepository.findByEmail(form.getEmail());
        if (otherOpt.isEmpty()) {
            // the other user does not exists
            error = "No user found with this email";
        } else {
            User other = otherOpt.get();
            if (currentUser.getId().equals(other.getId())) {
                // Current and Target user are the same : error
                error = "It's you !";
            } else if (currentUser.getConnections().contains(other)) {
                // Users are already connected !
                error = "The connection already exists";
            } else {
                try {
                    // Try to call the service
                    connectionService.connectTwoUsers(currentUser, other);
                    // Success
                    msg = "Success";
                } catch (Exception e) {
                    error = e.getMessage();
                }
            }
        }

        // Redirect with success or error message
        return RedirectUtil.redirectTo("/connections", msg, error);
    }

    @PostMapping("/connections/remove/{otherId}")
    public RedirectView removeConnection(@PathVariable("otherId") Long otherId) {
        // get the current user
        User currentUser = userService.getCurrentUser().orElseThrow();

        String error = null;
        String msg = null;

        // Try to find the target user with it's mail
        Optional<User> otherOpt = userRepository.findById(otherId);
        if (otherOpt.isEmpty()) {
            // target user not found
            error = "No user found with this id";
        } else {
            User other = otherOpt.get();
            if (currentUser.getId().equals(other.getId())) {
                // Current and Target user are the same : error
                error = "It's you !";
            } else if (!currentUser.getConnections().contains(other)) {
                // Users are not connected
                error = "The connection does not exists";
            } else {
                try {
                    // try to call the service
                    connectionService.disconnectTwoUsers(currentUser, other);
                    // success
                    msg = "Success";
                } catch (Exception e) {
                    error = e.getMessage();
                }
            }
        }

        // Redirect with success or error message
        return RedirectUtil.redirectTo("/connections", msg, error);
    }
}
