package com.wardenfar.paymybuddy.repository;

import com.wardenfar.paymybuddy.entity.BankTransfer;
import com.wardenfar.paymybuddy.entity.Transaction;
import com.wardenfar.paymybuddy.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankTransferRepository extends CrudRepository<BankTransfer, Long> {

    @Query("SELECT t FROM BankTransfer t WHERE t.user = :user ORDER BY t.date DESC")
    List<BankTransfer> findByUser(@Param("user") User user);

}
