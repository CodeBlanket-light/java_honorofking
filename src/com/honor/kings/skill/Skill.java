package com.honor.kings.skill;

/**
 * Skill 技能类：代表英雄的一个主动技能
 * 演示：封装（私有属性 name/damage/cooldown/description + getter）
 * 职责：存储技能名称、伤害值、冷却时间和描述信息，提供 use() 方法
 */
public class Skill {
    private String name;
    private int damage;
    private int cooldown;
    private String description;

    /** 构造器：初始化技能的名称、伤害、冷却和描述 */
    public Skill(String name, int damage, int cooldown, String description) {
        this.name = name;
        this.damage = damage;
        this.cooldown = cooldown;
        this.description = description;
    }

    public String getName() { return name; }
    public int getDamage() { return damage; }
    public int getCooldown() { return cooldown; }
    public String getDescription() { return description; }

    /** 释放技能：打印技能使用信息 */
    public void use() {
        System.out.println("释放技能 [" + name + "]：" + description + "，造成 " + damage + " 点伤害");
    }
}
