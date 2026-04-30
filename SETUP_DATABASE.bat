@echo off
REM ============================================
REM Ocean View Resort - Database Setup Helper
REM ============================================
REM Run this script to set up MySQL database

echo.
echo ============================================
echo   Ocean View Resort - Database Setup
echo ============================================
echo.

echo [*] Checking MySQL installation...
mysql --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] MySQL is not installed or not in PATH!
    echo [INFO] Download MySQL from: https://www.mysql.com/downloads/
    pause
    exit /b 1
)

echo [OK] MySQL is installed

echo.
echo Enter MySQL root password (press Enter if no password):
set /p PASSWORD="Password: "

echo.
echo [*] Creating database OceanViewDB...

REM Create database with default structure
mysql -u root -p%PASSWORD% -e "CREATE DATABASE IF NOT EXISTS OceanViewDB;" 2>nul

if %errorlevel% neq 0 (
    echo [ERROR] Failed to create database!
    echo [INFO] Make sure MySQL server is running
    echo [INFO] Check your password
    pause
    exit /b 1
)

echo [OK] Database OceanViewDB created

REM Import tables from SQL script if it exists
if exist "oceanviewdb.sql" (
    echo [*] Importing tables...
    mysql -u root -p%PASSWORD% OceanViewDB < oceanviewdb.sql 2>nul
    if %errorlevel% neq 0 (
        echo [WARNING] Could not import SQL script
        echo [INFO] You may need to manually import: oceanviewdb.sql
    ) else (
        echo [OK] Database tables created
    )
) else (
    echo [INFO] oceanviewdb.sql not found
    echo [INFO] You may need to manually create tables
    echo [INFO] Or restore from a backup
)

echo.
echo [OK] Database setup complete!
echo.
echo Next steps:
echo 1. Verify database connection settings in DBConnection.java
echo 2. Run START.bat to launch the application
echo.
pause
