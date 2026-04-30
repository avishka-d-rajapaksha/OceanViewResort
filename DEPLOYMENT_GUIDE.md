# 🌊 Ocean View Resort - Deployment Guide

## Quick Start (Easiest Method)

### For Windows Users:
1. **Extract the ZIP file** to any location on your computer
2. **Double-click `START.bat`** in the project folder
3. The application will automatically:
   - Check for Java installation
   - Build the project
   - Start the desktop application
   - Open the web browser to http://localhost:8080

**That's it!** The application will launch automatically.

---

## Prerequisites

Before running the application, ensure you have:

### ✅ Java 17 (Required)
- **Download:** https://www.oracle.com/java/technologies/downloads/
- **Verify Installation:**
  ```bash
  java -version
  ```
  You should see version 17.x.x

### ✅ Maven 3.6+ (Required)
- **Download:** https://maven.apache.org/download.cgi
- **Installation Guide:** https://maven.apache.org/install.html
- **Verify Installation:**
  ```bash
  mvn -version
  ```

### ✅ MySQL Server (Required)
- **Download:** https://www.mysql.com/downloads/
- **Verify Running:**
  ```bash
  mysql --version
  ```

### ✅ Database Setup
Before running the application, create the database:

1. **Open MySQL Command Prompt or MySQL Workbench**
2. **Run the SQL script:**
   ```sql
   source path/to/oceanviewdb.sql
   ```
   Or manually create database named `OceanViewDB`

3. **Verify Database Connection Settings in:**
   - File: `src/main/java/com/oceanview/db/DBConnection.java`
   - Default settings:
     - URL: `jdbc:mysql://localhost:3306/OceanViewDB`
     - User: `root`
     - Password: `` (empty)
   - **Modify if your MySQL credentials are different!**

---

## How to Run

### Option 1: Using BAT File (Recommended for Windows)
```
1. Double-click START.bat
2. Wait for the application to launch
```

### Option 2: Using Maven Command
```bash
mvn clean install
mvn javafx:run
```

### Option 3: Using IDE (NetBeans/IntelliJ)
1. Open the project in your IDE
2. Right-click on `Main.java`
3. Select "Run"

---

## Application Features

### Desktop Application (JavaFX)
- **Admin Dashboard** - Manage rooms, guests, and reservations
- **Secure Login** - User authentication
- **Room Management** - Add/edit room details
- **Guest Management** - Track guest information
- **Reservation System** - Create and manage bookings
- **Billing** - Generate invoices

### Web Portal
- **Access:** http://localhost:8080
- **Features:** Room browse, booking, guest info

---

## Troubleshooting

### Problem: "Java is not installed"
- **Solution:** Install Java 17 from https://www.oracle.com/java/
- **Check PATH:** Ensure Java is in your system PATH

### Problem: "Maven is not installed"
- **Solution:** Install Maven from https://maven.apache.org/download.cgi
- **Check PATH:** Add Maven to your system PATH

### Problem: "Database connection error"
- **Solution 1:** Check MySQL is running
  ```bash
  mysql -u root -p
  ```
- **Solution 2:** Verify database exists
  ```sql
  SHOW DATABASES;
  USE OceanViewDB;
  ```
- **Solution 3:** Update database credentials in:
  - `src/main/java/com/oceanview/db/DBConnection.java`

### Problem: "Port 8080 already in use"
- **Solution:** Change the port in `OceanViewServer.java`
  - Find: `SERVER_PORT = 8080`
  - Change to an available port (e.g., 8081)

### Problem: Application crashes on startup
- **Solution 1:** Check database connection
- **Solution 2:** Check console for detailed error messages
- **Solution 3:** Ensure all prerequisites are installed

---

## System Requirements

| Component | Minimum | Recommended |
|-----------|---------|-------------|
| Java      | 17      | 17+         |
| Maven     | 3.6.0   | 3.9+        |
| MySQL     | 5.7     | 8.0+        |
| RAM       | 512 MB  | 2 GB        |
| Disk      | 500 MB  | 1 GB        |

---

## Project Structure

```
OceanViewResort/
├── src/
│   ├── main/java/com/oceanview/
│   │   ├── controller/        # JavaFX controllers
│   │   ├── service/           # Business logic
│   │   ├── dao/               # Database access
│   │   ├── model/             # Data models
│   │   ├── db/                # Database connection
│   │   └── util/              # Utilities
│   └── test/                  # Unit tests
├── pom.xml                    # Maven configuration
├── START.bat                  # Quick start launcher (Windows)
├── run.bat                    # Full launcher (Windows)
└── README.md                  # Project documentation
```

---

## Default Login Credentials

- **Username:** admin
- **Password:** admin123

⚠️ **Change these in production!**

---

## Support & Issues

If you encounter any issues:

1. **Check Database Connection**
   - MySQL must be running
   - Database `OceanViewDB` must exist
   - Verify credentials in `DBConnection.java`

2. **Check Java & Maven**
   ```bash
   java -version
   mvn -version
   ```

3. **Check Application Logs**
   - Look at console output for error messages
   - Check `target/` folder for build logs

4. **Re-build Project**
   ```bash
   mvn clean install
   ```

---

## Version Information

- **Java:** 17
- **JavaFX:** 21.0.1
- **MySQL Connector:** 8.2.0
- **Maven:** 3.6+

---

**Enjoy using Ocean View Resort Management System!** 🌊
