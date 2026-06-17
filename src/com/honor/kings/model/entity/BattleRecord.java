package com.honor.kings.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * BattleRecord 实体类：记录战斗模式中的单场战斗详情
 * 演示：文件I/O（Serializable）、集合（Set 去重后转 List）
 * 用途：展示对战历史、统计装备出场率
 */
public class BattleRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private String time;              // 战斗时间，格式 yyyy-MM-dd HH:mm
    private String hero1Name;         // 英雄1名称
    private String hero2Name;         // 英雄2名称
    private List<String> hero1Equipment;  // 英雄1的装备名称列表
    private List<String> hero2Equipment;  // 英雄2的装备名称列表
    private String result;            // 结果："英雄名" 表示该英雄获胜，"平局" 表示平局

    // 构造器：传入所有字段初始化一条战斗记录
    public BattleRecord(String time, String hero1Name, String hero2Name,
                        List<String> hero1Equipment, List<String> hero2Equipment, String result) {
        this.time = time;
        this.hero1Name = hero1Name;
        this.hero2Name = hero2Name;
        this.hero1Equipment = hero1Equipment;
        this.hero2Equipment = hero2Equipment;
        this.result = result;
    }

    public String getTime() { return time; }
    public String getHero1Name() { return hero1Name; }
    public String getHero2Name() { return hero2Name; }
    public List<String> getHero1Equipment() { return hero1Equipment; }
    public List<String> getHero2Equipment() { return hero2Equipment; }
    public String getResult() { return result; }

    // getAllEquipmentNames：获取本场战斗涉及的所有装备名称（去重）
    // 集合：使用 Set 自动去重，再转为 List 返回
    public List<String> getAllEquipmentNames() {
        java.util.Set<String> all = new java.util.HashSet<>();
        if (hero1Equipment != null) all.addAll(hero1Equipment);
        if (hero2Equipment != null) all.addAll(hero2Equipment);
        return new java.util.ArrayList<>(all);
    }
}
