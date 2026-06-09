package com.king.entity;

import java.util.ArrayList;
import java.util.List;

public class Team extends BaseEntity implements Searchable, HasOwner {
    private List<String> playerIds;

    public Team() {
        super();
        this.playerIds = new ArrayList<>();
    }

    public Team(String id, String name) {
        super(id, name);
        this.playerIds = new ArrayList<>();
    }

    public List<String> getPlayerIds() { return playerIds; }
    public void setPlayerIds(List<String> playerIds) { this.playerIds = playerIds; }

    @Override
    public boolean matches(String keyword) {
        String k = keyword.toLowerCase();
        return id.toLowerCase().contains(k) || name.toLowerCase().contains(k);
    }

    @Override
    public List<String> getOwnedIds() { return playerIds; }
    @Override
    public void addOwned(String id) { playerIds.add(id); }
    @Override
    public boolean removeOwned(String id) { return playerIds.remove(id); }

    @Override
    public String toDisplayString() {
        return String.format("[%s] %s | 成员数:%d", id, name, playerIds.size());
    }

    @Override
    public String toString() { return toDisplayString(); }
}
