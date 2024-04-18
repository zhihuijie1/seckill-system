





## 一：代码生成器

> 功能：AutoGenerator 是 MyBatis-Plus 的代码生成器，通过 AutoGenerator 可以快速生成 Entity、Mapper、Mapper XML、Service、Controller 等各个模块的代码，极大的提升了开发效率。



**你给他传入一个表，他会快速的根据表中的字段来生成对应的 controller  service mapper pojo 并添加好注解  自己只负责代码的核心业务就行  搭建交给代码生成器**

### 1 -- pom.xml 组件选择

```xml
		<!--mybatis plus-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.3.2</version>
        </dependency>
        <!--mybatis-plus代码生成器依赖-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-generator</artifactId>
            <version>3.4.0</version>
        </dependency>
        <!--freemarker模板引擎-->
        <dependency>
            <groupId>org.freemarker</groupId>
            <artifactId>freemarker</artifactId>
            <version>2.3.30</version>
        </dependency>
        <!--mysql依赖-->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.0.33</version>
            <scope>runtime</scope>
        </dependency>
```



### 2 -- 生成器代码

```java
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("jobob");
        gc.setOpen(false);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/seckill?characterEncoding=utf-8&serverTimezone=GMT%2B8&userSSL=false");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("chengcheng");
        dsc.setPassword("171612cgj");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.baomidou.ant");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("templates/entity.java");
        templateConfig.setMapper("templates/mapper.java");
        templateConfig.setService("templates/service.java");
        templateConfig.setServiceImpl("templates/serviceImpl.java");
        templateConfig.setController("templates/controller.java");

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("t_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
```



## 二：参数校验



### 1 -- 添加依赖的组件

```xml
		<!--validation-->
        <!--检查用户输入的信息是否正确 也可以自定义注解检查 自定义信息是否正确-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
```

### 2 -- 在参数中添加 @Validated 注解

```java
    @RequestMapping("/doLogin")
    @ResponseBody
    public Result doLogin(@Validated LoginVo loginVo) { //@Validated -- 会自动检查传入参数中信息是否正确
        Result result = iUserService.doLogin(loginVo);
        return result;
    }
```

### 3 -- 在对应的参数上添加判断的注解 

```java
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 32)//加密后的密码最小长度是32位
    private String password;
}

```

### 4 -- 编写自己的注解@IsMobile 

![](D:/%E4%BD%A0%E5%A5%BDJava/30-17116071954951.png)

##### **@IsMobile注解**

```java
package com.chengcheng.seckill.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
//这些注释都是拷贝过来的
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsMobileValidator.class}) //验证器放进来
public @interface IsMobile {
    //手机号码默认是填写的
    boolean required() default true;
    
    //下面的抽象方法也是拷贝过来的
    String message() default "手机号码格式错误";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

```

##### **IsMobileValidator**

```java
package com.chengcheng.seckill.validator;

import com.chengcheng.seckill.utils.Validator;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//实现 ConstraintValidator<IsMobile, String> -- 用来实现自定义验证器里面的验证逻辑
//泛型 -- IsMobile -- 当某个字段上面标有@IsMobile时，当前的这个自定义验证器会自动介入
//泛型 -- String -- 当前的验证器适用于哪种类型的字段 -- 即@IsMobile注解适用于标注String类型的字段，当验证该字段时，该验证器会自动接入
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    //标记手机号码是否为必填
    private boolean required = false;
    //验证器初始化函数 -- 这个方法会在验证器被使用之前被调用 在isValid之前执行
    //constraintAnnotation -- 自定义注解的实例
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    //验证逻辑的核心代码
    //value -- 待验证值
    //context -- 上下文的环境信息
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required) {
            return Validator.isMobile(value);
        }else {
            if(StringUtils.isEmpty(value)) {
                return true;
            }else {
                return Validator.isMobile(value);
            }
        }
    }
}
```

##### **Validator**

```java
package com.chengcheng.seckill.utils;
import org.thymeleaf.util.StringUtils;
import java.util.regex.Pattern;


public class Validator {
    private static final Pattern mobile_pattern = Pattern.compile("[1]([3-9])[0-9]{9}$");

    public static boolean isMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        return mobile_pattern.matcher(mobile).matches();
    }
}

```



