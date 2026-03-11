package com.revpay.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revpay.dto.MoneyRequestDTO;
import com.revpay.entity.MoneyRequest;
import com.revpay.repository.MoneyRequestRepository;
import com.revpay.serviceimpl.MoneyRequestServiceImpl;

public class MoneyRequestServiceTest {

    private static final Logger logger = LogManager.getLogger(MoneyRequestServiceTest.class);

    @Mock
    private MoneyRequestRepository repository;

    @InjectMocks
    private MoneyRequestServiceImpl moneyRequestService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        logger.info("===== MoneyRequestServiceTest setUp complete =====");
    }

  
    private MoneyRequest buildRequest(String from, String to, double amount, String status) {
        MoneyRequest r = new MoneyRequest();
        r.setRequesterEmail(from);
        r.setReceiverEmail(to);
        r.setAmount(amount);
        r.setStatus(status);
        return r;
    }

    // Helper – builds a MoneyRequestDTO
    private MoneyRequestDTO buildDTO(String from, String to, double amount, String purpose) {
        MoneyRequestDTO dto = new MoneyRequestDTO();
        dto.setRequesterEmail(from);
        dto.setReceiverEmail(to);
        dto.setAmount(amount);
        dto.setPurpose(purpose);
        return dto;
    }

    @Test
    public void testRequestMoney_SaveIsCalledOnce() {
        logger.info("Running: testRequestMoney_SaveIsCalledOnce");

        MoneyRequestDTO dto = buildDTO("alice@test.com", "bob@test.com", 250.0, "Lunch");
        // thenAnswer(i -> i.getArgument(0)) returns whatever was passed to save()
        when(repository.save(any(MoneyRequest.class))).thenAnswer(i -> i.getArgument(0));

        moneyRequestService.requestMoney(dto);

        verify(repository, times(1)).save(any(MoneyRequest.class));
        logger.info("PASS: repository.save() called once for new request");
    }

    @Test
    public void testRequestMoney_RequesterEmailSavedCorrectly() {
        logger.info("Running: testRequestMoney_RequesterEmailSavedCorrectly");

        MoneyRequestDTO dto = buildDTO("mohan@test.com", "raj@test.com", 500.0, "Rent");

        when(repository.save(any(MoneyRequest.class))).thenAnswer(inv -> {
            MoneyRequest saved = inv.getArgument(0);
            assertEquals("Requester email must match DTO", "mohan@test.com", saved.getRequesterEmail());
            return saved;
        });

        moneyRequestService.requestMoney(dto);
        logger.info("PASS: requester email saved correctly");
    }

    @Test
    public void testGetRequestsForUser_ReturnsTwoRequests() {
        logger.info("Running: testGetRequestsForUser_ReturnsTwoRequests");

        MoneyRequest r1 = buildRequest("alice@test.com", "bob@test.com", 100.0, "PENDING");
        MoneyRequest r2 = buildRequest("charlie@test.com", "bob@test.com", 200.0, "PENDING");

        when(repository.findByReceiverEmailOrderByCreatedDateDesc("bob@test.com"))
                .thenReturn(Arrays.asList(r1, r2));

        List<MoneyRequestDTO> results = moneyRequestService.getRequestsForUser("bob@test.com");

        assertEquals("Must return 2 requests for bob", 2, results.size());
        assertEquals("First request must be from alice", "alice@test.com", results.get(0).getRequesterEmail());
        logger.info("PASS: {} requests returned for bob", results.size());
    }

    @Test
    public void testGetRequestsForUser_ReturnsEmptyForNoRequests() {
        logger.info("Running: testGetRequestsForUser_ReturnsEmptyForNoRequests");

        when(repository.findByReceiverEmailOrderByCreatedDateDesc("nobody@test.com"))
                .thenReturn(Collections.emptyList());

        List<MoneyRequestDTO> results = moneyRequestService.getRequestsForUser("nobody@test.com");

        assertNotNull("Result must not be null", results);
        assertTrue("Result must be empty", results.isEmpty());
        logger.info("PASS: empty list returned for user with no requests");
    }

    @Test
    public void testApproveRequest_StatusBecomesApproved() {
        logger.info("Running: testApproveRequest_StatusBecomesApproved");

        MoneyRequest req = buildRequest("a@test.com", "b@test.com", 150.0, "PENDING");

        when(repository.findById(1L)).thenReturn(Optional.of(req));
        when(repository.save(any(MoneyRequest.class))).thenAnswer(i -> i.getArgument(0));

        moneyRequestService.approveRequest(1L);

        assertEquals("Status must be APPROVED", "APPROVED", req.getStatus());
        verify(repository, times(1)).save(req);
        logger.info("PASS: status = {}", req.getStatus());
    }

    @Test
    public void testRejectRequest_StatusBecomesRejected() {
        logger.info("Running: testRejectRequest_StatusBecomesRejected");

        MoneyRequest req = buildRequest("c@test.com", "d@test.com", 300.0, "PENDING");

        when(repository.findById(2L)).thenReturn(Optional.of(req));
        when(repository.save(any(MoneyRequest.class))).thenAnswer(i -> i.getArgument(0));

        moneyRequestService.rejectRequest(2L);

        assertEquals("Status must be REJECTED", "REJECTED", req.getStatus());
        verify(repository, times(1)).save(req);
        logger.info("PASS: status = {}", req.getStatus());
    }

    @Test
    public void testApproveRequest_InvalidIdDoesNotCallSave() {
        logger.info("Running: testApproveRequest_InvalidIdDoesNotCallSave");

        when(repository.findById(999L)).thenReturn(Optional.empty());


        moneyRequestService.approveRequest(999L);

        verify(repository, never()).save(any(MoneyRequest.class));
        logger.info("PASS: save() not called for invalid ID 999");
    }

    @Test
    public void testRejectRequest_InvalidIdDoesNotCallSave() {
        logger.info("Running: testRejectRequest_InvalidIdDoesNotCallSave");

        when(repository.findById(888L)).thenReturn(Optional.empty());

        moneyRequestService.rejectRequest(888L);

        verify(repository, never()).save(any(MoneyRequest.class));
        logger.info("PASS: save() not called for invalid ID 888");
    }
}
