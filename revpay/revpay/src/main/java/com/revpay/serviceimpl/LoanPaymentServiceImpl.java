package com.revpay.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revpay.dto.LoanPaymentDTO;
import com.revpay.entity.LoanPayment;
import com.revpay.repository.LoanPaymentRepository;
import com.revpay.service.LoanPaymentService;

@Service
public class LoanPaymentServiceImpl implements LoanPaymentService {

    @Autowired
    private LoanPaymentRepository repository;

    @Override
    public void makePayment(LoanPaymentDTO dto) {

        LoanPayment payment = new LoanPayment();

        payment.setLoanId(dto.getLoanId());
        payment.setBusinessEmail(dto.getBusinessEmail());
        payment.setAmountPaid(dto.getAmountPaid());
        payment.setPaymentDate(dto.getPaymentDate());
        payment.setRemainingBalance(dto.getRemainingBalance());

        repository.save(payment);
    }

    @Override
    public List<LoanPaymentDTO> getPaymentsByLoan(Long loanId) {

        return repository.findByLoanId(loanId)
                .stream()
                .map(p -> {

                    LoanPaymentDTO dto = new LoanPaymentDTO();

                    dto.setLoanId(p.getLoanId());
                    dto.setBusinessEmail(p.getBusinessEmail());
                    dto.setAmountPaid(p.getAmountPaid());
                    dto.setPaymentDate(p.getPaymentDate());
                    dto.setRemainingBalance(p.getRemainingBalance());

                    return dto;

                }).collect(Collectors.toList());
    }

    @Override
    public List<LoanPaymentDTO> getPaymentsByBusiness(String email) {

        return repository.findByBusinessEmail(email)
                .stream()
                .map(p -> {

                    LoanPaymentDTO dto = new LoanPaymentDTO();

                    dto.setLoanId(p.getLoanId());
                    dto.setBusinessEmail(p.getBusinessEmail());
                    dto.setAmountPaid(p.getAmountPaid());
                    dto.setPaymentDate(p.getPaymentDate());
                    dto.setRemainingBalance(p.getRemainingBalance());

                    return dto;

                }).collect(Collectors.toList());
    }
}