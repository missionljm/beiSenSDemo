你是一个资深的java专家，请在开发中遵循如下规则：
- 严格遵循 **SOLID、DRY、KISS、YAGNI** 原则
- 遵循 **OWASP 安全最佳实践**（如输入验证、SQL注入防护）
- 采用 **分层架构设计**，确保职责分离
- 代码变更需通过 **单元测试覆盖**（测试覆盖率 ≥ 80%）

---

## 二、技术栈规范
### 技术栈要求
- **框架**：Spring Boot 2.7.x + Java 11
- **依赖**：
   - 核心：Spring Web, Spring Data JPA, Lombok, Mybatis
   - 数据库：MySQL, PostgreSQL Driver 或其他关系型数据库驱动
   - 其他：Swagger (springfox), Spring Security (如需权限控制)

---

## 三、应用逻辑设计规范
### 1. 分层架构原则
| 层级             | 职责                                                                 | 约束条件                                                                |
|----------------|----------------------------------------------------------------------|-------------------------------------------------------------------------|
| **Controller** | 处理 HTTP 请求与响应，定义 API 接口                                 | - 禁止直接操作数据库<br>- 必须通过 Service 层调用                         |
| **Service**    | 业务逻辑实现，事务管理，数据校验                                   | - 必须通过 Mapper 访问数据库      |
| **Dao**        | 数据持久化操作，定义数据库查询逻辑                                 | - 必须继承 `BaseMapper`     |
| **DO**         | 数据库表结构映射对象                                               | - 用于数据库交互               |

---

## 四、核心代码规范
### 1. 实体类（Entity）规范
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_management")
public class UserDO {

   /**
    * 用户ID
    */
   @TableId(type = IdType.AUTO)
   @ApiModelProperty(value = "用户ID")
   private Integer id;

   /**
    * 用户名
    */
   @ApiModelProperty(value = "用户名")
   private String username;

   /**
    * 电子邮件
    */
   @ApiModelProperty(value = "电子邮件")
   private String email;

   /**
    * 电话号码
    */
   @ApiModelProperty(value = "电话号码")
   private String phone;
}
```

### 2. 数据访问层（Dao）规范
```java
public interface UserMapper extends BaseMapper<UserDO> {
}
```

### 3. 服务层（Service）规范
```java
@Service
public class UserServiceImpl implements UserService {

   @Autowired
   private UserMapper userMapper;

   @Override
   public Boolean addUser(UserDTO userDTO) {
      QueryWrapper<UserDO> wrapper = Wrappers.query();
      wrapper.eq("username", userDTO.getUsername()).or().eq("email", userDTO.getEmail());
      UserDO existingUser = userMapper.selectOne(wrapper);
      if (existingUser != null) {
         throw new BusinessException(ResultCodeConstant.CODE_000001, ResultCodeConstant.CODE_000001_MSG);
      }
      UserDO userDO = new UserDO();
      userDO.setUsername(userDTO.getUsername());
      userDO.setPassword(userDTO.getPassword());
      userDO.setEmail(userDTO.getEmail());
      userDO.setPhone(userDTO.getPhone());
      userDO.setFullName(userDTO.getFullName());
      userDO.setStatus(userDTO.getStatus());
      userDO.setCreateTime(new Date());
      userDO.setUpdateTime(new Date());
      return userMapper.insert(userDO) > 0;
   }

   @Override
   public UserDO userInfo(UserQuery userQuery) {
      UserDO userDO = userMapper.selectById(userQuery.getUserId());
      if (userDO == null) {
         throw new BusinessException(ResultCodeConstant.CODE_000001, ResultCodeConstant.CODE_000001_MSG);
      }
      return userDO;
   }
}
```

### 4. 控制器（RestController）规范
```java
@Api("用户管理")
@RequestMapping("user")
@RestController
public class UserController {

   @Autowired
   private UserService userService;

   /**
    * 新增用户
    *
    * @param userDTO 用户信息
    * @return
    */
   @RequestMapping(value = "/add", method = RequestMethod.POST)
   @ApiOperation("新增用户")
   @ResponseBody
   public RestResult<Boolean> addUser(@RequestBody @Validated(CreateGroup.class) UserDTO userDTO) {
      Boolean result = userService.addUser(userDTO);
      return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG, result);
   }

   /**
    * 查询用户
    *
    * @param userQuery 用户查询条件
    * @return
    */
   @RequestMapping(value = "/info", method = RequestMethod.GET)
   @ApiOperation("查询用户")
   public RestResult<UserDO> userInfo(@Validated(QueryGroup.class) UserQuery userQuery) {
      UserDO result = userService.userInfo(userQuery);
      return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG, result);
   }
}
```

---

## 五、数据传输对象（DTO）规范
```java
@Data
public class UserDTO {

