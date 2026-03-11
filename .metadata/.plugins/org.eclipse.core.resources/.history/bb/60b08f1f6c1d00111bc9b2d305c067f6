package com.revpay.serviceimpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revpay.entity.Loan;
import com.revpay.entity.LoanPayment;
import com.revpay.repository.LoanPaymentRepository;
import com.revpay.repository.LoanRepository;
import com.revpay.service.LoanService;

@Service
public class LoanServiceImpl implements LoanService {

    private static final Logger logger = LogManager.getLogger(LoanServiceImpl.class);

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanPaymentRepository loanPaymentRepository;

    @Override
    public void payLoanEmi(Long loanId) {
        logger.info("EMI payment requested for loanId: {}", loanId);

        Loan loan = loanRepository.findById(loanId).orElseThrow();

        // check loan approval
        if (!loan.getStatus().equals("APPROVED")) {
            logger.error("Loan {} cannot accept EMI — status is: {}", loanId, loan.getStatus());
            throw new RuntimeException("Loan not approved yet");
        }

        // check if loan already completed
        if (loan.getRemainingAmount() <= 0) {
            logger.warn("Loan {} is already fully paid — remaining: {}", loanId, loan.getRemainingAmount());
            throw new RuntimeException("Loan already completed");
        }

        double emi = loan.getEmi();
        double newRemaining = loan.getRemainingAmount() - emi;

        if (newRemaining < 0) {
            newRemaining = 0;
        }

        // update loan
        loan.setRemainingAmount(newRemaining);
        loan.setMonthsPaid(loan.getMonthsPaid() + 1);

        if (newRemaining == 0) {
            loan.setStatus("COMPLETED");
            logger.info("Loan {} is now COMPLETED — all EMIs paid", loanId);
        }

        loanRepository.save(loan);
        logger.debug("Loan {} updated — remainingAmount: {}, monthsPaid: {}",
                loanId, newRemaining, loan.getMonthsPaid());

        // save EMI history
        LoanPayment payment = new LoanPayment();
        payment.setLoanId(loan.getId());
        payment.setBusinessEmail(loan.getBusinessEmail());
        payment.setAmountPaid(emi);
        payment.setPaymentDate(java.time.LocalDate.now());
        payment.setRemainingBalance(newRemaining);

        loanPaymentRepository.save(payment);
        logger.debug("LoanPayment record saved for loanId: {}", loanId);
    }
}