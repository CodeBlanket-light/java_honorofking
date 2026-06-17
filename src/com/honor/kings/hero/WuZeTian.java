package com.honor.kings.hero;

import com.honor.kings.skill.Skill;

public class WuZeTian extends Hero {
    public WuZeTian() {
        super("武则天", "女帝", 2800, 330, 130, "唐");
    }

    @Override
    protected void initSkills() {
        addSkill(new Skill("帝威", 420, 4, "释放帝王威压，对前方敌人造成法术伤害"));
        addSkill(new Skill("凤仪", 350, 6, "召唤凤凰之力攻击目标，造成灼烧伤害"));
        addSkill(new Skill("天罚", 750, 18, "以天子之名降下天雷，对大范围敌人造成毁灭性法术伤害"));
    }
}
