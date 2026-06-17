package com.honor.kings.hero;

import com.honor.kings.skill.Skill;
import java.util.ArrayList;
import java.util.List;

/**
 * Hero 抽象类（战斗体系）：代表可战斗的英雄角色
 * 演示：继承（被 ZhaoYun、LiBai、DiaoChan 继承）、抽象方法（initSkills）、
 *       聚合（持有 List<Skill>）、多态（attack/useSkill 基于技能多态）
 */
public abstract class Hero {
    protected String name;
    protected String title;
    protected int hp;
    protected int maxHp;
    protected int attack;
    protected int defense;
    protected List<Skill> skills;
    protected String faction;

    /** 构造器：初始化英雄属性并调用抽象方法 initSkills() 注册技能 */
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

    /** 抽象方法：子类在此注册自己的技能列表 */
    protected abstract void initSkills();

    /** 添加一个技能到技能列表 */
    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    /** 受到伤害：减去防御值后扣血（最小伤害为1），血量不低于0 */
    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        hp = Math.max(0, hp - actualDamage);
        System.out.println(name + " 受到 " + actualDamage + " 点伤害（防御减伤" + defense + "），剩余血量：" + hp);
    }

    /** 判断英雄是否存活（血量 > 0） */
    public boolean isAlive() {
        return hp > 0;
    }

    /** 普通攻击：对目标造成攻击力数值的伤害 */
    public void attack(Hero target) {
        System.out.println(name + " 对 " + target.getName() + " 发起普通攻击，造成 " + attack + " 点伤害");
        target.takeDamage(attack);
    }

    /** 使用指定索引的技能攻击目标：调用技能 use() 后造成技能伤害 */
    public void useSkill(int index, Hero target) {
        if (index < 0 || index >= skills.size()) {
            System.out.println("技能索引无效");
            return;
        }
        Skill skill = skills.get(index);
        skill.use();
        target.takeDamage(skill.getDamage());
    }

    /** 计算对目标的实际伤害（攻击力 - 防御力，最低1），不含技能加成 */
    public int calculateDamage(Hero target) {
        return Math.max(1, attack - target.getDefense());
    }

    /** 打印英雄的完整信息（称号、阵营、血量、攻击、防御、技能列表） */
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
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSkillCount() { return skills.size(); }
    public List<Skill> getSkills() { return skills; }
}
