package com.honor.kings.hero;

import com.honor.kings.skill.Skill;

public class DiaoChan extends Hero {

    public DiaoChan() {
        super("貂蝉", "绝世舞姬", 2800, 340, 130, "东汉");
    }

    @Override
    protected void initSkills() {
        addSkill(new Skill("落·红雨", 300, 5, "向指定方向丢出花球，对路径敌人造成伤害"));
        addSkill(new Skill("缘·心结", 240, 6, "向目标释放连环爆炸，回复自身生命"));
        addSkill(new Skill("绽·风华", 700, 22, "绽放风华形成法阵，对范围内敌人造成持续伤害"));
    }
}
