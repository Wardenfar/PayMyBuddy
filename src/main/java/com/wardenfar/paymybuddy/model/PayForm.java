package com.wardenfar.paymybuddy.model;

import lombok.*;

import javax.validation.constraints.Positive;

@Data
public class PayForm {

    @Positive
    long contactId;

    @Positive
    double amount;
}
