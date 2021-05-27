package com.wardenfar.paymybuddy.entity;

import com.wardenfar.paymybuddy.config.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(targetEntity = User.class)
    private User from;

    @ManyToOne(targetEntity = User.class)
    private User to;

    private double amount;

    private LocalDateTime date;

    public String formatAmount() {
        return amount + " " + Constants.MONEY_SYMBOL;
    }
}
