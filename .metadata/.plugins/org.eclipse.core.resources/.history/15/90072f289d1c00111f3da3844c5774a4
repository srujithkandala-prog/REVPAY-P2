package com.revpay.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revpay.entity.Loan;
import com.revpay.entity.LoanPayment;
import com.revpay.repository.LoanPaymentRepository;
import com.revpay.repository.LoanRepository;
import com.revpay.serviceimpl.LoanServiceImpl;

public class LoanServiceTest {

    private static final Logger logger = LogManager.getLogger(LoanServiceTest.class);

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanPaymentRepository loanPaymentRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        logger.info("===== LoanServiceTest setUp complete =====");
    }

    // Helper – creates an APPROVED loan ready for EMI payment
    private Loan approvedLoan(double totalAmount, double emi) {
        Loan loan = new Loan();
        loan.setBusinessEmail("biz@test.com");
        loan.setAmount(totalAmount);
        loan.setEmi(emi);
        loan.setRemainingAmount(totalAmount);
        loan.setMonthsPaid(0);
        loan.setStatus("APPROVED");
        loan.setTenureMonths(12);
        loan.setInterestRate(12.0);
        return loan;
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 1
    // Method : payLoanEmi()
    // Checks : remaining amount decreases by exactly one EMI
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testPayLoanEmi_RemainingAmountDecreasedByEmi() {
        logger.info("Running: testPayLoanEmi_RemainingAmountDecreasedByEmi");

        Loan loan = approvedLoan(12000.0, 1000.0);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(loanPaymentRepository.save(any(LoanPayment.class))).thenAnswer(i -> i.getArgument(0));

        loanService.payLoanEmi(1L);

        assertEquals("Remaining must reduce by one EMI (12000 - 1000 = 11000)", 11000.0, loan.getRemainingAmount(), 0.001);
        logger.info("PASS: remainingAmount = {}", loan.getRemainingAmount());
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 2
    // Method : payLoanEmi()
    // Checks : monthsPaid counter increments by 1
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testPayLoanEmi_MonthsPaidIncremented() {
        logger.info("Running: testPayLoanEmi_MonthsPaidIncremented");

        Loan loan = approvedLoan(12000.0, 1000.0);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(loanPaymentRepository.save(any(LoanPayment.class))).thenAnswer(i -> i.getArgument(0));

        loanService.payLoanEmi(1L);

        assertEquals("monthsPaid must be 1 after first payment", 1, loan.getMonthsPaid());
        logger.info("PASS: monthsPaid = {}", loan.getMonthsPaid());
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 3
    // Method : payLoanEmi()
    // Checks : when the last EMI is paid, remaining becomes 0
    //          and status becomes COMPLETED
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testPayLoanEmi_LastEmiCompletesLoan() {
        logger.info("Running: testPayLoanEmi_LastEmiCompletesLoan");

        Loan loan = approvedLoan(12000.0, 1000.0);
        loan.setRemainingAmount(1000.0); // only one payment left

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(loanPaymentRepository.save(any(LoanPayment.class))).thenAnswer(i -> i.getArgument(0));

        loanService.payLoanEmi(1L);

        assertEquals("Remaining must be 0 after last EMI", 0.0, loan.getRemainingAmount(), 0.001);
        assertEquals("Status must be COMPLETED after last EMI", "COMPLETED", loan.getStatus());
        logger.info("PASS: loan COMPLETED, remaining = {}", loan.getRemainingAmount());
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 4
    // Method : payLoanEmi()
    // Checks : a LoanPayment record is saved in loanPaymentRepository
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testPayLoanEmi_LoanPaymentRecordIsSaved() {
        logger.info("Running: testPayLoanEmi_LoanPaymentRecordIsSaved");

        Loan loan = approvedLoan(12000.0, 1000.0);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(loanPaymentRepository.save(any(LoanPayment.class))).thenAnswer(i -> i.getArgument(0));

        loanService.payLoanEmi(1L);

        // verify that a LoanPayment record was created and saved
        verify(loanPaymentRepository, times(1)).save(any(LoanPayment.class));
        logger.info("PASS: LoanPayment record saved to repository");
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 5
    // Method : payLoanEmi()
    // Checks : loan.save() is also called once (the loan itself is updated)
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testPayLoanEmi_LoanItselfIsSaved() {
        logger.info("Running: testPayLoanEmi_LoanItselfIsSaved");

        Loan loan = approvedLoan(12000.0, 1000.0);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(loanPaymentRepository.save(any(LoanPayment.class))).thenAnswer(i -> i.getArgument(0));

        loanService.payLoanEmi(1L);

        verify(loanRepository, times(1)).save(loan);
        logger.info("PASS: loanRepository.save() called once");
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 6
    // Method : payLoanEmi()
    // Checks : RuntimeException is thrown when loan status is PENDING
    //          @Test(expected=...) means the test PASSES only if that
    //          exception is actually thrown
    // ──────────────────────────────────────────────────────────────
    @Test(expected = RuntimeException.class)
    public void testPayLoanEmi_PendingLoanThrowsException() {
        logger.info("Running: testPayLoanEmi_PendingLoanThrowsException");

        Loan loan = approvedLoan(12000.0, 1000.0);
        loan.setStatus("PENDING"); // not approved yet

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        loanService.payLoanEmi(1L); // must throw RuntimeException("Loan not approved yet")
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 7
    // Method : payLoanEmi()
    // Checks : RuntimeException is thrown when loan is already fully paid
    // ──────────────────────────────────────────────────────────────
    @Test(expected = RuntimeException.class)
    public void testPayLoanEmi_AlreadyPaidLoanThrowsException() {
        logger.info("Running: testPayLoanEmi_AlreadyPaidLoanThrowsException");

        Loan loan = approvedLoan(12000.0, 1000.0);
        loan.setRemainingAmount(0.0); // fully paid already

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        loanService.payLoanEmi(1L); // must throw RuntimeException("Loan already completed")
    }

    // ──────────────────────────────────────────────────────────────
    // TEST 8
    // Method : payLoanEmi()
    // Checks : two consecutive payments reduce remaining by 2 × EMI
    // ──────────────────────────────────────────────────────────────
    @Test
    public void testPayLoanEmi_TwoConsecutivePayments() {
        logger.info("Running: testPayLoanEmi_TwoConsecutivePayments");

        Loan loan = approvedLoan(12000.0, 1000.0);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(loanPaymentRepository.save(any(LoanPayment.class))).thenAnswer(i -> i.getArgument(0));

        loanService.payLoanEmi(1L); // 12000 - 1000 = 11000
        loanService.payLoanEmi(1L); // 11000 - 1000 = 10000

        assertEquals("After 2 payments, remaining must be 10000", 10000.0, loan.getRemainingAmount(), 0.001);
        assertEquals("monthsPaid must be 2", 2, loan.getMonthsPaid());
        logger.info("PASS: 2 payments done — remaining = {}, monthsPaid = {}", loan.getRemainingAmount(), loan.getMonthsPaid());
    }
}
