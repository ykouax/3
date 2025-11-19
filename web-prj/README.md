# 在线商城系统

## 系统功能

### 用户功能
1. 用户注册与登录
2. 商品浏览与搜索
3. 购物车管理
4. 商品下单（待实现）

### 管理员功能
1. 管理员登录
2. 商品管理（增删改查）

## 使用说明

### 前端页面
- 用户商城页面: `甜品.html`
- 管理员页面: `admin.html`

### 管理员登录
- 访问 `admin.html`
- 默认管理员账号: `admin`
- 默认管理员密码: `admin123`

### 数据库设置
1. 执行 `admin_setup.sql` 创建管理员表并插入默认管理员账户
2. 执行 `update_product_table.sql` 为商品表添加图片URL字段

## 技术架构
- 前端: HTML + Vue.js + Element UI
- 后端: Java Servlet
- 数据库: MySQL

## API接口
- 商品管理: `/api/products`
- 用户管理: `/api/users`
- 管理员管理: `/api/admins`
- 购物车管理: `/api/cart`