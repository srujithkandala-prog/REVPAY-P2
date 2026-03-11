package com.revpay.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revpay.dto.AdminLogDTO;
import com.revpay.entity.AdminLog;
import com.revpay.repository.AdminLogRepository;
import com.revpay.service.AdminLogService;

@Service
public class AdminLogServiceImpl implements AdminLogService {

    @Autowired
    private AdminLogRepository repository;

    @Override
    public void logAction(AdminLogDTO dto) {

        AdminLog log = new AdminLog();

        log.setAction(dto.getAction());
        log.setAdminEmail(dto.getAdminEmail());

        repository.save(log);
    }

    @Override
    public List<AdminLogDTO> getLogsByAdmin(String email) {

        return repository.findByAdminEmail(email)
                .stream()
                .map(log -> {

                    AdminLogDTO dto = new AdminLogDTO();

                    dto.setId(log.getId());
                    dto.setAction(log.getAction());
                    dto.setAdminEmail(log.getAdminEmail());
                    dto.setCreatedDate(log.getCreatedDate());

                    return dto;

                }).toList();
    }
}