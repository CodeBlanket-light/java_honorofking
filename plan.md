# 王者荣耀信息管理系统 — 项目计划书


## 1. 项目目标（Project Goal）


开发一个基于 Java 的控制台应用程序，模拟"王者荣耀"游戏的信息管理系统。系统支持玩家、英雄、装备、队伍和比赛记录的管理，提供查询、排名、数据管理、装备推荐、战斗模式等功能，综合运用 Java SE 核心知识（OOP、集合、泛型、接口、枚举、异常处理、文件 I/O），并通过 AI 辅助完成设计与编码。



## 2. 需求分析（Requirement Analysis）


### 功能性需求
| 编号 | 功能 | 描述 |
|------|------|------|
| F01 | 登录 | 管理员（admin/admin123）和普通玩家（player1/123456）两种角色登录，支持注册新玩家 |
| F02 | 玩家查询 | 按姓名查找玩家，显示等级、胜率、拥有英雄及装备 |
| F03 | 队伍概览 | 按名称或 ID 查找队伍，显示成员、平均等级、比赛统计、胜率、最强玩家及最近比赛 |
| F04 | 英雄详情 | 查看英雄属性、类型、装备列表、拥有者及推荐装备（按综合分排序） |
| F05 | 装备统计 | 按综合分（使用次数×0.3 + 平均胜率×0.7）排名前 5 名 |
| F06 | 对战历史 | 显示战斗模式最近 5 场记录，含胜负统计、英雄选用率、装备出场率及胜场数 |
| F07 | 排行榜 | 按胜率降序排列前 3 名（胜率相同按总场次降序） |
| F08 | 数据管理 | 管理员可增删玩家、英雄、装备 |
| F09 | 战斗模式 | 从 15 个英雄中随机选 3 个进行回合制对战，记录保留最近 5 场 |
| F10 | 退出登录 / 退出系统 | 切换账号或保存数据并结束程序 |

### 非功能性需求
- 控制台交互，输入容错处理
- 数据通过序列化持久化到文件（data/game_data.ser）
- 硬编码至少 10 个玩家、15 个英雄、20 件装备、3 个队伍、10 场比赛



## 3. Java 概念使用（Java Concepts Used）


| 概念 | 使用位置 |
|------|---------|
| **封装** | 所有类的属性为 private，通过 getter/setter 或业务方法访问 |
| **继承** | `Person`（抽象类）← `Player` / `Admin` |
| **多态** | `Person.getRole()` 在 Player/Admin 中不同实现；instanceof 权限检查；接口多态 |
| **抽象类** | `Person` 声明为 abstract，包含抽象方法 `getRole()` |
| **接口** | `Searchable<T>`、`Persistable<T>` — 泛型接口，多态实现 |
| **泛型** | `Searchable<T>`、`Persistable<T>`、`List<T>`、`Map<K,V>` |
| **集合** | `ArrayList`、`HashMap`、`Stream API` 用于数据存储和统计 |
| **异常处理** | `try-catch` 处理 NumberFormatException、IOException、ClassNotFoundException |
| **文件 I/O** | `ObjectOutputStream`/`ObjectInputStream` 序列化保存/加载 GameData |
| **枚举** | `HeroType`（WARRIOR/MAGE）、`EquipmentCategory`（TANK/MAGIC）、`MatchStatus`（SCHEDULED/IN_PROGRESS/FINISHED/CANCELLED） |
| **聚合/组合** | `Hero` 聚合 `Equipment`、`Player` 聚合 `Hero` |
| **关联** | `Player` 单向关联 `Team`（不持有反向引用） |



## 4. 类设计（Class Design）


