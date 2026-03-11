package com.revpay.service;

import java.util.List;

import com.revpay.dto.LoanPaymentDTO;

public interface LoanPaymentService {

    void makePayment(LoanPaymentDTO dto);

    List<LoanPaymentDTO> getPaymentsByLoan(Long loanId);

    List<LoanPaymentDTO> getPaymentsByBusiness(String email);
}