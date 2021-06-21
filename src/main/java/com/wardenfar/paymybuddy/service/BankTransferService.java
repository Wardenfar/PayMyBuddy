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

/**
 * Service for bank transfers from/to bank
 */
@Service
public class BankTransferService {

    @Autowired
    BankTransferRepository bankTransferRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Make the transfer with the given direction
     */
    @Transactional(rollbackOn = Exception.class)
    public void updateUserMoney(User user, BigDecimal amount, TransferDirection direction) throws Exception {
        // the amount must have 2 decimals
        amount = MoneyUtil.round(amount);

        // The amount must be positive
        if (amount.compareTo(new BigDecimal("0")) < 0) {
            throw new Exception("Amount must be positive");
        }

        BankTransfer bankTransfer;
        if (direction == TransferDirection.TO_BANK) {
            // Check that the user has enough money
            if (user.getMoney().compareTo(amount) < 0) {
                throw new Exception("Not enough money");
            }

            // update user
            user.subMoney(amount);
            // create the bank transfer
            bankTransfer = createBankTransfer(user, amount.negate());
        } else {
            // update user
            user.addMoney(amount);
            // create the bank transfer
            bankTransfer = createBankTransfer(user, amount);
        }

        // Save entities
        bankTransferRepository.save(bankTransfer);
        userRepository.save(user);
    }

    /**
     * Create a bank transfer amount
     */
    private BankTransfer createBankTransfer(User user, BigDecimal amount) {
        BankTransfer bankTransfer = new BankTransfer();
        bankTransfer.setUser(user);
        bankTransfer.setAmount(amount);
        bankTransfer.setDate(LocalDateTime.now());
        return bankTransfer;
    }

    public enum TransferDirection {
        FROM_BANK,
        TO_BANK
    }
}
