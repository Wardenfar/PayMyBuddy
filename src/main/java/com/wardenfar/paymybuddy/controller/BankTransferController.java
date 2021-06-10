package com.wardenfar.paymybuddy.controller;

import com.wardenfar.paymybuddy.entity.User;
import com.wardenfar.paymybuddy.model.BankTransferForm;
import com.wardenfar.paymybuddy.service.BankTransferService;
import com.wardenfar.paymybuddy.service.UserService;
import com.wardenfar.paymybuddy.util.MoneyUtil;
import com.wardenfar.paymybuddy.util.RedirectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
public class BankTransferController {

    @Autowired
    UserService userService;

    @Autowired
    BankTransferService bankTransferService;

    @PostMapping("/transfer/fromBank")
    public RedirectView fromBank(@ModelAttribute("bankTransferForm") @Valid BankTransferForm form) {
        User current = userService.getCurrentUser().orElseThrow();

        String msg = null;
        String error = null;
        try {
            bankTransferService.updateUserMoney(current, form.getAmount(), BankTransferService.TransferDirection.FROM_BANK);
            msg = "Success";
        } catch (Exception e) {
            error = e.getMessage();
        }
        return RedirectUtil.redirectTo("/profile", msg, error);
    }

    @PostMapping("/transfer/toBank")
    public RedirectView toBank(@ModelAttribute("bankTransferForm") @Valid BankTransferForm form) {
        User current = userService.getCurrentUser().orElseThrow();

        String msg = null;
        String error = null;
        try {
            bankTransferService.updateUserMoney(current, form.getAmount(), BankTransferService.TransferDirection.TO_BANK);
            msg = "Success";
        } catch (Exception e) {
            error = e.getMessage();
        }
        return RedirectUtil.redirectTo("/profile", msg, error);
    }
}
