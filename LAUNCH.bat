@echo off
REM ============================================
REM Ocean View Resort - ONE BAT LAUNCHER
REM ============================================
REM Finds Java and Maven - Launches Application
REM NO PYTHON REQUIRED

setlocal enabledelayedexpansion

title Ocean View Resort - Startup

cls
color 0B
echo ============================================================
echo     OCEAN VIEW RESORT - SYSTEM LAUNCHER
echo ============================================================
echo.

REM ============ Find JAVA ============
echo [*] Finding Java...

REM Try common Java locations
for /d %%J in ("C:\Program Files\Java\jdk-17*" "C:\Program Files\Java\jdk-21*" "C:\Program Files\Java\jdk*") do (
    if exist "%%J\bin\java.exe" (
        set "JAVA_HOME=%%J"
        echo [OK] Java found: !JAVA_HOME!
        goto FOUND_JAVA
    )
)

REM Check if java is in PATH
for /f "delims=" %%F in ('where java.exe 2^>nul') do (
    echo [OK] Java found in PATH: %%F
    goto FOUND_JAVA
)

echo [ERROR] Java not found!
echo [INFO] Install Java 17 from: https://www.oracle.com/java/technologies/downloads/
echo [INFO] Press any key to close...
pause > nul
exit /b 1

:FOUND_JAVA

REM ============ Find MAVEN ============
echo [*] Finding Maven...

REM Check NetBeans Maven location (most common on Windows with NetBeans IDE)
if exist "C:\Program Files\NetBeans-24\netbeans\java\maven\bin\mvn.cmd" (
    set "MAVEN_HOME=C:\Program Files\NetBeans-24\netbeans\java\maven"
    echo [OK] Maven found: !MAVEN_HOME!
    goto FOUND_MAVEN
)

if exist "C:\Program Files\NetBeans-23\netbeans\java\maven\bin\mvn.cmd" (
    set "MAVEN_HOME=C:\Program Files\NetBeans-23\netbeans\java\maven"
    echo [OK] Maven found: !MAVEN_HOME!
    goto FOUND_MAVEN
)

REM Try standalone Maven locations
if exist "C:\Program Files\Maven\bin\mvn.cmd" (
    set "MAVEN_HOME=C:\Program Files\Maven"
    echo [OK] Maven found: !MAVEN_HOME!
    goto FOUND_MAVEN
)

if exist "C:\Maven\bin\mvn.cmd" (
    set "MAVEN_HOME=C:\Maven"
    echo [OK] Maven found: !MAVEN_HOME!
    goto FOUND_MAVEN
)

REM Check if mvn is in PATH
for /f "delims=" %%F in ('where mvn.cmd 2^>nul') do (
    echo [OK] Maven found in PATH
    goto FOUND_MAVEN
)

echo [ERROR] Maven not found at:
echo   - C:\Program Files\NetBeans-24\netbeans\java\maven
echo   - C:\Program Files\Maven
echo [INFO] Install Maven from: https://maven.apache.org/download.cgi
echo [INFO] Press any key to close...
pause > nul
exit /b 1

:FOUND_MAVEN

REM ============ SET ENVIRONMENT & BUILD ============
echo.
echo [*] Setting up environment variables...
if defined MAVEN_HOME (
    set "PATH=!MAVEN_HOME!\bin;!JAVA_HOME!\bin;!PATH!"
)

echo.
echo ============================================================
echo [*] Building application...
echo [*] This may take 1-2 minutes on first launch
echo ============================================================
echo.

cd /d "%~dp0"

REM Run Maven build
if defined MAVEN_HOME (
    call "!MAVEN_HOME!\bin\mvn.cmd" clean install javafx:run -q -DskipTests
) else (
    call mvn clean install javafx:run -q -DskipTests
)

if %errorlevel% neq 0 (
    echo.
    echo ============================================================
    echo [ERROR] Application failed to start!
    echo ============================================================
    echo.
    echo [INFO] Press any key to close...
    pause > nul
    exit /b 1
)

echo.
echo ============================================================
echo [SUCCESS] Application completed successfully!
echo ============================================================
echo.
echo [INFO] Press any key to close...
pause > nul
