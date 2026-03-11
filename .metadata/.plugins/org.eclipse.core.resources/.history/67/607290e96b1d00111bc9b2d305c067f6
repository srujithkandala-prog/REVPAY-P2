package com.revpay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revpay.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // =========================
    // USER TRANSACTION HISTORY
    // =========================
    List<Transaction> findBySenderEmailOrReceiverEmailOrderByTransactionDateDesc(
            String sender,
            String receiver
    );

    // =========================
    // FILTER BY TYPE
    // =========================
    List<Transaction> findBySenderEmailOrReceiverEmailAndTypeOrderByTransactionDateDesc(
            String sender,
            String receiver,
            String type
    );

    // =========================
    // ONLY SENT TRANSACTIONS
    // =========================
    List<Transaction> findBySenderEmailOrderByTransactionDateDesc(String senderEmail);

    // =========================
    // ADMIN SEARCH (SENDER OR RECEIVER)
    // =========================
    List<Transaction> findBySenderEmailOrReceiverEmail(
            String senderEmail,
            String receiverEmail
    );

    // =========================
    // LOAN REPAYMENTS
    // =========================
    @Query("SELECT t FROM Transaction t WHERE t.receiverEmail = :email AND t.type = 'LOAN_REPAYMENT' ORDER BY t.transactionDate DESC")
    List<Transaction> findLoanRepayments(@Param("email") String email);

}