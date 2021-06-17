package com.wardenfar.paymybuddy.controller;

import com.wardenfar.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Controller advice : add to all views the model attribute currentUser
 */
@ControllerAdvice
public class AppControllerAdvice {

    @Autowired
    UserService userService;

    /**
     * Add currentUser to all models
     */
    @ModelAttribute
    public void userModel(Model model) {
        model.addAttribute("user", userService.getCurrentUser().orElse(null));
    }
}
