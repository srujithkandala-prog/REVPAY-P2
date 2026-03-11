package com.revpay.service;

public interface WalletService {

    double getBalance(Long userId);

    void addMoney(Long userId, double amount);

    void withdrawMoney(Long userId, double amount);
}