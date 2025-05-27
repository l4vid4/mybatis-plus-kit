# MyBatis-Plus-Kit
🚀 mybatis-plus-kit 是基于 MyBatis-Plus 的二次封装组件，提供通用的 BaseService、BaseController 支持、分页查询、统一响应封装、异常处理器等能力，极大简化业务开发代码，让你专注于核心业务逻辑。
## ✨ 特性

- ✅ 通用的 BaseService / BaseController，快速构建基础 CRUD 接口

- ✅ PageQuery + PageResult：统一分页查询模型

- ✅ 自动封装 Controller 响应体（支持开启/关闭）

- ✅ 自定义异常统一捕获（可配置启用）

- ✅ 支持 Controller 接口精细化暴露

- ✅ 支持 VO 转换的分页查询接口

- ✅ 无侵入设计，可按需引入功能

## 🧩 模块结构

```cpp
mybatis-plus-kit
├── kit-core       // 核心能力，通用Service、Controller、分页、封装等
├── kit-starter    // Spring Boot Starter，自动装配、配置处理、响应/异常支持
├── kit-example    // 示例模块，演示如何使用kit-core + kit-starter
├── kit-generator    // 代码生成器，尚未开发，静待后续...... 
```

## 📦 快速开始

1. 引入依赖（Maven Central）

```xml
<dependency>
  <groupId>io.github.l4vid4</groupId>
  <artifactId>kit-starter</artifactId>
  <version>0.1.0</version>
</dependency>
```

2. 实体+Mapper

```java
@Data
@TableName("user")
public class User {
    private Long id;
    private String name;
    private String email;
}
```

```java
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
```

3. Service + Impl

```java
public interface UserService extends BaseService<User> {
}

@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {
}
```

4. Controller 使用通用能力

```java
@RestController
@RequestMapping("/user")
@DisableApis({DisableApis.Api.DELETE, DisableApis.Api.UPDATE}) //自定义不想暴露的接口
public class UserController extends BaseController<User, UserService> {
    
    //重写User → UserVO转换方法
 	@Override
    protected Function<User, UserVO> voConvertor() {
        return user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        };
    }
}
```

> 配置完上述所有后，自动拥有所有增删改查方法。
>
> - 可以在Controller类上加上`@DisableApis({DisableApis.Api.DELETE, DisableApis.Api.UPDATE})` 自定义不想暴露的接口
>
> | HTTP 方法 | 接口路径                | 方法名称          | 功能描述                           | 是否可禁用（通过注解） |
> | --------- | ----------------------- | ----------------- | ---------------------------------- | ---------------------- |
> | `GET`     | `/user/getById/{id}`    | `getById`         | 根据 ID 查询单个实体               | ✅ `GET_BY_ID`          |
> | `GET`     | `/user/list`            | `list`            | 查询所有数据（不分页）             | ✅ `LIST`               |
> | `GET`     | `/user/listByIds`       | `listByIds`       | 根据 ID 集合批量查询（不分页）     | ✅ `LIST_BY_IDS`        |
> | `POST`    | `/user/listByCondition` | `listByCondition` | 根据实体字段进行条件查询（不分页） | ✅ `LIST_BY_CONDITION`  |
> | `POST`    | `/user/page`            | `page`            | 分页查询实体（返回实体 T）         | ✅ `PAGE`               |
> | `POST`    | `/user/pageVo`          | `pageVo`          | 分页查询（返回 VO，默认无转换）    | ✅ `PAGE_VO`            |
> | `POST`    | `/user/save`            | `save`            | 新增数据                           | ✅ `SAVE`               |
> | `POST`    | `/user/update`          | `update`          | 更新数据（根据 ID）                | ✅ `UPDATE`             |
> | `GET`     | `/user/deleteById/{id}` | `deleteById`      | 删除单条数据（根据 ID）            | ✅ `DELETE_BY_ID`       |
> | `POST`    | `/user/delete`          | `delete`          | 批量删除（根据 ID 集合）           | ✅ `DELETE`             |



## 🧾 通用分页组件

### 分页参数类

```java
@Data
public class PageQuery {
    private Integer pageNo; // 页码
    private Integer pageSize; // 每一页数据量
    private String sortBy; // 排序字段
    private Boolean isAsc; // 是否正序
    
    // 将分页条件转换为Page<T>
    public <T> Page<T> toMpPage(OrderItem ... orders){
    }
    
    // 默认分页条件
    public <T> Page<T> toMpPage(String defaultSortBy, boolean isAsc){
    }
    
    // 按照create_time字段降序
    public <T> Page<T> toMpPageDefaultSortByCreateTimeDesc() {
    }
    
    // 按照update_time字段降序
    public <T> Page<T> toMpPageDefaultSortByUpdateTimeDesc() {
    }
}

```

### 分页响应类

```java
@Data
@AllArgsConstructor
public class PageResult<T> {
    private Long total;  //总条数
    private Long pages; // 总页数
    private List<T> list; // 数据
    
    // 返回空分页结果
    public static <V, P> PageResult<V> empty(Page<P> p){
    }
    
    //将MybatisPlus分页结果转为 VO分页结果
    public static <V, P> PageResult<V> of(Page<P> p, Class<V> voClass) {
    }
    
    //将MybatisPlus分页结果转为 VO分页结果，允许用户自定义PO到VO的转换方式
    public static <V, P> PageResult<V> of(Page<P> p, Function<P, V> convertor) {
    }
}
```



## ⚙️ 配置项说明（`application.yml`）

```yaml
mybatis-plus-kit:
  response-wrapper-enabled: true  # 是否启用统一响应封装（默认 true）
  exception-handler-enabled: true # 是否启用全局异常处理（默认 true）
```

## ✅ TODO（未来计划）

- 增加代码生成器支持（可选依赖）

- 接口权限粒度控制支持（如注解 + AOP 拦截）
- 通用字段自动填充（createTime, updateTime）
- 多数据源/分表兼容支持

## 🧑‍💻 贡献指南

欢迎 PR 或 issue！如果你觉得这个项目对你有帮助，欢迎点个 ⭐！

## 📄 License

[Apache 2.0](http://www.apache.org/licenses/)

