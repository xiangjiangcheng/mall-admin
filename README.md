# 商城
个人项目，学习使用。

## 技术栈

Java 17 + SpringBoot 3.3.10 + Spring Security 6 + JWT + MyBatis-Plus + Redis + MySQL 8 + Knife4j 4.5.0 + Lombok 1.18.36 + Maven 3.9.9
## 搭建步骤
1.spring boot  √ <br>
2.mysql  √ <br>
3.mybatis-plus  √ <br>
4.集成Knife4j https://doc.xiaominfo.com/docs/quick-start <br>
5.集成Redis <br>
6.集成Spring security + JWT认证鉴权 <br>
7.集成Caffeine缓存 <br>

## 文档说明
1.接口文档 <br>
Swagger UI 文档地址：http://localhost:8080/swagger-ui/index.html <br>
Knife4j 文档地址：http://localhost:8080/doc.html <br>

2.关于JWT-toekn <br>
访问 https://jwt.io/ 解析返回的 token ，主要分为三部分 Header(头部) 、Payload(负载) 和 Signature(签名) ，其中负载除了固定字段之外，还出现自定义扩展的字段 userId。

## 参考教程
1.项目搭建（0-1）： https://www.cnblogs.com/haoxianrui/p/18683051 <br>
2.Redis使用：https://www.cnblogs.com/castamere/p/13497747.html <br>
3.Spring boot 自定义注解+@Aspect实现切面输出日志：https://www.cnblogs.com/codecat/p/13810999.html <br>
4.caffeine缓存 

## ISSUE
1.Lombok 注解不生效， 见POM文件最后面注释部分<br>
2.加入全局异常处理器后，Knife4j文档打不开 原因是SpringBoot3.4.0+与Knife4j4.5.0不兼容，
解决方案：A:降级SprintBoot到3.3.10(√), B:升级springdoc-openapi-starter-webmvc-ui 2.7.0, 
issue details see: <br>
https://github.com/xiaoymin/knife4j/issues/865 <br>
https://blog.csdn.net/Lee_SmallNorth/article/details/144528609 <br>