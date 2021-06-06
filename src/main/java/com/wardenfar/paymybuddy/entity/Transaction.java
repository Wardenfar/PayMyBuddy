package com.wardenfar.paymybuddy.entity;

import com.wardenfar.paymybuddy.config.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @Column(columnDefinition = "Decimal(10,2) default 0.0")
    private BigDecimal amount;

    private LocalDateTime date;

    public String formatAmount() {
        return amount + " " + Constants.MONEY_SYMBOL;
    }
}
