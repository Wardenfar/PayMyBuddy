package com.wardenfar.paymybuddy.controller;

import com.wardenfar.paymybuddy.service.UserService;
import com.wardenfar.paymybuddy.util.RedirectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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

    @ExceptionHandler
    protected RedirectView handleError(Exception ex, HttpServletRequest request) {
        String referer = request.getHeader("referer");
        var builder = UriComponentsBuilder.fromHttpUrl(referer);

        String error = ex.getMessage();
        System.out.println(ex.getClass().getName());
        if (ex instanceof BindException) {
            error = handleErrorBinding((BindException) ex);
        }

        return RedirectUtil.processURI(builder, null, error);
    }

    protected String handleErrorBinding(BindException ex) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getDefaultMessage());
        }
        return String.join(" ", errors);
    }
}
