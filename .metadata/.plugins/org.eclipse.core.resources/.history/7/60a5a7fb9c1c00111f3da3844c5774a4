package com.revpay.serviceimpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.revpay.service.WalletService;

@Service
public class WalletServiceImpl implements WalletService {

    private static final Logger logger = LogManager.getLogger(WalletServiceImpl.class);

    private double balance = 0;

    @Override
    public double getBalance(Long userId) {
        logger.debug("Getting balance for userId: {} — current balance: {}", userId, balance);
        return balance;
    }

    @Override
    public void addMoney(Long userId, double amount) {
        logger.info("Adding {} to wallet for userId: {}", amount, userId);
        balance += amount;
        logger.debug("New balance after addMoney: {}", balance);
    }

    @Override
    public void withdrawMoney(Long userId, double amount) {
        logger.info("Withdrawing {} from wallet for userId: {}", amount, userId);
        balance -= amount;
        logger.debug("New balance after withdrawMoney: {}", balance);
    }
}