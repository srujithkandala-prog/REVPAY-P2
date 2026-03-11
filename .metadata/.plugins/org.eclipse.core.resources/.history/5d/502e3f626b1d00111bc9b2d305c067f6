package com.revpay.controller;

import java.io.PrintWriter;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.revpay.entity.Transaction;
import com.revpay.entity.User;
import com.revpay.repository.TransactionRepository;
import com.revpay.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class TransactionHistoryController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    // =========================
    // TRANSACTION HISTORY PAGE
    // =========================

    @GetMapping("/transactions-history")
    public String transactionHistory(Model model,
                                     Principal principal,
                                     @RequestParam(required=false) String type,
                                     @RequestParam(required=false) String search,
                                     @RequestParam(required=false) Double minAmount,
                                     @RequestParam(required=false) String startDate,
                                     @RequestParam(required=false) String endDate) {

        if(principal == null){
            return "redirect:/login";
        }

        String email = principal.getName();

        User user = userRepository.findByEmail(email).orElse(null);

        if(user == null){
            return "redirect:/login";
        }

        List<Transaction> transactions =
                transactionRepository
                        .findBySenderEmailOrReceiverEmailOrderByTransactionDateDesc(
                                email, email);

        // ===== TYPE FILTER =====
        if (type != null && !type.isEmpty()) {

            transactions = transactions.stream()
                    .filter(t -> t.getType() != null &&
                                 t.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        }

        // ===== SEARCH FILTER =====
        if (search != null && !search.isEmpty()) {

            String keyword = search.toLowerCase();

            transactions = transactions.stream()
                    .filter(t ->
                            (t.getSenderEmail() != null &&
                             t.getSenderEmail().toLowerCase().contains(keyword))
                            ||
                            (t.getReceiverEmail() != null &&
                             t.getReceiverEmail().toLowerCase().contains(keyword))
                    )
                    .collect(Collectors.toList());
        }

        // ===== AMOUNT FILTER =====
        if (minAmount != null) {

            transactions = transactions.stream()
                    .filter(t -> t.getAmount() >= minAmount)
                    .collect(Collectors.toList());
        }

        // ===== START DATE FILTER =====
        if (startDate != null && !startDate.isEmpty()) {

            LocalDate start = LocalDate.parse(startDate);

            transactions = transactions.stream()
                    .filter(t ->
                            t.getTransactionDate()
                             .toLocalDate()
                             .isAfter(start.minusDays(1)))
                    .collect(Collectors.toList());
        }

        // ===== END DATE FILTER =====
        if (endDate != null && !endDate.isEmpty()) {

            LocalDate end = LocalDate.parse(endDate);

            transactions = transactions.stream()
                    .filter(t ->
                            t.getTransactionDate()
                             .toLocalDate()
                             .isBefore(end.plusDays(1)))
                    .collect(Collectors.toList());
        }

        model.addAttribute("user", user);
        model.addAttribute("wallet", user.getWalletBalance());
        model.addAttribute("transactions", transactions);

        model.addAttribute("type", type);
        model.addAttribute("search", search);
        model.addAttribute("minAmount", minAmount);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "transactions-history";
    }


    // =========================
    // EXPORT TRANSACTIONS CSV
    // =========================

    @GetMapping("/transactions-export")
    public void exportTransactions(HttpServletResponse response,
                                   Principal principal) throws Exception {

        if(principal == null){
            return;
        }

        String email = principal.getName();

        List<Transaction> transactions =
                transactionRepository
                        .findBySenderEmailOrReceiverEmailOrderByTransactionDateDesc(
                                email, email);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition",
                "attachment; filename=transactions.csv");

        PrintWriter writer = response.getWriter();

        writer.println("ID,Sender,Receiver,Amount,Type,Date");

        for(Transaction t : transactions){

            writer.println(
                    t.getId() + "," +
                    t.getSenderEmail() + "," +
                    t.getReceiverEmail() + "," +
                    t.getAmount() + "," +
                    t.getType() + "," +
                    t.getTransactionDate()
            );
        }

        writer.flush();
        writer.close();
    }
}