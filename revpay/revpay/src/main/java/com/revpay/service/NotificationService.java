package com.revpay.service;

import java.util.List;
import com.revpay.dto.NotificationDTO;

public interface NotificationService {

    List<NotificationDTO> getUserNotifications(Long userId);

    void markAsRead(Long notificationId);

    void createNotification(NotificationDTO dto);
}