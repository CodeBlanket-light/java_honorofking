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

/**
 * TeamServiceImpl：队伍业务逻辑实现类
 * 演示：接口多态（Searchable + Persistable）、集合（HashMap）、
 *       Comparator 排序（getRanking）
 */
public class TeamServiceImpl implements Searchable<Team>, Persistable<Team> {

    /** 内存存储：以队伍 ID 为键，Team 对象为值 */
    private Map<String, Team> storage = new HashMap<>();

    /** 保存队伍到 storage */
    @Override
    public boolean save(Team entity) {
        storage.put(entity.getId(), entity);
        return true;
    }

    /** 根据 ID 查找队伍 */
    @Override
    public Team findById(String id) {
        return storage.get(id);
    }

    /** 根据 ID 删除队伍，返回是否成功 */
    @Override
    public boolean delete(String id) {
        return storage.remove(id) != null;
    }

    /** 返回所有队伍列表 */
    @Override
    public List<Team> findAll() {
        return new ArrayList<>(storage.values());
    }

    /** 按队伍名称关键字模糊搜索 */
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

    /** 获取指定队伍的成员列表（占位方法，待实现） */
    public List<Player> getTeamMembers(String teamId) {
        // TODO: implement via PlayerService.getPlayersByTeamId(teamId)
        return new ArrayList<>();
    }

    /** 返回队伍排行榜：按分数降序，同分按队伍名升序 */
    public List<Team> getRanking() {
        List<Team> list = new ArrayList<>(storage.values());
        list.sort(Comparator.comparingInt(Team::getScore).reversed()
                .thenComparing(Team::getTeamName));
        return list;
    }
}
