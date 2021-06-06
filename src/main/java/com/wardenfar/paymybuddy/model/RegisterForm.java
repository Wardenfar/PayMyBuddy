package com.wardenfar.paymybuddy.model;

import lombok.Data;

@Data
public class RegisterForm {

    private String email;

    private String password;
    private String confirm;

    private String firstName;
    private String lastName;

}
