# simpleryoInterview

# 一、环境
1、目录说明
  该程序实现分为前、后端两个工程。
  shop: 是后台java工程。
  shop-frontend：是前端reactjs工程。
  
2、开发运行环境
  OS: windows 7（其他操作系统和版本未测试）
  java version "1.8.0_131"
  Apache Maven 3.5.2
  nodejs v8.9.1
  npm 5.5.1
  yarn 0.20.4
  
# 二、运行
1、java工程
  在shop工程根目录下，命令行执行：mvnw spring-boot:run

2、前端工程
  在shop-frontend工程根目录下，先执行yarn安装依赖包。
  再执行npm run dev

3、访问
  http://localhost

4、注意
  该工程运行需要占用8080和80两个端口，需保证不冲突。

# 三、回答问题
#### 问题1、前后台都用了哪些框架，为什么？
1、后端java工程是一个rest工程，提供api接口供前台工程调用。
  使用了spring boot，原因：简化配置，容易部署运行。
  mvc框架使用spring mvc，原因：使用RestController注解非常方便实现rest controller；spring mvc的@ControllerAdvice方便实现统一异常处理；同时，集成Spring Security更加便捷（本工程没有集成）。
  数据持久使用mybatis，原因：个人原因，因为做数据库方面开发比较多，习惯使用sql语句，并且mybatis使用原生sql比hibernate更方便，有利于sql优化。
  
2、前端工程使用了reactjs 和 react flux
 选择reactjs是因为正好最近在学习，刚开始接触，还没有用reactjs做过项目，这次就当练手了。

#### 问题2、数据持久层是怎么实现的？
  数据库选择的H2的memory模式。
  由spring调用sql脚本完成数据库的创建和测试数据初始化。
  数据持久层使用mybatis，并抽象出通用的mapper和service接口以及实现，简单的增删改查使用mybatis的自动生成sql。
  数据库事务在service层控制，在方法级别采用注解@Transactional实现。

#### 问题3、用了多长时间完成？
  java工程和数据库用时3小时左右。
  前端工程用时5小时左右。
