package com.revpay.service;

import java.util.List;
import com.revpay.dto.MoneyRequestDTO;

public interface MoneyRequestService {

    void requestMoney(MoneyRequestDTO dto);

    List<MoneyRequestDTO> getRequestsForUser(String receiverEmail);

    void approveRequest(Long requestId);

    void rejectRequest(Long requestId);
}