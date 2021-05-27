package com.wardenfar.paymybuddy.service;

import com.wardenfar.paymybuddy.entity.User;
import com.wardenfar.paymybuddy.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email);
    }
}
