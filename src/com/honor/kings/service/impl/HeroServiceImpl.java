package com.honor.kings.service.impl;

import com.honor.kings.model.entity.Hero;
import com.honor.kings.model.entity.Equipment;
import com.honor.kings.service.Searchable;
import com.honor.kings.service.Persistable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HeroServiceImpl：英雄业务逻辑实现类
 * 演示：接口多态（同时实现 Searchable 和 Persistable）、
 *       集合（HashMap 作为内存存储）
 * 职责：英雄 CRUD、按名称搜索、装备操作
 */
public class HeroServiceImpl implements Searchable<Hero>, Persistable<Hero> {

    /** 内存存储：以英雄 ID 为键，Hero 对象为值 */
    private Map<String, Hero> storage = new HashMap<>();

    /** 保存英雄到 storage */
    @Override
    public boolean save(Hero entity) {
        storage.put(entity.getId(), entity);
        return true;
    }

    /** 根据 ID 查找英雄 */
    @Override
    public Hero findById(String id) {
        return storage.get(id);
    }

    /** 根据 ID 删除英雄，删除成功返回 true */
    @Override
    public boolean delete(String id) {
        return storage.remove(id) != null;
    }

    /** 返回所有英雄列表 */
    @Override
    public List<Hero> findAll() {
        return new ArrayList<>(storage.values());
    }

    /** 按英雄名称关键字模糊搜索 */
    @Override
    public List<Hero> searchByName(String keyword) {
        List<Hero> result = new ArrayList<>();
        for (Hero hero : storage.values()) {
            if (hero.getName().contains(keyword)) {
                result.add(hero);
            }
        }
        return result;
    }

    /** 为指定英雄装备一件物品（通过 ID 关联） */
    public void equipHero(String heroId, String equipmentId) {
        Hero hero = storage.get(heroId);
        if (hero != null) {
            Equipment eq = new Equipment();
            eq.setId(equipmentId);
            hero.equip(eq);
        }
    }

    /** 按阵营过滤英雄列表 */
    public List<Hero> getHeroesByFaction(String faction) {
        List<Hero> result = new ArrayList<>();
        for (Hero hero : storage.values()) {
            if (hero.getFaction().equals(faction)) {
                result.add(hero);
            }
        }
        return result;
    }
}
