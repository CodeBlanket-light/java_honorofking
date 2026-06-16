package com.honor.kings.model.entity;

import java.time.LocalDateTime;

public class Team {
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
