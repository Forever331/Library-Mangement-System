# Library-Mangement-System
  
Java实训课设计 图书管理系统
  
Mysql 8.0 + Druid 1.2.11
  
已完成功能 
+ 管理员登录 
+ 修改 \ 添加 \ 删除图书
+ 用户登录 \ 注册
+ 借书 \ 还书 
+ 识别借书用户

数据库链接修改链接 \ 用户名 \ 密码 请前往src目录下添加druid.properties文件 

并填入下方内容

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

感谢`GitHub Student Developer Pack`提供的免费学生试用包

感谢`MySql`和`alibaba/druid`提供的数据处理方案
  
感谢`JetBrains`提供的软件支持：

[<img align="left" alt="IntelliJIDEA" width="75px" src="https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.png"/>][JetBrains]

[<img align="left" alt="JetBrains" width="75px" src="https://resources.jetbrains.com/storage/products/intellij-idea/img/meta/intellij-idea_logo_300x300.png"/>][IntelliJIDEA]

[IntelliJIDEA]: https://www.jetbrains.com/idea/
[JetBrains]: https://www.jetbrains.com/
