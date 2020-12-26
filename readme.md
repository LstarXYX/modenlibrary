# 毕业设计——图书馆

参照之前的图书馆项目进行开发，SSM改为了SpringBoot
增加了Redis 
主要增加后台的数据可视化模块
如果有空，将添加在线选座位功能
采用前后端分离开发，前后台的页面都使用Vue进行制作，使用nginx部署
完成后的项目将发布在....

   # 毕业设计——图书馆
   
   参照之前的图书馆项目进行开发，SSM改为了SpringBoot
   增加了Redis 
   主要增加后台的数据可视化模块
   如果有空，将添加在线选座位功能
   采用前后端分离开发，前后台的页面都使用Vue进行制作，使用nginx部署
   完成后的项目将发布在....
   
   ## 主要模块
   
   - [主启动程序](#主启动程序)
   - [Data模块](#Data模块)
   - [DAO模块](#DAO模块)
   - [Service模块](#Service模块) 
   - [公共模块](#公共模块)
   - [Data数据模块](#Data数据模块)
   - [定时任务](#定时任务)
   
   ### 主启动程序
   
   - 启动项目 
   - 存放配置文件
   - Controller层
   
   ### Data模块
   
   设计数据模型 主要有：
   
   - book
   - user
   - 
   
   ### DAO模块
   
   用于对数据库进行操作的接口
   使用Mybatis
   
   ### Service模块
   
   主要业务实现模块
   
   ### 公共模块
   
   - 全局统一返回结果
   - 全局统一异常处理

### Data数据模块
用于提供数据给前端进行数据可视化
主要有查询
- 某天借阅人数
- 某月所有天的借阅人数
- 某年某月借阅总人数
- 从某年某月到某年某月的借阅人数 按月分组 
使用了自定义注解 给部分登陆后的操作添加日志记录
可以查询最新的100条
查询特定ip可以查询最新的30条

### 定时任务
- 每天0点自动保存前一天记录借阅人数的hash
- 每月15号自动清除上个月所保存的hash