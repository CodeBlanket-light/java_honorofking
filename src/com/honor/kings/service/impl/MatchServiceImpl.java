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

public class MatchServiceImpl implements Persistable<MatchRecord> {

    private Map<String, MatchRecord> storage = new HashMap<>();

    @Override
    public boolean save(MatchRecord entity) {
        storage.put(entity.getId(), entity);
        return true;
    }

    @Override
    public MatchRecord findById(String id) {
        return storage.get(id);
    }

    @Override
    public boolean delete(String id) {
        return storage.remove(id) != null;
    }

    @Override
    public List<MatchRecord> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void startMatch(Team teamA, Team teamB) {
        String id = "M" + System.currentTimeMillis();
        MatchRecord match = new MatchRecord(id, teamA, teamB, 0, 0,
                null, LocalDateTime.now(), MatchRecord.MatchStatus.SCHEDULED);
        storage.put(id, match);
    }

    public void finishMatch(String matchId, int scoreA, int scoreB) {
        MatchRecord match = storage.get(matchId);
        if (match == null) return;
        match.setScoreA(scoreA);
        match.setScoreB(scoreB);
        match.setStatus(MatchRecord.MatchStatus.FINISHED);
        match.determineWinner();
    }

    public List<MatchRecord> getPlayerMatchHistory(Player player) {
        // TODO: filter by player's team membership
        return new ArrayList<>(storage.values());
    }

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
