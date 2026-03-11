package com.revpay.service;

public interface AdminService {

    long getTotalUsers();

    long getTotalTransactions();

    long getTotalLoans();

    long getPendingLoans();
}