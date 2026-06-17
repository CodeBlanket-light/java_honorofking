package com.honor.kings.util;

import com.honor.kings.model.entity.Equipment;
import com.honor.kings.model.entity.Hero;
import com.honor.kings.model.entity.MatchRecord;
import com.honor.kings.model.entity.Team;
import com.honor.kings.model.person.Player;

import java.io.Serializable;
import java.util.List;

// 文件I/O 封装对象：用于序列化/反序列化所有游戏数据
// Serializable 接口标记该类可被 ObjectOutputStream 写入文件
public class GameData implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Player> players;
    private List<Hero> heroes;
    private List<Equipment> equipment;
    private List<Team> teams;
    private List<MatchRecord> matches;

    public GameData(List<Player> players, List<Hero> heroes, List<Equipment> equipment,
                    List<Team> teams, List<MatchRecord> matches) {
        this.players = players;
        this.heroes = heroes;
        this.equipment = equipment;
        this.teams = teams;
        this.matches = matches;
    }

    public List<Player> getPlayers() { return players; }
    public List<Hero> getHeroes() { return heroes; }
    public List<Equipment> getEquipment() { return equipment; }
    public List<Team> getTeams() { return teams; }
    public List<MatchRecord> getMatches() { return matches; }
}
