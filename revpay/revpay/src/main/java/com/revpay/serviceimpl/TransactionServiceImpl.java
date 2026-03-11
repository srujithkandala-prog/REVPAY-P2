package com.revpay.serviceimpl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revpay.entity.Transaction;
import com.revpay.repository.TransactionRepository;
import com.revpay.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LogManager.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        logger.debug("Fetching all transactions from database");
        List<Transaction> list = transactionRepository.findAll();
        logger.info("getAllTransactions() returned {} records", list.size());
        return list;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        logger.info("Saving transaction: {} -> {} | amount: {} | type: {}",
                transaction.getSenderEmail(),
                transaction.getReceiverEmail(),
                transaction.getAmount(),
                transaction.getType());
        transactionRepository.save(transaction);
        logger.debug("Transaction saved successfully");
    }
}