## 三：异常处理

> 系统中异常包括：编译时异常和运行时异常 RuntimeException ，前者通过捕获异常从而获取异常信息，后者主要通过规范代码开发、测试通过手段减少运行时异常的发生。在开发中，不管是dao层、service层还是controller层，都有可能抛出异常，在Springmvc中，能将所有类型的异常处理从各处理过程解耦出来，既保证了相关处理过程的功能较单一，也实现了异常信息的统一处理和维护。
>
> SpringBoot全局异常处理方式主要两种使用 @ControllerAdvice 和 @ExceptionHandler 注解。
> 使用 ErrorController类 来实现区别：
>
> 1. @ControllerAdvice 方式只能处理控制器抛出的异常。此时请求已经进入控制器中。
> 2. ErrorController类 方式可以处理所有的异常，包括未进入控制器的错误，比如404,401等错误
> 3. 如果应用中两者共同存在，则 @ControllerAdvice 方式处理控制器抛出的异常，
> ErrorController类 方式处理未进入控制器的异常。
> 4. @ControllerAdvice 方式可以定义多个拦截方法，拦截不同的异常类，并且可以获取抛出的异常
> 信息，自由度更大。



![](D:/%E4%BD%A0%E5%A5%BDJava/31-17116114555823.png)

##### GlobalException

```java
package com.chengcheng.seckill.exception;

import com.chengcheng.seckill.utils.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends RuntimeException {
    ResultCodeEnum resultCodeEnum;
}
```

##### GlobalExceptionHandler

```java
package com.chengcheng.seckill.exception;

import com.chengcheng.seckill.utils.Result;
import com.chengcheng.seckill.utils.ResultCodeEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//声明一个全局异常处理器类，用于处理Controller层抛出的异常。
@RestControllerAdvice
public class GlobalExceptionHandler {
    //声明一个异常处理方法，用于处理Exception类型的异常。
    @ExceptionHandler(Exception.class)  
    public Result ExceptionHandler(Exception e) {
        if (e instanceof GlobalException) { //处理手机号/密码错误异常
            GlobalException ex = (GlobalException) e;
            return Result.fail(ex.getResultCodeEnum());
        } else if (e instanceof BindException) {//参数校验时会向控制台抛BindException异常
            BindException ex = (BindException) e;
            //这一步主要拿到error code
            Result result = Result.fail(ResultCodeEnum.BIND_ERROR);
            //getBindingResult().getAllErrors().get(0) 因为只有一个error所以get(0)即可
            result.setMessage("参数校验异常：" +
                    ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        return Result.fail(ResultCodeEnum.SERVICE_ERROR);
    }
}
```



```
Resolved [org.springframework.validation.BindException: org.springframework.validation.BeanPropertyBindingResult: 1 errors

Field error in object 'loginVo' on field 'mobile': rejected value [111111111]; codes [IsMobile.loginVo.mobile,IsMobile.mobile,IsMobile.java.lang.String,IsMobile]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [loginVo.mobile,mobile]; arguments []; default message [mobile],true]; default message [手机号码格式错误]]
```









## 五：Redis实现分布式Session

### 方法一：将用户信息存入Redis

#### 1：依赖：

```xml
	    <!-- spring data redis 依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <!-- commons-pool2 对象池依赖 -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
        </dependency>
```

#### 2：配置

##### （1）yml文件

```yml
  redis:
    timeout: 10000ms
    host: 192.168.136.139
    database: 0
    port: 6379
    lettuce:
      pool:
        max-wait: 10000ms
        max-idle: 200
        min-idle: 5
        max-active: 1024
```

##### （2）RedisConfig

