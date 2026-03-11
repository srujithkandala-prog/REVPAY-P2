package com.revpay.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revpay.repository.LoanRepository;
import com.revpay.repository.TransactionRepository;
import com.revpay.repository.UserRepository;
import com.revpay.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public long getTotalUsers() {
        return userRepository.count();
    }

    @Override
    public long getTotalTransactions() {
        return transactionRepository.count();
    }

    @Override
    public long getTotalLoans() {
        return loanRepository.count();
    }

    @Override
    public long getPendingLoans() {
        return loanRepository.countByStatus("PENDING");
    }
}