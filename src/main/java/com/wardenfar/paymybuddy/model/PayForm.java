package com.wardenfar.paymybuddy.model;

import lombok.Data;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Form to pay another user
 */
@Data
public class PayForm {

    /**
     * Target user id
     */
    @Positive
    long connectionId;

    @Positive
    BigDecimal amount;
}
