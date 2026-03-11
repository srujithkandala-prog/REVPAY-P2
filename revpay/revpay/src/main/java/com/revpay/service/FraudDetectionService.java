package com.revpay.service;

import java.util.List;
import com.revpay.entity.Transaction;

public interface FraudDetectionService {

    List<Transaction> detectSuspiciousTransactions();
}