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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public String fullname() {
        return firstName + " " + lastName;
    }

    public void addMoney(BigDecimal amount) {
        this.money = MoneyUtil.round(this.money.add(amount));
    }

    public void subMoney(BigDecimal amount) {
        this.money = MoneyUtil.round(this.money.subtract(amount));
    }

    public String formatMoney() {
        return money + " " + Constants.MONEY_SYMBOL;
    }
}
