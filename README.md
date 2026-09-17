# Project GeMC (Government e-Marketplace Contract Management)

**Project GeMC** is a procurement management web application designed to simplify and streamline the lifecycle management of GeM Contracts for institutes and organizations.

The application provides robust role-based access control, step-by-step contract workflow management, contract search capabilities, and Excel data export features—enabling stakeholders to efficiently manage their specific responsibilities.

---

## 📋 Table of Contents
- [Features](#-features)
- [Workflow Overview](#-workflow-overview)
- [User Roles & Permissions](#-user-roles--permissions)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [Configuration](#-configuration)
- [Database Setup](#-database-setup)
- [Running the Application](#-running-the-application)
- [Security Considerations](#-security-considerations)
- [Future Enhancements](#-future-enhancements)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🚀 Features

### 🔍 Contract Search
* **By Contract Number**: Search directly using the exact GeM Contract Number.
* **By Date Range**: Filter contracts between specified start and end dates.
* **Public Search**: Search contract information without requiring an account login.

### 🔐 Role-Based Access Control (RBAC)
* Secure login system with fine-grained access management.
* Supports four distinct roles: **Admin**, **Buyer**, **Consignee**, and **Payment Authority**.

### 👥 User & Data Management
* **Admin Controls**: Create/modify users, reset passwords, export contract data to Excel, and delete contracts.
* **Excel Export**: Download complete contract data for reporting, audit, and analysis.

---

## 🔄 Workflow Overview

```
┌──────────────┐
│    Buyer     │
└──────┬───────┘
       │
       │ Add Contract Details
       ▼
┌──────────────┐
│  Consignee   │
└──────┬───────┘
       │
       │ Add Consignee Details
       ▼
┌────────────────────┐
│ Payment Authority  │
└──────────┬─────────┘
           │
           │ Add Payment Details
           ▼
┌────────────────────┐
│ Contract Completed │
└────────────────────┘
```

1. **Buyer** adds contract details and forwards the contract to the **Consignee**.
2. **Consignee** inputs receipt/consignment details and forwards it to the **Payment Authority**.
3. **Payment Authority** attaches payment release information to complete the contract cycle.

---

## 👤 User Roles & Permissions

| Feature / Action | Admin | Buyer | Consignee | Payment Authority |
| :--- | :---: | :---: | :---: | :---: |
| Login & Public Search | ✅ | ✅ | ✅ | ✅ |
| Add Contract Details | ❌ | ✅ | ❌ | ❌ |
| Forward to Consignee | ❌ | ✅ | ❌ | ❌ |
| Add Consignee Details | ❌ | ❌ | ✅ | ❌ |
| Forward to Payment Authority | ❌ | ❌ | ✅ | ❌ |
| Add Payment Details | ❌ | ❌ | ❌ | ✅ |
| Manage Users (Create/Edit/Reset) | ✅ | ❌ | ❌ | ❌ |
| Export Data to Excel | ✅ | ❌ | ❌ | ❌ |
| Delete Contracts | ✅ | ❌ | ❌ | ❌ |

---

## 🛠️ Technology Stack

| Domain | Technology |
| :--- | :--- |
| **Backend Framework** | Java 17+, Spring Boot, Spring Data JPA, Hibernate |
| **Frontend Framework** | HTML5, CSS3, JavaScript, Thymeleaf |
| **Database** | MySQL |
| **Build Tool** | Maven |

---

## 📁 Project Structure

```text
ProjectGEMC/
│
├── src/
│   ├── main/
│   │   ├── java/in/iasst/projectGEMC/
│   │   │   ├── config/          # Security & app configuration
│   │   │   ├── controllers/     # Web controllers
│   │   │   ├── entites/         # JPA Data entities
│   │   │   ├── exception/       # Exception handlers
│   │   │   ├── repositories/    # Spring Data repositories
│   │   │   ├── services/        # Business logic layer
│   │   │   └── ProjectGeMApplication.java
│   │   │
│   │   └── resources/
│   │       ├── templates/       # Thymeleaf views (admin, buyer, consignee, etc.)
│   │       ├── static/          # CSS, JS, images
│   │       └── application.properties
│   │
│   └── test/                    # Unit and integration tests
│
├── pom.xml
└── README.md
```

---

## ⚙️ Configuration

Application settings use environment variables with fallback defaults in `application.properties`.

| Variable | Description | Default Value |
| :--- | :--- | :--- |
| `GEMC_PORT` | Application server port | `8081` |
| `GEMC_DB_URL` | MySQL database connection URL | `jdbc:mysql://localhost:3306/gemc` |
| `GEMC_DB_USER` | Database username | `root` |
| `GEMC_DB_PASS` | Database password | `root` |
| `GEMC_DDL_STRATEGY` | Hibernate DDL strategy | `update` |
| `GEMC_SHOW_SQL` | Print SQL logs in console | `false` |
| `GEMC_JPA_OPEN_IN_VIEW` | Spring Open-Session-In-View | `true` |
| `GEMC_LOG_PATH` | Application log output path | `logs/project_gemc_log.log` |
| `GEMC_LOG_LEVEL_ROOT` | Root logging level | `WARN` |
| `GEMC_LOG_LEVEL_APP` | Application package log level | `INFO` |

---

## 🗄️ Database Setup

1. Login to your MySQL server and create the database:
   ```sql
   CREATE DATABASE gemc;
   ```
2. Configure your connection environment variables:
   ```bash
   export GEMC_DB_URL=jdbc:mysql://localhost:3306/gemc
   export GEMC_DB_USER=your_db_user
   export GEMC_DB_PASS=your_db_password
   ```

> **Important**: Never commit production credentials, passwords, or keys to public repositories.

---

## ▶️ Running the Application

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/ProjectGEMC.git
cd ProjectGEMC
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Run the Server
Using Maven:
```bash
mvn spring-boot:run
```
Or run the compiled executable JAR file:
```bash
java -jar target/ProjectGEMC-0.0.1-SNAPSHOT.jar
```

### 4. Access the Portal
Open your web browser and navigate to:
```text
http://localhost:8081
```

---

## 🔒 Security Considerations

* **Database Credentials**: Always use strong passwords and configure via environment variables in production.
* **Schema Management**: Switch `GEMC_DDL_STRATEGY` to `validate` in production environments.
* **HTTPS**: Enforce HTTPS protocol in production deployments.
* **Server Authorization**: Ensure role-based security checks are strictly enforced on backend endpoints (do not rely solely on front-end restrictions).

---

## 🚧 Future Enhancements

- [ ] Analytics dashboard with visual contract statistics
- [ ] Advanced multi-column filtering and sorting
- [ ] File/document attachment support for contracts
- [ ] Automated email/SMS notifications and reminders
- [ ] REST API integrations with external systems
- [ ] Docker containerization for deployment

---

## 🤝 Contributing

1. **Fork** the Repository.
2. **Create** a Feature Branch:
   ```bash
   git checkout -b feature/YourFeature
   ```
3. **Commit** your Changes:
   ```bash
   git commit -m "Add YourFeature"
   ```
4. **Push** to the Branch:
   ```bash
   git push origin feature/YourFeature
   ```
5. **Open** a Pull Request.

---

## 📄 License

This project is licensed under the **MIT License**.

```text
Copyright (c) 2026 Sanjib Kumar Malakar

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

⭐ **If you find Project GeMC helpful, please consider giving the repository a star on GitHub!**
