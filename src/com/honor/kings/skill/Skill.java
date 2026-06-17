package com.honor.kings.skill;

public class Skill {
    private String name;
    private int damage;
    private int cooldown;
    private String description;

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

    public void use() {
        System.out.println("释放技能 [" + name + "]：" + description + "，造成 " + damage + " 点伤害");
    }
}
