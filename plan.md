# 王者荣耀信息管理系统 — 项目计划书

---

## 1. 项目目标（Project Goal）

开发一个基于 Java 的控制台应用程序，模拟"王者荣耀"游戏的信息管理系统。系统支持玩家、英雄、装备、队伍和比赛记录的管理，提供查询、统计和管理功能，综合运用 Java SE 核心知识（OOP、集合、泛型、接口、异常处理等），并通过 AI 辅助完成设计与编码。

---

## 2. 需求分析（Requirement Analysis）

### 功能性需求
| 编号 | 功能 | 描述 |
|------|------|------|
| F01 | 登录 | 管理员（admin/admin123）和普通玩家（player1/123456）两种角色登录 |
| F02 | 玩家查询 | 按姓名查找玩家，显示等级、胜率、拥有英雄及装备 |
| F03 | 队伍概览 | 查看所有队伍的名称、积分、成员数等信息 |
| F04 | 英雄详情 | 查看指定英雄的基础属性和装备加成 |
| F05 | 装备统计 | 按类型统计装备数量 |
| F06 | 对战历史 | 查看比赛记录（对阵队伍、比分、时长） |
| F07 | 排行榜 | 按队伍积分或玩家胜率排序显示排名 |
| F08 | 数据管理 | 管理员可增删改英雄、装备、玩家数据 |
| F09 | 退出登录 / 退出系统 | 切换账号或结束程序 |

### 非功能性需求
- 控制台交互，输入容错处理
- 数据存储在内存（List/Map）中，无需数据库
- 硬编码至少 10 个玩家、15 个英雄、20 件装备、3 个队伍、10 场比赛

---

## 3. Java 概念使用（Java Concepts Used）

| 概念 | 使用位置 |
|------|---------|
| **封装** | 所有类的属性为 private，通过 getter/setter 或业务方法访问 |
| **继承** | `Person`（抽象类）← `Player` / `Admin` |
| **多态** | `Person.getRole()` 分别在 `Player`/`Admin` 中实现 |
| **抽象类** | `Person` 声明为 abstract，包含抽象方法 `getRole()` |
| **接口** | `Searchable<T>`、`Persistable<T>` — 泛型接口 |
| **泛型** | `Searchable<T>`、`Persistable<T>`、`List<T>`、`Map<K,V>` |
| **集合** | `ArrayList`、`HashMap`、`List`、`Map` |
| **异常处理** | `Scanner` 输入验证、`NumberFormatException` 防护 |
| **枚举** | `EquipmentType`（WEAPON/ARMOR/BOOTS/ACCESSORY）、`MatchStatus` |
| **聚合/组合** | `Hero` 聚合 `Equipment`、`Player` 聚合 `Hero` |
| **关联** | `Player` 单向关联 `Team` |

---

## 4. 类设计（Class Design）

### 包结构
```
com.honor.kings
├── model
│   ├── entity
│   │   ├── Hero.java
│   │   ├── Equipment.java
│   │   ├── Team.java
│   │   └── MatchRecord.java
│   └── person
│       ├── Person.java        (abstract)
│       ├── Player.java
│       └── Admin.java
├── service
│   ├── Searchable.java        (interface)
│   ├── Persistable.java       (interface)
│   └── impl
│       ├── HeroServiceImpl.java
│       ├── TeamServiceImpl.java
│       └── MatchServiceImpl.java
├── util
│   └── DataInitializer.java
└── HonorOfKings.java          (main entry)
```

### 关键设计决策
1. **单向关联**：`Team` 不持有 `Player` 列表，通过 `Player.currentTeam` 和 `PlayerService` 查询成员，避免一致性问题。
2. **泛型接口**：`Persistable<T>` 定义通用 CRUD，各 Service 实现类型安全的操作。
3. **静态数据层**：`DataInitializer` 持有静态列表，提供全局访问的 getter 方法。

---

## 5. UML 草稿（UML Draft）

```
Person (abstract)
├── - id: String
├── - name: String
├── - email: String
├── - createTime: LocalDateTime
├── + getRole(): String (abstract)
│
├── Player extends Person
│   ├── - level: int
│   ├── - totalMatches: int
│   ├── - winCount: int
│   ├── - ownedHeroes: List<Hero>              ——聚合——> Hero
│   └── - currentTeam: Team                     ——关联——> Team
│
└── Admin extends Person
    ├── - permissionLevel: String
    └── - department: String

Hero
├── - equipmentList: List<Equipment>            ——聚合——> Equipment
├── - faction: String
├── - level: int
├── + equip(Equipment): void
├── + unequip(String): boolean
└── + getTotalHp/Attack/Defense(): int

Equipment
├── - type: EquipmentType (enum)
└── + getDescription(): String

Team
├── - teamName: String
├── - maxMembers: int
├── - score: int
└── + isFull(): boolean

MatchRecord
├── - teamA, teamB: Team                         ——关联——> Team
├── - scoreA, scoreB: int
├── - status: MatchStatus (enum)
├── + determineWinner(): void
└── + getMatchSummary(): String

<<interface>> Searchable<T>
└── + searchByName(keyword: String): List<T>

<<interface>> Persistable<T>
├── + save(entity: T): boolean
├── + findById(id: String): T
├── + delete(id: String): boolean
└── + findAll(): List<T>
```

---

## 6. 数据设计（Data Design）

