package com.revpay.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revpay.entity.Transaction;
import com.revpay.repository.TransactionRepository;
import com.revpay.serviceimpl.TransactionServiceImpl;

public class TransactionServiceTest {

    private static final Logger logger = LogManager.getLogger(TransactionServiceTest.class);

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        logger.info("===== TransactionServiceTest setUp complete =====");
    }

    // Helper – builds a Transaction without touching the DB
    private Transaction build(String sender, String receiver, double amount, String type) {
        Transaction t = new Transaction();
        t.setSenderEmail(sender);
        t.setReceiverEmail(receiver);
        t.setAmount(amount);
        t.setType(type);
        return t;
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 1
    // Method : getAllTransactions()
    // Checks : returns the list that repository.findAll() provides
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testGetAllTransactions_ReturnsBothRecords() {
        logger.info("Running: testGetAllTransactions_ReturnsBothRecords");

        Transaction t1 = build("alice@test.com", "bob@test.com",   100.0, "SENT");
        Transaction t2 = build("bob@test.com",   "alice@test.com",  50.0, "SENT");

        when(transactionRepository.findAll()).thenReturn(Arrays.asList(t1, t2));

        List<Transaction> result = transactionService.getAllTransactions();

        assertEquals("Must return 2 transactions", 2, result.size());
        logger.info("PASS: getAllTransactions returned {} records", result.size());
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 2
    // Method : getAllTransactions()
    // Checks : returns empty list (not null) when no data exists
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testGetAllTransactions_ReturnsEmptyListWhenNoData() {
        logger.info("Running: testGetAllTransactions_ReturnsEmptyListWhenNoData");

        when(transactionRepository.findAll()).thenReturn(Collections.emptyList());

        List<Transaction> result = transactionService.getAllTransactions();

        assertNotNull("Result must not be null", result);
        assertTrue("Result must be empty", result.isEmpty());
        logger.info("PASS: empty list returned correctly");
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 3
    // Method : saveTransaction()
    // Checks : repository.save() is called exactly once
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testSaveTransaction_CallsRepositorySaveOnce() {
        logger.info("Running: testSaveTransaction_CallsRepositorySaveOnce");

        Transaction t = build("sender@test.com", "receiver@test.com", 500.0, "SENT");
        when(transactionRepository.save(any(Transaction.class))).thenReturn(t);

        transactionService.saveTransaction(t);

        verify(transactionRepository, times(1)).save(t);
        logger.info("PASS: repository.save() called exactly once");
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 4
    // Method : saveTransaction()
    // Checks : the amount field is not modified by the service
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testSaveTransaction_AmountIsNotModified() {
        logger.info("Running: testSaveTransaction_AmountIsNotModified");

        Transaction t = build("a@test.com", "b@test.com", 750.0, "SENT");
        when(transactionRepository.save(any(Transaction.class))).thenReturn(t);

        transactionService.saveTransaction(t);

        assertEquals("Amount must remain 750.0 unchanged", 750.0, t.getAmount(), 0.001);
        logger.info("PASS: amount not modified = {}", t.getAmount());
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 5
    // Method : saveTransaction()
    // Checks : the type field is preserved as SENT
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testSaveTransaction_TypeIsPreserved() {
        logger.info("Running: testSaveTransaction_TypeIsPreserved");

        Transaction t = build("x@test.com", "y@test.com", 200.0, "SENT");
        when(transactionRepository.save(any(Transaction.class))).thenReturn(t);

        transactionService.saveTransaction(t);

        assertEquals("Type must remain SENT", "SENT", t.getType());
        logger.info("PASS: type preserved = {}", t.getType());
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 6
    // Method : saveTransaction()
    // Checks : sender email is preserved after save
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testSaveTransaction_SenderEmailIsPreserved() {
        logger.info("Running: testSaveTransaction_SenderEmailIsPreserved");

        Transaction t = build("priya@test.com", "raj@test.com", 300.0, "SENT");
        when(transactionRepository.save(any(Transaction.class))).thenReturn(t);

        transactionService.saveTransaction(t);

        assertEquals("Sender email must be preserved", "priya@test.com", t.getSenderEmail());
        logger.info("PASS: senderEmail = {}", t.getSenderEmail());
    }
}
