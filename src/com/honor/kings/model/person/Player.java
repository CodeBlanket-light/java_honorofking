package com.honor.kings.model.person;

import com.honor.kings.model.entity.Hero;
import com.honor.kings.model.entity.Team;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Player：Person 的子类，代表游戏玩家（继承）
// 关联：聚合多个 Hero 对象（List<Hero>），单向关联 Team
// 集合：使用 List<Hero> 存储拥有的英雄
public class Player extends Person {
    private String password;
    private int level;
    private int totalMatches;
    private int winCount;
    private List<Hero> ownedHeroes;
    private Team currentTeam;

    public Player() {
        this.ownedHeroes = new ArrayList<>();
    }

    public Player(String id, String name, String email, LocalDateTime createTime,
                  int level, int totalMatches, int winCount) {
        this(id, name, email, createTime, level, totalMatches, winCount, null);
    }

    public Player(String id, String name, String email, LocalDateTime createTime,
                  int level, int totalMatches, int winCount, String password) {
        super(id, name, email, createTime);
        this.level = level;
        this.totalMatches = totalMatches;
        this.winCount = winCount;
        this.password = password;
        this.ownedHeroes = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "Player";
    }

    public void joinTeam(Team team) {
        if (team == null) {
            System.out.println("队伍不能为空");
            return;
        }
        // TODO: 待 Team 类定义后完善，调用 Team 相关方法
        this.currentTeam = team;
        System.out.println("玩家 [" + getName() + "] 加入了队伍 [" + team.getTeamName() + "]");
    }

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

    public void addHero(Hero hero) {
        if (hero == null) {
            System.out.println("英雄不能为空");
            return;
        }
        ownedHeroes.add(hero);
        System.out.println("玩家 [" + getName() + "] 获得了英雄 [" + hero.getName() + "]");
    }

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
    public int getTotalMatches() { return totalMatches; }
    public void setTotalMatches(int totalMatches) { this.totalMatches = totalMatches; }
    public int getWinCount() { return winCount; }
    public void setWinCount(int winCount) { this.winCount = winCount; }
    public List<Hero> getOwnedHeroes() { return ownedHeroes; }
    public void setOwnedHeroes(List<Hero> ownedHeroes) { this.ownedHeroes = ownedHeroes; }
    public Team getCurrentTeam() { return currentTeam; }
    public void setCurrentTeam(Team currentTeam) { this.currentTeam = currentTeam; }
}
