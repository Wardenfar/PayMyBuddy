package com.wardenfar.paymybuddy.controller;

import com.wardenfar.paymybuddy.model.RegisterForm;
import com.wardenfar.paymybuddy.repository.UserRepository;
import com.wardenfar.paymybuddy.service.UserService;
import com.wardenfar.paymybuddy.util.RedirectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Security Controller for login and register
 */
@Controller
public class SecurityController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    /**
     * Login page (the post request is handled by Spring Security)
     */
    @GetMapping("/login")
    public String login(Model model, @RequestParam Optional<String> msg, @RequestParam Optional<String> error) {
        model.addAttribute("msg", msg.orElse(null));
        model.addAttribute("error", error.orElse(null));
        return "login";
    }

    /**
     * Register page
     */
    @GetMapping("/register")
    public String register(Model model, @RequestParam Optional<String> msg, @RequestParam Optional<String> error) {
        model.addAttribute("msg", msg.orElse(null));
        model.addAttribute("error", error.orElse(null));
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    /**
     * Register Action
     */
    @PostMapping("/register")
    public RedirectView registerPost(@Valid RegisterForm form) {
        String message = null;
        String error = null;

        // test if an user already have this email
        boolean alreadyExists = userRepository.existsByEmail(form.getEmail());
        if (alreadyExists) {
            error = "Email already exists";
        } else if (!form.getPassword().equals(form.getConfirm())) {
            error = "The two passwords doesn't match";
        } else {
            try {
                // try to create the user
                userService.createUser(form.getEmail(), form.getFirstName(), form.getLastName(), form.getPassword());
                // success
                message = "Success";
            } catch (Exception e) {
                error = e.getMessage();
            }
        }

        // if no error : then redirect to the login page
        if (error == null) {
            return RedirectUtil.redirectTo("/login", message, null);
        } else {
            // else don't redirect and show the error
            return RedirectUtil.redirectTo("/register", null, error);
        }
    }
}
