package com.honor.kings.hero;

import com.honor.kings.skill.Skill;

public class HuaMuLan extends Hero {
    public HuaMuLan() {
        super("花木兰", "传说之刃", 3300, 290, 180, "魏");
    }

    @Override
    protected void initSkills() {
        addSkill(new Skill("空裂斩", 400, 5, "双剑连斩，对前方敌人造成物理伤害"));
        addSkill(new Skill("旋舞之刃", 360, 7, "旋转双刃攻击周围敌人，造成范围伤害"));
        addSkill(new Skill("巾帼不让", 750, 18, "爆发出全部力量，对大范围敌人造成致命打击"));
    }
}
