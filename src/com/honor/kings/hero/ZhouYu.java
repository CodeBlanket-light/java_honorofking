package com.honor.kings.hero;

import com.honor.kings.skill.Skill;

public class ZhouYu extends Hero {
    public ZhouYu() {
        super("周瑜", "大都督", 2900, 320, 140, "吴");
    }

    @Override
    protected void initSkills() {
        addSkill(new Skill("赤焰", 450, 5, "释放火焰弹，对目标造成法术伤害并灼烧"));
        addSkill(new Skill("东风", 380, 6, "召唤东风助火势，对路径上敌人造成法术伤害"));
        addSkill(new Skill("火烧赤壁", 780, 18, "引滔天大火覆盖战场，造成巨额法术伤害"));
    }
}