### 初始数据量
| 实体 | 数量 | 说明 |
|------|------|------|
| 装备 | 20 | 5 件 WEAPON、5 件 ARMOR、4 件 BOOTS、3 件 ACCESSORY |
| 英雄 | 15 | 涵盖三国、唐宋等不同阵营 |
| 队伍 | 3 | 荣耀战队、巅峰小队、王者之师 |
| 玩家 | 10 | 每个玩家拥有 2-4 个英雄，归属 1 个队伍 |
| 比赛 | 10 | 不同队伍配对，随机比分 |

### 数据关联
- 玩家 → 英雄：1:N（每个玩家拥有多个英雄）
- 英雄 → 装备：1:N（每个英雄装备 1-3 件装备）
- 玩家 → 队伍：N:1（多个玩家属于同一队伍）
- 比赛 → 队伍：M:N（每场比赛涉及两个队伍）

---

## 7. AI 使用计划（AI Usage Plan）

### AI 工具选择
| 阶段 | AI 工具 | 用途 |
|------|---------|------|
| 设计 | DeepSeek Chat | 架构设计审查、关联方向建议 |
| 编码 | DeepSeek Chat | 生成实体类、Service 层、数据初始化 |
| 测试 | DeepSeek Chat | 生成测试用例、代码审查 |
| 反思 | 手动 + AI 辅助 | 对比设计决策、记录学习心得 |

### AI 协作流程
1. **Architect Agent**：审查类设计、UML、关联关系 → 输出设计建议
2. **Implementation Agent**：按设计生成代码 → 手动编译验证 → 反馈修正
3. **Testing/Reviewer Agent**：生成测试用例、审查代码质量
4. **手动整合**：将 AI 片段组合到项目中，修正包路径和编译错误

---

## 8. 提示策略（Prompt Strategy）

### 提示工程原则
1. **角色设定**：每次提示明确指定 Agent 角色（Architect / Implementation / Testing）
2. **上下文约束**：说明项目包结构、类名、方法签名，避免 AI 输出不匹配的代码
3. **一次一个文件**：每次只要求生成一个类，保证输出可控
4. **只改指定文件**：明确要求"不要修改其他文件"
5. **渐进式迭代**：先设计→再生成框架→再补充细节

### 示例提示模板
```
[角色]：Implementation Agent
[任务]：请在 com.honor.kings.model.entity 包下创建 XXX 类
[约束]：只输出 Java 代码，不要额外说明
[验收标准]：编译通过，符合 README 中的类设计
```

---

## 9. 开发时间线（Development Timeline）

| 阶段 | 时间 | 任务 | 产出 |
|------|------|------|------|
| 设计 | Day 1 | 类设计、UML、README | `README.md`、`plan.md` |
| 模型层 | Day 2 | 实体类 + 人员类 | 7 个 model 类 |
| 接口与 Service | Day 3 | 泛型接口 + 3 个 Service 实现 | `Searchable`、`Persistable`、3 个 Impl |
| 数据初始化 | Day 4 | `DataInitializer` 硬编码数据 | 20 装备 + 15 英雄 + 3 队伍 + 10 玩家 + 10 比赛 |
| 主菜单 | Day 5 | 登录 + 菜单循环 + 玩家查询 | `HonorOfKings.java` |
| 测试与文档 | Day 6 | 测试用例、agent-log、reflection | `docs/test-cases.md`、`ai/` 文档 |
| 最终完善 | Day 7 | Bug 修复、编译验证、最终提交 | 项目完成 |

---

## 10. 测试计划（Testing Plan）

### 测试范围
| 模块 | 测试点 | 方法 |
|------|--------|------|
| 登录 | 正确/错误密码、角色区分 | 手动运行验证 |
| 玩家查询 | 存在/不存在的玩家 | 输入测试 |
| 队伍概览 | 列表显示 | 手动运行验证 |
| 英雄详情 | 属性显示 | 输入测试 |
| 装备统计 | 分类统计 | 手动运行验证 |
| 对战历史 | 记录列表 | 手动运行验证 |
| 排行榜 | 排序正确性 | 手动运行验证 |
| 数据管理 | 权限控制、增删改 | 角色切换测试 |
| 边界 | 空输入、无效菜单选项 | 多次输入测试 |

### 测试用例文件
详细测试用例见 `docs/test-cases.md`，包含至少 10 个测试用例。

---

## 11. 风险分析（Risk Analysis）

| 风险 | 概率 | 影响 | 缓解措施 |
|------|------|------|---------|
| AI 生成代码包路径错误 | 高 | 中 | 每次生成后手动检查 import |
| 关联关系设计错误 | 中 | 高 | Architect 审查先行，避免重构 |
| 中文编码乱码 | 中 | 中 | 统一使用 UTF-8，`chcp 65001` |
| 数据初始化数量不足 | 低 | 中 | 编写后人工核对数量 |
| 菜单输入无限循环 | 低 | 低 | 所有 Scanner 调用增加 .trim() 和默认分支 |
| 时间不足无法完成所有功能 | 中 | 高 | 优先实现核心功能（登录+查询），扩展功能占位 |

---

## 12. 最终反思占位（Final Reflection Placeholder）

### 项目完成后填写以下内容：

- **项目总体评价**：（完成度 / 质量 / 学习收获）
- **AI 协作总结**：（哪些做得好，哪些需改进）
- **后续改进方向**：（可扩展的功能、架构优化）
- **本次课设最大收获**：
