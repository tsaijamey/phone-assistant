# CLAUDE.md - 项目开发指南

本文档包含了在该项目中工作时的重要信息和最佳实践。

## Git 推送配置

在 Termux 环境中，使用 GitHub CLI (`gh`) 作为主要的推送工具：

### 推送代码的标准流程

1. **初始化 Git 仓库**
   ```bash
   git init
   git config --global --add safe.directory /storage/emulated/0/repos/phone-assistant
   ```

2. **配置 Git 身份**
   ```bash
   git config --global user.email "tsaijamey@users.noreply.github.com"
   git config --global user.name "tsaijamey"
   ```

3. **使用 gh 设置 Git 认证**
   ```bash
   gh auth setup-git
   ```

4. **推送代码**
   ```bash
   git add .
   git commit -m "提交信息"
   git push -u origin main
   ```

### 注意事项

- 在 Termux 环境中，由于存储权限问题，需要添加 safe.directory 配置
- 使用 `gh auth setup-git` 可以避免手动输入用户名密码
- 确保已经通过 `gh auth login` 登录 GitHub

## 项目结构

```
phone-assistant/
├── app/                     # Android 应用主目录
│   ├── src/main/java/      # Kotlin 源代码
│   └── src/main/res/       # 资源文件
├── .github/workflows/      # GitHub Actions 配置
└── README.md              # 项目说明文档
```

## 开发环境

- **平台**: Android/Termux
- **语言**: Kotlin
- **构建工具**: Gradle
- **最低 SDK**: 21

## 常用命令

### 构建相关
```bash
./gradlew build           # 构建项目
./gradlew installDebug    # 安装调试版本
./gradlew clean          # 清理构建文件
```

### Git 操作
```bash
git status               # 查看状态
git diff                 # 查看改动
git log --oneline -5     # 查看最近5次提交
```

## 代码规范

- 使用 Kotlin 官方编码规范
- 保持代码简洁易读
- 适当添加注释说明复杂逻辑
- 文件命名使用驼峰命名法

## 权限说明

应用需要的关键权限：
- `SYSTEM_ALERT_WINDOW` - 悬浮窗显示
- `ACCESSIBILITY_SERVICE` - 无障碍服务
- `INTERNET` - 网络访问

## 调试技巧

1. 使用 Android Studio 的 Logcat 查看日志
2. 在 Termux 中可以使用 `adb logcat` 查看日志
3. 添加断点调试 Kotlin 代码

## 更新记录

- 2025-07-29: 项目初始化，创建基础悬浮窗服务
- 2025-07-29: 添加中文 README 文档
- 2025-07-29: 配置 GitHub Actions 自动构建