### 包结构
```
com.honor.kings
├── model
│   ├── entity
│   │   ├── Hero.java              (含 HeroType 枚举)
│   │   ├── Equipment.java          (含 EquipmentCategory 枚举)
│   │   ├── Team.java
│   │   ├── MatchRecord.java        (含 MatchStatus 枚举)
│   │   └── BattleRecord.java
│   └── person
│       ├── Person.java             (abstract)
│       ├── Player.java
│       └── Admin.java
├── service
│   ├── Searchable.java             (interface)
│   ├── Persistable.java            (interface)
│   └── impl
│       ├── HeroServiceImpl.java
│       ├── TeamServiceImpl.java
│       └── MatchServiceImpl.java
├── util
│   ├── DataInitializer.java
│   ├── GameData.java               (序列化封装)
│   └── FileStorageUtil.java        (文件 I/O 工具)
├── hero/                            (战斗英雄抽象类及子类)
├── battle/                          (战斗系统)
├── skill/                           (技能类)
└── HonorOfKings.java                (main 入口 + 菜单循环)
```

### 关键设计决策
1. **单向关联**：`Team` 不持有 `Player` 列表，通过 `Player.currentTeam` 查询成员，避免双向一致性问题。
2. **泛型接口**：`Persistable<T>` 定义通用 CRUD，各 Service 实现类型安全的操作。
3. **序列化持久化**：所有实体实现 `Serializable`，通过 `GameData` 封装后写入 `data/game_data.ser`。
4. **枚举分类**：英雄分 WARRIOR/MAGE 类型，装备分 TANK/MAGIC 类别，用于装备推荐。
5. **战斗记录独立**：`BattleRecord` 专门存储战斗模式结果，与 `MatchRecord`（队伍比赛记录）分离。



## 5. UML 草稿（UML Draft）


