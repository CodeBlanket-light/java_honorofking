package com.honor.kings.service.impl;

import com.honor.kings.model.entity.Team;
import com.honor.kings.model.person.Player;
import com.honor.kings.service.Searchable;
import com.honor.kings.service.Persistable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamServiceImpl implements Searchable<Team>, Persistable<Team> {

    private Map<String, Team> storage = new HashMap<>();

    @Override
    public boolean save(Team entity) {
        storage.put(entity.getId(), entity);
        return true;
    }

    @Override
    public Team findById(String id) {
        return storage.get(id);
    }

    @Override
    public boolean delete(String id) {
        return storage.remove(id) != null;
    }

    @Override
    public List<Team> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Team> searchByName(String keyword) {
        List<Team> result = new ArrayList<>();
        for (Team team : storage.values()) {
            if (team.getTeamName().contains(keyword)) {
                result.add(team);
            }
        }
        return result;
    }

    public List<Player> getTeamMembers(String teamId) {
        // TODO: implement via PlayerService.getPlayersByTeamId(teamId)
        return new ArrayList<>();
    }

    public List<Team> getRanking() {
        List<Team> list = new ArrayList<>(storage.values());
        list.sort(Comparator.comparingInt(Team::getScore).reversed()
                .thenComparing(Team::getTeamName));
        return list;
    }
}
