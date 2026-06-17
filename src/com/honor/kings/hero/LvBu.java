package com.honor.kings.hero;

import com.honor.kings.skill.Skill;

public class LvBu extends Hero {
    public LvBu() {
        super("吕布", "战神", 4000, 250, 240, "东汉");
    }

    @Override
    protected void initSkills() {
        addSkill(new Skill("方天画斩", 500, 6, "挥舞方天画戟横扫，造成范围物理伤害"));
        addSkill(new Skill("赤兔冲锋", 400, 8, "骑赤兔马向前冲锋，击退路径上的敌人"));
        addSkill(new Skill("魔神降临", 900, 22, "爆发魔神之力，对周围敌人造成毁灭性打击"));
    }
}
