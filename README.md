# Library-Mangement-System
  
> By Forever (Hikarisame Technology Studio)

Java实训课设计 图书管理系统 
  
Mysql 8.0 + Druid 1.2.11
  
已完成功能 
+ 管理员登录 
+ 修改 \ 添加 \ 删除图书
+ 用户登录 \ 注册 \ 找回密码 (通过邮箱验证)
+ 借书 \ 还书 
+ 识别借书用户

数据库链接修改链接 \ 用户名 \ 密码 请前往src目录下druid.properties文件 

文件内容注释

```
#MySql8.0以下将driverClassName修改为com.mysql.jdbc.Driver
driverClassName=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://<服务器或本机IP>:3306/bookdb?useServerPrepStmts=true&serverTimezone=GMT%2B8&autoReconnect=true
username=<数据库用户名>
password=<数据库密码>
# 初始化连接数量
initialSize=5
# 初始化连接数量
maxActive=10
# 最大超时时间
maxWait=3000
```
邮箱正则识别`^[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$`  
随机码生成使用 Random`nextInt(999999)`字段生成  
若需要修改邮箱功能请前往`LoginUser.java / MailSend`字段自行填入
```
String SMTP = "填写SMTP发送地址";
String username = "填写邮箱用户名";
String password = "填写邮箱密码";
String FromMail = "填写所显示的发送邮箱";
String FromName = "填写所显示的发送用户";
```

感谢`GitHub Student Developer Pack`提供的免费学生试用包

感谢`MySql`和`alibaba/druid`提供的数据处理方案
  
感谢`JetBrains`提供的软件支持：

[<img align="left" alt="IntelliJIDEA" width="75px" src="https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.png"/>][JetBrains]

[<img align="left" alt="JetBrains" width="75px" src="https://resources.jetbrains.com/storage/products/intellij-idea/img/meta/intellij-idea_logo_300x300.png"/>][IntelliJIDEA]

[IntelliJIDEA]: https://www.jetbrains.com/idea/
[JetBrains]: https://www.jetbrains.com/
