package com.wardenfar.paymybuddy.service;

import com.wardenfar.paymybuddy.entity.BankTransfer;
import com.wardenfar.paymybuddy.entity.User;
import com.wardenfar.paymybuddy.repository.BankTransferRepository;
import com.wardenfar.paymybuddy.repository.UserRepository;
import com.wardenfar.paymybuddy.util.MoneyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class BankTransferService {

    public enum TransferDirection {
        FROM_BANK,
        TO_BANK
    }

    @Autowired
    BankTransferRepository bankTransferRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional(rollbackOn = Exception.class)
    public void updateUserMoney(User user, BigDecimal amount, TransferDirection direction) throws Exception {
        amount = MoneyUtil.round(amount);

        if (amount.compareTo(new BigDecimal("0")) < 0) {
            throw new Exception("Amount must be positive");
        }

        BankTransfer bankTransfer;
        if (direction == TransferDirection.TO_BANK) {
            if (user.getMoney().compareTo(amount) < 0) {
                throw new Exception("Not enough money");
            }
            user.subMoney(amount);
            bankTransfer = createBankTransfer(user, amount.negate());
        } else {
            user.addMoney(amount);
            bankTransfer = createBankTransfer(user, amount);
        }

        bankTransferRepository.save(bankTransfer);
        userRepository.save(user);
    }

    private BankTransfer createBankTransfer(User user, BigDecimal amount) {
        BankTransfer bankTransfer = new BankTransfer();
        bankTransfer.setUser(user);
        bankTransfer.setAmount(amount);
        bankTransfer.setDate(LocalDateTime.now());
        return bankTransfer;
    }
}
