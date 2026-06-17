package com.honor.kings.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Hero 实体类：代表游戏中的英雄
 * 演示：聚合（持有 List<Equipment>）、集合（ArrayList）、枚举（HeroType）、
 *       文件I/O（Serializable）、封装
 */
public class Hero implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 枚举：英雄类型，WARRIOR（战士）/ MAGE（法师），用于装备推荐 */
    public enum HeroType {
        WARRIOR, MAGE
    }

    private String id;
    private String name;
    private String title;
    private int baseHp;
    private int baseAttack;
    private int baseDefense;
    private List<Equipment> equipmentList;
    private String faction;
    private int level;
    private HeroType heroType;

    public Hero() {
        this.equipmentList = new ArrayList<>();
    }

    /** 构造器：默认英雄类型为 WARRIOR */
    public Hero(String id, String name, String title, int baseHp, int baseAttack, int baseDefense, String faction, int level) {
        this(id, name, title, baseHp, baseAttack, baseDefense, faction, level, HeroType.WARRIOR);
    }

    /** 构造器：完整初始化所有字段，包括英雄类型 */
    public Hero(String id, String name, String title, int baseHp, int baseAttack, int baseDefense, String faction, int level, HeroType heroType) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.baseHp = baseHp;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.faction = faction;
        this.level = level;
        this.heroType = heroType;
        this.equipmentList = new ArrayList<>();
    }

    // equip：为英雄装备一件物品，添加到 equipmentList 中
    public void equip(Equipment eq) {
        equipmentList.add(eq);
    }

    // unequip：按装备 ID 从 equipmentList 中移除装备，返回是否成功移除
    // 集合：使用 removeIf + lambda 按条件移除
    public boolean unequip(String equipmentId) {
        return equipmentList.removeIf(eq -> eq.getId().equals(equipmentId));
    }

    // getTotalHp：计算总生命值 = 基础生命 + 所有装备的生命加成之和
    // 集合：使用 Stream API 的 mapToInt 汇总装备加成
    public int getTotalHp() {
        int bonus = equipmentList.stream().mapToInt(Equipment::getBonusHp).sum();
        return baseHp + bonus;
    }

    // getTotalAttack：计算总攻击力 = 基础攻击 + 所有装备的攻击加成之和
    public int getTotalAttack() {
        int bonus = equipmentList.stream().mapToInt(Equipment::getBonusAttack).sum();
        return baseAttack + bonus;
    }

    // getTotalDefense：计算总防御力 = 基础防御 + 所有装备的防御加成之和
    public int getTotalDefense() {
        int bonus = equipmentList.stream().mapToInt(Equipment::getBonusDefense).sum();
        return baseDefense + bonus;
    }

    public HeroType getHeroType() { return heroType; }
    public void setHeroType(HeroType heroType) { this.heroType = heroType; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getBaseHp() { return baseHp; }
    public void setBaseHp(int baseHp) { this.baseHp = baseHp; }
    public int getBaseAttack() { return baseAttack; }
    public void setBaseAttack(int baseAttack) { this.baseAttack = baseAttack; }
    public int getBaseDefense() { return baseDefense; }
    public void setBaseDefense(int baseDefense) { this.baseDefense = baseDefense; }
    public List<Equipment> getEquipmentList() { return equipmentList; }
    public void setEquipmentList(List<Equipment> equipmentList) { this.equipmentList = equipmentList; }
    public String getFaction() { return faction; }
    public void setFaction(String faction) { this.faction = faction; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
}
