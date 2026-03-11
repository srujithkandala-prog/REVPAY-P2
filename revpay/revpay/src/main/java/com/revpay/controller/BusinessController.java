package com.revpay.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.revpay.entity.Transaction;
import com.revpay.repository.TransactionRepository;

@Controller
public class BusinessController {

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/business/loan-repayments")
    public String loanRepayments(Model model, Principal principal) {

        String email = principal.getName();

        List<Transaction> repayments =
                transactionRepository.findLoanRepayments(email);

        model.addAttribute("repayments", repayments);

        return "business-loan-repayments";
    }

}