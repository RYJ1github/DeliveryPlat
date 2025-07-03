# Yujie Delivery Platform

一个基于 Spring Boot 的外卖平台后端系统。

## 技术栈

- **后端框架**: Spring Boot 3.2.3
- **数据库**: MySQL 8.0
- **安全框架**: Spring Security + JWT
- **API 文档**: SpringDoc OpenAPI (Swagger)
- **构建工具**: Maven
- **Java 版本**: 17

## 功能特性

### 用户管理
- 用户注册/登录
- 角色管理（用户、餐厅老板、配送员、管理员）
- 用户信息管理

### 餐厅管理
- 餐厅信息管理
- 餐厅搜索和筛选
- 餐厅状态管理

### 菜单管理
- 菜单项管理
- 菜品分类
- 菜品可用性管理

### 订单管理
- 订单创建和管理
- 订单状态跟踪
- 配送员分配

### 安全特性
- JWT 认证
- 基于角色的权限控制
- 跨域支持

## 快速开始

### 环境要求

- Java 17+
- MySQL 8.0+
- Maven 3.6+

### 数据库配置

1. 创建 MySQL 数据库
2. 修改 `src/main/resources/application.properties` 中的数据库连接信息

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/yujiedelivery?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 运行项目

1. 克隆项目
```bash
git clone <repository-url>
cd yujiedelivery
```

2. 编译项目
```bash
mvn clean compile
```

3. 运行项目
```bash
mvn spring-boot:run
```

4. 访问应用
- 应用地址: http://localhost:8080
- API 文档: http://localhost:8080/swagger-ui.html

## API 文档

启动应用后，可以通过以下地址访问 API 文档：

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI 规范: http://localhost:8080/v3/api-docs

## 主要 API 端点

### 认证
- `POST /api/auth/login` - 用户登录
- `POST /api/auth/signup` - 用户注册

### 用户管理
- `GET /api/users/me` - 获取当前用户信息
- `PUT /api/users/{id}` - 更新用户信息
- `GET /api/users` - 获取所有用户（管理员）

### 餐厅管理
- `GET /api/restaurants` - 获取所有餐厅
- `GET /api/restaurants/{id}` - 获取餐厅详情
- `POST /api/restaurants` - 创建餐厅（餐厅老板）
- `PUT /api/restaurants/{id}` - 更新餐厅信息

### 菜单管理
- `GET /api/restaurants/{restaurantId}/menu-items` - 获取餐厅菜单
- `POST /api/restaurants/{restaurantId}/menu-items` - 添加菜单项
- `PUT /api/restaurants/{restaurantId}/menu-items/{id}` - 更新菜单项

### 订单管理
- `POST /api/orders` - 创建订单
- `GET /api/orders/{id}` - 获取订单详情
- `PUT /api/orders/{id}/status` - 更新订单状态

## 用户角色

- **USER**: 普通用户，可以下单
- **RESTAURANT_OWNER**: 餐厅老板，可以管理餐厅和菜单
- **DELIVERY_PERSON**: 配送员，可以接单配送
- **ADMIN**: 管理员，拥有所有权限

## 开发说明

### 项目结构

```
src/main/java/com/yujiedelivery/
├── config/          # 配置类
├── controller/      # 控制器层
├── dto/            # 数据传输对象
├── exception/      # 异常处理
├── model/          # 实体类
├── repository/     # 数据访问层
├── security/       # 安全相关
└── service/        # 业务逻辑层
```

### 数据库表

- `users` - 用户表
- `restaurants` - 餐厅表
- `menu_items` - 菜单项表
- `orders` - 订单表
- `order_items` - 订单项表

## 许可证

Apache License 2.0 