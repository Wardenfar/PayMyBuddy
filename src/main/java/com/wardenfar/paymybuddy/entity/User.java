package com.wardenfar.paymybuddy.entity;

import com.wardenfar.paymybuddy.config.Constants;
import com.wardenfar.paymybuddy.util.MoneyUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

/**
 * User Entity
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String password;
    private String roles;

    private String firstName;
    private String lastName;

    @Version
    private long version;

    @Column(columnDefinition = "Decimal(10,2) default 0.0")
    private BigDecimal money = BigDecimal.valueOf(0.0f);

    @ManyToMany(targetEntity = User.class)
    private Set<User> connections;

    @OneToMany(targetEntity = Transaction.class, mappedBy = "from")
    private Set<BankTransfer> fromTransactions;

    @OneToMany(targetEntity = Transaction.class, mappedBy = "to")
    private Set<BankTransfer> toTransactions;

    @OneToMany(targetEntity = BankTransfer.class, mappedBy = "user")
    private Set<BankTransfer> bankTransfers;

    /**
     * Unique field : email
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    /**
     * Unique field : email
     */
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    /**
     * Return the fullname of the user
     */
    public String fullname() {
        return firstName + " " + lastName;
    }

    /**
     * Give money
     */
    public void addMoney(BigDecimal amount) {
        this.money = MoneyUtil.round(this.money.add(amount));
    }

    /**
     * Take money
     */
    public void subMoney(BigDecimal amount) {
        this.money = MoneyUtil.round(this.money.subtract(amount));
    }

    /**
     * Format current money with the symbol
     */
    public String formatMoney() {
        return money + " " + Constants.MONEY_SYMBOL;
    }
}
