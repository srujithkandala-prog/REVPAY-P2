package com.revpay.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name="invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessEmail;

    private String customerName;
    private String customerEmail;

    private String description;

    private Double amount;
    private String itemDescription;
    private Integer quantity;
    private Double unitPrice;
    private Double tax;

    private String paymentTerms;
    private java.time.LocalDate dueDate;

    private String status = "PENDING";

    private LocalDateTime createdDate = LocalDateTime.now();

    // ===== GETTERS & SETTERS =====

    public Long getId() { return id; }

    public String getBusinessEmail() { return businessEmail; }
    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    public String getItemDescription() {
        return itemDescription;
    }
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTax() {
        return tax;
    }
    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }
    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public java.time.LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(java.time.LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}