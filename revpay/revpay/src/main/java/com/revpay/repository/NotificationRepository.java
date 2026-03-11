package com.revpay.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.revpay.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserEmailOrderByCreatedDateDesc(String email);

}