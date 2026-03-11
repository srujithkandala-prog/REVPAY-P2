package com.revpay.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class AdminLog {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String action;

private String adminEmail;

private LocalDateTime createdDate = LocalDateTime.now();

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getAction() {
	return action;
}

public void setAction(String action) {
	this.action = action;
}

public String getAdminEmail() {
	return adminEmail;
}

public void setAdminEmail(String adminEmail) {
	this.adminEmail = adminEmail;
}

public LocalDateTime getCreatedDate() {
	return createdDate;
}

public void setCreatedDate(LocalDateTime createdDate) {
	this.createdDate = createdDate;
}



}