package com.wardenfar.paymybuddy.controller;

import com.wardenfar.paymybuddy.repository.UserRepository;
import com.wardenfar.paymybuddy.util.TestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@Profile("it")
@RestController
public class TestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestUtil testUtil;

    @GetMapping("/reset")
    public String resetDb() throws Exception {
        reset();
        testUtil.run();
        return "Ok";
    }

    @Transactional
    public void reset() {
        userRepository.deleteAllInBatch();
    }
}
