package com.revpay.service;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.revpay.serviceimpl.WalletServiceImpl;


public class WalletServiceTest {

    private static final Logger logger = LogManager.getLogger(WalletServiceTest.class);

    private WalletServiceImpl walletService;

    @Before
    public void setUp() {

        walletService = new WalletServiceImpl();
        logger.info("===== WalletServiceTest setUp — fresh wallet created =====");
    }

    @Test
    public void testGetBalance_NewWalletIsZero() {
        logger.info("Running: testGetBalance_NewWalletIsZero");

        double balance = walletService.getBalance(1L);

        assertEquals("Brand-new wallet must be 0.0", 0.0, balance, 0.001);
        logger.info("PASS: initial balance = {}", balance);
    }

    @Test
    public void testAddMoney_SingleDeposit() {
        logger.info("Running: testAddMoney_SingleDeposit");

        walletService.addMoney(1L, 500.0);

        assertEquals("Balance must be 500.0 after one deposit", 500.0, walletService.getBalance(1L), 0.001);
        logger.info("PASS: balance after addMoney(500) = {}", walletService.getBalance(1L));
    }

    @Test
    public void testWithdrawMoney_ReducesBalance() {
        logger.info("Running: testWithdrawMoney_ReducesBalance");

        walletService.addMoney(1L, 1000.0);
        walletService.withdrawMoney(1L, 300.0);

        assertEquals("1000 - 300 must be 700", 700.0, walletService.getBalance(1L), 0.001);
        logger.info("PASS: balance after add(1000) → withdraw(300) = {}", walletService.getBalance(1L));
    }

    @Test
    public void testAddMoney_MultipleDepositsAccumulate() {
        logger.info("Running: testAddMoney_MultipleDepositsAccumulate");

        walletService.addMoney(1L, 200.0);
        walletService.addMoney(1L, 300.0);
        walletService.addMoney(1L, 100.0);

        assertEquals("200 + 300 + 100 must total 600", 600.0, walletService.getBalance(1L), 0.001);
        logger.info("PASS: total after 3 adds = {}", walletService.getBalance(1L));
    }

    @Test
    public void testWithdrawMoney_FullWithdrawalLeavesZero() {
        logger.info("Running: testWithdrawMoney_FullWithdrawalLeavesZero");

        walletService.addMoney(1L, 800.0);
        walletService.withdrawMoney(1L, 800.0);

        assertEquals("Full withdrawal must leave 0.0", 0.0, walletService.getBalance(1L), 0.001);
        logger.info("PASS: balance after full withdrawal = {}", walletService.getBalance(1L));
    }

    @Test
    public void testAddMoney_ZeroDoesNotChangeBalance() {
        logger.info("Running: testAddMoney_ZeroDoesNotChangeBalance");

        walletService.addMoney(1L, 500.0);
        walletService.addMoney(1L, 0.0);

        assertEquals("Adding 0 must not change balance", 500.0, walletService.getBalance(1L), 0.001);
        logger.info("PASS: balance unchanged after add(0) = {}", walletService.getBalance(1L));
    }
}
