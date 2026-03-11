package com.revpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revpay.entity.OtpVerification;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpVerification, Long>{

    Optional<OtpVerification> findByEmail(String email);

}