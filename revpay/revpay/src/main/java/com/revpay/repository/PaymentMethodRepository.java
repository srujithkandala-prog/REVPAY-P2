package com.revpay.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.revpay.entity.PaymentMethod;

public interface PaymentMethodRepository
        extends JpaRepository<PaymentMethod, Long> {

    List<PaymentMethod>
    findByUserEmail(String email);
}