```java
package com.chengcheng.seckill.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import
        org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //key序列器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value序列器
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //Hash类型 key序列器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //Hash类型 value序列器
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
}

/**
 * RedisConfig 它的主要功能是配置 RedisTemplate，用于与 Redis 数据库进行交互。
 * 为什么要进行序列化？
 * <p>
 * 1：持久化存储：Redis 可以将数据持久化到磁盘，序列化是将内存中的数据转换为可以持久化存储的形式，以便重启后可以重新加载数据。
 * <p>
 * 2：网络传输：Redis 通常用作缓存服务器，用于存储和提供数据。当数据通过网络传输时，需要将数据序列化为字节流以便于传输，并在接收端进行反序列化。
 * <p>
 * 使用了 GenericJackson2JsonRedisSerializer 对象进行序列化，
 * 它将对象序列化为 JSON 格式。这种序列化方式具有通用性，可以序列化各种类型的对象，
 * 并且 JSON 是一种轻量级的数据交换格式，在网络传输和持久化存储时效率较高。
 */

```



```
修改一下自动生成的 controller service serviceimpl mapper mapperxml  pojo 尤其是pojo中的id

实现goodscontroller获取 商品列表  ，因为有的商品既是秒杀商品也是普通商品  所以直接将秒杀商品与普通商品打包一下，将其属性都打包成一个对象中goodsVo，返回的时候就返回goodsVo这样前端需要什么商品信息直接从goodsVo中拿就行。

使用model对象来向前端返回对象
使用findGoodsVo()方法来获取GoodsVo,在service serviceImpl mapper mapper.xml中写sql来获取数据

注意springboot的静态资源默认存放在static /img/..   但是当自己写了一个mvc配置类后 就会默认去配置类中找路径  此时你必须自己手动指定这个路径
```



## 秒杀功能实现



### 1 -- SeckillController



#### 补充知识 1：springMVC参数绑定机制

> ```java
> public String doSeckill(Model model, User user, Long goodsId) {}
> ```
>
> Model是springMVC中的一个类，主要的功能是在controller中向前端传递数据
> User表示当前参与秒杀的对象
> goodsId是秒杀的商品
> 这些参数会通过springMVC的参数绑定机制，将请求中的参数信息自动绑定到controller层的方法上
>
> 
>
> **springMVC参数绑定机制：**
>
> Spring MVC 的参数绑定机制是将前端发来的请求中的数据自动绑定到controller层方法的参数上。。这个过程通常是自动完成的，Spring MVC 会根据方法参数的类型和名称，尝试从请求中提取对应的数据，并将其转换为正确的类型，然后传递给方法。
>
> **绑定机制的详细步骤**
>
> 1. **客户端发送请求**：用户在浏览器中输入URL或者通过页面上的表单提交请求到服务器端。
> 2. **DispatcherServlet 拦截请求**：当请求到达服务器端时，Spring MVC 的 DispatcherServlet 拦截并处理这个请求。
> 3. **HandlerMapping 定位处理器**：DispatcherServlet 调用 HandlerMapping 将请求映射到对应的处理器(Controller)上。
> 4. **确定方法参数**：HandlerMapping 确定了要调用的控制器方法后，会分析方法的参数列表。
> 5. **参数解析器解析参数**：Spring MVC 使用参数解析器(ParameterResolver)来解析方法的参数。参数解析器会检查方法的参数类型，并根据请求中的数据来填充这些参数。
> 6. **参数绑定**：一旦参数解析器确定了参数类型，它会从请求中提取数据并尝试将其转换为正确的类型。***如果参数的名称和请求中的参数名匹配，那么参数绑定就会成功。***
> 7. **调用控制器方法**：一旦所有参数都绑定成功，DispatcherServlet 就会调用控制器方法，并将绑定好的参数传递给方法。
> 8. **执行业务逻辑**：控制器方法执行业务逻辑，可能调用服务层或者其他组件来处理请求。
> 9. **返回视图或数据**：控制器方法执行完成后，通常会返回一个视图名称或者数据给客户端。
> 10. **视图解析**：DispatcherServlet 根据控制器方法返回的视图名称，使用视图解析器(ViewResolver)来解析视图并生成最终的响应内容。
> 11. **响应客户端**：DispatcherServlet 将生成的响应内容返回给客户端。



---------------



#### 补充知识 2：IService中的封装方法 QueryWrapper中的封装方法

