package com.honor.kings.hero;

import com.honor.kings.skill.Skill;

public class CaoCao extends Hero {
    public CaoCao() {
        super("曹操", "枭雄", 3600, 270, 190, "魏");
    }

    @Override
    protected void initSkills() {
        addSkill(new Skill("枭雄剑", 420, 5, "挥动倚天剑，对前方敌人造成物理伤害"));
        addSkill(new Skill("横槊赋诗", 360, 7, "吟诗鼓舞士气，攻击周围所有敌人"));
        addSkill(new Skill("赤壁之怒", 780, 20, "引燃赤壁之火，对大范围敌人造成灼烧伤害"));
    }
}
