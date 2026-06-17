# Agent Log

This project used DeepSeek Chat (model: DeepSeek-V3) as an AI assistant throughout development, playing three different agent roles: Architect Agent, Implementation Agent, and Testing/Reviewer Agent. Below are the main contributions of each role and corresponding human decisions.

---

## Architect Agent

### Contribution 1: Initial Class Structure Design Review
- Date: 2026-06-16
- AI Tool: DeepSeek Chat
- Main Suggestion: Reviewed the class structure of Person (abstract), Player, Admin, Hero, Equipment, Team, MatchRecord, and pointed out that the bidirectional association between Team and Player would cause consistency issues.
- Specific Solutions:
  - Option A (recommended): Change to unidirectional association. Team does not hold a Player list; Player holds a Team reference only. Query team members via PlayerService filtered by teamId.
  - Option B: Keep bidirectional but annotate one end with @JsonIgnore (not adopted, as this project does not use JSON serialization).
- Human Decision: Adopted Option A. Removed `List<Player> members` from Team, kept `Team currentTeam` in Player.
- Related Commits:
  - `275d808` [AI-Implementation] add Equipment, Hero, Team entity classes (Team designed with unidirectional association)
  - `d4c6c44` [Fix] correct import paths for Player and Admin

---

### Contribution 2: UML Draft and Design Documentation
- Date: 2026-06-17
- AI Tool: DeepSeek Chat
- Main Suggestion: Generated text-form UML class diagram based on the final class structure, supplemented relationship descriptions between classes (inheritance, aggregation, association).
- Human Decision: Accepted the UML description and integrated it into `plan.md` and `README.md`.
- Related Commits:
  - `851e0a6` [AI-Architect] complete class structure design and UML draft document

---

### Contribution 3: Architecture Description and Association Direction Advice
- Date: 2026-06-18
- AI Tool: DeepSeek Chat
- Main Suggestion: Analyzed the logical relationship between teams and battle history. Confirmed that the unidirectional association design (Player holds Team, Team does not hold Players) avoids circular references and simplifies battle history queries.
- Human Decision: Based on the discussion, moved team match records to team overview, changed battle history to only display personal battle mode records.
- Related Commits:
  - `4f82ffd` [AI-Review] persist battle records to file, add team creation in team overview

---

## Implementation Agent

### Contribution 1: Entity Class Generation
- Date: 2026-06-16 ~ 2026-06-17
- AI Tool: DeepSeek Chat
- Main Suggestion: Generated complete code for `Equipment` (with `EquipmentType` enum), `Hero` (with equip/unequip and total attribute calculation), `Team` (unidirectional association), `MatchRecord` (with `MatchStatus` enum) entity classes.
- Human Decision: All accepted. Modified some method signatures (e.g., unequip changed to remove by ID) and format (getDescription output format).
- Related Commits:
  - `275d808` [AI-Implementation] add Equipment, Hero, Team
  - `f347889` [AI-Implementation] add MatchRecord
  - `5c1dac5` [AI-Implementation] implement Person, Player, Admin entity classes

### Contribution 2: Service Layer Implementation
- Date: 2026-06-17
- AI Tool: DeepSeek Chat
- Main Suggestion: Generated `Searchable` and `Persistable` interfaces, and implementation code for `HeroServiceImpl`, `TeamServiceImpl`, `MatchServiceImpl` using `HashMap` for storage.
- Human Decision: Accepted code. Adjusted getTeamMembers method implementation (temporarily returns empty list with TODO comment).
- Related Commits:
  - `ebfe683` [AI-Implementation] add Searchable and Persistable interfaces
  - `a1930ff` [AI-Implementation] implement HeroServiceImpl
  - `6f30a20` [AI-Implementation] implement TeamServiceImpl
  - `74ac2d3` [AI-Implementation] implement MatchServiceImpl

