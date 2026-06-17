package com.honor.kings.battle;

import com.honor.kings.hero.Hero;
import java.util.Random;

public class Battle {
    private Hero hero1;
    private Hero hero2;
    private Random random;
    private String winner;

    public Battle(Hero hero1, Hero hero2) {
        this.hero1 = hero1;
        this.hero2 = hero2;
        this.random = new Random();
    }

    public void start() {
        System.out.println("\n========== 战斗开始 ==========");
        System.out.println(hero1.getTitle() + " " + hero1.getName() + " VS " +
                           hero2.getTitle() + " " + hero2.getName());
        System.out.println("==============================\n");

        int round = 1;
        while (hero1.isAlive() && hero2.isAlive()) {
            System.out.println("--- 第 " + round + " 回合 ---");

            Hero attacker = (round % 2 == 1) ? hero1 : hero2;
            Hero defender = (round % 2 == 1) ? hero2 : hero1;

            if (random.nextBoolean()) {
                int skillCount = attacker.getSkillCount();
                int skillIndex = random.nextInt(skillCount);
                attacker.useSkill(skillIndex, defender);
            } else {
                attacker.attack(defender);
            }

            if (!defender.isAlive()) {
                winner = attacker.getName();
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

    public String getWinner() {
        return winner;
    }
}
