# 💳 RevPay – Digital Payment & Wallet Management System


RevPay is a **full-stack digital payment platform** that simulates modern fintech systems such as **PayPal, Paytm, or PhonePe**.

The platform allows users to manage digital wallets, send and receive money, request payments, generate invoices, apply for loans, and monitor financial transactions.

It supports **individual users, business users, and administrators**, ensuring secure financial operations.

---

# 🚀 Features

## 👤 User Features

* User registration and login
* Secure authentication
* Digital wallet creation
* Add money to wallet
* Send money to other users
* Receive payments
* Request money
* View transaction history
* Export transaction reports
* Manage profile

---

## 🏢 Business Features

* Business account registration
* Business verification system
* Invoice generation
* Receive payments from customers
* Business financial dashboard

---

## 💰 Wallet Features

* Wallet balance management
* Add funds
* Debit funds during payments
* Credit funds when receiving payments
* Wallet transaction tracking

---

## 🔁 Transaction Management

* Send money
* Receive money
* Store transaction details
* Maintain transaction history
* Export transaction reports

---

## 🏦 Loan System

* Apply for loans
* Loan approval management
* Loan repayment tracking
* Installment payment system

---

## 🧾 Invoice System

* Generate invoices
* Send invoices to customers
* Track invoice payments

---

## 🔔 Notification System

* Real-time notifications
* Payment notifications
* Request notifications
* Invoice alerts

---

## 🛠 Admin Features

* Manage users
* Verify businesses
* Monitor system activity
* View admin logs
* System oversight

---

# 🏗 System Architecture



RevPay follows a **layered architecture pattern**.

```
Presentation Layer
(HTML + CSS + Thymeleaf)

        │
        ▼

Controller Layer
(Spring MVC Controllers)

        │
        ▼

Service Layer
(Business Logic)

        │
        ▼

Repository Layer
(Spring Data JPA)

        │
        ▼

Database Layer
(MySQL Database)
```

---

# 🧩 Architecture Diagram

```
+-------------------+
|    Web Browser    |
+---------+---------+
          |
          ▼
+--------------------+
|   Thymeleaf Views  |
+--------------------+
          |
          ▼
+--------------------+
|    Controllers     |
|   (Spring MVC)     |
+--------------------+
          |
          ▼
+--------------------+
|      Services      |
|   Business Logic   |
+--------------------+
          |
          ▼
+--------------------+
|    Repositories    |
|   Spring Data JPA  |
+--------------------+
          |
          ▼
+--------------------+
|     MySQL DB       |
+--------------------+
```

---

# ⚙️ Technology Stack

## Backend

* Java
* Spring Boot
* Spring MVC
* Spring Security
* Spring Data JPA
* Hibernate

---

## Frontend

* HTML
* CSS
* Bootstrap
* Thymeleaf

---

## Database

* MySQL

---

## Build Tool

* Maven

---

## Development Tools

* Eclipse
* IntelliJ IDEA
* Git
* GitHub

---

# 📂 Project Structure

```
revpay
│
├── config
│   ├── SecurityConfig
│   ├── PasswordConfig
│   └── WebConfig
│
├── controller
│   ├── AdminController
│   ├── AuthController
│   ├── BusinessController
│   ├── BusinessVerificationController
│   ├── InvoiceController
│   ├── LoanController
│   ├── LoanPaymentController
│   ├── MoneyRequestController
│   ├── NotificationController
│   ├── PaymentMethodController
│   ├── ProfileController
│   ├── TransactionController
│   ├── TransactionExportController
│   ├── TransactionHistoryController
│   └── WalletController
│
├── dto
│   ├── UserDTO
│   ├── TransactionDTO
│   ├── WalletDTO
│   ├── InvoiceDTO
│   ├── LoanDTO
│   └── NotificationDTO
│
├── entity
│   ├── User
│   ├── Wallet
│   ├── Transaction
│   ├── PaymentMethod
│   ├── BusinessProfile
│   ├── BusinessVerification
│   ├── Loan
│   ├── LoanPayment
│   ├── Invoice
│   └── Notification
│
├── repository
│   ├── UserRepository
│   ├── WalletRepository
│   ├── TransactionRepository
│   ├── LoanRepository
│   ├── InvoiceRepository
│   └── NotificationRepository
│
└── resources
    ├── templates
    ├── static
    └── application.properties
```

---

# 🔐 Security Implementation

Security is implemented using:

* **Spring Security**
* **BCrypt password encryption**
* **Role-based access control**
* **Secure authentication flow**

---

# 🗄 Database Tables

Main tables include:

* users
* wallets
* transactions
* invoices
* loans
* loan_payments
* business_profiles
* business_verifications
* notifications

---

# ▶️ How to Run the Project

## 1️⃣ Clone Repository

```
git clone https://github.com/yourusername/revpay.git
```

---

## 2️⃣ Open in IDE

Open the project in:

* Eclipse
* IntelliJ IDEA

---

## 3️⃣ Configure Database

Edit **application.properties**

```
spring.datasource.url=jdbc:mysql://localhost:3306/revpay
spring.datasource.username=root
spring.datasource.password=yourpassword
```

---

## 4️⃣ Run Application

Run the Spring Boot main class.

Application starts at:

```
http://localhost:8080
```

---

# 🔮 Future Enhancements

* Mobile application integration
* UPI payment gateway integration
* AI-based fraud detection
* Real-time payment processing
* Microservices architecture
* Cloud deployment (AWS)

---

# 👨‍💻 Author

**Srujith Kandala**

Full Stack Java Developer

This project was developed to demonstrate the architecture and functionality of a **digital payment platform similar to PayPal or Paytm**.

---

# ⭐ Support

If you like this project:

⭐ Star the repository
🍴 Fork the project
🚀 Contribute improvements
