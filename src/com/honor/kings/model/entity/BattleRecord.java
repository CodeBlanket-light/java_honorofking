package com.honor.kings.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class BattleRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private String time;
    private String hero1Name;
    private String hero2Name;
    private List<String> hero1Equipment;
    private List<String> hero2Equipment;
    private String result;

    public BattleRecord(String time, String hero1Name, String hero2Name,
                        List<String> hero1Equipment, List<String> hero2Equipment, String result) {
        this.time = time;
        this.hero1Name = hero1Name;
        this.hero2Name = hero2Name;
        this.hero1Equipment = hero1Equipment;
        this.hero2Equipment = hero2Equipment;
        this.result = result;
    }

    public String getTime() { return time; }
    public String getHero1Name() { return hero1Name; }
    public String getHero2Name() { return hero2Name; }
    public List<String> getHero1Equipment() { return hero1Equipment; }
    public List<String> getHero2Equipment() { return hero2Equipment; }
    public String getResult() { return result; }

    public List<String> getAllEquipmentNames() {
        java.util.Set<String> all = new java.util.HashSet<>();
        if (hero1Equipment != null) all.addAll(hero1Equipment);
        if (hero2Equipment != null) all.addAll(hero2Equipment);
        return new java.util.ArrayList<>(all);
    }
}
