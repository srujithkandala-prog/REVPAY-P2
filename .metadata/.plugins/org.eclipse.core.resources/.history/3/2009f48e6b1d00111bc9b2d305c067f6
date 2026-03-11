package com.revpay.dto;

import jakarta.validation.constraints.*;

public class RegisterRequest {

    @NotBlank(message="Full name required")
    @Pattern(regexp="^[A-Za-z ]{3,50}$",message="Enter valid full name")
    private String fullName;

    @NotBlank(message="Email required")
    @Email(message="Enter valid email")
    private String email;

    @NotBlank(message="Phone required")
    @Pattern(regexp="^[0-9]{10}$",message="Phone must be 10 digits")
    private String phone;

    @NotBlank(message="Password required")
    @Pattern(
        regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
        message="Password must contain uppercase, lowercase, number and special character"
    )
    private String password;

    @NotBlank(message="Confirm password required")
    private String confirmPassword;

    @NotBlank(message="Security question required")
    private String securityQuestion;

    @NotBlank(message="Security answer required")
    private String securityAnswer;

    // GETTERS & SETTERS

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getSecurityQuestion() { return securityQuestion; }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() { return securityAnswer; }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }
}