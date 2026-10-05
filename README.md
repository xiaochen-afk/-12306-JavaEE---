# 12306 订票练习项目

基于 Spring Boot、Thymeleaf、MyBatis 和 MySQL 的车票查询与预订练习项目。包含账号注册与登录、车次筛选、乘车人管理、选座订票、订单查询和取消订单。

## 环境要求

- JDK 17 或更高版本
- MySQL
- Maven Wrapper（仓库中的 `mvnw` / `mvnw.cmd`）

## 本地启动

1. 在 MySQL 8.0+ 中创建一个空的 `ticket` 数据库，并导入 [sql/init.sql](sql/init.sql)。在项目根目录打开 MySQL 客户端，执行：

   ```sql
   CREATE DATABASE ticket CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
   USE ticket;
   SOURCE sql/init.sql;
   ```

   `init.sql` 根据现有数据库表结构编写，仅包含虚构示例数据，不包含原始业务记录。它不删除已有表，应只导入空数据库。表 `order` 与 `all_seat_tpe` 是原有结构的一部分，当前主要订票流程使用 `orders`、`train`、`seat`、`seat_count`、`passenger` 和 `user`。
2. 将项目根目录的 `application-local.properties.example` 复制为 `application-local.properties`，填写本机 MySQL 账号和密码。此文件被 Git 忽略，也不会打进 JAR。也可以使用 `DB_URL`、`DB_USERNAME`、`DB_PASSWORD` 环境变量配置数据库。
3. 在 Windows PowerShell 中运行 `./mvnw.cmd spring-boot:run`，其他系统运行 `./mvnw spring-boot:run`。
4. 打开 `http://localhost:8081/login`。如需更改端口，设置 `SERVER_PORT` 环境变量。

示例登录账号为 `demo_user`，密码为 `demo123`。示例乘车人证件号为 `DEMO-ID-001` 和 `DEMO-ID-002`；这些仅用于本地演示。

## 代码结构

- `src/main/java/com/itheima/demo12306/Controller`：页面路由与请求参数
- `src/main/java/com/itheima/demo12306/Service`：业务处理
- `src/main/java/com/itheima/demo12306/Mapper` 与 `src/main/resources/com/itheima/demo12306/mapper`：MyBatis 数据访问
- `src/main/resources/templates`：Thymeleaf 页面
