package com.wardenfar.paymybuddy.model;

import lombok.Data;

/**
 * Registration form
 */
@Data
public class RegisterForm {

    private String email;

    // password and confirmation
    private String password;
    private String confirm;

    private String firstName;
    private String lastName;

}
