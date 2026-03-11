package com.revpay.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revpay.dto.NotificationDTO;
import com.revpay.entity.Notification;
import com.revpay.repository.NotificationRepository;
import com.revpay.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LogManager.getLogger(NotificationServiceImpl.class);

    @Autowired
    private NotificationRepository notificationRepository;

    public List<NotificationDTO> getUserNotifications(String email) {
        logger.debug("Fetching notifications for email: {}", email);

        List<Notification> notifications =
                notificationRepository.findByUserEmailOrderByCreatedDateDesc(email);

        logger.info("Found {} notification(s) for: {}", notifications.size(), email);

        return notifications.stream().map(n -> {

            NotificationDTO dto = new NotificationDTO();
            dto.setId(n.getId());
            dto.setMessage(n.getMessage());
            dto.setCreatedAt(n.getCreatedDate());

            return dto;

        }).collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> getUserNotifications(Long userId) {
        logger.warn("getUserNotifications(Long) called — not yet implemented, returning null");
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void markAsRead(Long notificationId) {
        logger.info("markAsRead called for notificationId: {} — not yet implemented", notificationId);
        // TODO Auto-generated method stub
    }

    @Override
    public void createNotification(NotificationDTO dto) {
        logger.info("createNotification called with message: {} — not yet implemented", dto.getMessage());
        // TODO Auto-generated method stub
    }
}