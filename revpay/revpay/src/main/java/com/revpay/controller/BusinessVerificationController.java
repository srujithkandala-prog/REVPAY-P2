package com.revpay.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.revpay.entity.BusinessVerification;
import com.revpay.repository.BusinessVerificationRepository;

@Controller
public class BusinessVerificationController {

    @Autowired
    private BusinessVerificationRepository repository;

    @GetMapping("/business-verification")
    public String verificationPage(Model model, Principal principal) {

        String email = principal.getName();

        List<BusinessVerification> list =
                repository.findByBusinessEmail(email);

        model.addAttribute("verifications", list);
        model.addAttribute("verification", new BusinessVerification());

        return "business-verification";
    }

    
    @PostMapping("/submit-verification")
    public String submitVerification(
            @RequestParam("documentName") String documentName,
            @RequestParam("file") MultipartFile file,
            Principal principal) throws IOException {

        
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;

        File dir = new File(uploadDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        File saveFile = new File(uploadDir + fileName);

        file.transferTo(saveFile);

        BusinessVerification verification = new BusinessVerification();

        verification.setBusinessEmail(principal.getName());
        verification.setDocumentName(documentName);
        verification.setDocumentPath("/uploads/" + fileName);
        verification.setStatus("PENDING");

        repository.save(verification);

        return "redirect:/business-verification";
    }

    @GetMapping("/admin-verifications")
    public String adminView(Model model) {

        model.addAttribute("list", repository.findAll());

        return "admin-verifications";
    }
    @GetMapping("/approve-verification/{id}")
    public String approve(@PathVariable Long id) {

        BusinessVerification v =
                repository.findById(id).orElse(null);

        if (v != null) {
            v.setStatus("APPROVED");
            repository.save(v);
        }

        return "redirect:/admin-verifications";
    }

    @GetMapping("/reject-verification/{id}")
    public String reject(@PathVariable Long id) {

        BusinessVerification v =
                repository.findById(id).orElse(null);

        if (v != null) {
            v.setStatus("REJECTED");
            repository.save(v);
        }

        return "redirect:/admin-verifications";
    }
}