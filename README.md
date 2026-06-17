# 王者荣耀信息管理系统 — Honor of Kings Management System

---

## 1. Project Overview

王者荣耀信息管理系统是一个基于 Java 的控制台应用程序，用于管理玩家、英雄、装备、队伍和比赛记录。系统提供查询、排名、数据管理、装备推荐、战斗模式等功能，综合运用了 Java SE 核心知识，包括面向对象编程（OOP）、集合框架、泛型、接口、枚举、异常处理和文件 I/O。

**核心实体**：Person（抽象类）→ Player / Admin、Hero（含 HeroType 枚举）、Equipment（含 EquipmentCategory 枚举）、Team、MatchRecord、BattleRecord

**架构决策**：采用三层架构（model / service / util），单向关联避免双向一致性问题，序列化实现持久化，接口多态支持可扩展的服务实现。

---

## 2. How to Run

### 环境要求
- JDK 17+
- 操作系统：Windows

### 编译运行

```bash
cd D:\develop\java_honor_of_kings
javac -d bin -sourcepath src src/com/honor/kings/HonorOfKings.java
java -cp bin com.honor.kings.HonorOfKings
```

> **注意**：Windows 系统下如果出现中文乱码或输入中文后搜索不到角色（如输入"梦泪"显示"未找到该玩家"），请在运行前先执行 `chcp 65001` 切换到 UTF-8 编码。

---

## 3. Default Login Accounts

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员（Admin） | admin | admin123 |
| 普通玩家（Player） | player1 | 123456 |

---

## 4. Implemented Features

| 选项 | 功能 | 说明 |
|------|------|------|
| 0 | 战斗模式（彩蛋） | 从 15 个英雄池中随机抽 3 个，每个英雄有 3 个专属技能，敌方 AI 自动决策（普攻或技能），胜利+100经验升级，平局+0.5胜场，记录保留最近 5 场 |
| 1 | 玩家查询 | 按姓名查找玩家，显示等级、胜率、英雄及装备 |
| 2 | 队伍概览 | 按名称或 ID 查找队伍，支持 JOIN 加入和 NEW 创建，显示成员、平均等级、比赛统计、胜率、最强玩家、最近 5 场比赛 |
| 3 | 英雄详情 | 按名称查找英雄，显示类型（WARRIOR/MAGE）、基础/总属性、装备列表、拥有者，以及按综合分排序的装备推荐 |
| 4 | 装备统计 | 按综合分排名前 5 名。**排名公式**：综合分 = 使用次数 × 0.3 + 平均胜率 × 0.7 |
| 5 | 对战历史 | 显示战斗模式最近 5 场记录，含胜负统计、英雄选用率、装备出场率及胜场数 |
| 6 | 排行榜 | 按表现分降序排列所有玩家。**排名公式**：表现分 = 胜场 × 3 - 负场 × 1 + 等级 × 2。平局按 0.5 胜场计算 |
| 7 | 数据管理 | 管理员增删玩家/英雄/装备（仅管理员可用） |
| 8 | 退出登录 | 返回登录界面，可切换账号 |
| 9 | 退出系统 | 保存数据并结束程序 |

**登录界面额外功能：**
- **注册**（选项 2）：输入用户名和密码即可注册新账号，密码至少 4 位，账号自动持久化到文件
- **登录支持**：已注册的玩家账号可直接登录（无需硬编码），管理员账号 admin 仍保留 Admin 权限

**装备推荐逻辑**：英雄详情底部根据 `HeroType` 推荐对应 `EquipmentCategory` 的装备（WARRIOR → TANK，MAGE → MAGIC），按综合分降序取前 3 件。

**玩家成长机制**：初始等级 1，经验值 0。战斗胜利获得 100 经验，升到下一级所需经验 = 当前等级 × 100。平局按 0.5 胜场计算（`winCount + 1`），输出"平局 - 可敬的对手"。

---

## 5. Java Concepts Used

