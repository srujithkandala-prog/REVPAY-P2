package com.revpay.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.revpay.entity.User;
import com.revpay.repository.UserRepository;

@Controller
public class WalletController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add-money")
    public String showAddMoneyPage(Model model, Principal principal) {

        String email = principal.getName();

        User user = userRepository.findByEmail(email).orElse(null);

        if(user == null){
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("wallet", user.getWalletBalance());

        return "add-money";
    }

    @PostMapping("/add-money")
    public String addMoney(@RequestParam Double amount, Principal principal) {

        String email = principal.getName();

        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {

            user.setWalletBalance(user.getWalletBalance() + amount);

            userRepository.save(user);
        }

        return "redirect:/dashboard";
    }
}