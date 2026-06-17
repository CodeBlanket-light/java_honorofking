package com.honor.kings.hero;

import com.honor.kings.skill.Skill;

public class ZhaoYun extends Hero {

    public ZhaoYun() {
        super("赵云", "常山赵子龙", 3500, 280, 200, "蜀");
    }

    @Override
    protected void initSkills() {
        addSkill(new Skill("惊雷之龙", 400, 5, "持枪向目标冲锋，造成物理伤害"));
        addSkill(new Skill("破云之龙", 320, 6, "快速刺出三枪，造成范围伤害"));
        addSkill(new Skill("天翔之龙", 600, 18, "跃向空中挥出强力一击，击飞目标"));
    }
}