| OOP 特性 | 体现位置 |
|---------|---------|
| **继承** | `Person`（抽象类）← `Player` / `Admin` |
| **封装** | 所有属性为 `private`，通过 getter/setter 或业务方法访问 |
| **聚合/组合** | `Hero` 聚合 `Equipment`；`Player` 聚合 `Hero`；`Player` 单向关联 `Team` |
| **多态** | **① 方法重写**：`Player` 和 `Admin` 重写 `Person.getRole()`，分别返回 `"Player"` 和 `"Admin"`。**② 运行时类型**：主菜单用 `instanceof Admin` 判断管理员权限。**③ 接口多态**：`HeroServiceImpl`、`TeamServiceImpl`、`MatchServiceImpl` 分别实现 `Searchable` / `Persistable` 接口 |
| **接口** | `Searchable<T>` 定义按名称搜索；`Persistable<T>` 定义通用 CRUD |
| **泛型** | `Searchable<T>`、`Persistable<T>`、`List<T>`、`Map<K,V>` |
| **集合** | `ArrayList`、`HashMap` 用于数据存储和统计 |
| **枚举** | `HeroType`（WARRIOR/MAGE）、`EquipmentCategory`（TANK/MAGIC）、`MatchStatus`（SCHEDULED/IN_PROGRESS/FINISHED/CANCELLED） |
| **组合** | `hero.Hero` 通过 `List<Skill>` 组合技能，15 个英雄各自拥有 3 个专属技能实例 |
| **异常处理** | 数字格式校验、输入验证、权限检查、文件读写异常捕获 |
| **文件 I/O** | 使用 `ObjectOutputStream`/`ObjectInputStream` 序列化保存/加载 `GameData` 到 `data/game_data.ser` |

---

## 6. AI Usage Summary

本项目使用 **DeepSeek Chat（DeepSeek-V3）** 作为 AI 辅助工具，扮演三种角色：

| 角色 | 职责 |
|------|------|
| **Architect Agent** | 审查类设计、关联方向、UML，提出单向关联改进建议 |
| **Implementation Agent** | 生成实体类、Service 层、DataInitializer、主菜单等代码 |
| **Testing/Reviewer Agent** | 审查代码编译错误，生成测试用例，验证功能正确性 |

AI 协作流程：设计审查 → 代码生成 → 手动编译验证 → 反馈修正。AI 生成的代码骨架约占项目代码量的 60%，手动调整的部分包括包路径修正、数据 Bug 修复（胜率超 100%）、菜单功能逻辑实现。

详细记录见 `ai/prompts.md` 和 `ai/reflection.md`。

---

## 7. Testing Summary

共计 **13 个测试用例**，覆盖全部 8 个功能模块，所有用例已通过。

| 测试 ID | 功能 | 用例数 | 状态 |
|---------|------|--------|------|
| TC-01 ~ TC-02 | 玩家查询（存在/不存在） | 2 | ☑ 通过 |
| TC-03 | 队伍概览 | 1 | ☑ 通过 |
| TC-04 | 英雄详情（含装备推荐） | 1 | ☑ 通过 |
| TC-05 | 装备统计（综合分排名） | 1 | ☑ 通过 |
| TC-06 | 对战历史（装备出场统计） | 1 | ☑ 通过 |
| TC-07 | 排行榜（表现分公式） | 1 | ☑ 通过 |
| TC-08 ~ TC-09 | 登录（正确/错误密码） | 2 | ☑ 通过 |
| TC-10 | 数据管理 | 1 | ☑ 通过 |
| TC-11 | 注册新玩家 | 1 | ☑ 通过 |
| TC-12 | 战斗模式 | 1 | ☑ 通过 |
| TC-13 | 已注册玩家密码错误 | 1 | ☑ 通过 |

详细测试用例见 `docs/test-cases.md`。

---

## 8. Known Limitations

- **持久化依赖手动保存**：数据通过序列化保存到 `data/game_data.ser`，但仅在程序正常退出（选项 9）时保存，异常关闭可能导致数据丢失。
- **战斗装备记录为空**：战斗模式记录中的装备列表为空（战斗英雄与模型实体英雄独立），装备出场统计仅对战斗模式有效。
- **管理员账号固定**：admin 账号为硬编码 Admin 类型，不支持通过注册创建管理员。
- **中文编码**：在 Windows cmd 默认编码（GBK）下中文可能显示乱码或输入中文搜索不到角色，需执行 `chcp 65001` 切换 UTF-8。
