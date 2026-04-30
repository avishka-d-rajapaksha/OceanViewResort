@echo off
REM ============================================
REM Ocean View Resort - Diagnostics & Troubleshooting
REM ============================================
REM Check system configuration and identify issues

echo.
echo ============================================
echo   Ocean View Resort - System Diagnostics
echo ============================================
echo.

setlocal enabledelayedexpansion

REM Check Java
echo [*] Checking Java Installation...
java -version >nul 2>&1
if %errorlevel% equ 0 (
    echo [OK] Java is installed
    for /f "tokens=*" %%i in ('java -version 2^>^&1') do set JAVA_VER=%%i
    echo     !JAVA_VER!
) else (
    echo [FAIL] Java is NOT installed
    echo     Action: Install Java 17 from https://www.oracle.com/java/technologies/downloads/
)

echo.

REM Check Maven
echo [*] Checking Maven Installation...
mvn -version >nul 2>&1
if %errorlevel% equ 0 (
    echo [OK] Maven is installed
    for /f "tokens=*" %%i in ('mvn -version 2^>^&1 ^| findstr "Apache Maven"') do echo     %%i
) else (
    echo [FAIL] Maven is NOT installed
    echo     Action: Install Maven from https://maven.apache.org/download.cgi
)

echo.

REM Check MySQL
echo [*] Checking MySQL Installation...
mysql --version >nul 2>&1
if %errorlevel% equ 0 (
    echo [OK] MySQL is installed
    mysql --version
) else (
    echo [FAIL] MySQL is NOT installed
    echo     Action: Install MySQL from https://www.mysql.com/downloads/
)

echo.

REM Check MySQL Connection
echo [*] Checking MySQL Server...
mysql -u root -e "SELECT 1" >nul 2>&1
if %errorlevel% equ 0 (
    echo [OK] MySQL Server is running
) else (
    echo [FAIL] MySQL Server is NOT running or password is incorrect
    echo     Action: Start MySQL Server
    echo     Or configure credentials in src/main/java/com/oceanview/db/DBConnection.java
)

echo.

REM Check Database
echo [*] Checking OceanViewDB Database...
mysql -u root -e "USE OceanViewDB; SELECT 1;" >nul 2>&1
if %errorlevel% equ 0 (
    echo [OK] Database OceanViewDB exists
) else (
    echo [FAIL] Database OceanViewDB NOT found
    echo     Action: Run SETUP_DATABASE.bat to create database
    echo     Or manually create database and import oceanviewdb.sql
)

echo.

REM Check Maven Configuration
echo [*] Checking Maven Configuration...
if exist "pom.xml" (
    echo [OK] pom.xml found
) else (
    echo [FAIL] pom.xml NOT found
    echo     Make sure you're in the correct project directory
)

echo.

REM Check Project Structure
echo [*] Checking Project Structure...
if exist "src\main\java" (
    echo [OK] src/main/java found
) else (
    echo [FAIL] src/main/java NOT found
)

if exist "src\main\resources" (
    echo [OK] src/main/resources found
) else (
    echo [INFO] src/main/resources not found (not critical)
)

echo.
echo ============================================
echo   SUMMARY
echo ============================================
echo.
echo If you see [FAIL] items, install the missing components.
echo.
echo Quick Setup:
echo 1. Install Java 17
echo 2. Install Maven
echo 3. Install MySQL
echo 4. Run SETUP_DATABASE.bat
echo 5. Run START.bat
echo.
echo For more help, see DEPLOYMENT_GUIDE.md
echo.
pause
