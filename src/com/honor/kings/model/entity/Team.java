package com.honor.kings.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Team 实体类：代表游戏中的队伍
 * 演示：关联（被 Player 单向关联）、文件I/O（Serializable）、封装
 */
public class Team implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String teamName;
    private int maxMembers;
    private int score;
    private LocalDateTime foundedTime;

    public Team() {}

    /** 构造器：初始化队伍的 ID、名称、最大人数、分数和创建时间 */
    public Team(String id, String teamName, int maxMembers, int score, LocalDateTime foundedTime) {
        this.id = id;
        this.teamName = teamName;
        this.maxMembers = maxMembers;
        this.score = score;
        this.foundedTime = foundedTime;
    }

    /** 判断队伍是否已满（占位方法，始终返回 false） */
    public boolean isFull() {
        return false;
    }

    /** 获取队伍平均等级（占位方法，始终返回 0） */
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
