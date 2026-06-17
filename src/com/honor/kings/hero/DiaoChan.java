package com.honor.kings.hero;

import com.honor.kings.skill.Skill;

/**
 * 貂蝉（绝世舞姬）英雄类
 * 演示：继承（extends Hero）、多态（initSkills 重写）
 * 使用三个技能：落·红雨、缘·心结、绽·风华
 */
public class DiaoChan extends Hero {

    /** 构造器：以貂蝉默认属性调用父类构造器 */
    public DiaoChan() {
        super("貂蝉", "绝世舞姬", 2800, 340, 130, "东汉");
    }

    /** 初始化貂蝉的三个技能 */
    @Override
    protected void initSkills() {
        addSkill(new Skill("落·红雨", 300, 5, "向指定方向丢出花球，对路径敌人造成伤害"));
        addSkill(new Skill("缘·心结", 240, 6, "向目标释放连环爆炸，回复自身生命"));
        addSkill(new Skill("绽·风华", 700, 22, "绽放风华形成法阵，对范围内敌人造成持续伤害"));
    }
}
