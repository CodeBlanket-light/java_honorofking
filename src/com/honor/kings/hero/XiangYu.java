package com.honor.kings.hero;

import com.honor.kings.skill.Skill;

public class XiangYu extends Hero {
    public XiangYu() {
        super("项羽", "霸王", 4200, 240, 260, "楚");
    }

    @Override
    protected void initSkills() {
        addSkill(new Skill("霸王举鼎", 480, 7, "巨力举鼎砸向地面，对周围敌人造成物理伤害"));
        addSkill(new Skill("破釜沉舟", 400, 8, "以破釜沉舟之势冲锋，击退路径上的敌人"));
        addSkill(new Skill("四面楚歌", 850, 22, "爆发霸王之力，对大范围敌人造成毁灭性打击"));
    }
}
