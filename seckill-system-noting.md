





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

### 方法一：使用SpringSession实现

##### 1：添加依赖

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
<!-- spring-session 依赖 -->
<dependency>
<groupId>org.springframework.session</groupId>
<artifactId>spring-session-data-redis</artifactId>
</dependency>
```

##### 2：配置redis

```yml
spring:
redis:
  #超时时间
 timeout: 10000ms
  #服务器地址
 host: 192.168.10.100
  #服务器端口
 port: 6379
  #数据库
 database: 0
  #密码
 password: root
 lettuce:
  pool:
    #最大连接数，默认8
    max-active: 1024
    #最大连接阻塞等待时间，默认-1
    max-wait: 10000ms
    #最大空闲连接
    max-idle: 200
    #最小空闲连接
   min-idle: 5
```

















## 问题集

### 1 - MD5加密原理

### 2 - 正则表达式





