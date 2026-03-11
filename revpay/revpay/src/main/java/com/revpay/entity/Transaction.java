package com.revpay.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderEmail;
    private String receiverEmail;

    private Double amount;

    private String type; // SENT or RECEIVED

    private LocalDateTime transactionDate = LocalDateTime.now();

    public Long getId() { return id; }

    public String getSenderEmail() { return senderEmail; }
    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverEmail() { return receiverEmail; }
    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() { return type; }
    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
}