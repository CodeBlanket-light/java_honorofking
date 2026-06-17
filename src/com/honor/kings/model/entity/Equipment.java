package com.honor.kings.model.entity;

import java.io.Serializable;

// Equipment：装备实体类
// 枚举：内部枚举 EquipmentType 定义装备类型（WEAPON/ARMOR/BOOTS/ACCESSORY）
// 聚合：被 Hero 通过 List<Equipment> 聚合引用
// 文件I/O：实现 Serializable 以便序列化
public class Equipment implements Serializable {
    private static final long serialVersionUID = 1L;
    public enum EquipmentType {
        WEAPON, ARMOR, BOOTS, ACCESSORY
    }

    private String id;
    private String name;
    private EquipmentType type;
    private int bonusHp;
    private int bonusAttack;
    private int bonusDefense;
    private int price;

    public Equipment() {}

    public Equipment(String id, String name, EquipmentType type, int bonusHp, int bonusAttack, int bonusDefense, int price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.bonusHp = bonusHp;
        this.bonusAttack = bonusAttack;
        this.bonusDefense = bonusDefense;
        this.price = price;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public EquipmentType getType() { return type; }
    public void setType(EquipmentType type) { this.type = type; }
    public int getBonusHp() { return bonusHp; }
    public void setBonusHp(int bonusHp) { this.bonusHp = bonusHp; }
    public int getBonusAttack() { return bonusAttack; }
    public void setBonusAttack(int bonusAttack) { this.bonusAttack = bonusAttack; }
    public int getBonusDefense() { return bonusDefense; }
    public void setBonusDefense(int bonusDefense) { this.bonusDefense = bonusDefense; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getDescription() {
        return name + " (" + type + ") +HP:" + bonusHp + " +ATK:" + bonusAttack + " +DEF:" + bonusDefense;
    }
}
