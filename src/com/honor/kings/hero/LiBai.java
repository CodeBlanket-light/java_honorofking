package com.honor.kings.hero;

import com.honor.kings.skill.Skill;

public class LiBai extends Hero {

    public LiBai() {
        super("李白", "青莲剑仙", 3000, 320, 150, "唐");
    }

    @Override
    protected void initSkills() {
        addSkill(new Skill("将进酒", 350, 5, "向指定方向突进两次，第三次返回原点"));
        addSkill(new Skill("神来之笔", 280, 6, "以自身为中心化剑为圈，减速敌人"));
        addSkill(new Skill("青莲剑歌", 750, 20, "化为剑气连续斩出五次，不可被选中"));
    }
}
