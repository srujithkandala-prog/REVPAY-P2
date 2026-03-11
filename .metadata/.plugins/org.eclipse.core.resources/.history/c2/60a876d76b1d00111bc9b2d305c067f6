package com.revpay.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.revpay.entity.MoneyRequest;

public interface MoneyRequestRepository
        extends JpaRepository<MoneyRequest, Long> {

    // Incoming requests
    List<MoneyRequest>
    findByReceiverEmailOrderByCreatedDateDesc(String email);

    // Outgoing requests (ADDED)
    List<MoneyRequest>
    findByRequesterEmailOrderByCreatedDateDesc(String email);

}