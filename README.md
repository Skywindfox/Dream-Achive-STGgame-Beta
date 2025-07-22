  # 🌠 DreamAchive - 动态弹幕射击引擎

  ## 版本 ： DEV 1.0
  
  由 **Skywind_fox** 开发的 2D 弹幕射击游戏框架，基于高性能 [LibGDX](https://libgdx.com/) 引擎构建。核心设计理念：通过数据驱动实现高度可配置的弹幕模式与敌人行为系统，支持快速关卡迭代开发。
  
  ---
  
  ## 📦 模块化项目结构
  
  ```plaintext
  DreamAchive/
  ├── core/                          # 游戏核心逻辑 (Java)
  │   ├── DreamAchiveGameMain.java   # 游戏入口
  │   ├── GameScreen.java            # 主游戏循环（实体更新/碰撞检测/渲染）
  │   ├── MainMenuScreen.java        # 菜单系统
  │   ├── PlayerBullet.java          # 玩家弹幕系统
  │   ├── EnemyBullet.java           # 敌人弹幕系统
  │   ├── Enemyinfo/                 # 敌人行为库
  │   │   ├── BasicEnemy.java        # 基础敌人模板
  │   │   ├── BasicEnemy2.java       # 多段行为敌人
  │   │   └── EnemyConfig.java       # 敌人数据容器 (支持JSON反序列化)
  │   ├── LevelManager.java          # 动态关卡加载器 (JSON → 游戏实体)
  │   └── assets/                    # 资源仓库
  │       ├── player.png             
  │       ├── bullet.png            
  │       ├── basic_enemy.png        
  │       ├── enemy_bullet.png       
  │       ├── pandingdian.png        # 判定点可视化
  │       └── level_test.json        # 关卡配置文件
  ├── desktop/                       # 桌面端启动器
  │   └── Lwjgl3Launcher.java        
  ├── build.gradle                   # Gradle构建配置
  └── README.md                      
  ```
  
  ---
  
  ## 🕹️ 精准操作控制
  
  | 控制方式          | 功能描述                          |
  |-------------------|-----------------------------------|
  | **方向键**        | 八向精准移动（像素级位移控制）    |
  | **WASD**          | 替代方向键操作方案                |
  | **Z键**           | 智能连发系统（自动冷却优化）      |
  | **Shift/X键**     | 低速模式（激活碰撞判定点可视化）  |
  
  ---
  
  ## ✨ 引擎核心特性
  
  ### 🚀 已实现能力
  - **动态弹幕系统**  
    ✅ 可旋转弹道轨迹 | ✅ 自定义发射模式 | ✅ 实体池优化
  - **敌人行为引擎**  
    ✅ 多阶段行为状态机 | ✅ 支持直线/正弦/自定义路径 | ✅ 基于继承的扩展架构
  - **数据驱动关卡**  
    ✅ JSON配置动态加载 | ✅ 毫秒级出场时间控制 | ✅ 可视化关卡编辑支持
  - **玩家控制系统**  
    ✅ 双速移动切换 | ✅ 判定点实时显示 | ✅ 连发冷却算法
  
  ### 🔧 开发中功能
  ```diff
  + 碰撞检测系统：圆形碰撞盒与伤害判定
  + 游戏状态系统：HP/分数/关卡进度管理
  + 特效系统：粒子引擎与镜头震动
  + 音频系统：Jukebox音效管理器
  ! 弹幕编辑器：可视化脚本工具（规划中）
  ```
  
  ---
  
  ## 🛠️ 快速开始指南
  
  > 基于 [GDX-Liftoff](https://github.com/tommyettinger/gdx-liftoff) 脚手架构建
  
  ### 开发环境要求
  - JDK 17+
  - Gradle 8.0+
  - Eclipse 2023+ (或 IntelliJ IDEA)
  
  ### 运行步骤
  ```bash
  # 1. 克隆仓库
  git clone https://github.com/yourusername/DreamAchive.git
  
  # 2. 导入IDE (Eclipse示例)
  File → Import → Gradle → Existing Gradle Project
  
  # 3. 执行主类
  desktop/src/main/java/io/github/skywindfox/dreamachive/lwjgl3/Lwjgl3Launcher.java
  ```
  
  ### 关卡配置示例
  ```json
  // assets/level_test.json
[
  {
    "type": "BasicEnemy",
    "time": 0.1,
    "x": 150,
    "y": 300,
    "hp": 1,
    "speed": 100,
    "shootInterval": 1000,
    "bulletSpeed": 0,
    "bulletCount": 0,
    "bulletAngleRange": 0,
    "movePattern": "straight",
    "moveParams": {
      "amplitude": 50,
      "frequency": 0.05
    }
  }
]

  ```
  
  ---
  
  ## 📜 许可协议
  
  采用 **Apache License 2.0** 开源协议，允许：
  - ✅ 商业使用
  - ✅ 代码修改
  - ✅ 专利授权
  - ✅ 私用修改
  
  *保留署名要求及责任限制*
  
  ---
  
  ## 👤 核心开发者
  
  **Skywind_fox**  
  STG游戏架构 | Java低性能游戏开发  | 0.5年Java开发经历
  [GitHub]   (https://github.com/Skywind_fox) 
  [BiliBili] (https://space.bilibili.com/255241531?spm_id_from=333.1007.0.0)
  
  > "这里我想写一句话的，但是我不知道写什么"
  
  ---
  
  ## 💌 致谢
  
  - [LibGDX](https://libgdx.com/) - 跨平台游戏框架
  - [gdx-liftoff](https://github.com/tommyettinger/gdx-liftoff) - 项目脚手架
  - 早期测试玩家群体
  
  > 🚀 **项目活跃开发中** - 每日提交更新 [![Commit Rate](https://img.shields.io/badge/commits/daily-brightgreen)]  
  > ⭐ **欢迎提交Issue和PR** - 共同打造精品STG引擎
  ```
