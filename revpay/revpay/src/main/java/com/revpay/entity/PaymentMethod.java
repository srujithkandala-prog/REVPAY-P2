package com.revpay.entity;

import jakarta.persistence.*;

@Entity
@Table(name="payment_methods")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    private String cardNumber;
    private String expiry;
    private String cvv;
    private String billingAddress;

    private boolean isDefault = false;

    // ===== GETTERS & SETTERS =====

    public Long getId() { return id; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiry() { return expiry; }
    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getBillingAddress() { return billingAddress; }
    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
}