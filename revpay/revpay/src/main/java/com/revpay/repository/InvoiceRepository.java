package com.revpay.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.revpay.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByBusinessEmailOrderByCreatedDateDesc(String email);

    List<Invoice> findByCustomerEmailOrderByCreatedDateDesc(String email);
}