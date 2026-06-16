package com.honor.kings.util;

import com.honor.kings.model.entity.Equipment;
import com.honor.kings.model.entity.Equipment.EquipmentType;
import com.honor.kings.model.entity.Hero;
import com.honor.kings.model.entity.Team;
import com.honor.kings.model.entity.MatchRecord;
import com.honor.kings.model.entity.MatchRecord.MatchStatus;
import com.honor.kings.model.person.Player;

import java.time.LocalDateTime;
import java.util.*;

public class DataInitializer {

    public static void initAll() {
        List<Equipment> allEquipment = createEquipment();
        List<Hero> allHeroes = createHeroes(allEquipment);
        List<Team> allTeams = createTeams();
        List<Player> allPlayers = createPlayers(allHeroes, allTeams);
        List<MatchRecord> allMatches = createMatches(allTeams);

        System.out.println("=== 数据初始化完成 ===");
        System.out.println("装备: " + allEquipment.size() + " 件");
        System.out.println("英雄: " + allHeroes.size() + " 个");
        System.out.println("队伍: " + allTeams.size() + " 个");
        System.out.println("玩家: " + allPlayers.size() + " 个");
        System.out.println("比赛记录: " + allMatches.size() + " 条");
    }

    private static List<Equipment> createEquipment() {
        List<Equipment> list = new ArrayList<>();
        list.add(new Equipment("E01", "无尽战刃", EquipmentType.WEAPON, 0, 130, 0, 2140));
        list.add(new Equipment("E02", "破军", EquipmentType.WEAPON, 0, 180, 0, 2950));
        list.add(new Equipment("E03", "宗师之力", EquipmentType.WEAPON, 0, 80, 0, 2100));
        list.add(new Equipment("E04", "暗影战斧", EquipmentType.WEAPON, 0, 85, 0, 2090));
        list.add(new Equipment("E05", "破晓", EquipmentType.WEAPON, 0, 100, 0, 3400));
        list.add(new Equipment("E06", "红莲斗篷", EquipmentType.ARMOR, 1000, 0, 240, 1830));
        list.add(new Equipment("E07", "不祥征兆", EquipmentType.ARMOR, 1200, 0, 270, 2180));
        list.add(new Equipment("E08", "反伤刺甲", EquipmentType.ARMOR, 400, 0, 420, 1860));
        list.add(new Equipment("E09", "魔女斗篷", EquipmentType.ARMOR, 1000, 0, 200, 2120));
        list.add(new Equipment("E10", "极寒风暴", EquipmentType.ARMOR, 500, 0, 360, 2100));
        list.add(new Equipment("E11", "抵抗之靴", EquipmentType.BOOTS, 0, 0, 110, 710));
        list.add(new Equipment("E12", "影忍之足", EquipmentType.BOOTS, 0, 0, 120, 710));
        list.add(new Equipment("E13", "冷静之靴", EquipmentType.BOOTS, 0, 0, 0, 710));
        list.add(new Equipment("E14", "急速战靴", EquipmentType.BOOTS, 0, 0, 0, 710));
        list.add(new Equipment("E15", "回响之杖", EquipmentType.WEAPON, 0, 240, 0, 2100));
        list.add(new Equipment("E16", "博学者之怒", EquipmentType.WEAPON, 0, 240, 0, 2300));
        list.add(new Equipment("E17", "虚无法杖", EquipmentType.WEAPON, 0, 160, 0, 2110));
        list.add(new Equipment("E18", "贤者之书", EquipmentType.ACCESSORY, 1600, 400, 0, 2990));
        list.add(new Equipment("E19", "辉月", EquipmentType.ACCESSORY, 0, 160, 0, 1990));
        list.add(new Equipment("E20", "血魔之怒", EquipmentType.ACCESSORY, 1000, 0, 0, 2120));
        return list;
    }

