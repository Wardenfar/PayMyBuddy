package com.wardenfar.paymybuddy.service;

import com.wardenfar.paymybuddy.config.Constants;
import com.wardenfar.paymybuddy.entity.Transaction;
import com.wardenfar.paymybuddy.entity.User;
import com.wardenfar.paymybuddy.repository.TransactionRepository;
import com.wardenfar.paymybuddy.repository.UserRepository;
import com.wardenfar.paymybuddy.util.MoneyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class TransferService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Transactional(rollbackOn = Exception.class)
    public void transfer(User from, User to, BigDecimal amount) throws Exception {
        amount = MoneyUtil.round(amount);

        if (amount.compareTo(new BigDecimal(0)) <= 0) {
            throw new Exception("The amount must be positive and non zero");
        }

        if (amount.compareTo(maxTransferAmountForUser(from)) > 0) {
            throw new Exception("You don't have enough money");
        }

        BigDecimal amountWithTax = amountWithTax(amount);

        if (amountWithTax.subtract(amount).compareTo(new BigDecimal(0)) <= 0) {
            throw new Exception("Amount to small (minimum 0.2)");
        }

        from.subMoney(amountWithTax);
        to.addMoney(amount);

        Transaction transaction = new Transaction();
        transaction.setFrom(from);
        transaction.setTo(to);
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());

        userRepository.saveAll(Arrays.asList(from, to));
        transactionRepository.save(transaction);
    }

    public BigDecimal amountWithTax(BigDecimal amount) {
        BigDecimal n = amount.add(amount.multiply(Constants.TRANSFER_TAX));
        return MoneyUtil.round(n);
    }

    public BigDecimal maxTransferAmountForUser(User user) {
        BigDecimal n = user.getMoney().multiply(new BigDecimal(1).subtract(Constants.TRANSFER_TAX));
        return MoneyUtil.round(n);
    }
}
