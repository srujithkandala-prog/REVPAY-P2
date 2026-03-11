package com.revpay.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revpay.dto.BusinessVerificationDTO;
import com.revpay.entity.BusinessVerification;
import com.revpay.repository.BusinessVerificationRepository;
import com.revpay.service.BusinessVerificationService;

@Service
public class BusinessVerificationServiceImpl
        implements BusinessVerificationService {

    @Autowired
    private BusinessVerificationRepository repository;

    @Override
    public void submitVerification(BusinessVerificationDTO dto) {

        BusinessVerification verification = new BusinessVerification();

        verification.setBusinessEmail(dto.getBusinessEmail());
        verification.setDocumentName(dto.getDocumentName());
        verification.setDocumentPath(dto.getDocumentPath());
        verification.setStatus("PENDING");

        repository.save(verification);
    }

    @Override
    public List<BusinessVerificationDTO> getPendingVerifications() {

        return repository.findByStatus("PENDING")
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BusinessVerificationDTO> getBusinessVerifications(String email) {

        return repository.findByBusinessEmail(email)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void approveVerification(Long id) {

        BusinessVerification verification =
                repository.findById(id).orElse(null);

        if (verification != null) {
            verification.setStatus("APPROVED");
            repository.save(verification);
        }
    }

    @Override
    public void rejectVerification(Long id) {

        BusinessVerification verification =
                repository.findById(id).orElse(null);

        if (verification != null) {
            verification.setStatus("REJECTED");
            repository.save(verification);
        }
    }

    private BusinessVerificationDTO convertToDTO(BusinessVerification v) {

        BusinessVerificationDTO dto = new BusinessVerificationDTO();

        dto.setId(v.getId());
        dto.setBusinessEmail(v.getBusinessEmail());
        dto.setDocumentName(v.getDocumentName());
        dto.setDocumentPath(v.getDocumentPath());
        dto.setStatus(v.getStatus());
        dto.setCreatedDate(v.getCreatedDate());

        return dto;
    }
}