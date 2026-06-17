# Reflection

## 1. Which AI tools or models did you use?
我主要使用了 DeepSeek Chat（模型：DeepSeek-V3）。在某些设计审查环节也参考了 ChatGPT（GPT-4o）的建议进行对比。

## 2. Which prompt was the most useful? Why?
最有用的提示词是 Prompt 01（Architect Agent 设计审查）。因为它在我写代码之前就指出了双向关联的一致性问题，让我避免了后期大规模重构。采纳方案 A（单向关联）后，Team 和 Player 的关系变得清晰，维护成本大大降低。

## 3. Which AI-generated suggestion was wrong, incomplete, or misleading?
AI 在生成 `TeamServiceImpl` 时，`getTeamMembers` 方法直接返回了空列表并建议我通过 PlayerService 实现。这个建议本身不错，但 AI 没有告诉我 PlayerService 还不存在，也没有生成 PlayerService 的代码。我不得不手动创建占位逻辑并添加 TODO 注释。这不算严重错误，但确实不够完整。

## 4. How did you check whether AI-generated code was correct?
- 每生成一个类，我都在 IDE（VS Code）中手动编译检查，确保没有语法错误。
- 检查 import 路径是否匹配项目包结构。
对比课程要求（如 `Person` 必须是抽象类，`Player` 和 `Admin` 必须继承它），确认 AI 生成的类满手动运行主菜单，验证登录和查询功能是否正常工作。

## 5. What bugs did you fix yourself instead of asking AI to fix?
我修正了 `Player.java` 和 `Admin.java` 的 import 路径错误。AI 建议我修正，但我没有让 AI 重写文件，而是自己在 IDE 里手动改了。另外，`MatchRecord` 的 `determineWinner()` 方法在平局时 AI 未明确处理 winner 为 null，我手动补充了该逻辑。

## 6. What Java concept did you understand better after using AI?
通过 AI 生成代码和解释，我对 **聚合 vs 组合** 以及 **单向 vs 双向关联** 的理解更清晰了。之前没有仔细考虑过关联方向对代码维护性的影响，AI 的 Architect 提示让我真正实践了“高内聚低耦合”的设计原则。

## 7. What Java concept are you still unsure about?
我对泛型接口（如 `Searchable<T>`、`Persistable<T>`）和 **集合框架**（`Map`、`List` 的恰当使用）还有一些不确定。虽然 AI 帮我生成了代码，但在处理 `searchByName` 时，如何高效地模糊匹配多个字段，我还没有很好的把握。另外，`LocalDateTime` 的格式化和比较也让我有点困惑。

## 8. Did AI make the project easier, harder, or both? Explain.
两者都有。
更容易的一面：AI 快速生成了大量样板代码（构造器、getter/setter、基础 CRUD），节省了约 40% 的键盘敲击时间。设计审查功能也帮我避免了早期决策失误。
更困难的一面：AI 生成的代码不完全符合我的项目包结构，导致编译错误和调试时间。我需要花精力检查 AI 的每一行输出，确保它引用的类确实存在。这增加了认知负担，但确实加深了我对代码的理解。

## 9. Which parts of the final project were mainly written by you?
- `Player.java` 和 `Admin.java` 的 import 修正及业务方法（如 `joinTeam`、`leaveTeam`）的细节调整。
`HonorOfKings.java` 主菜单的硬编码示例数据（手动填充的玩家和英雄信息）以及菜单流程控制逻辑。
所有 `git commit` 信息的撰写、`plan.md` 的部分章节、`agent-log.md` 和 `reflection.md` 的内容整理。
将 AI 生成的多个类组合到一起，并修改包路径冲突。

## 10. Which parts were mainly generated or heavily assisted by AI?
全部实体类（`Person`、`Player`、`Admin`、`Hero`、`Equipment`、`Team`、`MatchRecord`）的初版代码骨架。
所有 Service 层接口和实现类（`Searchable`、`Persistable`、`HeroServiceImpl`、`TeamServiceImpl`、`MatchServiceImpl`）。
 `DataInitializer` 中的大量硬编码数据集生成。
`HonorOfKings.java` 初始菜单框架和登录逻辑。
UML 类图的文本描述和 `plan.md` 中的设计建议。