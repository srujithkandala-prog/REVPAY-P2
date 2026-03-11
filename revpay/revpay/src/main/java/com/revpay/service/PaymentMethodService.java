package com.revpay.service;

import java.util.List;
import com.revpay.entity.PaymentMethod;

public interface PaymentMethodService {

    List<PaymentMethod> getCardsByUser(String email);

    void saveCard(PaymentMethod card);

    void deleteCard(Long id);
}