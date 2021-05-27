package com.wardenfar.paymybuddy.service;

import com.wardenfar.paymybuddy.entity.User;
import com.wardenfar.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

@Service
public class ConnectionService {

    @Autowired
    UserRepository userRepository;

    @Transactional(rollbackOn = Exception.class)
    public void connectTwoUsers(User a, User b) {
        a.getConnections().add(b);
        b.getConnections().add(a);

        userRepository.saveAll(Arrays.asList(a, b));
    }
}
