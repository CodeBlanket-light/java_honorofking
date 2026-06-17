package com.honor.kings.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * MatchRecord 实体类：代表一场比赛记录
 * 演示：关联（持有两个 Team 对象）、枚举（MatchStatus）、
 *       文件I/O（Serializable）
 */
public class MatchRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 枚举：比赛状态 — SCHEDULED（已排期）/ IN_PROGRESS（进行中）/ FINISHED（已结束）/ CANCELLED（已取消） */
    public enum MatchStatus {
        SCHEDULED, IN_PROGRESS, FINISHED, CANCELLED
    }

    private String id;
    private Team teamA;
    private Team teamB;
    private int scoreA;
    private int scoreB;
    private Team winner;
    private String duration;
    private LocalDateTime matchTime;
    private MatchStatus status;

    public MatchRecord() {}

    /** 构造器：初始化比赛记录并自动调用 determineWinner() 确定胜者 */
    public MatchRecord(String id, Team teamA, Team teamB, int scoreA, int scoreB, String duration, LocalDateTime matchTime, MatchStatus status) {
        this.id = id;
        this.teamA = teamA;
        this.teamB = teamB;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.duration = duration;
        this.matchTime = matchTime;
        this.status = status;
        determineWinner();
    }

    /** 根据双方比分决定胜者：比分高的一方获胜，平局时 winner 设为 null */
    public void determineWinner() {
        if (scoreA > scoreB) {
            winner = teamA;
        } else if (scoreB > scoreA) {
            winner = teamB;
        } else {
            winner = null;
        }
    }

    /** 生成比赛摘要字符串，格式如 "蜀 VS 唐, 3:2, 蜀 wins" */
    public String getMatchSummary() {
        String teamAName = (teamA != null) ? teamA.getTeamName() : "Unknown";
        String teamBName = (teamB != null) ? teamB.getTeamName() : "Unknown";
        String result;
        if (winner != null) {
            result = winner.getTeamName() + " wins";
        } else {
            result = "Draw";
        }
        return teamAName + " vs " + teamBName + ", " + scoreA + ":" + scoreB + ", " + result;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Team getTeamA() { return teamA; }
    public void setTeamA(Team teamA) { this.teamA = teamA; }
    public Team getTeamB() { return teamB; }
    public void setTeamB(Team teamB) { this.teamB = teamB; }
    public int getScoreA() { return scoreA; }
    public void setScoreA(int scoreA) { this.scoreA = scoreA; }
    public int getScoreB() { return scoreB; }
    public void setScoreB(int scoreB) { this.scoreB = scoreB; }
    public Team getWinner() { return winner; }
    public void setWinner(Team winner) { this.winner = winner; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public LocalDateTime getMatchTime() { return matchTime; }
    public void setMatchTime(LocalDateTime matchTime) { this.matchTime = matchTime; }
    public MatchStatus getStatus() { return status; }
    public void setStatus(MatchStatus status) { this.status = status; }
}
