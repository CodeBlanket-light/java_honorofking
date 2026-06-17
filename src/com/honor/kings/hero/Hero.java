package com.honor.kings.hero;

import com.honor.kings.skill.Skill;
import java.util.ArrayList;
import java.util.List;

public abstract class Hero {
    protected String name;
    protected String title;
    protected int hp;
    protected int maxHp;
    protected int attack;
    protected int defense;
    protected List<Skill> skills;
    protected String faction;

    public Hero(String name, String title, int hp, int attack, int defense, String faction) {
        this.name = name;
        this.title = title;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.faction = faction;
        this.skills = new ArrayList<>();
        initSkills();
    }

    protected abstract void initSkills();

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        hp = Math.max(0, hp - actualDamage);
        System.out.println(name + " 受到 " + actualDamage + " 点伤害（防御减伤" + defense + "），剩余血量：" + hp);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void attack(Hero target) {
        System.out.println(name + " 对 " + target.getName() + " 发起普通攻击，造成 " + attack + " 点伤害");
        target.takeDamage(attack);
    }

    public void useSkill(int index, Hero target) {
        if (index < 0 || index >= skills.size()) {
            System.out.println("技能索引无效");
            return;
        }
        Skill skill = skills.get(index);
        skill.use();
        target.takeDamage(skill.getDamage());
    }

    public void showInfo() {
        System.out.println("===== " + title + " " + name + " =====");
        System.out.println("阵营：" + faction);
        System.out.println("血量：" + hp + "/" + maxHp);
        System.out.println("攻击：" + attack + "  防御：" + defense);
        System.out.println("技能：");
        for (int i = 0; i < skills.size(); i++) {
            Skill s = skills.get(i);
            System.out.println("  " + (i + 1) + ". " + s.getName() + " - " + s.getDescription() + "（伤害：" + s.getDamage() + "）");
        }
    }

    public String getName() { return name; }
    public String getTitle() { return title; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getSkillCount() { return skills.size(); }
}
