# 王者荣耀信息管理系统 — Honor of Kings Management System

---

## 1. Project Overview

王者荣耀信息管理系统是一个基于 Java 的控制台应用程序，用于管理玩家、英雄、装备、队伍和比赛记录。系统提供查询、排名、数据管理等功能，综合运用了 Java SE 核心知识，包括面向对象编程（OOP）、集合框架、泛型、接口、枚举和异常处理。

**核心实体**：Person（抽象类）→ Player / Admin、Hero、Equipment、Team、MatchRecord

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
| 1 | 玩家查询 | 按姓名查找玩家，显示等级、胜率、英雄及装备 |
| 2 | 队伍概览 | 按名称或 ID 查找队伍，显示成员、平均等级、比赛统计、胜率、最强玩家 |
| 3 | 英雄详情 | 按名称查找英雄，显示基础/总属性、装备列表、拥有该英雄的玩家 |
| 4 | 装备统计 | 统计每件装备被英雄使用的次数，按降序显示前 5 名。**排名公式**：遍历所有英雄的 `equipmentList`，统计每件装备的出现次数 → 按次数降序排列 → 次数相同按装备名称排序 → 取前 5 名 |
| 5 | 对战历史 | 按玩家姓名查询最近 5 场比赛，显示对手、日期、比分、结果 |
| 6 | 排行榜 | 按胜率降序排列，显示前 3 名玩家 |
| 7 | 数据管理 | 管理员增删玩家/英雄/装备（仅管理员可用） |
| 8 | 退出登录 | 返回登录界面，可切换账号 |
| 9 | 退出系统 | 结束程序 |

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
| **枚举** | `EquipmentType`（WEAPON/ARMOR/BOOTS/ACCESSORY）、`MatchStatus`（SCHEDULED/IN_PROGRESS/FINISHED/CANCELLED） |
| **异常处理** | 数字格式校验、输入验证、权限检查 |

---

## 6. AI Usage Summary

本项目使用 **DeepSeek Chat（DeepSeek-V3）** 作为 AI 辅助工具，扮演三种角色：

| 角色 | 职责 |
|------|------|
| **Architect Agent** | 审查类设计、关联方向、UML，提出单向关联改进建议 |
| **Implementation Agent** | 生成实体类、Service 层、DataInitializer、主菜单等代码 |
| **Testing/Reviewer Agent** | 审查代码编译错误，生成 10 个测试用例 |

AI 协作流程：设计审查 → 代码生成 → 手动编译验证 → 反馈修正。AI 生成的代码骨架约占项目代码量的 60%，手动调整的部分包括包路径修正、数据 Bug 修复（胜率超 100%）、菜单功能逻辑实现。

详细记录见 `ai/prompts.md` 和 `ai/reflection.md`。

---

## 7. Testing Summary

共计 **10 个测试用例**，覆盖全部 8 个功能模块，所有用例已通过。

| 测试 ID | 功能 | 用例数 | 状态 |
|---------|------|--------|------|
| TC-01 ~ TC-02 | 玩家查询（存在/不存在） | 2 | ☑ 通过 |
| TC-03 | 队伍概览 | 1 | ☑ 通过 |
| TC-04 | 英雄详情 | 1 | ☑ 通过 |
| TC-05 | 装备统计 | 1 | ☑ 通过 |
| TC-06 | 对战历史 | 1 | ☑ 通过 |
| TC-07 | 排行榜 | 1 | ☑ 通过 |
| TC-08 ~ TC-09 | 登录（正确/错误密码） | 2 | ☑ 通过 |
| TC-10 | 数据管理 | 1 | ☑ 通过 |

详细测试用例见 `docs/test-cases.md`。

---

## 8. Known Limitations

- **内存存储**：所有数据存储在内存（List/Map）中，程序关闭后数据丢失，不支持持久化。
- **对战历史维度有限**：仅支持按玩家姓名查询，不支持按队伍或时间段查询。
- **固定账号**：登录账号硬编码（admin/admin123、player1/123456），不支持注册新用户。
- **中文编码**：在 Windows cmd 默认编码（GBK）下中文可能显示乱码或输入中文搜索不到角色，需执行 `chcp 65001` 切换 UTF-8。
