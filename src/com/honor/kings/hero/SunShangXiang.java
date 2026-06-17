package com.honor.kings.hero;

import com.honor.kings.skill.Skill;

public class SunShangXiang extends Hero {
    public SunShangXiang() {
        super("孙尚香", "千金重弩", 2700, 360, 120, "吴");
    }

    @Override
    protected void initSkills() {
        addSkill(new Skill("龙鸣", 480, 4, "射出强力弩箭，贯穿路径上的敌人"));
        addSkill(new Skill("穿云箭", 400, 5, "快速连射三支弩箭，造成多次伤害"));
        addSkill(new Skill("红莲风暴", 800, 16, "召唤火箭雨覆盖大范围区域，造成灼烧伤害"));
    }
}
