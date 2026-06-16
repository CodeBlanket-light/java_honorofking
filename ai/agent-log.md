# Agent Log

本项目在开发过程中使用了 DeepSeek Chat（模型：DeepSeek-V3）作为 AI 辅助工具，并扮演了三种不同的代理角色：Architect Agent（架构师）、Implementation Agent（实现代理）、Testing/Reviewer Agent（测试/审查代理）。以下是每种角色的主要贡献和相应的人工决策记录。

---

## Architect Agent（架构师代理）

### 贡献 1：初始类结构设计审查
- 时间：2026-06-16
- AI 工具：DeepSeek Chat
- 主要建议：建议了 Person（抽象）、Player、Admin、Hero、Equipment、Team、MatchRecord 的类结构，并指出 Team 与 Player 之间的双向关联会导致一致性问题。
- 具体方案：
  - 方案 A（推荐）：改为单向关联，Team 不持有 Player 列表，仅在 Player 中持有 Team 引用；查询队伍成员通过 PlayerService 按 teamId 过滤。
  - 方案 B：保留双向但用 @JsonIgnore 标记一端（不采用，因为本项目未使用 JSON 序列化）。
- 人工决策：采纳方案 A，移除 Team 中的 `List<Player> members`，在 Player 中保留 `Team currentTeam`。
- 相关提交：
  - `275d808` [AI-Implementation] add Equipment, Hero, Team entity classes（Team 按单向关联设计）
  - `d4c6c44` [Fix] correct import paths for Player and Admin

---

### 贡献 2：UML 草稿与设计文档完善
- 时间：2026-06-17
- AI 工具：DeepSeek Chat
- 主要建议：根据最终类结构，生成了文本形式的 UML 类图，并补充了类之间的关系描述（继承、聚合、关联）。
- 人工决策：接受 UML 描述，并将其整合到 `plan.md` 和 `README.md` 中。
- 相关提交：
  - `851e0a6` [AI-Architect] 完成类结构设计与UML草稿文档

---

## Implementation Agent（实现代理）

### 贡献 1：实体类生成
- 时间：2026-06-16 ~ 2026-06-17
- AI 工具：DeepSeek Chat
- 主要建议：生成了 `Equipment`（含 `EquipmentType` 枚举）、`Hero`（含 `equip/unequip` 和总属性计算）、`Team`（单向关联）、`MatchRecord`（含 `MatchStatus` 枚举）等实体类的完整代码。
- 人工决策：全部接受，但修改了部分方法签名（如 `unequip` 改为通过 ID 移除）和格式（`getDescription` 输出格式）。
- 相关提交：
  - `275d808` [AI-Implementation] add Equipment, Hero, Team
  - `f347889` [AI-Implementation] add MatchRecord
  - `5c1dac5` [AI-Implementation] 实现Person、Player、Admin实体类

### 贡献 2：Service 层实现
- 时间：2026-06-17
- AI 工具：DeepSeek Chat
- 主要建议：生成了 `Searchable` 和 `Persistable` 接口，以及 `HeroServiceImpl`、`TeamServiceImpl`、`MatchServiceImpl` 的实现代码，使用 `HashMap` 作为存储。
- 人工决策：接受代码，但调整了 `getTeamMembers` 方法的实现（暂时返回空列表并添加 TODO）。
- 相关提交：
  - `ebfe683` [AI-Implementation] add Searchable and Persistable interfaces
  - `a1930ff` [AI-Implementation] implement HeroServiceImpl
  - `6f30a20` [AI-Implementation] implement TeamServiceImpl
  - `74ac2d3` [AI-Implementation] implement MatchServiceImpl

---

## Testing/Reviewer Agent（测试/审查代理）

### 贡献 1：发现 import 路径错误
- 时间：2026-06-17
- AI 工具：DeepSeek Chat
- 主要发现：`Player.java` 和 `Admin.java` 中引用了 `model.entity.Hero` 和 `model.entity.Team`，但当时 `model.entity` 包尚未创建，导致编译失败。
- 人工决策：手动修正 import 语句，确保指向正确的包路径。
- 相关提交：
  - `d4c6c44` [Fix] correct import paths for Player and Admin

### 贡献 2：建议增加测试用例
- 时间：2026-06-17
- AI 工具：DeepSeek Chat
- 主要建议：针对玩家查询、队伍概览、英雄详情等核心功能，提供了一组手动测试用例模板（包含输入、预期输出、实际输出）。
- 人工决策：采纳建议，创建了 `docs/test-cases.md` 并填写了部分测试用例。
- 相关提交：
  - （待提交，计划使用 `[AI-Review] add test cases`）

---

## 其他备注
- 所有 AI 生成的代码均经过人工审查和编译验证，确保符合课程要求和 Java 规范。
- 部分代码（如主菜单的硬编码数据）由人工编写，未依赖 AI