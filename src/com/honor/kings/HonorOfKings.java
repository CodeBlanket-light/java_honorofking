package com.honor.kings;

import com.honor.kings.model.entity.Hero;
import com.honor.kings.model.person.Player;
import com.honor.kings.model.person.Admin;
import com.honor.kings.model.person.Person;
import com.honor.kings.service.impl.HeroServiceImpl;
import com.honor.kings.util.DataInitializer;

import com.honor.kings.model.entity.Team;
import com.honor.kings.model.entity.MatchRecord;
import com.honor.kings.model.entity.Equipment;
import java.util.*;

import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class HonorOfKings {
    private static Person currentUser;
    private static HeroServiceImpl heroService = new HeroServiceImpl();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DataInitializer.initAll();

        while (true) {
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
        System.out.print("用户名: ");
        String username = scanner.nextLine().trim();
        System.out.print("密码: ");
        String password = scanner.nextLine().trim();

        if ("admin".equals(username) && "admin123".equals(password)) {
            currentUser = new Admin("A01", "admin", "admin@honor.com", java.time.LocalDateTime.now(), "SUPER", "运营部");
            System.out.println("登录成功！当前角色: " + currentUser.getRole() + " (" + currentUser.getName() + ")");
        } else if ("player1".equals(username) && "123456".equals(password)) {
            currentUser = new Player("P01", "player1", "player1@honor.com", java.time.LocalDateTime.now(), 10, 50, 30);
            System.out.println("登录成功！当前角色: " + currentUser.getRole() + " (" + currentUser.getName() + ")");
        } else {
            System.out.println("用户名或密码错误，请重试。");
        }
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
                System.out.println("感谢游玩，再见！");
                scanner.close();
                System.exit(0);
                break;
            default:
                System.out.println("无效输入，请输入1-9之间的数字。");
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
        System.out.print("请输入要查询的队伍名称或ID: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("输入不能为空");
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

    private static void showEquipmentStats() {
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
        List<Entry<String, Integer>> sorted = equipCount.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
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
        System.out.print("请输入要查询的玩家姓名: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("输入不能为空");
            return;
        }
        Player player = null;
        for (Player p : DataInitializer.getAllPlayers()) {
            if (p.getName().equalsIgnoreCase(name)) {
                player = p;
                break;
            }
        }
        if (player == null) {
            System.out.println("未找到该玩家");
            return;
        }
        Team playerTeam = player.getCurrentTeam();
        if (playerTeam == null) {
            System.out.println("该玩家暂无对战记录");
            return;
        }
        List<MatchRecord> records = new ArrayList<>();
        for (MatchRecord match : DataInitializer.getAllMatches()) {
            Team a = match.getTeamA();
            Team b = match.getTeamB();
            if ((a != null && a.getId().equals(playerTeam.getId())) ||
                (b != null && b.getId().equals(playerTeam.getId()))) {
                records.add(match);
            }
        }
        if (records.isEmpty()) {
            System.out.println("该玩家暂无对战记录");
            return;
        }
        records.sort((m1, m2) -> m2.getMatchTime().compareTo(m1.getMatchTime()));
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println(player.getName() + " 最近5场对战记录:");
        int count = 0;
        for (MatchRecord match : records) {
            if (count >= 5) break;
            Team opponent;
            boolean isTeamA = match.getTeamA() != null && match.getTeamA().getId().equals(playerTeam.getId());
            if (isTeamA) {
                opponent = match.getTeamB();
            } else {
                opponent = match.getTeamA();
            }
            String opponentName = opponent != null ? opponent.getTeamName() : "未知";
            String dateStr = match.getMatchTime() != null ? match.getMatchTime().format(fmt) : "未知";
            String score = match.getScoreA() + " : " + match.getScoreB();
            String result;
            if (match.getWinner() == null) {
                result = "平";
            } else if (match.getWinner().getId().equals(playerTeam.getId())) {
                result = "胜";
            } else {
                result = "负";
            }
            System.out.println((count + 1) + ". 对手: " + opponentName + " | 日期: " + dateStr + " | 比分: " + score + " | 结果: " + result);
            count++;
        }
    }

    private static void showRanking() {
        List<Player> players = DataInitializer.getAllPlayers();
        if (players.isEmpty()) {
            System.out.println("暂无玩家数据");
            return;
        }
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
}
