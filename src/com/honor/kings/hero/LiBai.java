package com.honor.kings.hero;

import com.honor.kings.skill.Skill;

/**
 * 李白（青莲剑仙）英雄类
 * 演示：继承（extends Hero）、多态（initSkills 重写）
 * 使用三个技能：将进酒、神来之笔、青莲剑歌
 */
public class LiBai extends Hero {

    /** 构造器：以李白默认属性调用父类构造器 */
    public LiBai() {
        super("李白", "青莲剑仙", 3000, 320, 150, "唐");
    }

    /** 初始化李白的三个技能 */
    @Override
    protected void initSkills() {
        addSkill(new Skill("将进酒", 350, 5, "向指定方向突进两次，第三次返回原点"));
        addSkill(new Skill("神来之笔", 280, 6, "以自身为中心化剑为圈，减速敌人"));
        addSkill(new Skill("青莲剑歌", 750, 20, "化为剑气连续斩出五次，不可被选中"));
    }
}
