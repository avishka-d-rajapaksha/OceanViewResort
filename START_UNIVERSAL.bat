@echo off
REM ============================================
REM Ocean View Resort - Python Universal Launcher
REM ============================================
REM Recommended launcher - works with or without Maven

title Ocean View Resort - Universal Launcher

REM Check if Python is installed
python --version >nul 2>&1
if %errorlevel% neq 0 (
    REM Python not found, try python3
    python3 --version >nul 2>&1
    if %errorlevel% neq 0 (
        echo [ERROR] Python is not installed!
        echo.
        echo This launcher requires Python 3.x
        echo.
        echo Options:
        echo 1. Install Python from: https://www.python.org/downloads/
        echo    (Make sure to check "Add Python to PATH" during installation)
        echo.
        echo 2. Or use START.bat with Maven installed
        echo.
        echo 3. Or open project in NetBeans/IntelliJ and click "Run"
        echo.
        pause
        exit /b 1
    ) else (
        python3 "%~dp0launcher.py"
        pause
        exit /b %errorlevel%
    )
) else (
    python "%~dp0launcher.py"
    pause
    exit /b %errorlevel%
)
