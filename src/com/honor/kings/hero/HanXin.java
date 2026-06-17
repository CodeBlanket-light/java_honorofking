package com.honor.kings.hero;

import com.honor.kings.skill.Skill;

public class HanXin extends Hero {
    public HanXin() {
        super("韩信", "国士无双", 3100, 300, 160, "汉");
    }

    @Override
    protected void initSkills() {
        addSkill(new Skill("背水一战", 420, 6, "向指定方向突进，刺击路径上的敌人"));
        addSkill(new Skill("暗度陈仓", 350, 7, "长枪横扫，对周围敌人造成物理伤害"));
        addSkill(new Skill("国士无双", 650, 20, "跃起后向地面刺出致命一击，击飞敌人"));
    }
}
