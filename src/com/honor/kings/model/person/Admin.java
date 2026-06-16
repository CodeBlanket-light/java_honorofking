package com.honor.kings.model.person;

import com.honor.kings.model.entity.Hero;
import com.honor.kings.model.entity.MatchRecord;
import com.honor.kings.model.person.Player;

import java.time.LocalDateTime;

public class Admin extends Person {
    private String permissionLevel;
    private String department;

    public Admin() {
    }

    public Admin(String id, String name, String email, LocalDateTime createTime,
                 String permissionLevel, String department) {
        super(id, name, email, createTime);
        this.permissionLevel = permissionLevel;
        this.department = department;
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    public void approveMatch(MatchRecord match) {
        // TODO: 待 MatchRecord 类定义后完善审批逻辑
        System.out.println("管理员 [" + getName() + "] 审批了比赛 [ID: " + match.getId() + "]");
    }

    public void banPlayer(Player player) {
        // TODO: 待 Player 相关功能完善后实现封禁逻辑
        System.out.println("管理员 [" + getName() + "] 封禁了玩家 [" + player.getName() + "]");
    }

    public void adjustHeroStats(Hero hero) {
        // TODO: 待 Hero 类定义后完善调整属性逻辑
        System.out.println("管理员 [" + getName() + "] 调整了英雄 [" + hero.getName() + "] 的属性");
    }

    public String getPermissionLevel() { return permissionLevel; }
    public void setPermissionLevel(String permissionLevel) { this.permissionLevel = permissionLevel; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
