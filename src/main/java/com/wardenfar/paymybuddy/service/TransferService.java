package com.wardenfar.paymybuddy.service;

import com.wardenfar.paymybuddy.entity.User;
import com.wardenfar.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

@Service
public class TransferService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public void transfer(User from, User to, double amount) throws Exception {
        if (amount > from.getMoney()) {
            throw new Exception("You don't have enough money");
        }

        from.subMoney(amount);
        to.addMoney(amount);

        userRepository.saveAll(Arrays.asList(from, to));
    }
}
