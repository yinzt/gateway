# Getting Started

### 使用技术栈：

* spring-boot

* redis

* mybatis-plus

* JDK 1.8

### 接口地址规范
* 地址统一为api/v1/{package}/{Controller}这种格式
  * 其中package为controller下的包名(非必须)，controller为业务类型，如类为UserController时path为 api/v1/user
* 多个参数时使用对象接受参数
* 返回值推荐使用对象


### 用户登陆管理
* 框架本身已经做了登陆及登陆验证管理
* 只需要在验证登陆成功时调用 SecurityApi中的login方法即可获取token，框架会自动托管登陆状态
* 退出登陆时调用SecurityApi中的logout 清除登陆状态
* 在需要登陆才能访问的controller或controller的方法上加上@Auth注解即可

### 统一返回值及异常处理
* 统一返回值格式为
```json
{
    "code":0, //状态码，0成功，非0为异常，见 InfoCode类
    "msg":"成功",
    "data":{} //根据不同的接口返回内容可能为object也可能为array
}
```
**注：在Controller中直接返回需要返回的业务数据即可，如：**
```java
 @GetMapping("/list")
    public Object list(String paramKey, String paramName) {
        return configApi.configList(paramKey, paramName);
    }
```
框架会自动将返回的内容映射到data对象中。
如需返回异常码直接抛出异常即可：
```java
    if(StrUtil.isBlank(paramKey)) {
        throw new ApiRuntimeException(InfoCode.FREEZE);
    }
```

### 接口封装及工具类

* api包中的接口已经在framework,xpack等包中实现，直接调用即可，api包中对外提供接口服务的类统一以Api结尾。
  * RedisCache  
  * ConfigApi 提供系统参数配置相关的接口
  * PlatformApi 提供部署模式查询，有些业务逻辑需要根据部署模式实现
  * SecurityApi 提供生成token，解析token等相关接口
  * TenantApi 查询租户相关信息接口
  * LicenseApi 提供license相关接口
* 常用的基本工具类
  * 一般情况下不要自己编写工具类，请使用hutool工具包或使用框架中的工具类，不要在引入其他工具包。
  * ConfigUtil提供读取xcsa.conf中的配置项
