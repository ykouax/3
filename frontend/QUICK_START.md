# 快速启动指南

## 📦 上传到 GitHub

### 1. 初始化 Git 仓库
```bash
cd frontend
git init
git add .
git commit -m "Initial commit: 在线商城前端项目"
```

### 2. 创建 GitHub 仓库
1. 登录 [GitHub](https://github.com)
2. 点击右上角 "+" → "New repository"
3. 输入仓库名称（如：`online-shop-frontend`）
4. 选择 Public 或 Private
5. 不要勾选 "Initialize with README"（我们已有 README.md）
6. 点击 "Create repository"

### 3. 推送到 GitHub
```bash
# 添加远程仓库（替换为你的仓库地址）
git remote add origin https://github.com/your-username/online-shop-frontend.git

# 推送代码
git branch -M main
git push -u origin main
```

---

## 🚀 本地运行

### 方式一：直接打开
双击 `index.html` 在浏览器中打开

### 方式二：使用本地服务器（推荐）

**使用 Python（需安装 Python）：**
```bash
# Python 3
python -m http.server 8000

# Python 2
python -m SimpleHTTPServer 8000
```
然后访问：http://localhost:8000

**使用 VS Code Live Server：**
1. 安装 VS Code 扩展：Live Server
2. 右键 `index.html` → "Open with Live Server"

**使用 Node.js http-server：**
```bash
npx http-server -p 8000
```

---

## ⚙️ 配置后端地址

编辑 `index.html` 第 573 行：
```javascript
var xhr = axios.create({
    baseURL: 'http://localhost:8080'  // 修改为你的后端地址
});
```

---

## 📝 默认管理员账号

- **用户名**: `admin`
- **密码**: `admin123`

---

## 🛠️ 故障排除

**问题：页面空白或无法加载？**
- 检查浏览器控制台（F12）是否有错误
- 确认后端服务已启动
- 验证后端地址配置正确

**问题：图片不显示？**
- 确认 `assets/images/` 目录下有图片文件
- 检查图片命名格式：`product-{id}.jpg`

**问题：无法登录或购物？**
- 确认后端 API 服务正常运行
- 检查 Network 面板的 API 请求状态
- 确认跨域（CORS）配置正确

---

## 📚 更多信息

详细文档请查看 [README.md](./README.md)
