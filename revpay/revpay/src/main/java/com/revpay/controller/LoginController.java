package com.revpay.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.revpay.entity.Transaction;
import com.revpay.entity.User;
import com.revpay.repository.TransactionRepository;
import com.revpay.repository.UserRepository;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {

        String email = principal.getName();

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return "redirect:/login";
        }
        if ("ADMIN".equals(user.getRole().name())) {
            return "redirect:/admin-dashboard";
        }
        if ("BUSINESS".equals(user.getRole().name())) {
            return "redirect:/business-dashboard";
        }
        List<Transaction> transactions =
                transactionRepository
                        .findBySenderEmailOrReceiverEmailOrderByTransactionDateDesc(
                                email, email);

        double totalSent = 0;
        double totalReceived = 0;

        for (Transaction t : transactions) {

            if (email.equals(t.getSenderEmail())) {
                totalSent += t.getAmount();
            }

            if (email.equals(t.getReceiverEmail())) {
                totalReceived += t.getAmount();
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("transactions", transactions);
        model.addAttribute("totalSent", totalSent);
        model.addAttribute("totalReceived", totalReceived);
        model.addAttribute("username", user.getFullName());
        model.addAttribute("walletBalance", user.getWalletBalance());
        model.addAttribute("accountId", "REV-" + user.getId());
        model.addAttribute("accountType", user.getRole().name());
        model.addAttribute("totalTransactions", transactions.size());
        model.addAttribute("recentTransactions", transactions);

        return "dashboard";
    }


    @GetMapping("/business-dashboard")
    public String businessDashboard(Model model, Principal principal) {

        String email = principal.getName();

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        if (!"BUSINESS".equals(user.getRole().name())) {
            return "redirect:/dashboard";
        }

        List<Transaction> transactions =
                transactionRepository
                        .findBySenderEmailOrReceiverEmailOrderByTransactionDateDesc(
                                email, email);

        double revenue = 0;

        for (Transaction t : transactions) {
            if (email.equals(t.getReceiverEmail())) {
                revenue += t.getAmount();
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("wallet", user.getWalletBalance());
        model.addAttribute("transactions", transactions);
        model.addAttribute("revenue", revenue);

        return "business-dashboard";
    }


    @GetMapping("/transactions")
    public String transactions(Model model, Principal principal) {

        String email = principal.getName();

        List<Transaction> transactions =
                transactionRepository
                        .findBySenderEmailOrReceiverEmailOrderByTransactionDateDesc(
                                email, email);

        model.addAttribute("transactions", transactions);

        return "transactions";
    }


    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }


    @PostMapping("/verify-email")
    public String verifyEmail(@RequestParam String email, Model model) {

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            model.addAttribute("error", "Email not found");
            return "forgot-password";
        }

        model.addAttribute("email", email);
        model.addAttribute("question", user.getSecurityQuestion());

        return "security-question";
    }


    @PostMapping("/verify-answer")
    public String verifyAnswer(@RequestParam String email,
                               @RequestParam String answer,
                               Model model) {

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return "redirect:/forgot-password";
        }

        if (!answer.equalsIgnoreCase(user.getSecurityAnswer())) {

            model.addAttribute("email", email);
            model.addAttribute("question", user.getSecurityQuestion());
            model.addAttribute("error", "Wrong answer");

            return "security-question";
        }

        model.addAttribute("email", email);

        return "reset-password";
    }


    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword) {

        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }

        return "redirect:/login";
    }
}