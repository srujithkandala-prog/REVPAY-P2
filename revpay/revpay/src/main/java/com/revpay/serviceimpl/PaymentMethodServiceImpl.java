package com.revpay.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revpay.entity.PaymentMethod;
import com.revpay.repository.PaymentMethodRepository;
import com.revpay.service.PaymentMethodService;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethod> getCardsByUser(String email) {
        return paymentMethodRepository.findByUserEmail(email);
    }

    @Override
    public void saveCard(PaymentMethod card) {
        paymentMethodRepository.save(card);
    }

    @Override
    public void deleteCard(Long id) {
        paymentMethodRepository.deleteById(id);
    }
}