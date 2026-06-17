package com.honor.kings.hero;

import com.honor.kings.skill.Skill;

public class DaJi extends Hero {
    public DaJi() {
        super("妲己", "魅惑之狐", 2600, 370, 110, "商");
    }

    @Override
    protected void initSkills() {
        addSkill(new Skill("魅惑", 480, 4, "释放魅惑之力，迷惑目标并造成法术伤害"));
        addSkill(new Skill("狐火", 400, 5, "召唤狐火攻击敌人，造成灼烧法术伤害"));
        addSkill(new Skill("万狐朝宗", 820, 18, "召唤万狐之力爆发，对目标造成巨额法术伤害"));
    }
}
