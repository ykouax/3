# 在线商城系统 - 前端

一个基于 Vue.js 和 Element UI 的在线商城前端项目。

## 项目简介

这是一个现代化的电商前端界面，提供用户购物和管理员后台管理功能。

## 功能特性

### 用户功能
- 🛍️ 商品浏览与搜索
- 🛒 购物车管理
- 👤 用户注册与登录
- 💳 在线支付（微信支付）

### 管理员功能
- 🔐 管理员登录
- 📦 商品管理（增删改查）
- 📊 商品库存管理

## 技术栈

- **Vue.js 2.7.6** - 渐进式 JavaScript 框架
- **Element UI** - 基于 Vue 的组件库
- **Axios** - HTTP 客户端（需自行下载）
- **原生 JavaScript** - 核心逻辑

## 项目结构

```
frontend/
├── index.html              # 主页面
├── assets/                # 静态资源目录
│   ├── css/              # 样式文件（可扩展）
│   ├── js/               # JavaScript 资源
│   │   ├── vue/         # Vue.js 核心库
│   │   │   ├── vue-2.7.6.min.js
│   │   │   ├── vue-router-3.6.5.js
│   │   │   └── axios.min.js (需下载)
│   │   ├── element/     # Element UI 组件库
│   │   │   ├── index.js
│   │   │   ├── index.css
│   │   │   └── fonts/
│   │   └── utils/       # 工具函数
│   │       └── ajaxUtils.js
│   └── images/          # 图片资源
│       ├── product-*.jpg
│       └── wechat-pay.jpg
└── README.md             # 项目说明文档
```

## 快速开始

### 前置要求

- 现代浏览器（Chrome、Firefox、Edge 等）
- 后端服务（Java Servlet API）已启动

### 安装步骤

1. **克隆项目**
   ```bash
   git clone <your-repository-url>
   cd frontend
   ```

2. **下载 Axios**（必需）
   
   由于 axios.min.js 未包含在仓库中，请从以下任一方式获取：
   
   **方式一：CDN 引用**（推荐快速测试）
   
   在 `index.html` 中将：
   ```html
   <script src="./assets/js/vue/axios.min.js"></script>
   ```
   替换为：
   ```html
   <script src="https://cdn.jsdelivr.net/npm/axios@1.6.0/dist/axios.min.js"></script>
   ```

   **方式二：本地下载**
   - 访问 [Axios GitHub Releases](https://github.com/axios/axios/releases)
   - 下载 `axios.min.js` 文件
   - 放置到 `assets/js/vue/axios.min.js`

3. **配置后端地址**
   
   在 `index.html` 第 573 行修改后端服务地址：
   ```javascript
   var xhr = axios.create({
       baseURL: 'http://localhost:8080'  // 本地开发环境
       // baseURL: 'https://your-domain.com'  // 生产环境
   });
   ```

4. **启动项目**
   
   由于是纯静态前端项目，您可以：
   
   - **方式一：直接打开**
     双击 `index.html` 在浏览器中打开
   
   - **方式二：使用 Live Server**（推荐）
     ```bash
     # 使用 VS Code 的 Live Server 扩展
     # 或使用 Python 快速启动服务器
     python -m http.server 8000
     # 访问 http://localhost:8000
     ```

## API 接口

项目需要后端提供以下 RESTful API：

| 功能 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 获取商品列表 | GET | `/api/products` | 支持 `?keyword=` 搜索参数 |
| 添加商品 | POST | `/api/products` | 管理员功能 |
| 更新商品 | PUT | `/api/products` | 管理员功能 |
| 删除商品 | DELETE | `/api/products?id=` | 管理员功能 |
| 用户登录 | GET | `/api/users?username=` | 验证用户 |
| 用户注册 | POST | `/api/users` | 创建新用户 |
| 管理员登录 | POST | `/api/admins` | 管理员验证 |
| 获取购物车 | GET | `/api/cart?userId=` | 用户购物车 |
| 添加到购物车 | POST | `/api/cart` | 添加商品 |
| 更新购物车 | PUT | `/api/cart` | 更新数量 |
| 删除购物车 | DELETE | `/api/cart?id=` | 移除商品 |

## 使用说明

### 用户端
1. 访问 `index.html` 查看商品列表
2. 点击右上角"登录"进行用户登录/注册
3. 浏览商品，点击"加入购物车"
4. 查看购物车，调整数量，点击"微信结算"

### 管理员端
1. 点击右上角"管理后台"
2. 默认管理员账号：`admin`，密码：`admin123`
3. 进入商品管理界面
4. 可添加、编辑、删除商品

## 配置说明

### 修改后端地址
编辑 `index.html` 第 573-576 行：
```javascript
var xhr = axios.create({
    baseURL: 'http://your-backend-url'  // 修改为实际后端地址
});
```

### 添加商品图片
将商品图片放置在 `assets/images/` 目录下，命名规则：
- `product-{id}.jpg` - 根据商品 ID 自动匹配
- 或在管理后台设置图片 URL

## 浏览器支持

- Chrome (推荐)
- Firefox
- Safari
- Edge
- 不支持 IE11 及以下版本

## 注意事项

⚠️ **重要提示**：
1. 项目依赖后端 API，请确保后端服务已启动
2. `axios.min.js` 需要自行下载或使用 CDN
3. 跨域问题：如后端地址不同，需配置 CORS
4. 生产环境建议使用 HTTPS

## 开发建议

### 调试模式
按 F12 打开浏览器开发者工具，查看：
- Console：JavaScript 错误
- Network：API 请求状态
- Vue DevTools：Vue 组件状态

### 常见问题

**Q: 页面加载后显示空白？**
- 检查浏览器控制台是否有错误
- 确认 axios.min.js 已正确加载
- 验证后端服务是否启动

**Q: 商品图片不显示？**
- 确认图片文件存在于 `assets/images/` 目录
- 检查图片命名是否正确
- 查看浏览器控制台 404 错误

**Q: 无法登录或注册？**
- 检查后端 API 是否正常
- 查看 Network 面板的请求响应
- 确认后端地址配置正确

## 后续优化建议

- [ ] 使用 Vue CLI 构建工程化项目
- [ ] 引入状态管理（Vuex）
- [ ] 添加单元测试
- [ ] 图片懒加载优化
- [ ] 移动端适配优化
- [ ] 添加商品详情页
- [ ] 订单管理功能

## 许可证

本项目仅供学习交流使用。

## 联系方式

如有问题或建议，请联系项目维护者。
