package com.wardenfar.paymybuddy.repository;

import com.wardenfar.paymybuddy.entity.BankTransfer;
import com.wardenfar.paymybuddy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Bank Transfer Repository
 */
public interface BankTransferRepository extends JpaRepository<BankTransfer, Long> {

    /**
     * Find transfer related to an user
     */
    @Query("SELECT t FROM BankTransfer t WHERE t.user = :user ORDER BY t.date DESC")
    List<BankTransfer> findByUser(@Param("user") User user);

}
