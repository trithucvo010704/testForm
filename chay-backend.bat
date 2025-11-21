@echo off
echo ====================================
echo    CHAY BACKEND (Spring Boot)
echo ====================================
echo.

cd recruitment-backend\recruitment-backend

echo Dang chay Backend tren port 8080...
echo.

REM Chay Maven Wrapper
call mvnw.cmd spring-boot:run

pause

