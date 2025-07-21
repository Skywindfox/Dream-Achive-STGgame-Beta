# DreamAchive 弹幕射击游戏

DreamAchive 是一款基于 LibGDX 引擎开发的弹幕射击游戏，游戏中包含多种独特的弹幕模式和丰富的玩家控制系统。

## 游戏截图

开发中，暂无截图  
游戏内占位符都是瞎截图截出来的

## 主要功能

- 敌人弹幕模式：包括圆形弹幕和螺旋弹幕两种形式  
- 玩家控制：支持角色移动、射击及慢速模式  
- 完整的菜单系统：包含主菜单和游戏内界面

## 如何运行游戏

1. 确保安装 Java 8 或更高版本  
2. 下载并解压项目代码  
3. 使用推荐的开发环境（如 IntelliJ IDEA）打开项目  
4. 运行 `DreamAchiveGame.java` 文件即可启动游戏

## 游戏控制说明

- 方向键：控制玩家角色的移动  
- Z键：发射子弹  
- Shift 或 X 键：激活慢速模式（展示判定点）  
- Enter 键：开始新游戏

## 项目文件结构

### 主要代码文件：

- `Bullet.java`：玩家子弹类  
- `BulletPattern.java`：弹幕模式接口  
- `CirclePattern.java`：圆形弹幕实现  
- `SpiralPattern.java`：螺旋弹幕实现  
- `DreamAchiveGame.java`：游戏主类，负责游戏的初始化与运行  
- `Enemy.java`：敌人类，负责敌人的行为与属性  
- `EnemyBullet.java`：敌方子弹类  
- `GameScreen.java`：游戏主画面类  
- `MainMenuScreen.java`：主菜单画面类  
- `Player.java`：玩家角色类  

### 资源文件（位于 `assets` 文件夹中）：

- `enemy.png`：敌人图像  
- `enemy_bullet.png`：敌方子弹图像  
- `hitbox_test.png`：判定点图像  
- `wtfisthis.png`：玩家角色图像

---

## 🚀 构建与运行

项目已内置 Gradle Wrapper，支持以下常用命令：

- `./gradlew lwjgl3:run`  
  运行游戏（开发调试用）

- `./gradlew build`  
  构建整个项目，包括所有模块

- `./gradlew lwjgl3:jar`  
  构建桌面平台的可执行 JAR 包，输出目录为 `lwjgl3/build/libs`

- `./gradlew clean`  
  清理项目中的构建缓存（build 文件夹）

其他可选参数说明：

- `--refresh-dependencies`：强制刷新依赖项缓存  
- `--offline`：启用离线模式构建  
- `--continue`：即使有任务失败也继续执行后续任务  
- `--daemon`：后台运行 Gradle 进程，加快后续构建速度

---

## ✅ 下一步计划

- 添加敌人类和基础 AI  
- 实现弹幕生成器系统（螺旋弹幕、圆形弹幕等）  
- 实装玩家与敌人碰撞判定机制  
- 添加计分系统、分数 UI 和游戏内反馈  
- 设计基本关卡逻辑与时间轴控制  
- 添加暂停菜单与游戏结束画面  
- 音效、BGM 与资源管理

---

## 🤝 如何贡献

欢迎对本项目提出建议或贡献代码。贡献方式如下：

1. Fork 本项目  
2. 创建一个新的分支：`git checkout -b feature/your-feature`  
3. 提交你的更改：`git commit -m "添加了某某功能"`  
4. 推送到你的仓库：`git push origin feature/your-feature`  
5. 创建 Pull Request（合并请求）

我们会尽快审查并与你沟通！

---

## 📜 许可证

本项目采用 MIT License 授权，允许个人和商业用途、修改、分发，但需要保留原作者署名和许可声明。

---

## 🖥 支持平台

- `core`：核心逻辑模块，包含游戏逻辑、数据结构和资源引用  
- `lwjgl3`：桌面平台模块，基于 LWJGL3 提供渲染与窗口支持

---

## ✨ 致谢

本项目基于 LibGDX 引擎开发，感谢其强大的功能与活跃的社区。  
特别感谢所有参与和支持此项目的开发者与玩家！
