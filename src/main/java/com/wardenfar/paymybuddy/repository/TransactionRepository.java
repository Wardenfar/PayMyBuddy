package com.wardenfar.paymybuddy.repository;

import com.wardenfar.paymybuddy.entity.Transaction;
import com.wardenfar.paymybuddy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Transaction Repository
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Find all transaction from an user
     */
    @Query("SELECT t FROM Transaction t WHERE t.from = :user ORDER BY t.date DESC")
    List<Transaction> findByUserFrom(@Param("user") User user);

    /**
     * Find all transaction to an user
     */
    @Query("SELECT t FROM Transaction t WHERE t.to = :user ORDER BY t.date DESC")
    List<Transaction> findByUserTo(@Param("user") User user);

    /**
     * Find all transaction from or to an user
     */
    @Query("SELECT t FROM Transaction t WHERE t.from = :user OR t.to = :user ORDER BY t.date DESC")
    List<Transaction> findByUserAny(@Param("user") User user);
}
