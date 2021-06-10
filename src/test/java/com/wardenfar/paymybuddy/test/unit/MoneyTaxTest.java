package com.wardenfar.paymybuddy.test.unit;

import com.wardenfar.paymybuddy.service.TransferService;
import com.wardenfar.paymybuddy.util.MoneyUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTaxTest {

    TransferService service = new TransferService();

    /**
     * Test apply tax to the transfer amount
     */
    @Test
    void applyTax() {
        assertEquals(val("10.50"), service.amountWithTax(val("10.00")));
        assertEquals(val("105.00"), service.amountWithTax(val("100.00")));

        assertEquals(val("20.51"), service.amountWithTax(val("19.54")));
        assertEquals(val("20.52"), service.amountWithTax(val("19.55")));

        assertEquals(val("20.52"), service.amountWithTax(val("19.559999")));

        assertEquals(val("105.00"), service.amountWithTax(val("100.00")));
    }

    /**
     * Test the maximum a User can transfer without the tax
     */
    @Test
    void maxTransferAmount(){
        assertEquals(val("10.00"), service.maxTransferAmountForMoney(val("10.50")));
        assertEquals(val("100.00"), service.maxTransferAmountForMoney(val("105.00")));

        assertEquals(val("19.53"), service.maxTransferAmountForMoney(val("20.51")));
        assertEquals(val("19.54"), service.maxTransferAmountForMoney(val("20.52")));

        assertEquals(val("19.54"), service.maxTransferAmountForMoney(val("20.5299")));

        assertEquals(val("100.00"), service.maxTransferAmountForMoney(val("105.00")));
    }

    /**
     * Test founding : we use FLOOR rounding
     */
    @Test
    void rounding(){
        assertEquals(val("10.05"), MoneyUtil.round(val("10.05")));
        assertEquals(val("10.05"), MoneyUtil.round(val("10.051")));
        assertEquals(val("10.05"), MoneyUtil.round(val("10.058")));
        assertEquals(val("10.06"), MoneyUtil.round(val("10.061")));
        assertEquals(val("1052320.99"), MoneyUtil.round(val("1052320.999")));
    }

    BigDecimal val(String n) {
        return new BigDecimal(n);
    }

}