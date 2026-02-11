@echo off
REM Windows 批处理脚本：用于启动 WebDemo 应用程序
REM @echo off：关闭命令回显，使输出更清晰

REM 设置控制台代码页为 UTF-8，解决中文乱码问题
chcp 65001 >nul

REM 设置控制台颜色和标题
title WebDemo Application Launcher
color 0A

echo.
echo ========================================
echo    WebDemo Application Launcher
echo ========================================
echo.

REM 检查 Java 是否安装
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Java not found, please install JDK 17 or higher
    echo.
    echo Download: https://www.oracle.com/java/technologies/downloads/
    echo.
    pause
    exit /b 1
)

REM 检查 Maven 是否安装
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Maven not found, please install Maven
    echo.
    echo Download: https://maven.apache.org/download.cgi
    echo.
    pause
    exit /b 1
)

REM 显示环境信息（重定向到 nul 避免乱码）
echo [INFO] Environment detected:
java -version >nul 2>nul
mvn -version >nul 2>nul
echo   Java: Installed
echo   Maven: Installed
echo.

REM 输出启动提示信息
echo [STARTING] Starting WebDemo Application...
echo.

REM 输出服务器访问地址提示
echo [ACCESS ADDRESSES]
echo   - Local: http://localhost:10005
echo   - LAN: http://YOUR_LOCAL_IP:10005
echo   - Public: http://YOUR_PUBLIC_IP:10005
echo.
echo [TIP] Press Ctrl+C to stop application
echo.

REM 使用 Maven 启动 Spring Boot 应用程序
REM spring-boot:run 是 Maven 插件命令，用于运行 Spring Boot 应用
mvn spring-boot:run

REM 检查执行结果
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Application startup failed, error code: %ERRORLEVEL%
    echo.
    pause
    exit /b %ERRORLEVEL%
)

REM 如果正常退出，显示提示
echo.
echo [INFO] Application stopped
echo.
pause
