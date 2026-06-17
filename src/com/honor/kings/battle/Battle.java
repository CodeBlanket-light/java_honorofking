package com.honor.kings.battle;

import com.honor.kings.hero.Hero;
import com.honor.kings.skill.Skill;
import java.util.List;
import java.util.Random;

/**
 * Battle：战斗引擎类
 * 演示：聚合（持有两个 Hero 对象）、封装
 * 职责：管理两个英雄之间的回合制对战，判定胜负
 */
public class Battle {
    private Hero hero1;
    private Hero hero2;
    private Random random;
    private String winner;

    /** 构造器：传入两个对战英雄 */
    public Battle(Hero hero1, Hero hero2) {
        this.hero1 = hero1;
        this.hero2 = hero2;
        this.random = new Random();
    }

    /** 开始回合制战斗：双方轮流攻击/释放技能，血量归零或超过30回合时结束 */
    public void start() {
        System.out.println("\n========== 战斗开始 ==========");
        System.out.println(hero1.getTitle() + " " + hero1.getName() + " VS " +
                           hero2.getTitle() + " " + hero2.getName());
        System.out.println("==============================\n");

        int round = 1;
        while (hero1.isAlive() && hero2.isAlive()) {
            System.out.println("--- 第 " + round + " 回合 ---");

            if (round % 2 == 1) {
                System.out.println("【玩家回合】 " + hero1.getName() + " 行动:");
                playerTurn(hero1, hero2);
            } else {
                System.out.println("【敌方回合】 " + hero2.getName() + " 行动:");
                aiTurn(hero2, hero1);
            }

            if (!hero2.isAlive()) {
                winner = hero1.getName();
                System.out.println("\n========== " + winner + " 获胜！==========");
                return;
            }
            if (!hero1.isAlive()) {
                winner = hero2.getName();
                System.out.println("\n========== " + winner + " 获胜！==========");
                return;
            }

            round++;
            if (round > 30) {
                winner = "平局";
                System.out.println("\n========== 平局！战斗超过30回合 ==========");
                return;
            }
        }
    }

    /** 玩家回合：随机使用技能或普通攻击 */
    private void playerTurn(Hero attacker, Hero defender) {
        List<Skill> skills = attacker.getSkills();
        if (skills == null || skills.isEmpty()) {
            attacker.attack(defender);
            return;
        }
        if (random.nextBoolean()) {
            int idx = random.nextInt(skills.size());
            attacker.useSkill(idx, defender);
        } else {
            attacker.attack(defender);
        }
    }

    /** 敌方 AI 回合：随机数 r=0 普攻，r=1~3 释放对应技能 */
    private void aiTurn(Hero attacker, Hero defender) {
        List<Skill> skills = attacker.getSkills();
        if (skills == null || skills.isEmpty()) {
            attacker.attack(defender);
            return;
        }
        int r = random.nextInt(skills.size() + 1);
        if (r == 0) {
            attacker.attack(defender);
        } else {
            int skillIndex = r - 1;
            if (skillIndex < skills.size()) {
                Skill s = skills.get(skillIndex);
                System.out.print(attacker.getName() + " 释放技能 [" + s.getName() + "]");
                attacker.useSkill(skillIndex, defender);
            } else {
                attacker.attack(defender);
            }
        }
    }

    /** 返回获胜者名称，平局返回 "平局" */
    public String getWinner() {
        return winner;
    }
}
