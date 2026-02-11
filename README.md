# WebDemo - Java Web Application

基于 Spring Boot 构建的基础 Web 应用程序，支持 IPv4 公网访问。

## 技术栈

- Java 17
- Spring Boot 3.2.0
- Thymeleaf 模板引擎
- Tomcat 嵌入式服务器

## 项目结构

```
webdemo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── webdemo/
│   │   │           ├── WebDemoApplication.java
│   │   │           └── controller/
│   │   │               └── HomeController.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── templates/
│   │           ├── index.html
│   │           └── about.html
├── pom.xml
├── start.bat (Windows)
└── start.sh (Linux/Mac)
```

## 快速开始

### 前置要求

- JDK 17 或更高版本
- Maven 3.6 或更高版本

### 运行应用

**Windows:**
```bash
start.bat
```

**Linux/Mac:**
```bash
chmod +x start.sh
./start.sh
```

**或使用 Maven 直接运行:**
```bash
mvn spring-boot:run
```

## 公网访问配置

应用已配置为支持 IPv4 公网访问：

1. **服务器地址**: `0.0.0.0` (监听所有网络接口)
2. **端口**: `8080`

### 访问方式

- **本地访问**: http://localhost:8080
- **局域网访问**: http://192.168.31.36:8080
- **公网访问**: http://220.187.35.16:9041

### 防火墙配置

确保防火墙允许 8080 端口入站连接：

**Windows:**
```powershell
netsh advfirewall firewall add rule name="WebDemo" dir=in action=allow protocol=TCP localport=8080
```

**Linux (UFW):**
```bash
sudo ufw allow 8080/tcp
```

**Linux (firewalld):**
```bash
sudo firewall-cmd --permanent --add-port=8080/tcp
sudo firewall-cmd --reload
```

### 路由器端口转发

如果需要从外网访问，需要在路由器上配置端口转发：

1. 登录路由器管理界面
2. 找到"端口转发"或"虚拟服务器"设置
3. 添加规则：
   - 外部端口: 8080
   - 内部端口: 8080
   - 内部 IP: 您的电脑 IP 地址
   - 协议: TCP

## 性能优化

应用已包含以下优化配置：

- **最大线程数**: 200
- **最大连接数**: 8192
- **连接超时**: 20 秒
- **响应压缩**: 启用
- **Thymeleaf 缓存**: 启用（生产环境）

## 健康检查

应用提供健康检查端点：

- **健康状态**: http://localhost:8080/actuator/health
- **应用信息**: http://localhost:8080/actuator/info

## 页面说明

- **首页** (`/`): 欢迎页面，展示应用特性
- **关于页面** (`/about`): 项目介绍和技术栈说明

## 开发建议

1. 修改 `application.properties` 中的配置以适应您的环境
2. 在 `controller` 包中添加更多控制器
3. 在 `templates` 目录中添加新的 HTML 页面
4. 使用 Thymeleaf 语法实现动态内容

## 注意事项

- 生产环境建议修改默认端口
- 建议配置 HTTPS 以保证安全
- 定期更新依赖版本以获取安全修复
- 考虑添加日志管理和监控

## 许可证

MIT License