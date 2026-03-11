package com.revpay.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revpay.dto.NotificationDTO;
import com.revpay.entity.Notification;
import com.revpay.repository.NotificationRepository;
import com.revpay.serviceimpl.NotificationServiceImpl;

public class NotificationServiceTest {

    private static final Logger logger = LogManager.getLogger(NotificationServiceTest.class);

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        logger.info("===== NotificationServiceTest setUp complete =====");
    }

    private Notification buildNotification(String email, String message) {
        Notification n = new Notification();
        n.setUserEmail(email);
        n.setMessage(message);
        n.setType("GENERAL");
        n.setIsRead(false);
        return n;
    }

    @Test
    public void testGetUserNotificationsByEmail_ReturnsTwoItems() {
        logger.info("Running: testGetUserNotificationsByEmail_ReturnsTwoItems");

        Notification n1 = buildNotification("alice@test.com", "You received Rs.500");
        Notification n2 = buildNotification("alice@test.com", "Your OTP is 1234");

        when(notificationRepository.findByUserEmailOrderByCreatedDateDesc("alice@test.com"))
                .thenReturn(Arrays.asList(n1, n2));

        List<NotificationDTO> result = notificationService.getUserNotifications("alice@test.com");

        assertNotNull("Result must not be null", result);
        assertEquals("Must return 2 notifications", 2, result.size());
        logger.info("PASS: {} notifications returned", result.size());
    }

    @Test
    public void testGetUserNotificationsByEmail_EmptyForUnknownUser() {
        logger.info("Running: testGetUserNotificationsByEmail_EmptyForUnknownUser");

        when(notificationRepository.findByUserEmailOrderByCreatedDateDesc("ghost@test.com"))
                .thenReturn(Collections.emptyList());

        List<NotificationDTO> result = notificationService.getUserNotifications("ghost@test.com");

        assertNotNull("Result must not be null even for empty list", result);
        assertTrue("Result must be empty for unknown user", result.isEmpty());
        logger.info("PASS: empty list returned for unknown user");
    }

    @Test
    public void testGetUserNotificationsByEmail_RepositoryCalledWithCorrectEmail() {
        logger.info("Running: testGetUserNotificationsByEmail_RepositoryCalledWithCorrectEmail");

        when(notificationRepository.findByUserEmailOrderByCreatedDateDesc("user@test.com"))
                .thenReturn(Collections.emptyList());

        notificationService.getUserNotifications("user@test.com");

        verify(notificationRepository, times(1))
                .findByUserEmailOrderByCreatedDateDesc("user@test.com");
        logger.info("PASS: repository called with correct email parameter");
    }

    @Test
    public void testGetUserNotificationsByEmail_MessageMappedCorrectly() {
        logger.info("Running: testGetUserNotificationsByEmail_MessageMappedCorrectly");

        Notification n = buildNotification("bob@test.com", "Transaction successful");

        when(notificationRepository.findByUserEmailOrderByCreatedDateDesc("bob@test.com"))
                .thenReturn(Collections.singletonList(n));

        List<NotificationDTO> result = notificationService.getUserNotifications("bob@test.com");

        assertEquals("DTO message must match entity message", "Transaction successful", result.get(0).getMessage());
        logger.info("PASS: message mapped correctly = {}", result.get(0).getMessage());
    }

    @Test
    public void testGetUserNotificationsByUserId_ReturnsNullAsStub() {
        logger.info("Running: testGetUserNotificationsByUserId_ReturnsNullAsStub");

        List<NotificationDTO> result = notificationService.getUserNotifications(1L);

        assertNull("Long overload is a TODO stub — must return null", result);
        logger.info("PASS: Long overload returns null (stub behaviour confirmed)");
    }
}
