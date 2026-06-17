package com.honor.kings.hero;

import com.honor.kings.skill.Skill;

public class GuanYu extends Hero {
    public GuanYu() {
        super("关羽", "武圣", 3800, 260, 220, "蜀");
    }

    @Override
    protected void initSkills() {
        addSkill(new Skill("单刀赴会", 500, 7, "青龙偃月刀横扫前方，造成范围物理伤害"));
        addSkill(new Skill("青龙偃月", 380, 8, "蓄力挥刀，对路径上敌人造成高额伤害"));
        addSkill(new Skill("刀锋铁骑", 800, 20, "骑乘冲锋，击退并眩晕路径上的敌人"));
    }
}