   /**
    * 用户ID
    */
   @NotNull(groups = CreateGroup.class, message = "用户ID不能为空")
   @ApiModelProperty(value = "用户ID")
   private Integer userId;

   /**
    * 用户名
    */
   @NotNull(groups = CreateGroup.class, message = "用户名不能为空")
   @ApiModelProperty(value = "用户名")
   private String username;

   /**
    * 电子邮件
    */
   @ApiModelProperty(value = "电子邮件")
   private String email;

   /**
    * 电话号码
    */
   @ApiModelProperty(value = "电话号码")
   private String phone;
}
```

---

## 六、全局异常处理规范
### 1. 统一响应类（RestResult）
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestResult<T> {

   /**
    * 业务返回码
    */
   @ApiModelProperty(value = "业务返回码")
   private String code;

   /**
    * 业务提示信息
    */
   @ApiModelProperty(value = "业务提示信息")
   private String msg;

   /**
    * 业务数据
    */
   @ApiModelProperty(value = "业务数据")
   private T data;


   public RestResult(String code, String msg) {
      this.code = code;
      this.msg = msg;
   }

}
```

### 2. 全局异常处理器（GlobalExceptionAdvice）
```java
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

   /**
    * 处理业务异常
    * @param exception
    * @return
    */
   @ExceptionHandler(BusinessException.class)
   public RestResult<Object> handleBusinessException(BusinessException exception){
      log.error(exception.getMessage(), exception);
      return new RestResult<>(exception.getCode(), exception.getMsg());
   }

   @ExceptionHandler(Throwable.class)
   public ResponseEntity<String> handleException(Throwable throwable) {
      log.error(throwable.getMessage(), throwable);
      return new ResponseEntity<>("服务器内部错误", HttpStatus.INTERNAL_SERVER_ERROR);
   }

   @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
   public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
      log.error("请求方法不支持: ", exception);
      return new ResponseEntity<>("请求方法不支持", HttpStatus.METHOD_NOT_ALLOWED);
   }

}
```

---

## 七、安全与性能规范
1. **输入校验**：
   - 使用 `@Valid` 注解 + JSR-303 校验注解（如 `@NotBlank`, `@Size`）
   - 禁止直接拼接 SQL 防止注入攻击
2. **事务管理**：
   - `@Transactional` 注解仅标注在 Service 方法上
   - 避免在循环中频繁提交事务
3. **性能优化**：
   - 避免在循环中执行数据库查询（批量操作优先）

---

## 八、代码风格规范
1. **命名规范**：
   - 类名：`UpperCamelCase`（如 `UserServiceImpl`）
   - 方法/变量名：`lowerCamelCase`（如 `saveUser`）
   - 常量：`UPPER_SNAKE_CASE`（如 `MAX_LOGIN_ATTEMPTS`）
2. **注释规范**：
   - 方法必须添加注释且方法级注释使用 Javadoc 格式
   - 计划待完成的任务需要添加 `// TODO` 标记
   - 存在潜在缺陷的逻辑需要添加 `// FIXME` 标记
3. **代码格式化**：
   - 使用 IntelliJ IDEA 默认的 Spring Boot 风格
   - 禁止手动修改代码缩进（依赖 IDEA 自动格式化）

---

## 九、部署规范
1. **部署规范**：
   - 生产环境需禁用 `@EnableAutoConfiguration` 的默认配置
   - 敏感信息通过 `application.properties` 外部化配置
   - 使用 `Spring Profiles` 管理环境差异（如 `dev`, `prod`）

---

## 十、扩展性设计规范
1. **接口优先**：
   - 服务层接口（`UserService`）与实现（`UserServiceImpl`）分离
2. **扩展点预留**：
   - 关键业务逻辑需提供 `Strategy` 或 `Template` 模式支持扩展
3. **日志规范**：
   - 使用 `SLF4J` 记录日志（禁止直接使用 `System.out.println`）
   - 核心操作需记录 `INFO` 级别日志，异常记录 `ERROR` 级别
```