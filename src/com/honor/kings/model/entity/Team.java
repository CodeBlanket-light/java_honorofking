package com.honor.kings.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

// Team：队伍实体类
// 关联：Player 单向关联 Team（Player 持有 currentTeam，Team 不持有成员列表）
// 成员查询：通过 PlayerService 按 teamId 查询，避免双向一致性问题
// 文件I/O：实现 Serializable 以便序列化
public class Team implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String teamName;
    private int maxMembers;
    private int score;
    private LocalDateTime foundedTime;

    public Team() {}

    public Team(String id, String teamName, int maxMembers, int score, LocalDateTime foundedTime) {
        this.id = id;
        this.teamName = teamName;
        this.maxMembers = maxMembers;
        this.score = score;
        this.foundedTime = foundedTime;
    }

    public boolean isFull() {
        return false;
    }

    public int getAvgLevel() {
        return 0;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }
    public int getMaxMembers() { return maxMembers; }
    public void setMaxMembers(int maxMembers) { this.maxMembers = maxMembers; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public LocalDateTime getFoundedTime() { return foundedTime; }
    public void setFoundedTime(LocalDateTime foundedTime) { this.foundedTime = foundedTime; }
}
