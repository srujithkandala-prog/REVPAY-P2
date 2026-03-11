package com.revpay.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revpay.dto.MoneyRequestDTO;
import com.revpay.entity.MoneyRequest;
import com.revpay.repository.MoneyRequestRepository;
import com.revpay.service.MoneyRequestService;

@Service
public class MoneyRequestServiceImpl implements MoneyRequestService {

    private static final Logger logger = LogManager.getLogger(MoneyRequestServiceImpl.class);

    @Autowired
    private MoneyRequestRepository repository;

    @Override
    public void requestMoney(MoneyRequestDTO dto) {
        logger.info("Money request: {} -> {} | amount: {} | purpose: {}",
                dto.getRequesterEmail(), dto.getReceiverEmail(),
                dto.getAmount(), dto.getPurpose());

        MoneyRequest request = new MoneyRequest();
        request.setRequesterEmail(dto.getRequesterEmail());
        request.setReceiverEmail(dto.getReceiverEmail());
        request.setAmount(dto.getAmount());
        request.setPurpose(dto.getPurpose());

        repository.save(request);
        logger.debug("MoneyRequest saved to database");
    }

    @Override
    public List<MoneyRequestDTO> getRequestsForUser(String receiverEmail) {
        logger.debug("Fetching money requests for receiverEmail: {}", receiverEmail);

        List<MoneyRequest> requests =
                repository.findByReceiverEmailOrderByCreatedDateDesc(receiverEmail);

        logger.info("Found {} money request(s) for: {}", requests.size(), receiverEmail);

        return requests.stream().map(r -> {

            MoneyRequestDTO dto = new MoneyRequestDTO();
            dto.setId(r.getId());
            dto.setRequesterEmail(r.getRequesterEmail());
            dto.setReceiverEmail(r.getReceiverEmail());
            dto.setAmount(r.getAmount());
            dto.setStatus(r.getStatus());
            dto.setCreatedDate(r.getCreatedDate());

            return dto;

        }).collect(Collectors.toList());
    }

    @Override
    public void approveRequest(Long requestId) {
        logger.info("Approving money request ID: {}", requestId);

        MoneyRequest request = repository.findById(requestId).orElse(null);

        if (request != null) {
            request.setStatus("APPROVED");
            repository.save(request);
            logger.debug("Money request {} approved and saved", requestId);
        } else {
            logger.warn("approveRequest: request ID {} not found — no action taken", requestId);
        }
    }

    @Override
    public void rejectRequest(Long requestId) {
        logger.info("Rejecting money request ID: {}", requestId);

        MoneyRequest request = repository.findById(requestId).orElse(null);

        if (request != null) {
            request.setStatus("REJECTED");
            repository.save(request);
            logger.debug("Money request {} rejected and saved", requestId);
        } else {
            logger.warn("rejectRequest: request ID {} not found — no action taken", requestId);
        }
    }
}