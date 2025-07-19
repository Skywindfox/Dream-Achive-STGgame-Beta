# DreamAchive 弹幕射击游戏

DreamAchive 是一款基于 LibGDX 引擎开发的弹幕射击游戏，游戏中包含多种独特的弹幕模式和丰富的玩家控制系统。

## 游戏截图
- 开发中，暂无截图
- 游戏内占位符都是瞎截图截出来的

## 主要功能
- **敌人弹幕模式**：包括圆形弹幕和螺旋弹幕两种形式
- **玩家控制**：支持角色移动、射击及慢速模式
- **完整的菜单系统**：包含主菜单和游戏内界面

## 如何运行游戏
1. 确保安装 Java 8 或更高版本
2. 下载并解压项目代码
3. 使用推荐的开发环境（如 IntelliJ IDEA）打开项目
4. 运行 `DreamAchiveGame.java` 文件即可启动游戏

## 游戏控制说明
- **方向键**：控制玩家角色的移动
- **Z键**：发射子弹
- **Shift 或 X 键**：激活慢速模式（展示判定点）
- **Enter 键**：开始新游戏

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

## 下一步计划
- 添加更多种类的弹幕模式
- 实现敌人的生命值与碰撞检测
- 添加分数系统及关卡设计
- 增加游戏暂停与结束画面

## 如何贡献
欢迎提出建议或贡献代码！如果您希望参与改进此项目，可以按以下步骤操作：
1. Fork 此仓库
2. 创建一个新分支（`git checkout -b feature/您的功能`）
3. 提交您的更改（`git commit -m '添加了XX功能'`）
4. 推送到您的分支（`git push origin feature/您的功能`）
5. 创建 Pull Request，提出您的修改

## 许可证
此项目采用 MIT 许可证，您可以自由使用、修改及分发。

## 支持的平台
- **core**：主要模块，包含所有平台共享的应用逻辑
- **lwjgl3**：基于 LWJGL3 的桌面平台，旧文档中称为 'desktop'

## 构建与管理
本项目使用 [Gradle](https://gradle.org/) 来管理依赖。Gradle Wrapper 已包含在项目中，您可以通过 `gradlew.bat`（Windows）或 `./gradlew`（Mac/Linux）命令运行 Gradle 任务。

常用 Gradle 任务和参数：
- `--continue`：使用此参数时，错误不会中止任务执行
- `--daemon`：启用 Gradle 守护进程来执行任务
- `--offline`：启用离线模式，使用缓存的依赖项
- `--refresh-dependencies`：强制验证所有依赖项，适用于快照版本
- `build`：构建所有源代码并创建项目归档文件
- `cleanEclipse`：移除 Eclipse 项目数据
- `cleanIdea`：移除 IntelliJ 项目数据
- `clean`：删除 `build` 文件夹，清理编译后的类文件和构建的归档
- `eclipse`：生成 Eclipse 项目数据
- `idea`：生成 IntelliJ 项目数据
- `lwjgl3:jar`：构建可执行的 JAR 文件，位于 `lwjgl3/build/libs`
- `lwjgl3:run`：启动应用程序
- `test`：运行单元测试（如果有）

注意，大多数非特定于单一项目的任务可以通过 `name:` 前缀运行，其中 `name` 是指定项目的 ID。例如，`core:clean` 仅会删除 `core` 项目的 `build` 文件夹。

