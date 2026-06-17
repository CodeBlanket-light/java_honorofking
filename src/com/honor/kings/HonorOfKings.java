package com.honor.kings;

import com.honor.kings.model.entity.Hero;
import com.honor.kings.model.person.Player;
import com.honor.kings.model.person.Admin;
import com.honor.kings.model.person.Person;
import com.honor.kings.service.impl.HeroServiceImpl;
import com.honor.kings.util.DataInitializer;
import com.honor.kings.util.FileStorageUtil;

import com.honor.kings.model.entity.Team;
import com.honor.kings.model.entity.MatchRecord;
import com.honor.kings.model.entity.Equipment;
import com.honor.kings.hero.ZhaoYun;
import com.honor.kings.hero.LiBai;
import com.honor.kings.hero.DiaoChan;
import com.honor.kings.battle.Battle;
import java.util.*;

import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class HonorOfKings {
    // 多态：currentUser 声明为 Person 类型，实际可指向 Player 或 Admin 实例
    private static Person currentUser;
    private static HeroServiceImpl heroService = new HeroServiceImpl();
    private static Scanner scanner = new Scanner(System.in);
    // 战斗记录列表：只保留最近 3 场，格式为 "时间 | 对战双方 | 结果"
    private static List<String> battleRecords;

    // 程序入口：先初始化数据，然后循环显示登录或主菜单
    public static void main(String[] args) {
        battleRecords = DataInitializer.initAll();

        while (true) {
            // currentUser 为 null 表示未登录，显示登录界面
            // 非 null 表示已登录，显示主菜单
            if (currentUser == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private static void showLoginMenu() {
        System.out.println("\n==================================");
        System.out.println("        王者荣耀 - 登录");
        System.out.println("==================================");
        System.out.println("1. 登录");
        System.out.println("2. 注册");
        System.out.print("请选择: ");
        String choice = scanner.nextLine().trim();
        if ("2".equals(choice)) {
            showRegister();
            return;
        } else if (!"1".equals(choice)) {
            System.out.println("选择错误，请输入1或2。");
            return;
        }
        System.out.print("用户名: ");
        String username = scanner.nextLine().trim();
        System.out.print("密码: ");
        String password = scanner.nextLine().trim();

        // admin 为硬编码的管理员账号（Admin 类型），不走玩家列表
        if ("admin".equals(username) && "admin123".equals(password)) {
            currentUser = new Admin("A01", "admin", "admin@honor.com", java.time.LocalDateTime.now(), "SUPER", "运营部");
            System.out.println("登录成功！当前角色: " + currentUser.getRole() + " (" + currentUser.getName() + ")");
            return;
        }
        // 遍历玩家列表查找匹配的用户名（包括预置的 player1 和注册用户）
        Player found = null;
        for (Player p : DataInitializer.getAllPlayers()) {
            if (p.getName().equals(username)) {
                found = p;
                break;
            }
        }
        if (found == null) {
            System.out.println("用户名不存在");
        } else if (found.getPassword() != null && found.getPassword().equals(password)) {
            // 多态：currentUser 被赋值为 Player 对象，与 Admin 有不同权限
            currentUser = found;
            System.out.println("登录成功！当前角色: " + currentUser.getRole() + " (" + currentUser.getName() + ")");
        } else {
            System.out.println("密码错误");
        }
    }

    private static void showRegister() {
        System.out.println("\n=== 注册新玩家 ===");
        System.out.print("请输入用户名: ");
        String regName = scanner.nextLine().trim();
        if (regName.isEmpty()) {
            System.out.println("用户名不能为空。");
            return;
        }
        for (Player p : DataInitializer.getAllPlayers()) {
            if (p.getName().equals(regName)) {
                System.out.println("用户名已存在，请重新输入。");
                return;
            }
        }
        System.out.print("请输入密码（至少4位）: ");
        String regPwd = scanner.nextLine();
        if (regPwd.length() < 4) {
            System.out.println("密码不能少于4位。");
            return;
        }
        // ID 使用时间戳确保唯一性，如 p1712345678901
        String id = "p" + System.currentTimeMillis();
        Player newPlayer = new Player(id, regName, regName + "@honor.com", LocalDateTime.now(), 1, 0, 0, regPwd);
        DataInitializer.getAllPlayers().add(newPlayer);
        FileStorageUtil.saveAllData(
                DataInitializer.getAllPlayers(),
                DataInitializer.getAllHeroes(),
                DataInitializer.getAllEquipment(),
                DataInitializer.getAllTeams(),
                DataInitializer.getAllMatches()
        );
        System.out.println("注册成功！请重新登录。");
    }

    private static void showMainMenu() {
        System.out.println("\n==================================");
        System.out.println("  王者荣耀 Java版 - 主菜单");
        System.out.println("  当前用户: " + currentUser.getName() + " (" + currentUser.getRole() + ")");
        System.out.println("==================================");
        System.out.println("1. 玩家查询（按姓名查找）");
        System.out.println("2. 队伍概览");
        System.out.println("3. 英雄详情");
        System.out.println("4. 装备统计");
        System.out.println("5. 对战历史");
        System.out.println("6. 排行榜");
        System.out.println("7. 数据管理（仅管理员）");
        System.out.println("8. 退出登录");
        System.out.println("9. 退出系统");
        System.out.println("0. 战斗模式（彩蛋）");
        System.out.print("请选择: ");

        String input = scanner.nextLine().trim();
        switch (input) {
            case "1":
                queryPlayer();
                break;
            case "2":
                queryTeam();
                break;
            case "3":
                queryHeroDetail();
                break;
            case "4":
                showEquipmentStats();
                break;
            case "5":
                showMatchHistory();
                break;
            case "6":
                showRanking();
                break;
            // 多态：instanceof 判断运行时类型，Admin 才有数据管理权限
            case "7":
                if (currentUser instanceof Admin) {
                    showDataManagement();
                } else {
                    System.out.println("权限不足，仅管理员可操作。");
                }
                break;
            case "8":
                currentUser = null;
                System.out.println("已退出登录。");
                return;
            case "9":
                saveBeforeExit();
                System.out.println("感谢游玩，再见！");
                scanner.close();
                System.exit(0);
                break;
            case "0":
                startBattleMode();
                break;
            default:
                System.out.println("无效输入，请输入0-9之间的数字。");
        }
    }

    private static void queryPlayer() {
        System.out.print("请输入要查询的玩家姓名: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("输入不能为空");
            return;
        }
        for (Player player : DataInitializer.getAllPlayers()) {
            if (player.getName().equalsIgnoreCase(name)) {
                System.out.println("姓名: " + player.getName());
                System.out.println("等级: " + player.getLevel());
                System.out.println("胜率: " + String.format("%.1f", player.getWinRate()) + "%");
                System.out.println("拥有英雄:");
                for (Hero hero : player.getOwnedHeroes()) {
                    System.out.print("  - " + hero.getName());
                    List<com.honor.kings.model.entity.Equipment> eqList = hero.getEquipmentList();
                    if (eqList != null && !eqList.isEmpty()) {
                        System.out.print(" [装备: ");
                        for (int i = 0; i < eqList.size(); i++) {
                            if (i > 0) System.out.print(", ");
                            System.out.print(eqList.get(i).getName());
                        }
                        System.out.print("]");
                    }
                    System.out.println();
                }
                return;
            }
        }
        System.out.println("未找到该玩家");
    }

    private static void queryTeam() {
        System.out.println("\n已有队伍:");
        for (Team t : DataInitializer.getAllTeams()) {
            System.out.println("  " + t.getId() + " - " + t.getTeamName());
        }
        System.out.println("你也可以输入 NEW 创建一个新队伍");
        System.out.print("请输入要查询的队伍名称或ID: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("输入不能为空");
            return;
        }
        if ("NEW".equalsIgnoreCase(input)) {
            System.out.print("请输入新队伍ID: ");
            String newId = scanner.nextLine().trim();
            System.out.print("请输入新队伍名称: ");
            String newName = scanner.nextLine().trim();
            if (newId.isEmpty() || newName.isEmpty()) {
                System.out.println("队伍ID和名称不能为空");
                return;
            }
            Team newTeam = new Team(newId, newName, 10, 0, LocalDateTime.now());
            DataInitializer.getAllTeams().add(newTeam);
            System.out.println("队伍已创建！");
            return;
        }
        Team found = null;
        for (Team team : DataInitializer.getAllTeams()) {
            if (team.getTeamName().equalsIgnoreCase(input) || team.getId().equalsIgnoreCase(input)) {
                found = team;
                break;
            }
        }
        if (found == null) {
            System.out.println("未找到该队伍");
            return;
        }
        int totalMatches = 0;
        int wins = 0;
        for (MatchRecord match : DataInitializer.getAllMatches()) {
            Team a = match.getTeamA();
            Team b = match.getTeamB();
            if ((a != null && a.getId().equals(found.getId())) ||
                (b != null && b.getId().equals(found.getId()))) {
                totalMatches++;
                if (match.getWinner() != null && match.getWinner().getId().equals(found.getId())) {
                    wins++;
                }
            }
        }
        List<Player> members = new java.util.ArrayList<>();
        Player strongest = null;
        int levelSum = 0;
        for (Player p : DataInitializer.getAllPlayers()) {
            if (p.getCurrentTeam() != null && p.getCurrentTeam().getId().equals(found.getId())) {
                members.add(p);
                levelSum += p.getLevel();
                if (strongest == null || p.getLevel() > strongest.getLevel()) {
                    strongest = p;
                }
            }
        }
        double avgLevel = members.isEmpty() ? 0 : (double) levelSum / members.size();

        System.out.println("队伍名称: " + found.getTeamName());
        System.out.println("分数: " + found.getScore());
        System.out.print("成员列表: ");
        if (members.isEmpty()) {
            System.out.println("无");
        } else {
            System.out.println();
            for (Player p : members) {
                System.out.println("  - " + p.getName());
            }
        }
        System.out.println("平均等级: " + String.format("%.1f", avgLevel));
        System.out.println("总比赛场次: " + totalMatches);
        System.out.println("胜率: " + (totalMatches == 0 ? "N/A" : String.format("%.1f", (double) wins / totalMatches * 100) + "%"));
        System.out.println("队内最强玩家: " + (strongest == null ? "无" : strongest.getName()));
        if (totalMatches > 0) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            System.out.println("最近比赛记录:");
            List<MatchRecord> teamMatches = new java.util.ArrayList<>();
            for (MatchRecord m : DataInitializer.getAllMatches()) {
                Team a = m.getTeamA();
                Team b = m.getTeamB();
                if ((a != null && a.getId().equals(found.getId())) ||
                    (b != null && b.getId().equals(found.getId()))) {
                    teamMatches.add(m);
                }
            }
            teamMatches.sort((m1, m2) -> m2.getMatchTime().compareTo(m1.getMatchTime()));
            int cnt = 0;
            for (MatchRecord m : teamMatches) {
                if (cnt >= 5) break;
                String opponent = m.getTeamA() != null && m.getTeamA().getId().equals(found.getId()) ?
                    (m.getTeamB() != null ? m.getTeamB().getTeamName() : "未知") :
                    (m.getTeamA() != null ? m.getTeamA().getTeamName() : "未知");
                String dateStr = m.getMatchTime() != null ? m.getMatchTime().format(fmt) : "未知";
                String result;
                if (m.getWinner() == null) result = "平";
                else if (m.getWinner().getId().equals(found.getId())) result = "胜";
                else result = "负";
                System.out.println("  " + (cnt + 1) + ". VS " + opponent + " | " + dateStr + " | " + m.getScoreA() + ":" + m.getScoreB() + " | " + result);
                cnt++;
            }
        }
    }

    private static void queryHeroDetail() {
        System.out.print("请输入要查询的英雄名称: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("输入不能为空");
            return;
        }
        com.honor.kings.model.entity.Hero found = null;
        for (com.honor.kings.model.entity.Hero h : DataInitializer.getAllHeroes()) {
            if (h.getName().equalsIgnoreCase(name)) {
                found = h;
                break;
            }
        }
        if (found == null) {
            System.out.println("未找到该英雄");
            return;
        }
        System.out.println("英雄名称: " + found.getName());
        System.out.println("称号: " + found.getTitle());
        System.out.println("阵营: " + found.getFaction());
        System.out.println("基础属性: HP=" + found.getBaseHp() + " 攻击=" + found.getBaseAttack() + " 防御=" + found.getBaseDefense());
        System.out.println("总属性: HP=" + found.getTotalHp() + " 攻击=" + found.getTotalAttack() + " 防御=" + found.getTotalDefense());
        List<Equipment> eqList = found.getEquipmentList();
        if (eqList != null && !eqList.isEmpty()) {
            System.out.println("当前装备:");
            for (Equipment eq : eqList) {
                System.out.println("  - " + eq.getName() + " (" + eq.getType() + ")");
            }
        } else {
            System.out.println("当前装备: 无");
        }
        List<String> ownerNames = new java.util.ArrayList<>();
        for (Player p : DataInitializer.getAllPlayers()) {
            for (com.honor.kings.model.entity.Hero h : p.getOwnedHeroes()) {
                if (h.getId().equals(found.getId())) {
                    ownerNames.add(p.getName());
                    break;
                }
            }
        }
        if (ownerNames.isEmpty()) {
            System.out.println("拥有该英雄的玩家: 无");
        } else {
            System.out.println("拥有该英雄的玩家: " + String.join(", ", ownerNames));
        }
    }

    // 装备使用次数排名：遍历所有英雄的装备列表，统计每件装备出现次数
    // 排名公式：使用次数降序 → 次数相同按名称排序 → 取前5名
    private static void showEquipmentStats() {
        // 两个 Map 分别存装备ID→次数 和 装备ID→对象
        Map<String, Integer> equipCount = new HashMap<>();
        Map<String, Equipment> equipMap = new HashMap<>();
        for (Equipment eq : DataInitializer.getAllEquipment()) {
            equipMap.put(eq.getId(), eq);
            equipCount.put(eq.getId(), 0);
        }
        for (com.honor.kings.model.entity.Hero h : DataInitializer.getAllHeroes()) {
            for (Equipment eq : h.getEquipmentList()) {
                equipCount.merge(eq.getId(), 1, Integer::sum);
            }
        }
        boolean hasData = false;
        for (int count : equipCount.values()) {
            if (count > 0) { hasData = true; break; }
        }
        if (!hasData) {
            System.out.println("暂无装备数据");
            return;
        }
        // 使用 Stream API 对 Map 按值降序排序，次数相同按装备名称排序
        // sorted() 自定义比较器：先比次数（降序），次数相同比名称（升序）
        List<Entry<String, Integer>> sorted = equipCount.entrySet().stream()
                .sorted((a, b) -> {
                    int cmp = b.getValue().compareTo(a.getValue());
                    if (cmp != 0) return cmp;
                    return equipMap.get(a.getKey()).getName().compareTo(equipMap.get(b.getKey()).getName());
                })
                .collect(Collectors.toList());
        System.out.println("装备使用排行榜（前5名）:");
        int rank = 1;
        for (Entry<String, Integer> entry : sorted) {
            if (rank > 5) break;
            Equipment eq = equipMap.get(entry.getKey());
            System.out.println(rank + ". " + eq.getName() + " (" + eq.getType() + ") - 使用次数: " + entry.getValue());
            rank++;
        }
    }

    private static void showMatchHistory() {
        if (battleRecords == null || battleRecords.isEmpty()) {
            System.out.println("暂无对战记录");
            return;
        }
        System.out.println("战斗模式记录（最近3场）:");
        for (String r : battleRecords) {
            System.out.println("  " + r);
        }
    }

    private static void showRanking() {
        List<Player> players = DataInitializer.getAllPlayers();
        if (players.isEmpty()) {
            System.out.println("暂无玩家数据");
            return;
        }
        // 排行榜排序规则：先按胜率降序排列
        // 平局处理：总场次为0时胜率设为-1（排在最后）；胜率相同则按总场次降序
        players.sort((a, b) -> {
            double rateA = a.getTotalMatches() == 0 ? -1 : a.getWinRate();
            double rateB = b.getTotalMatches() == 0 ? -1 : b.getWinRate();
            if (rateB != rateA) {
                return Double.compare(rateB, rateA);
            }
            return Integer.compare(b.getTotalMatches(), a.getTotalMatches());
        });
        System.out.println("玩家排行榜（前3名）:");
        for (int i = 0; i < 3 && i < players.size(); i++) {
            Player p = players.get(i);
            String rateStr = p.getTotalMatches() == 0 ? "N/A" : String.format("%.1f%%", p.getWinRate());
            System.out.println((i + 1) + ". " + p.getName() + " - 胜率: " + rateStr + " - 总场次: " + p.getTotalMatches() + " - 胜场: " + p.getWinCount());
        }
    }

    private static void showDataManagement() {
        while (true) {
            System.out.println("\n=== 数据管理 ===");
            System.out.println("1. 添加玩家");
            System.out.println("2. 删除玩家");
            System.out.println("3. 添加英雄");
            System.out.println("4. 删除英雄");
            System.out.println("5. 添加装备");
            System.out.println("6. 删除装备");
            System.out.println("7. 返回主菜单");
            System.out.print("请选择: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("请输入玩家ID: ");
                    String pId = scanner.nextLine().trim();
                    System.out.print("请输入玩家姓名: ");
                    String pName = scanner.nextLine().trim();
                    System.out.print("请输入玩家邮箱: ");
                    String pEmail = scanner.nextLine().trim();
                    System.out.print("请输入玩家等级: ");
                    String pLevelStr = scanner.nextLine().trim();
                    // 异常处理：Integer.parseInt 可能抛出 NumberFormatException
                    try {
                        int pLevel = Integer.parseInt(pLevelStr);
                        Player newPlayer = new Player(pId, pName, pEmail, LocalDateTime.now(), pLevel, 0, 0);
                        DataInitializer.getAllPlayers().add(newPlayer);
                        System.out.println("操作成功");
                    } catch (NumberFormatException e) {
                        System.out.println("操作失败");
                    }
                    break;
                case "2":
                    System.out.print("请输入要删除的玩家ID: ");
                    String delPId = scanner.nextLine().trim();
                    boolean removedPlayer = DataInitializer.getAllPlayers().removeIf(p -> p.getId().equals(delPId));
                    System.out.println(removedPlayer ? "操作成功" : "未找到该玩家");
                    break;
                case "3":
                    System.out.print("请输入英雄ID: ");
                    String hId = scanner.nextLine().trim();
                    System.out.print("请输入英雄名称: ");
                    String hName = scanner.nextLine().trim();
                    System.out.print("请输入英雄称号: ");
                    String hTitle = scanner.nextLine().trim();
                    System.out.print("请输入阵营: ");
                    String hFaction = scanner.nextLine().trim();
                    System.out.print("请输入基础HP: ");
                    String hpHp = scanner.nextLine().trim();
                    System.out.print("请输入基础攻击: ");
                    String hpAtk = scanner.nextLine().trim();
                    System.out.print("请输入基础防御: ");
                    String hpDef = scanner.nextLine().trim();
                    try {
                        com.honor.kings.model.entity.Hero newHero = new com.honor.kings.model.entity.Hero(hId, hName, hTitle,
                                Integer.parseInt(hpHp), Integer.parseInt(hpAtk), Integer.parseInt(hpDef), hFaction, 1);
                        DataInitializer.getAllHeroes().add(newHero);
                        System.out.println("操作成功");
                    } catch (NumberFormatException e) {
                        System.out.println("操作失败");
                    }
                    break;
                case "4":
                    System.out.print("请输入要删除的英雄ID: ");
                    String delHId = scanner.nextLine().trim();
                    boolean removedHero = DataInitializer.getAllHeroes().removeIf(h -> h.getId().equals(delHId));
                    System.out.println(removedHero ? "操作成功" : "未找到该英雄");
                    break;
                case "5":
                    System.out.print("请输入装备ID: ");
                    String eId = scanner.nextLine().trim();
                    System.out.print("请输入装备名称: ");
                    String eName = scanner.nextLine().trim();
                    System.out.print("请输入装备类型(WEAPON/ARMOR/BOOTS/ACCESSORY): ");
                    String eTypeStr = scanner.nextLine().trim();
                    System.out.print("请输入HP加成: ");
                    String eHp = scanner.nextLine().trim();
                    System.out.print("请输入攻击加成: ");
                    String eAtk = scanner.nextLine().trim();
                    System.out.print("请输入防御加成: ");
                    String eDef = scanner.nextLine().trim();
                    System.out.print("请输入价格: ");
                    String ePrice = scanner.nextLine().trim();
                    // 枚举解析：EquipmentType.valueOf() 将字符串转为枚举常量
                    // 若输入不匹配任一枚举值（如输入"HELMET"），抛出 IllegalArgumentException
                    try {
                        Equipment.EquipmentType eType = Equipment.EquipmentType.valueOf(eTypeStr.toUpperCase());
                        Equipment newEq = new Equipment(eId, eName, eType,
                                Integer.parseInt(eHp), Integer.parseInt(eAtk), Integer.parseInt(eDef), Integer.parseInt(ePrice));
                        DataInitializer.getAllEquipment().add(newEq);
                        System.out.println("操作成功");
                    } catch (Exception e) {
                        System.out.println("操作失败");
                    }
                    break;
                case "6":
                    System.out.print("请输入要删除的装备ID: ");
                    String delEId = scanner.nextLine().trim();
                    boolean removedEq = DataInitializer.getAllEquipment().removeIf(e -> e.getId().equals(delEId));
                    System.out.println(removedEq ? "操作成功" : "未找到该装备");
                    break;
                case "7":
                    return;
                default:
                    System.out.println("无效输入，请输入1-7之间的数字。");
            }
        }
    }

    // 程序退出前调用序列化保存所有数据到 data/game_data.ser
    private static void saveBeforeExit() {
        FileStorageUtil.saveAllData(
                DataInitializer.getAllPlayers(),
                DataInitializer.getAllHeroes(),
                DataInitializer.getAllEquipment(),
                DataInitializer.getAllTeams(),
                DataInitializer.getAllMatches(),
                battleRecords
        );
    }

    private static void startBattleMode() {
        System.out.println("\n=== 战斗模式 ===");
        com.honor.kings.hero.Hero[] heroes = {
            new ZhaoYun(),
            new LiBai(),
            new DiaoChan()
        };
        for (int i = 0; i < heroes.length; i++) {
            System.out.println((i + 1) + ". " + heroes[i].getName());
        }
        System.out.print("选择第一个英雄 (1-" + heroes.length + "): ");
        int choice1;
        int choice2;
        try {
            choice1 = Integer.parseInt(scanner.nextLine().trim()) - 1;
            System.out.print("选择第二个英雄 (1-" + heroes.length + "): ");
            choice2 = Integer.parseInt(scanner.nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("输入无效");
            return;
        }
        if (choice1 < 0 || choice1 >= heroes.length ||
            choice2 < 0 || choice2 >= heroes.length) {
            System.out.println("输入无效");
            return;
        }
        if (choice1 == choice2) {
            System.out.println("不能选择同一个英雄");
            return;
        }
        Battle battle = new Battle(heroes[choice1], heroes[choice2]);
        battle.start();
        String winner = battle.getWinner();
        String timeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String record = timeStr + " | " + heroes[choice1].getName() + " VS " + heroes[choice2].getName() + " | " + winner;
        battleRecords.add(record);
        if (battleRecords.size() > 3) {
            battleRecords.remove(0);
        }
    }
}
