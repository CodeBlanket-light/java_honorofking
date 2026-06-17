# Honor of Kings Management System

---

## 1. Project Overview

Honor of Kings Information Management System is a Java-based console application for managing players, heroes, equipment, teams, and match records. The system provides search, ranking, data management, equipment recommendation, and battle mode features, comprehensively utilizing Java SE core knowledge including OOP, collections framework, generics, interfaces, enums, exception handling, and file I/O.

**Core Entities**: Person (abstract) → Player / Admin, Hero (with HeroType enum), Equipment (with EquipmentCategory enum), Team, MatchRecord, BattleRecord

**Architecture Decision**: Three-layer architecture (model / service / util), unidirectional association to avoid bidirectional consistency issues, serialization for persistence, interface polymorphism for extensible service implementations.

---

## 2. How to Run

### Requirements
- JDK 17+
- OS: Windows

### Build and Run

```bash
cd D:\develop\java_honor_of_kings
javac -d bin -sourcepath src src/com/honor/kings/HonorOfKings.java
java -cp bin com.honor.kings.HonorOfKings
```

> **Note**: If Chinese characters appear garbled or searching fails under Windows cmd, run `chcp 65001` before starting to switch to UTF-8 encoding.

---

## 3. Default Login Accounts

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | admin123 |
| Player | player1 | 123456 |

---

## 4. Implemented Features

| Option | Feature | Description |
|--------|---------|-------------|
| 0 | Battle Mode (Easter Egg) | Randomly pick 3 from 15 heroes, each with 3 unique skills. Enemy AI auto-decides (normal attack or skill). Win = +100 exp, Tie = +0.5 wins. Records keep last 5. |
| 1 | Player Query | Search player by name, display level, win rate, owned heroes and equipment |
| 2 | Team Overview | Search team by name/ID, support JOIN to join and NEW to create. Display members, avg level, match stats, win rate, best player, recent 5 matches |
| 3 | Hero Details | Search hero by name, display type (WARRIOR/MAGE), base/total stats, equipment, owners, and equipment recommendations by composite score |
| 4 | Equipment Stats | Top 5 by composite score. **Formula**: composite score = uses × 0.3 + avg win rate × 0.7 |
| 5 | Battle History | Show last 5 battle mode records with win/loss stats, hero pick rate, equipment appearance rate and win count |
| 6 | Rankings | All players sorted by performance score descending. **Formula**: performance = wins × 3 - losses × 1 + level × 2. Tie = +0.5 wins |
| 7 | Data Management | Admin add/delete players, heroes, equipment (admin only) |
| 8 | Logout | Return to login screen, switch accounts |
| 9 | Exit | Save data and exit |

**Login Extra Features:**
- **Register** (Option 2): Enter username and password (min 4 chars) to create a new account, auto-persisted to file
- **Login Support**: Registered players can login directly (no hardcoding needed). Admin account retains Admin privileges

**Equipment Recommendation Logic**: Hero details display recommended equipment based on `HeroType` matching `EquipmentCategory` (WARRIOR → TANK, MAGE → MAGIC), top 3 by composite score descending.

**Player Growth**: Initial level 1, exp 0. Battle victory gives +100 exp. Exp required for next level = current level × 100. Tie counts as 0.5 wins (`winCount + 1`), outputs "Tie - Honorable Opponent".

---

## 5. Java Concepts Used

| Concept | Location |
|---------|----------|
| **Inheritance** | `Person` (abstract) ← `Player` / `Admin` |
| **Encapsulation** | All fields are `private`, accessed via getters/setters or business methods |
| **Aggregation/Composition** | `Hero` aggregates `Equipment`; `Player` aggregates `Hero`; `Player` unidirectional association with `Team` |
| **Polymorphism** | **① Method overriding**: `Player`/`Admin` override `Person.getRole()`. **② Runtime type**: `instanceof Admin` for permission check. **③ Interface polymorphism**: `HeroServiceImpl`, `TeamServiceImpl`, `MatchServiceImpl` implement `Searchable`/`Persistable` |
| **Interface** | `Searchable<T>` defines search by name; `Persistable<T>` defines generic CRUD |
| **Generics** | `Searchable<T>`, `Persistable<T>`, `List<T>`, `Map<K,V>` |
| **Collections** | `ArrayList`, `HashMap` for data storage and statistics |
| **Enum** | `HeroType` (WARRIOR/MAGE), `EquipmentCategory` (TANK/MAGIC), `MatchStatus` (SCHEDULED/IN_PROGRESS/FINISHED/CANCELLED) |
| **Composition** | `hero.Hero` composes `List<Skill>`, 15 heroes each have 3 unique skill instances |
| **Exception Handling** | Number format validation, input validation, permission checks, file I/O exception handling |
| **File I/O** | `ObjectOutputStream`/`ObjectInputStream` serialization to save/load `GameData` to `data/game_data.ser` |

---

## 6. AI Usage Summary

This project used **DeepSeek Chat (DeepSeek-V3)** as an AI assistant in three roles:

| Role | Responsibilities |
|------|-----------------|
| **Architect Agent** | Review class design, association direction, UML, propose unidirectional association improvements |
| **Implementation Agent** | Generate entity classes, Service layer, DataInitializer, main menu code |
| **Testing/Reviewer Agent** | Review compilation errors, generate test cases, verify functionality |

AI collaboration workflow: Design review → Code generation → Manual compilation verification → Feedback and fixes. AI-generated code skeleton accounts for approximately 60% of the project. Manual adjustments include package path fixes, data bug fixes (win rate over 100%), and menu function logic implementation.

Detailed records in `ai/prompts.md` and `ai/reflection.md`.

---

## 7. Testing Summary

**13 test cases** covering all 8 functional modules, all passed.

| Test ID | Feature | Cases | Status |
|---------|---------|-------|--------|
| TC-01 ~ TC-02 | Player query (exist/not exist) | 2 | ☑ Pass |
| TC-03 | Team overview | 1 | ☑ Pass |
| TC-04 | Hero details (with equipment recommendation) | 1 | ☑ Pass |
| TC-05 | Equipment stats (composite score ranking) | 1 | ☑ Pass |
| TC-06 | Battle history (equipment appearance stats) | 1 | ☑ Pass |
| TC-07 | Rankings (performance score formula) | 1 | ☑ Pass |
| TC-08 ~ TC-09 | Login (correct/wrong password) | 2 | ☑ Pass |
| TC-10 | Data management | 1 | ☑ Pass |
| TC-11 | Register new player | 1 | ☑ Pass |
| TC-12 | Battle mode | 1 | ☑ Pass |
| TC-13 | Registered player wrong password | 1 | ☑ Pass |

Detailed test cases in `docs/test-cases.md`.

---

## 8. Known Limitations

- **Manual persistence dependency**: Data is serialized to `data/game_data.ser`, but only saved on normal exit (Option 9). Abnormal shutdown may cause data loss.
- **Battle equipment records empty**: Battle mode record equipment lists are empty (battle heroes are independent from model entity heroes). Equipment appearance stats only apply to battle mode.
- **Admin account fixed**: The admin account is hardcoded as Admin type and cannot be created through registration.
- **Chinese encoding**: Under Windows cmd default encoding (GBK), Chinese characters may display incorrectly or search may fail. Execute `chcp 65001` to switch to UTF-8.
