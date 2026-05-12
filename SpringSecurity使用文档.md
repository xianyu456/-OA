# Spring Security + JWT 安全认证文档

## 一、项目架构概览

```
请求 → JwtAuthenticationFilter → SecurityFilterChain → Controller(@PreAuthorize)
         ↑ 解析JWT Token            ↑ URL级别权限检查        ↑ 方法级别权限检查
```

## 二、核心组件说明

### 2.1 组件清单

| 文件 | 位置 | 作用 |
|------|------|------|
| `SecurityConfig.java` | common/config/ | 安全总配置：密码编码器、认证管理器、过滤链、跨域、异常处理 |
| `JwtAuthenticationFilter.java` | common/filter/ | JWT过滤器：每个请求先经过它，解析Token并设置认证信息 |
| `MyUserDetails.java` | common/config/ | 实现UserDetails，包装User实体，提供权限列表 |
| `MyUserDetailService.java` | common/util/ | 实现UserDetailsService，从数据库加载用户 |
| `JwtUtils.java` | common/util/ | JWT工具类：生成Token、验证Token、解析Token |
| `JwtConfig.java` | common/config/ | JWT配置：密钥和过期时间（从yml读取） |
| `GlobalExceptionHandler.java` | common/config/ | 全局异常处理：将403/401转为统一Result格式 |
| `Result.java` | common/result/ | 统一返回结果类 |
| `AuthController.java` | manage/controller/ | 登录接口（/auth/login） |
| `UserController.java` | manage/controller/ | 用户接口示例（/user/*） |
| `AdminController.java` | manage/controller/ | 管理员接口示例（/admin/*） |

### 2.2 角色映射

User表中`role`字段（Integer）映射为Spring Security角色（String）：

| role值 | 角色含义 | Spring Security角色名 | 说明 |
|--------|----------|----------------------|------|
| 1 | 员工 | `ROLE_EMPLOYEE` | 基础权限：查看个人信息、打卡 |
| 2 | HR | `ROLE_HR` | HR权限：查看员工列表、管理考勤 |
| 3 | 老板 | `ROLE_BOSS` | 超级权限：系统设置、所有数据 |

角色转换在 `User.getRoleName()` 方法中完成。

## 三、登录流程

```
1. 前端 POST /auth/login  {"username":"001","password":"123456"}
2. AuthController 接收请求
3. 创建 UsernamePasswordAuthenticationToken
4. AuthenticationManager.authenticate() 认证
   ├→ MyUserDetailService.loadUserByUsername() 从数据库查用户
   ├→ PasswordEncoder.matches() 验证密码（BCrypt）
   └→ 返回包含权限的 Authentication 对象
5. JwtUtils.generateToken() 生成JWT
6. 返回 {"code":1, "msg":"登录成功", "data":{"token":"...","username":"001","role":"1"}}
```

## 四、JWT认证流程（后续请求）

```
1. 前端请求头携带: Authorization: Bearer <token>
2. JwtAuthenticationFilter.doFilterInternal()
   ├→ 从请求头提取Token
   ├→ JwtUtils.validateToken() 验证Token有效性和过期
   ├→ JwtUtils.getUsername() 获取用户名
   ├→ MyUserDetailService.loadUserByUsername() 加载用户（含权限）
   ├→ 创建 UsernamePasswordAuthenticationToken（含权限）
   └→ 存入 SecurityContextHolder
3. SecurityFilterChain.authorizeHttpRequests() 检查URL权限
4. Controller方法上的 @PreAuthorize 检查方法权限
```

## 五、权限控制（两种方式）

### 5.1 URL级别（SecurityConfig中配置）

```java
.requestMatchers("/auth/**", "/public/**", "/error").permitAll()  // 公开
.requestMatchers("/admin/**").hasAnyRole("HR", "BOSS")            // HR/老板
.requestMatchers("/hr/**").hasRole("HR")                          // 仅HR
.requestMatchers("/user/**").hasAnyRole("EMPLOYEE", "HR", "BOSS") // 所有用户
.anyRequest().authenticated()                                     // 其余需登录
```

### 5.2 方法级别（@PreAuthorize注解）

```java
@PreAuthorize("hasRole('EMPLOYEE')")        // 仅员工
@PreAuthorize("hasAnyRole('HR', 'BOSS')")   // HR或老板
@PreAuthorize("hasRole('BOSS')")            // 仅老板
```

配合 `@AuthenticationPrincipal MyUserDetails principal` 可获取当前登录用户信息：
```java
@GetMapping("/profile")
public Result<?> getProfile(@AuthenticationPrincipal MyUserDetails principal) {
    // principal.getUserId(), principal.getUsername(), principal.getRealName()
}
```

## 六、统一返回格式 Result

```java
// 成功（无数据）
Result.success()
// → {"code":1, "msg":null, "data":null}

// 成功（带数据）
Result.success(userObject)
// → {"code":1, "msg":null, "data":{...}}

// 成功（带消息和数据）
Result.success("登录成功", dataMap)
// → {"code":1, "msg":"登录成功", "data":{...}}

// 失败（默认code=0）
Result.error("参数错误")
// → {"code":0, "msg":"参数错误", "data":null}

// 失败（指定code）
Result.error(401, "Token已过期")
// → {"code":401, "msg":"Token已过期", "data":null}
Result.error(403, "权限不足")
// → {"code":403, "msg":"权限不足", "data":null}
```

## 七、本次修改内容

### 7.1 修复的问题

| 问题 | 严重程度 | 修复方式 |
|------|----------|----------|
| `MyUserDetails.getAuthorities()` 返回空List | **致命** | 改为 `List.of(new SimpleGrantedAuthority(user.getRoleName()))`，将role转换为 `ROLE_EMPLOYEE`/`ROLE_HR`/`ROLE_BOSS` |
| `Result` 类缺少 `success(msg,data)` 和 `error(code,msg)` 重载 | **编译错误** | 新增两个静态方法 |
| `LoginDTO` 字段名大写（`Username`/`Password`） | **功能问题** | 改为小写 `username`/`password`，符合JSON规范 |
| 登录接口路径 `/kaoqin/login` 不在 permitAll 中 | **逻辑错误** | 新建 `AuthController` 映射到 `/auth/login`，与permitAll匹配 |
| `SecurityConfig` 角色名不匹配 | **功能问题** | `hasRole("ADMIN")` 改为 `hasAnyRole("HR","BOSS")`，`hasRole("USER")` 改为 `hasRole("EMPLOYEE")` |
| 过滤器直接拼接JSON错误响应 | **代码规范** | 改为使用 `ObjectMapper` 序列化 `Result` 对象 |
| `MyUserDetailService` 中 `User user = new User()` 死代码 | **代码规范** | 删除 |

### 7.2 新增的文件

| 文件 | 作用 |
|------|------|
| `model/User.java` — 新增 `getRoleName()` 方法 | 将Integer角色转为Spring Security角色字符串 |
| `manage/controller/AuthController.java` | 登录控制器，路径 `/auth/login` |
| `manage/controller/UserController.java` | 用户接口示例，展示 `@PreAuthorize` 和 `@AuthenticationPrincipal` 用法 |
| `manage/controller/AdminController.java` | 管理员接口示例，展示BOSS/HR权限分层 |
| `common/config/GlobalExceptionHandler.java` | 全局异常处理，将 `AccessDeniedException`(403) 和 `AuthenticationException`(401) 转为Result格式 |

### 7.3 修改的文件

| 文件 | 修改内容 |
|------|----------|
| `Result.java` | 新增 `success(String msg, T data)` 和 `error(Integer code, String msg)` |
| `LoginDTO.java` | `Username`→`username`, `Password`→`password` |
| `MyUserDetails.java` | 实现 `getAuthorities()`，新增 `getUserId()`/`getRealName()` |
| `MyUserDetailService.java` | 删除死代码 |
| `SecurityConfig.java` | 修复角色映射、新增异常处理、添加 import |
| `JwtAuthenticationFilter.java` | 错误响应用 ObjectMapper+Result 序列化 |
| `LoginController.java` | 路径改为 `/auth` |

## 八、测试验证

### 8.1 测试登录

```bash
# 登录（返回JWT Token）
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"001","password":"123456"}'

# 响应示例
{"code":1,"msg":"登录成功","data":{"token":"eyJhbG...","username":"001","role":"1"}}
```

### 8.2 测试带Token访问

```bash
# 获取个人信息（所有角色可访问）
curl http://localhost:8080/user/profile \
  -H "Authorization: Bearer <token>"

# 获取用户列表（仅HR和BOSS）
curl http://localhost:8080/user/list \
  -H "Authorization: Bearer <token>"

# 管理员仪表盘（仅HR和BOSS）
curl http://localhost:8080/admin/dashboard \
  -H "Authorization: Bearer <token>"

# 系统设置（仅BOSS）
curl http://localhost:8080/admin/settings \
  -H "Authorization: Bearer <token>"
```

### 8.3 验证权限拒绝

员工(role=1)访问管理员接口应返回403：
```json
{"code":403, "msg":"权限不足，无法访问该资源", "data":null}
```

未登录访问受保护接口应返回401：
```json
{"code":401, "msg":"未登录或Token无效，请先登录", "data":null}
```

## 九、扩展指南

### 添加新角色

1. 数据库 `user.role` 字段增加新值（如4=部门主管）
2. `User.getRoleName()` 添加映射：`case 4 -> "ROLE_MANAGER"`
3. SecurityConfig 中添加URL规则：`.requestMatchers("/manager/**").hasRole("MANAGER")`
4. 控制器方法添加：`@PreAuthorize("hasRole('MANAGER')")`

### 添加新公开接口

在 SecurityConfig 的 `permitAll()` 中添加路径，或在 `authorizeHttpRequests` 中新增 `.requestMatchers("/api/public/**").permitAll()`

### Token过期时间修改

修改 `application-dev.yml` 中的 `mwh.jwt.expiration` 值（单位：毫秒，86400000=24小时）

## 十、注意事项

1. **密码存储**：必须使用 BCrypt 加密存储，`PasswordEncoder` 已配置为 `BCryptPasswordEncoder`
2. **Token安全**：JWT密钥 `jwt.secret` 在生产环境必须使用强密钥且不外泄
3. **HTTPS**：生产环境应强制使用 HTTPS，防止Token被中间人截获
4. **CORS**：当前仅允许 `localhost:3000`，部署时需修改为实际前端域名
5. **无状态**：服务端不存储Session，每次请求都需携带Token，服务端重启不影响已登录用户
