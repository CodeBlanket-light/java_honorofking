package com.honor.kings.hero;

import com.honor.kings.skill.Skill;

public class ZhuGeLiang extends Hero {
    public ZhuGeLiang() {
        super("诸葛亮", "卧龙", 2900, 350, 140, "蜀");
    }

    @Override
    protected void initSkills() {
        addSkill(new Skill("东风破", 450, 4, "召唤东风攻击目标，造成法术伤害"));
        addSkill(new Skill("空城计", 350, 6, "释放八卦法阵，减速并攻击阵内敌人"));
        addSkill(new Skill("卧龙吟", 700, 16, "召唤陨石从天而降，造成大范围法术伤害"));
    }
}