```mermaid
classDiagram
    class Person {
        <<abstract>>
        - String id
        - String name
        - String email
        - LocalDateTime createTime
        + getRole() String*
    }
    class Player {
        - String password
        - int level
        - int totalMatches
        - int winCount
        - List~Hero~ ownedHeroes
        - Team currentTeam
        + getRole() String
        + getWinRate() double
        + joinTeam(Team)
        + leaveTeam()
        + addHero(Hero)
    }
    class Admin {
        - String permissionLevel
        - String department
        + getRole() String
        + approveMatch(MatchRecord)
        + banPlayer(Player)
        + adjustHeroStats(Hero)
    }
    class Hero {
        - String id
        - String name
        - String title
        - int baseHp
        - int baseAttack
        - int baseDefense
        - List~Equipment~ equipmentList
        - String faction
        - int level
        - HeroType heroType
        + equip(Equipment)
        + unequip(String) boolean
        + getTotalHp() int
        + getTotalAttack() int
        + getTotalDefense() int
    }
    class Equipment {
        - String id
        - String name
        - EquipmentCategory category
        - int bonusHp
        - int bonusAttack
        - int bonusDefense
        - int price
        + getDescription() String
    }
    class Team {
        - String id
        - String teamName
        - int maxMembers
        - int score
        - LocalDateTime foundedTime
        + isFull() boolean
    }
    class MatchRecord {
        - String id
        - Team teamA
        - Team teamB
        - int scoreA
        - int scoreB
        - Team winner
        - String duration
        - LocalDateTime matchTime
        - MatchStatus status
        + determineWinner()
        + getMatchSummary() String
    }
    class BattleRecord {
        - String time
        - String hero1Name
        - String hero2Name
        - List~String~ hero1Equipment
        - List~String~ hero2Equipment
        - String result
        + getAllEquipmentNames() List~String~
    }
    class Searchable {
        <<interface>>
        + searchByName(String) List~T~
    }
    class Persistable {
        <<interface>>
        + save(T) boolean
        + findById(String) T
        + delete(String) boolean
        + findAll() List~T~
    }
    class HeroType {
        <<enumeration>>
        WARRIOR
        MAGE
    }
    class EquipmentCategory {
        <<enumeration>>
        TANK
        MAGIC
    }
    class MatchStatus {
        <<enumeration>>
        SCHEDULED
        IN_PROGRESS
        FINISHED
        CANCELLED
    }

    Person <|-- Player
    Person <|-- Admin
    Player o--> "0..*" Hero : 拥有
    Hero o--> "0..*" Equipment : 装备
    Player --> "0..1" Team : 属于
    MatchRecord --> "2" Team : 对战
    Hero --> HeroType
    Equipment --> EquipmentCategory
    MatchRecord --> MatchStatus
    HeroServiceImpl ..|> Searchable
    HeroServiceImpl ..|> Persistable
    TeamServiceImpl ..|> Searchable
    TeamServiceImpl ..|> Persistable
    MatchServiceImpl ..|> Persistable




## 6. 数据设计（Data Design）


### 初始数据量
| 实体 | 数量 | 说明 |

| 装备 | 20 | 12 件 TANK（肉装）、8 件 MAGIC（法装） |
| 英雄 | 15 | 11 个 WARRIOR（战士）、4 个 MAGE（法师） |
| 队伍 | 3 | 荣耀战队、巅峰小队、王者之师 |
| 玩家 | 10 | 每个玩家拥有 2-4 个英雄，归属 1 个队伍 |
| 比赛 | 10 | 不同队伍配对，随机比分 |

### 数据关联
- 玩家 → 英雄：1:N（每个玩家拥有多个英雄）
- 英雄 → 装备：1:N（每个英雄必有一双靴子 + 1-2 件其他装备）
- 玩家 → 队伍：N:1（多个玩家属于同一队伍）
- 比赛 → 队伍：M:N（每场比赛涉及两个队伍）
- 装备推荐：HeroType.WARRIOR → EquipmentCategory.TANK，HeroType.MAGE → EquipmentCategory.MAGIC



## 7. AI 使用计划（AI Usage Plan）


### AI 工具选择
| 阶段 | AI 工具 | 用途 |

| 设计 | DeepSeek Chat / DeepSeek-V3 | 架构设计审查、关联方向建议、枚举设计 |
| 编码 | DeepSeek Chat / DeepSeek-V3 | 生成实体类、Service 层、数据初始化、主菜单 |
| 测试 | DeepSeek Chat / DeepSeek-V3 | 生成测试用例、代码审查、功能验证 |
| 反思 | 手动 + AI 辅助 | 对比设计决策、记录学习心得 |

### AI 协作流程
1. **Architect Agent**：审查类设计、UML、关联关系 → 输出设计建议
2. **Implementation Agent**：按设计生成代码 → 手动编译验证 → 反馈修正
3. **Testing/Reviewer Agent**：生成测试用例、审查代码质量、运行验证
4. **手动整合**：将 AI 片段组合到项目中，修正包路径和编译错误


## 8. 提示策略（Prompt Strategy）


### 提示工程原则
1. **角色设定**：每次提示明确指定 Agent 角色（Architect / Implementation / Testing）
2. **上下文约束**：说明项目包结构、类名、方法签名，避免 AI 输出不匹配的代码
3. **一次一个文件**：每次只要求生成一个类或修改一个文件，保证输出可控
4. **只改指定文件**：明确要求"不要修改其他文件"
5. **渐进式迭代**：先设计→再生成框架→再补充细节→再测试验证
6. **专业名词指挥**： 使用较为专业的词汇可以提高生成质量，并且便于后期修改时定位

### 示例提示模板

[角色]：Implementation Agent
[任务]：请在 com.honor.kings.model.entity 包下创建 XXX 类
[约束]：只输出 Java 代码，不要额外说明
[验收标准]：编译通过，符合 README 中的类设计




## 9. 开发时间线（Development Timeline）


| 阶段 | 时间 | 任务 | 产出 |
|------|------|------|------|
| 设计 | Day 1 | 类设计、UML、README | `README.md`、`plan.md` |
| 模型层 | Day 2 | 实体类 + 人员类 | 7 个 model 类 |
| 接口与 Service | Day 3 | 泛型接口 + 3 个 Service 实现 | `Searchable`、`Persistable`、3 个 Impl |
| 数据初始化 | Day 4 | `DataInitializer` 硬编码数据 | 20 装备 + 15 英雄 + 3 队伍 + 10 玩家 + 10 比赛 |
| 主菜单 | Day 5 | 登录 + 菜单循环 + 玩家查询 | `HonorOfKings.java` |
| 扩展功能 | Day 6 | 排名公式、装备推荐、战斗模式 | 排行榜、装备统计、英雄详情推荐 |
| 持久化 | Day 7 | 序列化保存/加载 | `FileStorageUtil`、`GameData`、`BattleRecord` |
| 测试与文档 | Day 8 | 测试用例、agent-log、reflection | `docs/test-cases.md`、`ai/` 文档 |
| 最终完善 | Day 9 | Bug 修复、编译验证、最终提交 | 项目完成 |



## 10. 测试计划（Testing Plan）


### 测试范围
| 模块 | 测试点 | 方法 |
|------|--------|------|
| 登录 | 正确/错误密码、角色区分、输入验证 | 手动运行验证 |
| 注册 | 用户名唯一性、密码长度校验、登录 | 功能测试 |
| 玩家查询 | 存在/不存在的玩家 | 输入测试 |
| 队伍概览 | 按名称/ID 查找、新建队伍 | 输入测试 |
| 英雄详情 | 属性显示、装备推荐（WARRIOR→TANK/MAGE→MAGIC） | 输入测试 |
| 装备统计 | 综合分排名公式 | 手动运行验证 |
| 对战历史 | 战斗记录、装备出场统计 | 手动运行验证 |
| 排行榜 | 胜率降序排序、平局处理 | 手动运行验证 |
| 数据管理 | 权限控制、增删操作 | 角色切换测试 |
| 战斗模式 | 英雄选择、战斗流程、记录持久化 | 功能测试 |
| 边界 | 空输入、无效菜单选项、编码问题 | 多次输入测试 |

### 测试用例文件
详细测试用例见 `docs/test-cases.md`，包含 13 个测试用例（TC-01 ~ TC-13）。



## 11. 风险分析（Risk Analysis）


| 风险 | 概率 | 影响 | 缓解措施 |

| AI 生成代码包路径错误 | 高 | 中 | 每次生成后手动检查 import |
| 关联关系设计错误 | 中 | 高 | Architect 审查先行，避免重构 |
| 中文编码乱码 | 中 | 中 | 统一使用 UTF-8，`chcp 65001` |
| 序列化兼容性问题 | 中 | 中 | 所有实体类添加 serialVersionUID |
| 数据初始化数量不足 | 低 | 中 | 编写后人工核对数量 |
| 菜单输入无限循环 | 低 | 低 | 所有 Scanner 调用增加 .trim() 和默认分支 |
| 时间不足无法完成所有功能 | 中 | 高 | 优先实现核心功能（登录+查询），扩展功能占位 |



## 12. 最终反思（Final Reflection）


### 项目总体评价
项目完成了所有核心功能需求：玩家查询、队伍概览、英雄详情（含装备推荐）、装备统计（含综合分排名）、对战历史（含装备出场统计）、排行榜（含多级排序）、登录注册、数据管理、战斗模式彩蛋。代码编译通过，运行正常，测试用例 13 个全部通过。

### AI 协作总结
- 做得好：Architect Agent 在设计阶段发现了双向关联的一致性问题，避免了后期大规模重构。Implementation Agent 高效生成了大量样板代码（实体类、Service 层），节省约 70% 的编码时间。Testing/Reviewer Agent 以及 **人工测试** 帮助发现测试用例与代码不一致的问题。
- **需改进**：AI 生成的代码有时包路径不匹配，需要手动修正。而且部分细节处理并没有逻辑，并且使用时会出现名词理解错误，从而导致执行错误，部分建议（如 getTeamMembers）不够完整，需要人工补充。

### 后续改进方向
- 增加 JUnit 单元测试覆盖核心逻辑
- 支持管理员编辑（修改）操作，不限于增删
- 战斗模式使用真实的 15 个英雄进行战斗（而非仅名字展示）
- 支持按时间段查询对战历史

### 本次课设最大收获
通过 AI 辅助设计与编码，深入理解了单向关联 vs 双向关联的设计取舍。同时，通过手动修正 AI 生成的包路径错误和胜率计算 Bug，真正实践了"测试和调试 AI 辅助代码，而非盲目信任生成代码"的原则。
