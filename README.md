# awesome-spring-boot

## 异常处理

SpringBoot原生返回的异常json数据比较多，有两种自定义的方式：

* 实现ErrorController接口，会自动替换Spring自带的BasicErrorController。
* 使用@ControllerAdvice，然后使用@ExceptionHandler捕获MethodArgumentNotValidException。

```java
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class BasedErrorController implements ErrorController {

    private ErrorAttributes errorAttributes;

    @Autowired
    public BasedErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping
    public ResponseEntity error(HttpServletRequest request) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(requestAttributes, false);
        List<FieldError> errors = (List) errorAttributes.get("errors");
        List<String> errorMessages = errors.stream()
                .map(error -> error.getDefaultMessage())
                .collect(toList());
        int status = (int) errorAttributes.get("status");
        Map<String, List<String>> result = new HashMap<String, List<String>>() {{
            put("errors", errorMessages);
        }};
        return ResponseEntity.status(status).body(result);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
```

## 错误消息

在SpringBoot中使用Bean Validation，一般都会用到Hibernate Validator，其ValidationMessages.properties(ValidationMessages_zh.properties)本身是支持国际化的，但是中文必须写成Unicode。可以借助Spring MessageSource的方式支持中文。

```yml
spring:
  messages:
    basename: ValidationMessages
```

```java
@Configuration
public class BeanValidationConfig {

    @Bean
    public Validator validator(MessageSource messageSource) {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource);
        return validatorFactoryBean;
    }
}
```
