package com.wardenfar.paymybuddy.filter;

import com.wardenfar.paymybuddy.entity.User;
import com.wardenfar.paymybuddy.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
@Log4j2
@Order(1)
public class RequestLoggerFilter implements Filter {

    @Autowired
    UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        Optional<User> user = userService.getCurrentUser();
        String userValue = "null";
        if(user.isPresent()){
            userValue = user.get().getEmail();
        }

        log.info("{} - Request {} {}", userValue, req.getMethod(), req.getRequestURI());
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("{} - Error : {}", userValue, e.getMessage(), e);
            return;
        }
        log.info("{} - Response {}", userValue, res.getStatus());
    }
}
