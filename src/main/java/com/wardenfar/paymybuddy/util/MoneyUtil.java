package com.wardenfar.paymybuddy.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyUtil {

    public static BigDecimal round(BigDecimal n) {
        return n.setScale(2, RoundingMode.HALF_DOWN);
    }
}
