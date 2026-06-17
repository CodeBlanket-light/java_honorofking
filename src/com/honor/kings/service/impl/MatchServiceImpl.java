package com.honor.kings.service.impl;

import com.honor.kings.model.entity.MatchRecord;
import com.honor.kings.model.entity.Team;
import com.honor.kings.model.person.Player;
import com.honor.kings.service.Persistable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MatchServiceImpl：比赛记录业务逻辑实现类
 * 演示：接口多态（实现 Persistable<MatchRecord>）、集合（HashMap）、
 *       异常处理（方法中通过判空处理 null 场景）
 */
public class MatchServiceImpl implements Persistable<MatchRecord> {

    /** 内存存储：以比赛 ID 为键，MatchRecord 对象为值 */
    private Map<String, MatchRecord> storage = new HashMap<>();

    /** 保存比赛记录到 storage */
    @Override
    public boolean save(MatchRecord entity) {
        storage.put(entity.getId(), entity);
        return true;
    }

    /** 根据 ID 查找比赛记录 */
    @Override
    public MatchRecord findById(String id) {
        return storage.get(id);
    }

    /** 根据 ID 删除比赛记录 */
    @Override
    public boolean delete(String id) {
        return storage.remove(id) != null;
    }

    /** 返回所有比赛记录 */
    @Override
    public List<MatchRecord> findAll() {
        return new ArrayList<>(storage.values());
    }

    /** 开始一场新比赛：创建 MatchRecord，状态设为 SCHEDULED */
    public void startMatch(Team teamA, Team teamB) {
        String id = "M" + System.currentTimeMillis();
        MatchRecord match = new MatchRecord(id, teamA, teamB, 0, 0,
                null, LocalDateTime.now(), MatchRecord.MatchStatus.SCHEDULED);
        storage.put(id, match);
    }

    /** 结束比赛：设置双方比分和状态，调用 determineWinner() 确定胜者 */
    public void finishMatch(String matchId, int scoreA, int scoreB) {
        MatchRecord match = storage.get(matchId);
        if (match == null) return;
        match.setScoreA(scoreA);
        match.setScoreB(scoreB);
        match.setStatus(MatchRecord.MatchStatus.FINISHED);
        match.determineWinner();
    }

    /** 获取指定玩家的比赛历史（占位方法，暂返回所有记录） */
    public List<MatchRecord> getPlayerMatchHistory(Player player) {
        // TODO: filter by player's team membership
        return new ArrayList<>(storage.values());
    }

    /** 计算指定队伍的胜率（胜场 / 总场次），未参与任何比赛时返回 0.0 */
    public double getTeamWinRate(Team team) {
        int total = 0;
        int wins = 0;
        for (MatchRecord match : storage.values()) {
            boolean involvesTeam = (match.getTeamA() != null && match.getTeamA().getId().equals(team.getId()))
                    || (match.getTeamB() != null && match.getTeamB().getId().equals(team.getId()));
            if (!involvesTeam) continue;
            total++;
            if (match.getWinner() != null && match.getWinner().getId().equals(team.getId())) {
                wins++;
            }
        }
        return total == 0 ? 0.0 : (double) wins / total;
    }
}
