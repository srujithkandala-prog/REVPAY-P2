package com.revpay.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.revpay.entity.MoneyRequest;

public interface MoneyRequestRepository
        extends JpaRepository<MoneyRequest, Long> {

    List<MoneyRequest>
    findByReceiverEmailOrderByCreatedDateDesc(String email);

    List<MoneyRequest>
    findByRequesterEmailOrderByCreatedDateDesc(String email);

}