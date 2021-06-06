package com.wardenfar.paymybuddy.model;

import lombok.Data;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class PayForm {

    @Positive
    long connectionId;

    @Positive
    BigDecimal amount;
}
