 # 王者荣耀Java项目设计说明

## 项目分层结构
```
com.honor.kings
├── model                  # 数据模型层
│   ├── entity             # 实体类
│   │   ├── Hero.java
│   │   ├── Equipment.java
│   │   ├── Team.java
│   │   └── MatchRecord.java
│   └── person             # 人员类
│       ├── Person.java    (抽象父类)
│       ├── Player.java    (子类)
│       └── Admin.java     (子类)
├── service                # 业务逻辑层
│   ├── HeroService.java
│   ├── TeamService.java
│   ├── MatchService.java
│   └── impl               # 实现类
│       ├── HeroServiceImpl.java
│       ├── TeamServiceImpl.java
│       └── MatchServiceImpl.java
└── HonorOfKings.java      # 主入口
```

---

## 一、model 层

### 1. abstract class Person（抽象父类）
- 核心属性：`String id, String name, String email, LocalDateTime createTime`
- 核心方法：`abstract String getRole()`（子类实现返回角色名）
- 关系：被 `Player` 和 `Admin` 继承（继承关系）

### 2. class Player extends Person
- 核心属性：`int level, int totalMatches, int winCount, List<Hero> ownedHeroes, Team currentTeam`
- 核心方法：`joinTeam(Team team), leaveTeam(), addHero(Hero hero), getWinRate()`
- 关系：继承 `Person`；聚合多个 `Hero`（玩家拥有英雄）；关联 `Team`（玩家属于队伍）

### 3. class Admin extends Person
- 核心属性：`String permissionLevel, String department`
- 核心方法：`approveMatch(MatchRecord match), banPlayer(Player player), adjustHeroStats(Hero hero)`
- 关系：继承 `Person`

### 4. class Hero（实体）
- 核心属性：`String id, String name, String title, int baseHp, int baseAttack, int baseDefense, List<Equipment> equipmentList, String faction, int level`
- 核心方法：`equip(Equipment eq), unequip(Equipment eq), getTotalHp(), getTotalAttack(), getTotalDefense()`
- 关系：聚合多个 `Equipment`（组合，英雄装备多件装备）

### 5. class Equipment（实体）
- 核心属性：`String id, String name, EquipmentType type, int bonusHp, int bonusAttack, int bonusDefense, int price`
- 核心方法：`getDescription()`
- 枚举：`EquipmentType { WEAPON, ARMOR, BOOTS, ACCESSORY }`
- 关系：被 `Hero` 聚合引用

### 6. class Team（实体）
- 核心属性：`String id, String teamName, int maxMembers, int score, LocalDateTime foundedTime`
- 核心方法：`isFull()`（通过查询当前成员数判断）
- 关系：不再持有 `Player` 列表；成员查询通过 `PlayerService.getPlayersByTeamId(teamId)` 实现

### 7. class MatchRecord（实体）
- 核心属性：`String id, Team teamA, Team teamB, int scoreA, int scoreB, Team winner, String duration, LocalDateTime matchTime, MatchStatus status`
- 核心方法：`determineWinner(), getMatchSummary()`
- 枚举：`MatchStatus { SCHEDULED, IN_PROGRESS, FINISHED, CANCELLED }`
- 关系：关联两个 `Team`（对战双方）

---

## 二、接口

### interface Searchable
- 方法：`List<T> searchByName(String keyword)`
- 业务意义：支持按名称搜索实体（英雄、装备、队伍等），让 `HeroService`、`TeamService` 等实现此接口
- 实现者：`HeroServiceImpl`, `TeamServiceImpl`

### interface Persistable
- 方法：`boolean save(T entity), T findById(String id), boolean delete(String id), List<T> findAll()`
- 业务意义：定义通用的持久化 CRUD 操作，让所有 Service 实现此接口
- 实现者：`HeroServiceImpl`, `TeamServiceImpl`, `MatchServiceImpl`

---

## 三、service 层

### 1. class HeroServiceImpl implements Searchable<Hero>, Persistable<Hero>
- 核心方法：
  - `save(Hero hero)`, `findById(id)`, `delete(id)`, `findAll()` —— 实现 Persistable
  - `searchByName(keyword)` —— 实现 Searchable
  - `equipHero(Hero hero, Equipment eq)`, `unequipHero(Hero hero, Equipment eq)`
  - `getHeroesByFaction(String faction)`
- 关系：实现 `Searchable<Hero>` 和 `Persistable<Hero>` 接口

### 2. class TeamServiceImpl implements Searchable<Team>, Persistable<Team>
- 核心方法：
  - `save(Team team)`, `findById(id)`, `delete(id)`, `findAll()` —— 实现 Persistable
  - `searchByName(keyword)` —— 实现 Searchable
  - `addPlayerToTeam(Team team, Player player)`, `removePlayerFromTeam(Team team, Player player)`（通过 `Player.setTeam()` 实现，无需操作 `Team.members`）
  - `getMembers(String teamId)`（调用 `PlayerService` 按 teamId 查询）
  - `getRanking()`
- 关系：实现 `Searchable<Team>` 和 `Persistable<Team>` 接口

### 3. class MatchServiceImpl implements Persistable<MatchRecord>
- 核心方法：
  - `save(MatchRecord match)`, `findById(id)`, `delete(id)`, `findAll()` —— 实现 Persistable
  - `startMatch(Team teamA, Team teamB)`
  - `finishMatch(String matchId, int scoreA, int scoreB)`
  - `getPlayerMatchHistory(Player player)`
  - `getTeamWinRate(Team team)`
- 关系：实现 `Persistable<MatchRecord>` 接口

---

## 四、OOP 特性体现总结

| OOP 特性 | 体现位置 |
|---------|---------|
| **继承** | `Person` ← `Player` / `Admin` |
| **封装** | 所有属性为 `private`，通过 getter/setter 或业务方法访问 |
| **聚合/组合** | `Hero` 聚合 `Equipment`；`Player` 聚合 `Hero`；`Player` 单向关联 `Team` |
| **多态** | `Person.getRole()` 由子类实现不同行为；`Searchable` / `Persistable` 接口多态 |
| **接口** | `Searchable` 按名称搜索；`Persistable` 通用 CRUD 持久化 |

---

## 五、类关系图（文字描述）

```
Person (abstract)
  ├── Player ——聚合——> Hero (1:N) ——聚合——> Equipment (1:N)
  │            单向关联
  │              └——> Team (N:1)
  └── Admin

MatchRecord ——关联——> Team (2个, teamA & teamB)

成员查询路径：PlayerService.getPlayersByTeamId(teamId) ——查询——> Player.teamId

HeroServiceImpl ——实现——> Searchable<Hero>, Persistable<Hero>
TeamServiceImpl ——实现——> Searchable<Team>, Persistable<Team>
MatchServiceImpl ——实现——> Persistable<MatchRecord>
```
