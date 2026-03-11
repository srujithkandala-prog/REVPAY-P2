package com.revpay.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name="loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessEmail;

    private Double amount;
    private String purpose;
    private Integer tenureMonths;
    private Double interestRate = 12.0;
    private Double emi;
    private Integer totalMonths;
    private double remainingAmount;
    

    

   

    private int monthsPaid;

    private String status = "PENDING";

    private LocalDateTime createdDate = LocalDateTime.now();

    // ===== GETTERS & SETTERS =====

    public Long getId() { return id; }

    public String getBusinessEmail() { return businessEmail; }
    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Integer getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(Integer tenureMonths) {
        this.tenureMonths = tenureMonths;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getEmi() {
        return emi;
    }

    public void setEmi(Double emi) {
        this.emi = emi;
    }

    public Integer getTotalMonths() {
        return totalMonths;
    }

    public void setTotalMonths(Integer totalMonths) {
        this.totalMonths = totalMonths;
    }
    public double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
    public int getMonthsPaid() {
        return monthsPaid;
    }

    public void setMonthsPaid(int monthsPaid) {
        this.monthsPaid = monthsPaid;
    }
}