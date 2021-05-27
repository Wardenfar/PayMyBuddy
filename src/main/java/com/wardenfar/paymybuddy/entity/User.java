package com.wardenfar.paymybuddy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private float money = 0.0f;

    @ManyToMany(targetEntity = User.class)
    private Set<User> contacts;

    public String fullname(){
        return firstName + " " + lastName;
    }

    public void addMoney(double amount) {
        this.money += amount;
    }

    public void subMoney(double amount) {
        this.money -= amount;
    }
}
