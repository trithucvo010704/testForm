@echo off
echo ====================================
echo    CHAY CA BACKEND VA FRONTEND
echo ====================================
echo.
echo Luu y: Can 2 terminal rieng biet!
echo.
echo Terminal 1: Backend (port 8080)
echo Terminal 2: Frontend (port 8100)
echo.
pause

REM Mo terminal 1 cho Backend
start "Backend - Port 8080" cmd /k "cd /d %~dp0recruitment-backend\recruitment-backend && mvnw.cmd spring-boot:run"

REM Doi 3 giay de Backend bat dau
timeout /t 3 /nobreak >nul

REM Mo terminal 2 cho Frontend
start "Frontend - Port 8100" cmd /k "cd /d %~dp0recruitment-app && ionic serve"

echo.
echo Da mo 2 terminal:
echo - Terminal 1: Backend (http://localhost:8080)
echo - Terminal 2: Frontend (http://localhost:8100)
echo.
echo Nhan phim bat ky de dong cua so nay...
pause >nul

