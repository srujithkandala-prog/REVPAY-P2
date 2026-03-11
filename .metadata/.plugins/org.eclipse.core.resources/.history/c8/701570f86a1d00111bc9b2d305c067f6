package com.revpay.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.revpay.entity.PaymentMethod;
import com.revpay.entity.User;
import com.revpay.repository.PaymentMethodRepository;
import com.revpay.repository.UserRepository;

@Controller
public class PaymentMethodController {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private UserRepository userRepository;

    // ======================
    // VIEW PAYMENT METHODS PAGE
    // ======================

    @GetMapping("/payment-methods")
    public String paymentMethods(Model model, Principal principal) {

        String email = principal.getName();

        User user = userRepository.findByEmail(email).orElse(null);

        List<PaymentMethod> methods =
                paymentMethodRepository.findByUserEmail(email);

        model.addAttribute("user", user);
        model.addAttribute("methods", methods);
        model.addAttribute("card", new PaymentMethod());

        return "payment-methods";
    }

    // ======================
    // ADD CARD WITH VALIDATION
    // ======================

    @PostMapping("/add-card")
    public String addCard(@ModelAttribute PaymentMethod card,
                          Principal principal,
                          Model model) {

        String email = principal.getName();

        User user = userRepository.findByEmail(email).orElse(null);

        List<PaymentMethod> methods =
                paymentMethodRepository.findByUserEmail(email);

        String cardNumber = card.getCardNumber();
        String expiry = card.getExpiry();
        String cvv = card.getCvv();

        // CARD NUMBER VALIDATION (16 digits)
        if(cardNumber == null || !cardNumber.matches("\\d{16}")){
            model.addAttribute("error","Card number must be exactly 16 digits");
            model.addAttribute("user", user);
            model.addAttribute("methods", methods);
            model.addAttribute("card", card);
            return "payment-methods";
        }

        // EXPIRY VALIDATION (MM/YY)
        if(expiry == null || !expiry.matches("(0[1-9]|1[0-2])/[0-9]{2}")){
            model.addAttribute("error","Expiry must be in MM/YY format");
            model.addAttribute("user", user);
            model.addAttribute("methods", methods);
            model.addAttribute("card", card);
            return "payment-methods";
        }

        // CVV VALIDATION (3 digits)
        if(cvv == null || !cvv.matches("\\d{3}")){
            model.addAttribute("error","CVV must be exactly 3 digits");
            model.addAttribute("user", user);
            model.addAttribute("methods", methods);
            model.addAttribute("card", card);
            return "payment-methods";
        }

        card.setUserEmail(email);

        paymentMethodRepository.save(card);

        return "redirect:/payment-methods";
    }

    // ======================
    // DELETE CARD
    // ======================

    @GetMapping("/delete-card/{id}")
    public String deleteCard(@PathVariable Long id) {

        paymentMethodRepository.deleteById(id);

        return "redirect:/payment-methods";
    }

    // ======================
    // SET DEFAULT CARD
    // ======================

    @GetMapping("/set-default/{id}")
    public String setDefault(@PathVariable Long id,
                             Principal principal) {

        String email = principal.getName();

        List<PaymentMethod> cards =
                paymentMethodRepository.findByUserEmail(email);

        for (PaymentMethod c : cards) {
            c.setDefault(false);
            paymentMethodRepository.save(c);
        }

        PaymentMethod selected =
                paymentMethodRepository.findById(id).orElse(null);

        if (selected != null) {
            selected.setDefault(true);
            paymentMethodRepository.save(selected);
        }

        return "redirect:/payment-methods";
    }
}