> ```java
> SeckillOrder seckillOrder = seckillOrderService.getOne(new
>                 QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq(
>                 "goods_id",
>                 goodsId));
> ```
>
> seckillOrderService实现了IService接口，其中封装了大量的service层的操作，
>
> seckillOrderService.getOne( ) ：IService中的方法，用于从数据库中获取满足条件的单个记录。
>
> new QueryWrapper<SeckillOrder>() ：创建一个查询条件的包装器，用于构建数据库的查询条件。
>
> . eq("user_id",  user.getId( ) ) .eq("goods_id", goodsId));  在查询条件包装器中添加一个等于条件（.eq（））。
>
> 即要求查询的结果中用户ID（user_id）与某个当前用户的ID相等，商品id与当前的商品id相等。



```java
package com.chengcheng.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chengcheng.seckill.pojo.Order;
import com.chengcheng.seckill.pojo.SeckillOrder;
import com.chengcheng.seckill.pojo.User;
import com.chengcheng.seckill.service.IGoodsService;
import com.chengcheng.seckill.service.IOrderService;
import com.chengcheng.seckill.service.ISeckillOrderService;
import com.chengcheng.seckill.utils.ResultCodeEnum;
import com.chengcheng.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IOrderService orderService;

    @RequestMapping("/doSeckill")
    public String doSeckill(Model model, User user, Long goodsId) {
        if (user == null) {
            return "login";
        }
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.findGoodVoByGoodsId(goodsId);
        //判断库存
        if (goods.getStockCount() < 1) {
            model.addAttribute("errorMessage", ResultCodeEnum.EMPTY_STOCK.getMessage());
            return "seckillFail";
        }
        //判断是否重复抢购
        SeckillOrder seckillOrder = seckillOrderService.getOne(new
                QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq(
                "goods_id",
                goodsId));
        if (seckillOrder != null) {// 说明之前已经抢购过了
            model.addAttribute("errorMessage", ResultCodeEnum.REPEATE_ERROR.getMessage());
            return "seckillFail";
        }
        //进行秒杀的逻辑处理 哪个用户对哪个商品进行秒杀，返回一个秒杀订单
        Order order = orderService.seckill(user, goods);
        model.addAttribute("order", order);
        model.addAttribute("goods", goods);
        return "orderDetail";
    }
}
```

秒杀的controller层主要负责的功能是：1：判断当前要秒杀的商品是否有库存  2：一个用户只能限购秒杀一件当前商品



### 2 -- OrderServiceImpl

```java
package com.chengcheng.seckill.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengcheng.seckill.mapper.OrderMapper;
import com.chengcheng.seckill.pojo.Order;
import com.chengcheng.seckill.pojo.SeckillGoods;
import com.chengcheng.seckill.pojo.SeckillOrder;
import com.chengcheng.seckill.pojo.User;
import com.chengcheng.seckill.service.IGoodsService;
import com.chengcheng.seckill.service.IOrderService;
import com.chengcheng.seckill.service.ISeckillGoodsService;
import com.chengcheng.seckill.service.ISeckillOrderService;
import com.chengcheng.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Override
    @Transactional
    public Order seckill(User user, GoodsVo goods) {
        //秒杀商品表减库存
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new
                QueryWrapper<SeckillGoods>().eq("goods_id",
                goods.getId()));
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        seckillGoodsService.updateById(seckillGoods);
        //生成订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setUserId(user.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrderService.save(seckillOrder);
        return order;
    }
}
```









## 问题集

### 1 - MD5加密原理

### 2 - 正则表达式

### 3 - mybatisplus 中 IService中常用的方法

```java
 //查询所有
userService .list();
//根据查询条件查询一个返回实体类对象
userService.getOne(lambdaQueryWrapper);
//分页查询所有
userService.page(pageInfo,lambdaQueryWrapper);
//查询数量
userService .count();
//根据ID查list集合
userService .listByIds();
//根据ID删除
userService .removeById();
userService .removeByIds();
//修改
userService .update();
userMapper.updateById(实体类);
//新增
userService .save();
userMapper.insert(实体类);
//批量新增
userService .saveBatch(集合);
```











