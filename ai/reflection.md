# Reflection

## 1. Which AI tools or models did you use?
I used DeepSeek Chat (model: DeepSeek-V3) throughout the project, playing three roles: Architect, Implementer, and Testing/Reviewer. No other AI tools were used.

## 2. Which prompt was the most useful? Why?
The most useful prompt was Prompt 01 (Architect Agent design review). It pointed out the bidirectional association consistency problem between Team and Player before coding began, saving me from large-scale refactoring later. After adopting the unidirectional association approach (Team does not hold a Player list, queried via Player.currentTeam), deleting a team no longer requires synchronizing Player updates, greatly reducing maintenance costs. This decision was the most critical design choice throughout the entire project.

## 3. Which AI-generated suggestion was wrong, incomplete, or misleading?
When refactoring the battle system, AI suggested creating 12 new hero classes (GuanYu, ZhuGeLiang, etc.), each with 3 unique skills. The suggestion itself was good, but the AI-generated skill data didn't match the heroes' lore well enough (e.g., ZhouYu's skill names didn't fit his historical image), so I manually adjusted some skill names and damage values. Additionally, when generating the ranking feature, AI initially used a "strength score" formula (win rate×50+level×10-total matches×0.1), but the course requirement was sorting by win rate descending. I later changed it to use Comparator chaining, and further adjusted to a "performance score" formula (wins×3-losses×1+level×2). Also, when adding the registration feature, the AI-generated code accidentally deleted the `new Player(...)` variable declaration line while editing comments, causing a compilation error. I restored the file with git checkout and re-edited to fix it.

## 4. How did you check whether AI-generated code was correct?
I verified using the following methods:
- After each code generation, compiled with `javac` to ensure no syntax errors.
- Manually ran the program and tested every menu function (login, query, battle, ranking, etc.) to verify input/output matched expectations.
- Checked import paths against the actual package structure, the most common AI error source.
- For ranking formulas and equipment recommendation logic, manually calculated with known data to verify correctness.
- Finally performed a full regression test to ensure new features didn't break existing ones.

## 5. What bugs did you fix yourself instead of asking AI to fix?
- **Win rate calculation bug**: In DataInitializer, players' winCount could exceed totalMatches (e.g., winCount=131, totalMatches=49), causing win rates over 100%. I manually fixed the initialization logic to ensure winCount ≤ totalMatches.
- **Encoding issue**: Under Windows cmd, Chinese characters displayed as garbled text, and searching for "梦泪" returned "Player not found". I fixed this by adding `chcp 65001` to switch to UTF-8 encoding, and documented it in the README.
- **Hero data index misalignment**: The faction field and numeric field indices in the hero data array didn't match, causing Integer.parseInt to fail on Chinese strings. I manually fixed the array indices.
- **Ranking formula adjustments**: AI initially implemented a "strength score" formula (win rate×50+level×10-total matches×0.1). I changed it to sort by win rate descending, then later adjusted to a "performance score" formula (wins×3-losses×1+level×2).
- **Battle record type migration**: When changing from String list to BattleRecord objects, GameData and FileStorageUtil types weren't synchronized. I manually fixed all type declarations.

## 6. What Java concept did you understand better after using AI?
With AI assistance, I gained the deepest understanding of **File I/O (serialization)**. AI generated the FileStorageUtil and GameData classes, demonstrating how to use ObjectOutputStream/ObjectInputStream to persist structured data to files. I learned:
- All entity classes must implement Serializable and add serialVersionUID.
- Use try-with-resources to auto-close streams and prevent resource leaks.
- Encapsulate multiple lists into a single GameData object for serialization, simplifying I/O operations.
- The design pattern of prioritizing save file loading at startup, falling back to hardcoded data on failure.

## 7. What Java concept are you still unsure about?
I'm still uncertain about the practical application scenarios of **generic interfaces** (like `Searchable<T>`, `Persistable<T>`). Although AI helped generate the interfaces and implementation classes, the main menu ultimately uses DataInitializer's static methods directly instead of calling through the Service interfaces. I understand that generics can improve code reusability, but I'm not yet confident in how to elegantly organize Service layer calls in a console application.

## 8. Did AI make the project easier, harder, or both? Explain.
Both.
**Easier**: AI quickly generated大量 boilerplate code (constructors, getters/setters, entity skeletons, 15 hero classes with skills), saving about 60% of coding time. The Architect Agent identified the bidirectional association problem during the design phase, avoiding large-scale refactoring later. The Testing Agent helped generate test cases and verify functionality.
**Harder**: AI-generated code often had mismatched package paths, requiring manual import fixes each time. AI-suggested features (like getTeamMembers via PlayerService) were incomplete and needed manual supplementation. Battle hero skill data needed manual adjustments to match character lore. Overall, AI significantly sped up coding but also increased code review workload.

## 9. Which parts of the final project were mainly written by you?
- **Bug fixes**: Win rate over 100% correction, hero data index misalignment fix, encoding issue troubleshooting.
- **Menu function logic**: Player query, team overview (including JOIN and NEW), hero details (including equipment recommendation), equipment statistics (including composite score ranking), battle history (including equipment appearance stats), ranking (including multi-level sorting) — all 9 menu items' logic implementation.
- **Battle system integration**: Integrated the 15 AI-generated battle heroes into startBattleMode(), implementing random selection and AI decision logic.
- **Data persistence**: Refactored battle records from String list to BattleRecord objects, integrated into the serialization system.
- **Documentation**: README.md, plan.md, test-cases.md content organization and continuous updates.
- **Git management**: All commit message writing, branch management, and pushing.

## 10. Which parts were mainly generated or heavily assisted by AI?
- **Entity class skeletons**: Initial versions of `Person`, `Player`, `Admin`, `Hero`, `Equipment`, `Team`, `MatchRecord` (including attributes, constructors, getters/setters).
- **Service layer**: `Searchable`, `Persistable` interfaces and `HeroServiceImpl`, `TeamServiceImpl`, `MatchServiceImpl` implementations.
- **Data initialization**: Hardcoded dataset in `DataInitializer` (20 equipment, 15 heroes, 3 teams, 10 players, 10 matches).
- **Battle hero classes**: 12 new hero classes (GuanYu, ZhuGeLiang, etc.) with 3 skills each. However, it was later discovered that although 15 hero classes were created, the actual battle interface still only used the original 3 fixed hero objects (ZhaoYun, LiBai, DiaoChan), just displaying random names — a display-layer bug. I later created the `buildHeroPool()` method to randomly select 3 real hero instances from the 15-hero pool for battle, establishing a complete matching mechanism.
- **Tie handling logic**: Since turn-based games theoretically shouldn't have ties, I set a 30-round limit. Exceeding 30 rounds forces a tie判定, counted as 0.5 wins (winCount + 1), outputting "Tie - Honorable Opponent".
- **Serialization tools**: `FileStorageUtil` and `GameData` framework code.
- **UML class diagram**: Mermaid-format UML diagram generation.
- **Main menu framework**: Initial menu framework and login logic in `HonorOfKings.java` (later manually extended with all feature implementations).
