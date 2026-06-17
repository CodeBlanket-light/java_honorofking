package com.honor.kings.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Hero：英雄实体类（非抽象），代表可玩英雄
// 聚合：持有 List<Equipment>，一个英雄可装备多件装备（聚合关系）
// 集合：使用 List<Equipment> 存储装备列表
// 文件I/O：实现 Serializable 以便序列化
// 枚举：HeroType 定义英雄类型（WARRIOR战士/MAGE法师），用于装备推荐
public class Hero implements Serializable {
    private static final long serialVersionUID = 1L;

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

    public Hero(String id, String name, String title, int baseHp, int baseAttack, int baseDefense, String faction, int level) {
        this(id, name, title, baseHp, baseAttack, baseDefense, faction, level, HeroType.WARRIOR);
    }

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

    public void equip(Equipment eq) {
        equipmentList.add(eq);
    }

    public boolean unequip(String equipmentId) {
        return equipmentList.removeIf(eq -> eq.getId().equals(equipmentId));
    }

    public int getTotalHp() {
        int bonus = equipmentList.stream().mapToInt(Equipment::getBonusHp).sum();
        return baseHp + bonus;
    }

    public int getTotalAttack() {
        int bonus = equipmentList.stream().mapToInt(Equipment::getBonusAttack).sum();
        return baseAttack + bonus;
    }

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
