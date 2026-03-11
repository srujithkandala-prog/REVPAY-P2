package com.revpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.revpay.entity.BusinessProfile;
import com.revpay.entity.Role;
import com.revpay.entity.User;
import com.revpay.repository.BusinessProfileRepository;
import com.revpay.repository.UserRepository;
import com.revpay.service.OtpService;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinessProfileRepository businessRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ⭐ NEW SERVICE
    @Autowired
    private OtpService otpService;


    // ================================
    // SHOW REGISTER PAGE
    // ================================
    @GetMapping("/register")
    public String showRegisterPage(Model model) {

        model.addAttribute("user", new User());

        return "register";
    }


    // ================================
    // REGISTER USER
    // ================================
    @PostMapping("/register")
    public String registerUser(

            @ModelAttribute User user,

            @RequestParam String confirmPassword,

            @RequestParam(required = false) String businessName,
            @RequestParam(required = false) String gstNumber,
            @RequestParam(required = false) String businessType,
            @RequestParam(required = false) String businessAddress

    ) {

        // =============================
        // PASSWORD MATCH CHECK
        // =============================
        if (!user.getPassword().equals(confirmPassword)) {

            return "redirect:/register?error=password";
        }


        // =============================
        // EMAIL DUPLICATE CHECK
        // =============================
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {

            return "redirect:/register?error=email";
        }


        // =============================
        // ROLE DEFAULT SAFETY
        // =============================
        if (user.getRole() == null) {

            user.setRole(Role.PERSONAL);
        }


        // =============================
        // ENCODE PASSWORD
        // =============================
        user.setPassword(passwordEncoder.encode(user.getPassword()));


        // =============================
        // SAVE USER
        // =============================
        userRepository.save(user);


        /// =============================
     // ⭐ GENERATE OTP (NEW CODE)
     // =============================
     String otp = otpService.generateOtp();

     user.setOtp(otp);
     user.setVerified(false);

     userRepository.save(user);

     // send OTP
     otpService.sendOtp(user.getEmail(), otp);

        // =============================
        // BUSINESS PROFILE SAVE
        // =============================
        if (user.getRole() == Role.BUSINESS) {

            BusinessProfile business = new BusinessProfile();

            business.setBusinessName(businessName);
            business.setGstNumber(gstNumber);
            business.setBusinessType(businessType);
            business.setBusinessAddress(businessAddress);

            business.setUser(user);

            businessRepository.save(business);
        }

        // ⭐ REDIRECT TO OTP PAGE
        return "redirect:/verify-otp?email=" + user.getEmail();
    }


    // ================================
    // OTP PAGE
    // ================================
    @GetMapping("/verify-otp")
    public String verifyOtpPage(@RequestParam String email, Model model){

        model.addAttribute("email", email);

        return "verify-otp";
    }


    // ================================
    // VERIFY OTP
    // ================================
    @PostMapping("/verify-otp")
    public String verifyOtp(

            @RequestParam String email,
            @RequestParam String otp

    ){

        User user = userRepository.findByEmail(email).orElse(null);

        if(user != null && otp.equals(user.getOtp())){

            user.setVerified(true);

            userRepository.save(user);

            return "redirect:/login?verified";
        }

        return "redirect:/verify-otp?error";
    }

}