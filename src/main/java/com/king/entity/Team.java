package com.king.entity;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String id;
    private String name;
    private List<String> playerIds;

    public Team() {
        this.playerIds = new ArrayList<>();
    }

    public Team(String id, String name) {
        this();
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<String> getPlayerIds() { return playerIds; }
    public void setPlayerIds(List<String> playerIds) { this.playerIds = playerIds; }

    @Override
    public String toString() {
        return String.format("[%s] %s | 成员数:%d", id, name, playerIds.size());
    }
}
