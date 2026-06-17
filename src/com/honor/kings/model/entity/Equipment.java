package com.honor.kings.model.entity;

import java.io.Serializable;

/**
 * Equipment 实体类：代表游戏中的装备
 * 演示：枚举（EquipmentCategory 定义 TANK/MAGIC 两类）、
 *       聚合（被 Hero 的 List<Equipment> 引用）、文件I/O（Serializable）
 */
public class Equipment implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 枚举：装备类别，TANK（物理防御装）/ MAGIC（法术输出装） */
    public enum EquipmentCategory {
        TANK, MAGIC
    }

    private String id;
    private String name;
    private EquipmentCategory category;
    private int bonusHp;
    private int bonusAttack;
    private int bonusDefense;
    private int price;

    public Equipment() {}

    /** 构造器：初始化装备的所有属性（ID、名称、类别、属性加成、价格） */
    public Equipment(String id, String name, EquipmentCategory category, int bonusHp, int bonusAttack, int bonusDefense, int price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.bonusHp = bonusHp;
        this.bonusAttack = bonusAttack;
        this.bonusDefense = bonusDefense;
        this.price = price;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public EquipmentCategory getCategory() { return category; }
    public void setCategory(EquipmentCategory category) { this.category = category; }
    public int getBonusHp() { return bonusHp; }
    public void setBonusHp(int bonusHp) { this.bonusHp = bonusHp; }
    public int getBonusAttack() { return bonusAttack; }
    public void setBonusAttack(int bonusAttack) { this.bonusAttack = bonusAttack; }
    public int getBonusDefense() { return bonusDefense; }
    public void setBonusDefense(int bonusDefense) { this.bonusDefense = bonusDefense; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    // getDescription：返回装备的文本描述，含名称、类别、属性加成
    public String getDescription() {
        return name + " (" + category + ") +HP:" + bonusHp + " +ATK:" + bonusAttack + " +DEF:" + bonusDefense;
    }
}
