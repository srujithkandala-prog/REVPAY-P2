package com.revpay.service;

import java.util.List;
import com.revpay.dto.BusinessVerificationDTO;

public interface BusinessVerificationService {

    void submitVerification(BusinessVerificationDTO dto);

    List<BusinessVerificationDTO> getPendingVerifications();

    List<BusinessVerificationDTO> getBusinessVerifications(String email);

    void approveVerification(Long id);

    void rejectVerification(Long id);
}