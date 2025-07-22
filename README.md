  # 🌠 DreamAchive
  
  由 **Skywind_fox** 开发的 2D 弹幕射击游戏，基于 [LibGDX](https://libgdx.com/) 引擎构建，目标是实现一个拥有高速/低速移动、连发射击、多阶段敌人行为与自定义弹幕的精致 STG 游戏。
  
  ---
  
  ## 📦 项目结构
  
  ```
  DreamAchive/
  ├── core/ # 游戏核心逻辑（Java）
  │   ├── DreamAchiveGameMain.java
  │   ├── GameScreen.java # 游戏主循环与渲染
  │   ├── MainMenuScreen.java
  │   ├── PlayerBullet.java
  │   ├── EnemyBullet.java
  │   ├── Enemyinfo/ # 所有敌人类型
  │   │   ├── BasicEnemy.java
  │   │   └── BasicEnemy2.java
  │   └── assets/ # 素材目录
  │       ├── player.png
  │       ├── bullet.png
  │       ├── basic_enemy.png
  │       └── pandingdian.png
  ├── desktop/ # 桌面端启动器
  │   └── Lwjgl3Launcher.java
  ├── build.gradle
  └── README.md
  ```
  
  ---
  
  ## 🕹️ 游戏操作
  
  | 键位                 | 功能说明                     |
  |----------------------|------------------------------|
  | ← / → / ↑ / ↓        | 控制玩家上下左右移动         |
  | A / D / W / S        | 替代方向键进行移动控制       |
  | Z                    | 连续射击（子弹冷却机制）     |
  | Shift 或 X           | 低速模式（显示判定点）       |
  
  ---
  
  ## ✨ 特性一览
  
  - ✅ 玩家连发机制、低速移动与判定点展示  
  - ✅ 玩家与敌人子弹系统、可旋转弹幕方向
  - ✅ 支持多段移动行为的敌人（例如 BasicEnemy2）
  - ✅ 基类敌人 `BasicEnemy` 可扩展不同行为
  - ✅ 模块化架构，便于维护与扩展
  - 🚧 正在开发的功能：
    - 敌人与玩家碰撞判定
    - 血量系统 / 得分系统
    - 自定义关卡与弹幕脚本
    - 背景滚动与音效支持
  
  ---
  
  ## 🛠️ 构建与运行方式
  
  > 本项目使用 [GDX-Liftoff](https://github.com/tommyettinger/gdx-liftoff) 构建，支持 Gradle。
  
  ### 💻 运行方式（推荐 IDE：Eclipse）
  
  1. 克隆项目：
     ```bash
     git clone https://github.com/yourusername/DreamAchive.git
     ```
  2. 导入项目（Eclipse）：
     `File → Import → Gradle → Existing Gradle Project → 选择项目根目录`
  3. 运行桌面启动器：
     ```bash
     # Eclipse 中右键运行：
     desktop → src → main → java → io.github.skywindfox.dreamachive.lwjgl3 → Lwjgl3Launcher.java
     ```
  
  ---
  
  ## 📜 使用许可
  
  本项目目前为个人开发阶段，若计划开源发布，请自行选择适合的开源许可证（如 MIT 或 Apache-2.0）。
  
  ---
  
  ## 👤 作者
  
  **Skywind_fox**  
  「一个热爱射击游戏与 Java 开发的 STG 玩家 & 开发者」
  
  ---
  
  ## 💬 鸣谢
  
  - [LibGDX 引擎](https://libgdx.com/)
  - [GDX-Liftoff 脚手架](https://github.com/tommyettinger/gdx-liftoff)
  - 所有鼓励与建议过此项目的朋友
  
  > 🚧 本项目仍在开发早期阶段，感谢你的关注与支持！