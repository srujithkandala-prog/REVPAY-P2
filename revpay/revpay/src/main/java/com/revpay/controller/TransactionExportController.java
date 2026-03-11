package com.revpay.controller;

import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.revpay.entity.Transaction;
import com.revpay.repository.TransactionRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class TransactionExportController {

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/export-transactions")
    public void exportCSV(HttpServletResponse response,
                          Principal principal) throws Exception {

        String email = principal.getName();

        List<Transaction> transactions =
                transactionRepository
                        .findBySenderEmailOrReceiverEmailOrderByTransactionDateDesc(
                                email, email);

        // CSV response setup
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition",
                "attachment; filename=transactions.csv");

        PrintWriter writer = response.getWriter();

        // HEADER
        writer.println("Sender,Receiver,Amount,Type,Date");

        // DATA
        for (Transaction t : transactions) {

            writer.println(
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