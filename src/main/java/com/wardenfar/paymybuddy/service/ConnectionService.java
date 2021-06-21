package com.wardenfar.paymybuddy.service;

import com.wardenfar.paymybuddy.entity.User;
import com.wardenfar.paymybuddy.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * Service to connection or disconnect two users
 */
@Log4j2
@Service
public class ConnectionService {

    @Autowired
    UserRepository userRepository;

    /**
     * Connect two users and rollback if exception
     */
    @Transactional(rollbackOn = Exception.class)
    public void connectTwoUsers(User a, User b) {
        a.getConnections().add(b);
        b.getConnections().add(a);

        userRepository.saveAll(Arrays.asList(a, b));
        log.info("Connect {} <-> {}", a.fullname(), b.fullname());
    }

    /**
     * Disconnect two users and rollback if exception
     */
    @Transactional(rollbackOn = Exception.class)
    public void disconnectTwoUsers(User a, User b) {
        a.getConnections().remove(b);
        b.getConnections().remove(a);

        userRepository.saveAll(Arrays.asList(a, b));
        log.info("Disconnect {} <-> {}", a.fullname(), b.fullname());
    }
}
