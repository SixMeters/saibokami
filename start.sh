#!/bin/bash
# Linux/Mac Shell 脚本：用于启动 WebDemo 应用程序
# #!/bin/bash：指定脚本解释器为 bash

# 输出启动提示信息
echo "Starting WebDemo Application..."
echo ""

# 输出服务器访问地址提示
echo "Server will be available at: http://0.0.0.0:8080"
# 提示用户使用 IPv4 地址进行公网访问
echo "For public access, use your IPv4 address: http://YOUR_IPV4:8080"
echo ""

# 使用 Maven 启动 Spring Boot 应用程序
# spring-boot:run 是 Maven 插件命令，用于运行 Spring Boot 应用
mvn spring-boot:run