package com.revpay.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.revpay.entity.Notification;
import com.revpay.repository.NotificationRepository;

@Controller
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/notifications")
    public String notifications(Model model, Principal principal){

        if(principal == null){
            return "redirect:/login";
        }

        String email = principal.getName();

        List<Notification> notifications =
                notificationRepository
                .findByUserEmailOrderByCreatedDateDesc(email);

        model.addAttribute("notifications", notifications);

        return "notifications";
    }

    // MARK AS READ
    @GetMapping("/notifications/read/{id}")
    public String markAsRead(@PathVariable Long id){

        Notification notification =
                notificationRepository.findById(id).orElse(null);

        if(notification != null){
            notification.setIsRead(true);
            notificationRepository.save(notification);
        }

        return "redirect:/notifications";
    }

}