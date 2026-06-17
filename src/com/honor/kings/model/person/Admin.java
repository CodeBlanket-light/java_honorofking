package com.honor.kings.model.person;

import com.honor.kings.model.entity.Hero;
import com.honor.kings.model.entity.MatchRecord;
import com.honor.kings.model.person.Player;

import java.time.LocalDateTime;

/**
 * Admin 类：继承自 Person，代表系统管理员
 * 演示：继承（extends Person）、多态（getRole() 返回 "Admin"）
 * 职责：拥有数据管理权限（审批比赛、封禁玩家、调整英雄属性）
 */
public class Admin extends Person {
    private String permissionLevel;
    private String department;

    public Admin() {
    }

    /** 构造器：初始化管理员信息，含权限等级和所属部门 */
    public Admin(String id, String name, String email, LocalDateTime createTime,
                 String permissionLevel, String department) {
        super(id, name, email, createTime);
        this.permissionLevel = permissionLevel;
        this.department = department;
    }

    /** 多态实现：返回 "Admin" 角色标识 */
    @Override
    public String getRole() {
        return "Admin";
    }

    /** 审批比赛记录（预留方法，待完善） */
    public void approveMatch(MatchRecord match) {
        // TODO: 待 MatchRecord 类定义后完善审批逻辑
        System.out.println("管理员 [" + getName() + "] 审批了比赛 [ID: " + match.getId() + "]");
    }

    /** 封禁玩家（预留方法，待完善） */
    public void banPlayer(Player player) {
        // TODO: 待 Player 相关功能完善后实现封禁逻辑
        System.out.println("管理员 [" + getName() + "] 封禁了玩家 [" + player.getName() + "]");
    }

    /** 调整英雄属性（预留方法，待完善） */
    public void adjustHeroStats(Hero hero) {
        // TODO: 待 Hero 类定义后完善调整属性逻辑
        System.out.println("管理员 [" + getName() + "] 调整了英雄 [" + hero.getName() + "] 的属性");
    }

    public String getPermissionLevel() { return permissionLevel; }
    public void setPermissionLevel(String permissionLevel) { this.permissionLevel = permissionLevel; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