    private static List<Hero> createHeroes(List<Equipment> allEquipment) {
        List<Hero> list = new ArrayList<>();
        Random rand = new Random(42);

        String[][] heroData = {
            {"H01", "赵云", "常山赵子龙", "蜀", "3500", "280", "200"},
            {"H02", "李白", "青莲剑仙", "唐", "3000", "320", "150"},
            {"H03", "貂蝉", "绝世舞姬", "东汉", "2800", "340", "130"},
            {"H04", "关羽", "武圣", "蜀", "3800", "260", "220"},
            {"H05", "诸葛亮", "卧龙", "蜀", "2900", "350", "140"},
            {"H06", "韩信", "国士无双", "汉", "3100", "300", "160"},
            {"H07", "花木兰", "传说之刃", "魏", "3300", "290", "180"},
            {"H08", "孙尚香", "千金重弩", "吴", "2700", "360", "120"},
            {"H09", "吕布", "战神", "东汉", "4000", "250", "240"},
            {"H10", "武则天", "女帝", "唐", "2800", "330", "130"},
            {"H11", "曹操", "枭雄", "魏", "3600", "270", "190"},
            {"H12", "周瑜", "大都督", "吴", "2900", "320", "140"},
            {"H13", "项羽", "霸王", "楚", "4200", "240", "260"},
            {"H14", "妲己", "魅惑之狐", "商", "2600", "370", "110"},
            {"H15", "程咬金", "热血战斧", "唐", "4500", "230", "250"},
        };

        for (String[] d : heroData) {
            Hero hero = new Hero(d[0], d[1], d[2], Integer.parseInt(d[3]), Integer.parseInt(d[4]),
                    Integer.parseInt(d[5]), d[6], 1);
            int equipCount = 1 + rand.nextInt(3);
            for (int i = 0; i < equipCount; i++) {
                hero.equip(allEquipment.get(rand.nextInt(allEquipment.size())));
            }
            list.add(hero);
        }
        return list;
    }

    private static List<Team> createTeams() {
        List<Team> list = new ArrayList<>();
        list.add(new Team("T01", "荣耀战队", 10, 1500, LocalDateTime.now().minusDays(30)));
        list.add(new Team("T02", "巅峰小队", 8, 1200, LocalDateTime.now().minusDays(20)));
        list.add(new Team("T03", "王者之师", 12, 1800, LocalDateTime.now().minusDays(15)));
        return list;
    }

    private static List<Player> createPlayers(List<Hero> allHeroes, List<Team> allTeams) {
        List<Player> list = new ArrayList<>();
        Random rand = new Random(123);
        String[] names = {"梦泪", "剑仙", "张大仙", "孤影", "北慕", "可杰", "韩涵", "微凉", "故辞", "小予神"};

        for (int i = 0; i < 10; i++) {
            Player p = new Player("P" + String.format("%02d", i + 1), names[i],
                    names[i] + "@honor.com", LocalDateTime.now().minusDays(rand.nextInt(365)),
                    1 + rand.nextInt(30), 10 + rand.nextInt(200), 5 + rand.nextInt(150));
            int heroCount = 2 + rand.nextInt(3);
            for (int j = 0; j < heroCount; j++) {
                p.addHero(allHeroes.get(rand.nextInt(allHeroes.size())));
            }
            p.joinTeam(allTeams.get(rand.nextInt(allTeams.size())));
            list.add(p);
        }
        return list;
    }

    private static List<MatchRecord> createMatches(List<Team> allTeams) {
        List<MatchRecord> list = new ArrayList<>();
        Random rand = new Random(999);
        String[] durations = {"15:30", "18:45", "12:20", "22:10", "14:55", "20:05", "16:40", "11:30", "25:00", "19:15"};

        for (int i = 0; i < 10; i++) {
            Team a = allTeams.get(rand.nextInt(allTeams.size()));
            Team b;
            do {
                b = allTeams.get(rand.nextInt(allTeams.size()));
            } while (b.getId().equals(a.getId()));

            int scoreA = rand.nextInt(5);
            int scoreB = rand.nextInt(5);

            MatchRecord match = new MatchRecord("M" + String.format("%02d", i + 1),
                    a, b, scoreA, scoreB, durations[i],
                    LocalDateTime.now().minusDays(rand.nextInt(60)),
                    MatchStatus.FINISHED);
            list.add(match);
        }
        return list;
    }
}
