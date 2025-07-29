# 手机助手

一个个人手机助手应用，通过悬浮按钮界面提供快速访问各种功能。

## 功能特性

- 悬浮按钮始终显示在其他应用之上
- 快速访问助手功能
- 现代化的 Material Design 用户界面
- 集成无障碍服务以实现高级功能

## 快速开始

### 系统要求

- Android Studio Arctic Fox 或更新版本
- Android SDK 21 或更高版本
- Kotlin 1.5.0 或更高版本

### 安装步骤

1. 克隆仓库：
   ```bash
   git clone https://github.com/tsaijamey/phone-assistant.git
   ```

2. 在 Android Studio 中打开项目

3. 在您的设备或模拟器上构建并运行应用

### 使用方法

1. 启动应用并授予必要的权限
2. 在提示时启用无障碍服务
3. 悬浮按钮将出现在您的屏幕上
4. 点击按钮以访问助手功能

## 开发说明

### 项目结构

```
phone-assistant/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/phoneassistant/
│   │   │   │   ├── MainActivity.kt          # 主活动
│   │   │   │   └── FloatingButtonService.kt # 悬浮按钮服务
│   │   │   └── res/
│   │   │       ├── layout/                  # 布局文件
│   │   │       ├── drawable/                # 图形资源
│   │   │       └── values/                  # 值资源
│   │   └── build.gradle
│   └── build.gradle
└── README.md
```

### 构建项目

构建项目：

```bash
./gradlew build
```

安装到已连接的设备：

```bash
./gradlew installDebug
```

## 主要功能模块

### 悬浮按钮服务
- 使用 Android 的 WindowManager 实现悬浮窗口
- 支持拖动和点击交互
- 自动贴边功能

### 无障碍服务
- 提供系统级别的操作权限
- 支持手势模拟和界面交互
- 增强的助手功能实现

## 权限说明

应用需要以下权限：
- `SYSTEM_ALERT_WINDOW` - 显示悬浮窗口
- `ACCESSIBILITY_SERVICE` - 提供高级辅助功能
- `INTERNET` - 网络访问（用于在线功能）

## 贡献指南

欢迎贡献代码！请随时提交 Pull Request。

### 开发规范
- 使用 Kotlin 编码规范
- 保持代码简洁和可读性
- 添加必要的注释说明
- 提交前运行测试

## 许可证

本项目采用 MIT 许可证 - 详见 LICENSE 文件。

## 联系方式

如有问题或建议，请通过以下方式联系：
- 提交 Issue：[GitHub Issues](https://github.com/tsaijamey/phone-assistant/issues)
- Pull Request：[GitHub PR](https://github.com/tsaijamey/phone-assistant/pulls)