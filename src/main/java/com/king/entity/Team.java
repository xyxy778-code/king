package com.king.entity;

import com.king.dao.DataStore;
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

    public List<Player> getMembers() {
        DataStore store = DataStore.getInstance();
        List<Player> members = new ArrayList<>();
        for (String pid : playerIds) {
            Player p = store.players.get(pid);
            if (p != null) members.add(p);
        }
        return members;
    }

    public int getTotalScore() {
        return getMembers().stream().mapToInt(Player::getScore).sum();
    }

    public double getAverageLevel() {
        List<Player> members = getMembers();
        if (members.isEmpty()) return 0;
        return members.stream().mapToInt(Player::getLevel).average().orElse(0);
    }

    @Override
    public String toDisplayString() {
        return String.format("[%s] %s | 成员数:%d | 总积分:%d | 平均等级:%.1f",
                id, name, playerIds.size(), getTotalScore(), getAverageLevel());
    }

    @Override
    public String toString() { return toDisplayString(); }
}
