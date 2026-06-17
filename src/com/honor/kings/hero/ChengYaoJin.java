package com.honor.kings.hero;

import com.honor.kings.skill.Skill;

public class ChengYaoJin extends Hero {
    public ChengYaoJin() {
        super("程咬金", "热血战斧", 4500, 230, 250, "唐");
    }

    @Override
    protected void initSkills() {
        addSkill(new Skill("爆裂双斧", 400, 6, "挥舞双斧旋转攻击，对周围敌人造成物理伤害"));
        addSkill(new Skill("激怒", 350, 7, "怒火中烧，用力劈砍前方敌人造成高额伤害"));
        addSkill(new Skill("正义冲锋", 700, 20, "以热血之力向前冲锋，击飞路径上的敌人"));
    }
}
