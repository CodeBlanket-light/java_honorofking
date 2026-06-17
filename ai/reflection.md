# Reflection

## 1. Which AI tools or models did you use?
我全程使用了 DeepSeek Chat（模型：DeepSeek-V3），作为架构师、实现者和测试/审查员三种角色完成项目。没有使用其他 AI 工具。

## 2. Which prompt was the most useful? Why?
最有用的提示词是 Prompt 01（Architect Agent 设计审查）。它在编码开始前就指出了 Team 和 Player 之间双向关联的一致性问题，让我避免了后期大规模重构。采纳单向关联方案（Team 不持有 Player 列表，通过 Player.currentTeam 查询）后，删除队伍不需要同步更新 Player，维护成本大大降低。这个决策贯穿了整个项目，是设计上最关键的正确决定。

## 3. Which AI-generated suggestion was wrong, incomplete, or misleading?
在战斗系统重构时，AI 建议创建 12 个新英雄类（关羽、诸葛亮等），每个英雄 3 个专属技能。这个建议本身很好，但 AI 生成的技能数据与英雄人设匹配度不够高（如周瑜的技能名称不符合历史形象），我手动修改了部分技能名称和伤害值。此外，AI 在生成排行榜功能时最初使用的是"实力分"公式（胜率×50+等级×10-总场次×0.1），但课程要求的是按胜率降序排列，我后来改为使用 Comparator 链式排序，又调整为表现分公式（胜场×3-负场×1+等级×2）。另外，在添加注册功能时，AI 生成的代码在编辑注释时误删了 `new Player(...)` 的变量声明行，导致编译报错，我通过 git checkout 恢复文件后重新编辑才修复。

## 4. How did you check whether AI-generated code was correct?
我主要采用以下方式验证：
- 每次生成代码后，使用 `javac` 编译检查，确保无语法错误。
- 手动运行程序，测试每个菜单功能（登录、查询、战斗、排行榜等），验证输入输出是否符合预期。
- 检查 import 路径是否与实际包结构一致，这是 AI 最常见的错误来源。
- 对排名公式和装备推荐逻辑，输入已知数据手动计算验证结果是否正确。
- 最终进行全功能回归测试，确保新增功能不破坏已有功能。

## 5. What bugs did you fix yourself instead of asking AI to fix?
- **胜率计算 Bug**：DataInitializer 中玩家的 winCount 可能超过 totalMatches（如 winCount=131, totalMatches=49），导致胜率超过 100%。我手动修正了初始化逻辑，确保 winCount ≤ totalMatches。
- **编码问题**：在 Windows cmd 下中文显示乱码，搜索"梦泪"返回"未找到该玩家"。我通过添加 `chcp 65001` 切换 UTF-8 编码解决，并在 README 中说明。
- **英雄数据索引错位**：DataInitializer 中英雄数据数组的阵营字段和数值字段索引不对应，导致 Integer.parseInt 解析中文字符串失败。我手动修复了数组索引。
- **排行榜公式多次调整**：AI 最初实现了"实力分"公式（胜率×50+等级×10-总场次×0.1），我改为按胜率降序排序，后又调整为由己的"表现分"公式（胜场×3-负场×1+等级×2）。
- **战斗记录类型变更**：从 String 列表改为 BattleRecord 对象时，GameData 和 FileStorageUtil 的类型未同步更新，我手动修正了所有引用的类型声明。

## 6. What Java concept did you understand better after using AI?
通过 AI 辅助，我对 **文件 I/O（序列化）** 的理解最深。AI 生成了 FileStorageUtil 和 GameData 类，展示了如何使用 ObjectOutputStream/ObjectInputStream 将结构化数据持久化到文件。我学会了：
- 所有实体类必须实现 Serializable 接口并添加 serialVersionUID。
- 使用 try-with-resources 自动关闭流，避免资源泄漏。
- 将多个列表封装到一个 GameData 对象中序列化，简化 I/O 操作。
- 在程序启动时优先加载存档，失败时回退到硬编码数据的设计模式。

## 7. What Java concept are you still unsure about?
我对 **泛型接口**（如 `Searchable<T>`、`Persistable<T>`）的实际应用场景还有一些不确定。虽然 AI 帮我生成了接口和实现类，但在主菜单中最终直接使用了 DataInitializer 的静态方法，没有通过接口调用的方式使用 Service 层。我知道泛型可以提升代码的复用性，但在控制台应用中如何优雅地组织 Service 层调用，我还没有很好的把握。

## 8. Did AI make the project easier, harder, or both? Explain.
两者都有。
**更容易的一面**：AI 快速生成了大量样板代码（构造器、getter/setter、实体类骨架、15 个英雄类及技能），节省了约 60% 的编码时间。Architect Agent 在设计阶段发现双向关联问题，避免了后期大规模重构。Testing Agent 帮助生成测试用例并验证功能正确性。
**更困难的一面**：AI 生成的代码经常包路径不匹配，每次都需要手动修正 import。AI 建议的功能（如 getTeamMembers 通过 PlayerService 实现）不够完整，需要人工补充。战斗英雄的技能数据需要手动调整才符合人设。总体而言，AI 让编码速度大幅提升，但也增加了代码审查的工作量。

## 9. Which parts of the final project were mainly written by you?
- **Bug 修复**：胜率超 100% 的修正、英雄数据索引错位修复、编码问题排查。
- **菜单功能逻辑**：玩家查询、队伍概览（含 JOIN 加入和 NEW 创建）、英雄详情（含装备推荐）、装备统计（含综合分排名）、对战历史（含装备出场统计）、排行榜（含多级排序）等全部 9 个菜单项的逻辑实现。
- **战斗系统整合**：将 AI 生成的 15 个战斗英雄整合到 startBattleMode() 中，实现随机抽取和 AI 决策逻辑。
- **数据持久化**：将战斗记录从 String 列表重构为 BattleRecord 对象，并整合到序列化体系中。
- **文档**：README.md、plan.md、test-cases.md 的内容整理和持续更新。
- **Git 管理**：所有 commit 信息的撰写、分支管理和推送。

## 10. Which parts were mainly generated or heavily assisted by AI?
- **实体类初版骨架**：`Person`、`Player`、`Admin`、`Hero`、`Equipment`、`Team`、`MatchRecord` 的初版代码（含属性、构造器、getter/setter）。
- **Service 层**：`Searchable`、`Persistable` 接口及 `HeroServiceImpl`、`TeamServiceImpl`、`MatchServiceImpl` 的实现。
- **数据初始化**：`DataInitializer` 中的 20 件装备、15 个英雄、3 个队伍、10 个玩家的硬编码数据集。
- **战斗英雄类**：12 个新英雄类（关羽、诸葛亮等）的创建及 3 个技能的初始数据。但后来发现尽管创建了 15 个英雄类，实际战斗界面仍然只使用了原来固定的 3 个英雄对象（赵云、李白、貂蝉），只是随机显示不同的名字，属于显示层漏洞。之后我创建了 `buildHeroPool()` 方法，从 15 个英雄池中随机抽取 3 个真正的英雄实例进行对战，建立了完整的匹配机制。
- **序列化工具**：`FileStorageUtil`、`GameData` 的框架代码。
- **UML 类图**：Mermaid 格式的 UML 图生成。
- **主菜单框架**：`HonorOfKings.java` 的初始菜单框架和登录逻辑（后续人工扩展了所有功能实现）。
