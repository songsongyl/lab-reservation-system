# Lab Management

## functional requirement
### User Roles and Permissions
The system should support different user roles, such as Admin, Teacher, and Student.  

Admins should have full control over the lab scheduling system, including setting lab availability and managing users.  

Teachers can reserve labs, manage their schedules, and view lab usage statistics.

###  Lab Reservation Management
The system should allow users to view available lab slots based on time, date, and lab requirements.

Users should be able to book labs for a specific duration and receive confirmations.

Users should be able to modify or cancel their bookings based on availability.

The system should prevent double-booking and allow admins to review all reservations.  

## Front-End Design

**登录**  

**首页**  

- logo 头像 欢迎回来 悬浮之后显示 退出 个人信息管理 侧边栏 课程管理 Routerview 路由跳转  

- 显示当前教师课表信息 包括周次 课程名称 实验室名称等等  

- 课程： 列表显示 更新移除 左上角加号添加课程 点击之后弹出模态框 form表单 输入课程名称 选课人数 学时 有数字限制 提交  

- 课程预约： 加载当前老师全部课程 显示课程名称 下拉菜单展示  选择课程后加载指定课程详细信息 已选/全部 8/8  
 （首页已经加载课表数据，是否需要重新请求？） --基于缓存   
  限制人数 显示全部可用实验室（实验名称） 人数装不下的单独声明 一个cell可以装多个课程   
  点击一个cell显示详细信息 模态框显示 1.复选框（选不选） 课程名称 复选框来选周次    
  

- 临时预约：考试
- 当前预约： 选择课程 渲染table 右侧垃圾桶（不需要更改 应该删掉重新预约）  



## Scheme Design
