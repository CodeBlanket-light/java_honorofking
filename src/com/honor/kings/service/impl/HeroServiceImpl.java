package com.honor.kings.service.impl;

import com.honor.kings.model.entity.Hero;
import com.honor.kings.model.entity.Equipment;
import com.honor.kings.service.Searchable;
import com.honor.kings.service.Persistable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeroServiceImpl implements Searchable<Hero>, Persistable<Hero> {

    private Map<String, Hero> storage = new HashMap<>();

    @Override
    public boolean save(Hero entity) {
        storage.put(entity.getId(), entity);
        return true;
    }

    @Override
    public Hero findById(String id) {
        return storage.get(id);
    }

    @Override
    public boolean delete(String id) {
        return storage.remove(id) != null;
    }

    @Override
    public List<Hero> findAll() {
        return new ArrayList<>(storage.values());
    }

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

    public void equipHero(String heroId, String equipmentId) {
        Hero hero = storage.get(heroId);
        if (hero != null) {
            Equipment eq = new Equipment();
            eq.setId(equipmentId);
            hero.equip(eq);
        }
    }

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
