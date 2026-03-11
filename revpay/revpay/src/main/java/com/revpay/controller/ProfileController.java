package com.revpay.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.revpay.entity.User;
import com.revpay.repository.UserRepository;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {

        User user = userRepository
                .findByEmail(principal.getName())
                .orElse(null);

        model.addAttribute("user", user);

        return "profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@RequestParam String fullName,
                                @RequestParam String phone,
                                Principal principal,
                                RedirectAttributes redirectAttributes) {

        User user = userRepository
                .findByEmail(principal.getName())
                .orElse(null);

        if (user != null) {
            user.setFullName(fullName);
            user.setPhone(phone);
            userRepository.save(user);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Profile updated successfully!");
        }

        return "redirect:/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 Model model,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {

        User user = userRepository
                .findByEmail(principal.getName())
                .orElse(null);

        if (user == null)
            return "redirect:/profile";

        if (!passwordEncoder.matches(
                currentPassword,
                user.getPassword())) {

            model.addAttribute("user", user);
            model.addAttribute("error",
                    "Current password incorrect");

            return "profile";
        }

        user.setPassword(
                passwordEncoder.encode(newPassword));

        userRepository.save(user);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Password changed successfully!");

        return "redirect:/profile";
    }

    
    @PostMapping("/set-transaction-pin")
    public String setTransactionPin(
            @RequestParam String transactionPin,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        User user = userRepository
                .findByEmail(principal.getName())
                .orElse(null);

        if (user != null) {

            user.setTransactionPin(transactionPin);

            userRepository.save(user);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Transaction PIN updated successfully!");
        }

        return "redirect:/profile";
    }
}