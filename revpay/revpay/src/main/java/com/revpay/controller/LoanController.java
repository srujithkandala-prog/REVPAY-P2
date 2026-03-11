package com.revpay.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.revpay.entity.Loan;
import com.revpay.entity.User;
import com.revpay.repository.LoanRepository;
import com.revpay.repository.UserRepository;

@Controller
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/apply-loan")
    public String applyLoanPage(Model model) {
        model.addAttribute("loan", new Loan());
        return "apply-loan";
    }

    @PostMapping("/apply-loan")
    public String saveLoan(@ModelAttribute Loan loan,
                           Principal principal) {

        loan.setBusinessEmail(principal.getName());
        if (loan.getAmount() != null &&
                loan.getTenureMonths() != null) {

            // default interest if null
            if (loan.getInterestRate() == null) {
                loan.setInterestRate(12.0);
            }

            double principalAmount = loan.getAmount();
            double annualRate = loan.getInterestRate();

            double monthlyRate =
                    annualRate / 12 / 100;

            int months = loan.getTenureMonths();

            double emi =
                    (principalAmount * monthlyRate *
                            Math.pow(1 + monthlyRate, months))
                    /
                    (Math.pow(1 + monthlyRate, months) - 1);

            loan.setEmi(emi);
            loan.setTotalMonths(months);
        }

        loanRepository.save(loan);

        return "redirect:/business-dashboard";
    }
    @GetMapping("/my-loans")
    public String myLoans(Model model,
                          Principal principal) {

        String email = principal.getName();

        List<Loan> loans =
                loanRepository
                        .findByBusinessEmailOrderByCreatedDateDesc(email);

        model.addAttribute("loans", loans);

        return "my-loans";
    }
    @GetMapping("/approve-loan/{id}")
   
    public String approveLoan(@PathVariable Long id) {

        Loan loan = loanRepository.findById(id).orElse(null);

        if (loan != null && "PENDING".equals(loan.getStatus())) {

            // change status
            loan.setStatus("APPROVED");
            loanRepository.save(loan);
            User business = userRepository
                    .findByEmail(loan.getBusinessEmail())
                    .orElse(null);

            if (business != null) {

                Double current =
                        business.getWalletBalance();

                if (current == null) {
                    current = 0.0;
                }

                business.setWalletBalance(
                        current + loan.getAmount());

                userRepository.save(business);

                System.out.println("Loan amount added to wallet: "
                        + loan.getAmount());
            }
        }

        return "redirect:/admin-loans";
    }

    @GetMapping("/admin-loans")
    public String adminLoans(Model model) {

        model.addAttribute("loans", loanRepository.findAll());

        return "admin-loans";
    }
}