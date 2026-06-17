package com.honor.kings.util;

import com.honor.kings.model.entity.*;
import com.honor.kings.model.person.Player;

import java.io.Serializable;
import java.util.List;

/**
 * GameData：数据封装对象，用于序列化/反序列化全部游戏数据
 * 演示：文件I/O（作为 ObjectOutputStream 的写入对象，实现 Serializable）
 * 内容：玩家、英雄、装备、队伍、比赛记录、战斗记录六类数据
 */
public class GameData implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Player> players;
    private List<Hero> heroes;
    private List<Equipment> equipment;
    private List<Team> teams;
    private List<MatchRecord> matches;
    private List<BattleRecord> battleRecords;

    // 构造器：传入5个列表（不含战斗记录），战斗记录初始化为空列表
    public GameData(List<Player> players, List<Hero> heroes, List<Equipment> equipment,
                    List<Team> teams, List<MatchRecord> matches) {
        this(players, heroes, equipment, teams, matches, new java.util.ArrayList<>());
    }

    public GameData(List<Player> players, List<Hero> heroes, List<Equipment> equipment,
                    List<Team> teams, List<MatchRecord> matches, List<BattleRecord> battleRecords) {
        this.players = players;
        this.heroes = heroes;
        this.equipment = equipment;
        this.teams = teams;
        this.matches = matches;
        this.battleRecords = battleRecords;
    }

    public List<Player> getPlayers() { return players; }
    public List<Hero> getHeroes() { return heroes; }
    public List<Equipment> getEquipment() { return equipment; }
    public List<Team> getTeams() { return teams; }
    public List<MatchRecord> getMatches() { return matches; }
    public List<BattleRecord> getBattleRecords() { return battleRecords; }
}
