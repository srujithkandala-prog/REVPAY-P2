package com.revpay.service;

import java.util.List;
import com.revpay.dto.AdminLogDTO;

public interface AdminLogService {

    void logAction(AdminLogDTO dto);

    List<AdminLogDTO> getLogsByAdmin(String email);
}