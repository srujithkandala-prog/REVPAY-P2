package com.revpay.service;

import java.util.List;
import com.revpay.entity.Transaction;

public interface TransactionService {

    List<Transaction> getAllTransactions();

    void saveTransaction(Transaction transaction);
}