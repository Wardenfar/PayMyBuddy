package com.wardenfar.paymybuddy.test.unit;

import com.wardenfar.paymybuddy.util.RegexUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegexUtilTest {

    @Test
    void checkValidEmail(){
        assertTrue(RegexUtil.validEmail("test.test@gmail.com"));
        assertTrue(RegexUtil.validEmail("user@yahoo.fr"));
    }

    @Test
    void checkInvalidEmail(){
        assertFalse(RegexUtil.validEmail("test@test@gmail.com"));
        assertFalse(RegexUtil.validEmail("user.yahoo.fr"));
    }

}