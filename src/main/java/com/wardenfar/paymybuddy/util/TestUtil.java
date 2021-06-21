package com.wardenfar.paymybuddy.util;

import com.wardenfar.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Initialize database at startup for tests
 */
@Component
@Profile("it")
public class TestUtil implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        userService.createUser("panda@gmail.com", "Panda", "ROUX", "testtest");
        userService.createUser("girafe@gmail.com", "Girafe", "GIRAFE", "testtest");
    }
}
