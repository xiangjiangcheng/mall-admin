# 商城
个人项目，学习使用。

## 技术栈

Java 17 + SpringBoot 3
## 搭建步骤
1.spring boot  √
2.mysql  √
3.mybatis-plus  √
4.集成Knife4j https://doc.xiaominfo.com/docs/quick-start
5.集成Redis
6.集成Spring security + JWT认证鉴权

## 文档说明
1.接口文档
Swagger UI 文档地址：http://localhost:8080/swagger-ui/index.html
Knife4j 文档地址：http://localhost:8080/doc.html

2.关于JWT-toekn
访问 https://jwt.io/ 解析返回的 token ，主要分为三部分 Header(头部) 、Payload(负载) 和 Signature(签名) ，其中负载除了固定字段之外，还出现自定义扩展的字段 userId。

## 参考教程
项目搭建（0-1）： https://www.cnblogs.com/haoxianrui/p/18683051
Redis使用：https://www.cnblogs.com/castamere/p/13497747.html

## ISSUE
1. Lombok 注解不生效， 见POM文件最后面注释部分
2. 加入全局异常处理器后，Knife4j文档打不开 原因是SpringBoot3.4.0+与Knife4j4.5.0不兼容，
解决方案：A:降级SprintBoot到3.3.X, B:升级springdoc-openapi-starter-webmvc-ui 2.7.0, 
issue details see:
https://github.com/xiaoymin/knife4j/issues/865
https://blog.csdn.net/Lee_SmallNorth/article/details/144528609