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

## 游戏架构
游戏采用经典的MVC（模型-视图-控制器）架构，但更具体地，我们使用LibGDX的屏幕（Screen）来管理不同的游戏状态（如主菜单、游戏主屏幕）。游戏的核心逻辑在`GameScreen`中，它负责更新游戏状态（玩家、敌人、子弹等）和渲染。
### 主要组件：
- **模型（Model）**：包括玩家(`Player`)、敌人(`Enemy`)、子弹(`Bullet`, `EnemyBullet`)、弹幕模式(`BulletPattern`及其实现)等，这些类封装了游戏实体的状态和行为。
- **视图（View）**：由各个屏幕（`MainMenuScreen`, `GameScreen`）负责渲染，使用LibGDX的`SpriteBatch`进行绘制。
- **控制器（Controller）**：输入处理分散在`Player`（处理玩家移动和射击）和各个屏幕（如`MainMenuScreen`处理开始游戏）中。
- 
## 代码结构说明
### 核心类
- `Assets`：资源管理类，使用`AssetManager`异步加载和管理纹理、关卡数据等。所有资源通过此类获取，确保资源不重复加载和统一释放。
- `BulletPattern`：弹幕模式接口，定义了`update`方法，用于更新弹幕状态并发射子弹。
  - `CirclePattern`：圆形弹幕模式，敌人向周围发射圆形分布的子弹。
  - `SpiralPattern`：螺旋弹幕模式，敌人发射螺旋状扩散的子弹。
- `EnemySpawner`：敌人生成器，根据关卡数据生成敌人波次。它管理多个`EnemyWave`，每个波次包含多个`EnemySpawnInfo`（敌人生成信息）。
- `EnemyWave`：表示一波敌人，包含多个敌人生成信息（`EnemySpawnInfo`）和波次之间的延迟。
- `LevelLoader`：关卡加载器，使用LibGDX的`Json`类从JSON文件加载关卡数据（`LevelData`）。
- `LevelData`：关卡数据类，包含多个波次（`WaveData`）的数据。
- `WaveData`：波次数据类，包含波次延迟和敌人生成数据（`SpawnData`）列表。
- `SpawnData`：敌人生成数据，包括延迟、位置、敌人类型和弹幕模式数据（`PatternData`）。
- `PatternData`：弹幕模式数据，用于定义弹幕类型的参数。
### 游戏流程
1. 游戏启动：`DreamAchiveGame`类初始化，加载资源（`Assets.load()`）并设置主菜单屏幕（`MainMenuScreen`）。
2. 主菜单：按Enter键进入游戏主屏幕（`GameScreen`）。
3. 游戏主屏幕：
   - 初始化玩家、敌人生成器等。
   - 加载关卡（`loadLevel`方法），从JSON文件读取关卡数据并配置敌人生成器。
   - 在`render`方法中更新游戏状态（玩家、敌人、子弹、碰撞检测等）并渲染。
## 资源管理
游戏资源（纹理、关卡JSON文件）通过`Assets`类统一管理。在游戏启动时调用`Assets.load()`将资源加入加载队列，然后使用`AssetManager`完成加载。资源包括：
- 纹理：玩家、敌人、子弹、判定点等。
- 关卡数据：JSON文件，位于`assets/levels/`目录下。
资源使用完毕后，在游戏退出时调用`Assets.dispose()`释放。
## 关卡设计
关卡数据使用JSON格式存储，结构如下（以`level1.json`为例）：
```json
{
  "waves": [
    {
      "delayAfterWave": 2.0,
      "spawns": [
        {
          "delay": 0,
          "x": 100,
          "type": "BASIC",
          "pattern": {
            "type": "CIRCLE",
            "interval": 0.7
          }
        },
        {
          "delay": 1,
          "x": 200,
          "type": "BASIC",
          "pattern": {
            "type": "SPIRAL",
            "baseInterval": 0.03,
            "amplitude": 0.3,
            "frequency": 1.0
          }
        }
      ]
    },
    // 更多波次...
  ]
}
```
- `waves`：关卡中的敌人波次列表。
- 每个波次（`WaveData`）包含：
  - `delayAfterWave`：本波次结束后到下一波次开始的延迟时间（秒）。
  - `spawns`：敌人生成信息列表（`SpawnData`）。
- 每个敌人生成信息（`SpawnData`）包含：
  - `delay`：相对于波次开始的延迟时间（秒）。
  - `x`：敌人在屏幕上的X坐标。
  - `type`：敌人类型（`BASIC`, `FAST`, `TANK`, `BOSS`）。
  - `pattern`：弹幕模式数据（`PatternData`），根据类型不同参数也不同。
  
## 弹幕模式
目前实现了两种弹幕模式：
### 1. 圆形弹幕（CirclePattern）
- 敌人以圆形轨迹发射子弹，子弹均匀分布在360度方向上。
- 可以设置发射间隔（`interval`），单位为秒。
- 每次发射的子弹数量在双数模式（40发）和单数模式（60发）之间交替。
### 2. 螺旋弹幕（SpiralPattern）
- 敌人发射螺旋状扩散的子弹，通过相位累加器实现旋转效果。
- 参数：
  - `baseInterval`：基础发射间隔。
  - `amplitude`：发射间隔的正弦波变化幅度，使弹幕节奏变化。
  - `frequency`：发射间隔的正弦波变化频率。
- 每次发射两发子弹，角度由相位累加器和螺旋速度计算得出。
## 碰撞检测
游戏使用矩形碰撞箱（`Rectangle`）进行碰撞检测：
- 玩家子弹与敌人：当玩家子弹的碰撞箱与敌人的碰撞箱重叠时，视为击中。
- 敌人子弹与玩家：当敌人子弹的碰撞箱与玩家的碰撞箱（判定点）重叠时，视为玩家被击中。
## 已知问题
- 目前玩家被击中仅打印日志，未实现生命值减少或游戏结束逻辑。
- 敌人类型目前仅使用`BASIC`，其他类型尚未实现差异。
- 关卡数据加载失败时使用演示波次，但演示波次数据是硬编码的。
  
## 开发注意事项
- **资源路径**：纹理资源放在`assets/`目录下，关卡JSON文件放在`assets/levels/`目录下。
- **JSON解析**：使用LibGDX的`Json`类解析关卡数据，注意注册自定义类型（如`EnemyType`）。
- **资源释放**：纹理等资源通过`AssetManager`统一管理，不要在实体类中手动释放。

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

