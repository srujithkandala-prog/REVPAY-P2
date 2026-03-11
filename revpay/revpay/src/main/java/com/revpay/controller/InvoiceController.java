package com.revpay.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.revpay.entity.Invoice;
import com.revpay.entity.Transaction;
import com.revpay.entity.User;
import com.revpay.repository.InvoiceRepository;
import com.revpay.repository.TransactionRepository;
import com.revpay.repository.UserRepository;

@Controller
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    @GetMapping("/create-invoice")
    public String createInvoicePage(Model model) {
        model.addAttribute("invoice", new Invoice());
        return "create-invoice";
    }

    @PostMapping("/create-invoice")
    public String saveInvoice(@ModelAttribute Invoice invoice,
                              Principal principal) {

        invoice.setBusinessEmail(principal.getName());
        if (invoice.getQuantity() != null &&
                invoice.getUnitPrice() != null &&
                invoice.getTax() != null) {

            double subtotal =
                    invoice.getQuantity() *
                    invoice.getUnitPrice();

            double total =
                    subtotal + (subtotal * invoice.getTax() / 100);

            invoice.setAmount(total);
        }

        invoiceRepository.save(invoice);

        return "redirect:/business-dashboard";
    }
    @GetMapping("/invoices")
    public String viewInvoices(Model model,
                               Principal principal) {

        String email = principal.getName();

        List<Invoice> invoices =
                invoiceRepository
                        .findByBusinessEmailOrderByCreatedDateDesc(email);

        model.addAttribute("invoices", invoices);

        return "invoices";
    }
    @GetMapping("/my-invoices")
    public String myInvoices(Model model,
                             Principal principal) {

        String email = principal.getName();

        List<Invoice> invoices =
                invoiceRepository
                        .findByCustomerEmailOrderByCreatedDateDesc(email);

        model.addAttribute("invoices", invoices);

        return "customer-invoices";
    }

    @GetMapping("/pay-invoice/{id}")
    public String payInvoice(@PathVariable Long id,
                             Principal principal) {

        Invoice invoice = invoiceRepository.findById(id).orElse(null);

        if (invoice == null || !"PENDING".equals(invoice.getStatus()))
            return "redirect:/dashboard";

        User customer = userRepository
                .findByEmail(principal.getName()).orElse(null);

        User business = userRepository
                .findByEmail(invoice.getBusinessEmail()).orElse(null);

        if (customer == null || business == null)
            return "redirect:/dashboard";

        if (customer.getWalletBalance() < invoice.getAmount())
            return "redirect:/dashboard";

        // Transfer money
        customer.setWalletBalance(
                customer.getWalletBalance() - invoice.getAmount());

        business.setWalletBalance(
                business.getWalletBalance() + invoice.getAmount());

        userRepository.save(customer);
        userRepository.save(business);

        Transaction t = new Transaction();
        t.setSenderEmail(customer.getEmail());
        t.setReceiverEmail(business.getEmail());
        t.setAmount(invoice.getAmount());
        t.setType("INVOICE_PAYMENT");

        transactionRepository.save(t);

        invoice.setStatus("PAID");
        invoiceRepository.save(invoice);

        return "redirect:/dashboard";
    }
}