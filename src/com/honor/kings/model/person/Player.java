package com.honor.kings.model.person;

import com.honor.kings.model.entity.Hero;
import com.honor.kings.model.entity.Team;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Player 类：继承自 Person，代表游戏玩家
 * 演示：继承（extends Person）、聚合（持有 List<Hero>）、集合（ArrayList）、
 *       封装（私有成员+getter）、关联（Team）
 */
public class Player extends Person {
    private String password;
    private int level;
    private int exp;
    private int totalMatches;
    private int winCount;
    private List<Hero> ownedHeroes;
    private Team currentTeam;

    public Player() {
        this.ownedHeroes = new ArrayList<>();
    }

    /** 构造器：不带密码版本，委托给带密码的构造器 */
    public Player(String id, String name, String email, LocalDateTime createTime,
                  int level, int totalMatches, int winCount) {
        this(id, name, email, createTime, level, totalMatches, winCount, null);
    }

    /** 构造器：完整初始化所有字段，包括密码 */
    public Player(String id, String name, String email, LocalDateTime createTime,
                  int level, int totalMatches, int winCount, String password) {
        super(id, name, email, createTime);
        this.level = level;
        this.exp = 0;
        this.totalMatches = totalMatches;
        this.winCount = winCount;
        this.password = password;
        this.ownedHeroes = new ArrayList<>();
    }

    /** 多态实现：返回 "Player" 角色标识 */
    @Override
    public String getRole() {
        return "Player";
    }

    /** 加入指定队伍，设置 currentTeam 并输出提示 */
    public void joinTeam(Team team) {
        if (team == null) {
            System.out.println("队伍不能为空");
            return;
        }
        // TODO: 待 Team 类定义后完善，调用 Team 相关方法
        this.currentTeam = team;
        System.out.println("玩家 [" + getName() + "] 加入了队伍 [" + team.getTeamName() + "]");
    }

    /** 离开当前队伍，清空 currentTeam 并输出提示 */
    public void leaveTeam() {
        if (currentTeam == null) {
            System.out.println("玩家 [" + getName() + "] 当前不在任何队伍中");
            return;
        }
        // TODO: 待 Team 类定义后完善，调用 Team 相关方法
        String teamName = currentTeam.getTeamName();
        this.currentTeam = null;
        System.out.println("玩家 [" + getName() + "] 离开了队伍 [" + teamName + "]");
    }

    /** 为玩家添加一个英雄到 ownedHeroes 列表 */
    public void addHero(Hero hero) {
        if (hero == null) {
            System.out.println("英雄不能为空");
            return;
        }
        ownedHeroes.add(hero);
        System.out.println("玩家 [" + getName() + "] 获得了英雄 [" + hero.getName() + "]");
    }

    /** 计算胜率（百分比），总场次为0时返回0.0 */
    public double getWinRate() {
        if (totalMatches == 0) {
            return 0.0;
        }
        return (double) winCount / totalMatches * 100;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public int getExp() { return exp; }
    public void setExp(int exp) { this.exp = exp; }

    /** 增加经验值并检查升级，升到下一级所需经验 = 当前等级 × 100 */
    public void addExp(int amount) {
        exp += amount;
        while (exp >= level * 100) {
            exp -= level * 100;
            level++;
            System.out.println("恭喜升级！当前等级：" + level);
        }
    }
    public int getTotalMatches() { return totalMatches; }
    public void setTotalMatches(int totalMatches) { this.totalMatches = totalMatches; }
    public int getWinCount() { return winCount; }
    public void setWinCount(int winCount) { this.winCount = winCount; }
    public List<Hero> getOwnedHeroes() { return ownedHeroes; }
    public void setOwnedHeroes(List<Hero> ownedHeroes) { this.ownedHeroes = ownedHeroes; }
    public Team getCurrentTeam() { return currentTeam; }
    public void setCurrentTeam(Team currentTeam) { this.currentTeam = currentTeam; }
}
