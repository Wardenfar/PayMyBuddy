package com.wardenfar.paymybuddy.model;

import lombok.Data;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Make a bank transfer
 */
@Data
public class BankTransferForm {

    // Amount (from/to the bank)
    @Positive
    BigDecimal amount;
}