### Contribution 3: Main Menu and Data Initialization
- Date: 2026-06-17
- AI Tool: DeepSeek Chat
- Main Suggestion: Generated the complete main menu framework (9 options), login logic, and DataInitializer with hardcoded datasets (20 equipment, 15 heroes, 3 teams, 10 players, 10 matches).
- Human Decision: Accepted code. Gradually replaced hardcoded data with archive loading and expanded all menu functions.
- Related Commits:
  - `1607b04` [Human] add DataInitializer with hardcoded dataset
  - `b73595f` [AI-Implementation] add battle mode, user registration

### Contribution 4: Serialization Persistence
- Date: 2026-06-17
- AI Tool: DeepSeek Chat
- Main Suggestion: Created FileStorageUtil and GameData classes using ObjectOutputStream/ObjectInputStream for data serialization persistence. Modified data load/save flow.
- Human Decision: Accepted code. Added Serializable interface to all entity classes.
- Related Commits:
  - `fdbe4b5` [Human] add Serializable, password auth, registration, battle mode, file I/O

### Contribution 5: Ranking Formulas and Equipment Recommendation
- Date: 2026-06-18
- AI Tool: DeepSeek Chat
- Main Suggestion: Implemented new ranking formulas (equipment composite score and player performance score), and equipment recommendation logic based on hero type matching.
- Human Decision: Accepted code. Manually adjusted formulas multiple times to meet course requirements.
- Related Commits:
  - `5f52fe6` [Human] test ranking dynamic and equipment recommendation

### Contribution 6: Battle System Refactoring
- Date: 2026-06-18
- AI Tool: DeepSeek Chat
- Main Suggestion: Expanded battle hero pool from 3 to 15 heroes with unique skills. Refactored battle engine with player turn and AI decision logic.
- Human Decision: Accepted code. Fixed display-layer bug where only 3 fixed heroes were actually used despite 15 classes being created.
- Related Commits:
  - `43aa2a8` [AI-Implementation] expand hero pool to 15 with skills, add AI decision logic

---

## Testing/Reviewer Agent

### Contribution 1: Found Import Path Errors
- Date: 2026-06-17
- AI Tool: DeepSeek Chat
- Main Finding: `Player.java` and `Admin.java` referenced `model.entity.Hero` and `model.entity.Team`, but the `model.entity` package hadn't been created yet, causing compilation failures.
- Human Decision: Manually fixed import statements to point to correct package paths.
- Related Commits:
  - `d4c6c44` [Fix] correct import paths for Player and Admin

### Contribution 2: Suggested Adding Test Cases
- Date: 2026-06-17
- AI Tool: DeepSeek Chat
- Main Suggestion: Provided a set of manual test case templates (including input, expected output, actual output) for core features like player query, team overview, hero details.
- Human Decision: Adopted the suggestion. Created `docs/test-cases.md` and filled in test results.
- Related Commits:
  - `bd0456c` [AI-Review] add 3 new test cases: registration, battle mode, password error

### Contribution 3: Login Input Validation
- Date: 2026-06-18
- AI Tool: DeepSeek Chat
- Main Finding: Login screen offered options 1 and 2, but entering other values caused unexpected behavior without feedback.
- Human Decision: Added input validation on the login screen. Inputs other than 1 or 2 prompt "Invalid selection, please enter 1 or 2." and return to the login screen.
- Related Commits:
  - `86dd403` [AI-Review] add input validation for login menu selection

### Contribution 4: Comprehensive Feature Testing
- Date: 2026-06-18
- AI Tool: DeepSeek Chat
- Main Finding: Ran full-feature tests. Found ranking formula didn't match course requirements and team display was missing from player queries.
- Human Decision: Adjusted ranking formula to use performance score, added team display to player queries, added JOIN command for joining teams.
- Related Commits:
  - `e584e71` [Human] add team join feature, display player team in query, verify all features

---

## Other Notes
- All AI-generated code was manually reviewed and compilation-verified to ensure compliance with course requirements and Java standards.
- Some code (e.g., main menu hardcoded data) was manually written without AI assistance.
- All AI-generated code requiring modifications reflects human review and quality control decisions.
