package com.king.entity;

import java.util.ArrayList;
import java.util.List;

public class Player extends Person implements Searchable, Rankable, HasOwner {
    private int level;
    private int score;
    private List<String> heroIds;

    public Player() {
        super();
        this.heroIds = new ArrayList<>();
    }

    public Player(String id, String username, String password, String nickname) {
        super(id, username, password, nickname);
        this.level = 1;
        this.score = 0;
        this.heroIds = new ArrayList<>();
    }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public List<String> getHeroIds() { return heroIds; }
    public void setHeroIds(List<String> heroIds) { this.heroIds = heroIds; }

    @Override
    public boolean matches(String keyword) {
        return super.matches(keyword);
    }

    @Override
    public int getRankValue() { return score; }

    @Override
    public List<String> getOwnedIds() { return heroIds; }

    @Override
    public void addOwned(String id) { heroIds.add(id); }

    @Override
    public boolean removeOwned(String id) { return heroIds.remove(id); }

    @Override
    public String toDisplayString() {
        return String.format("[%s] %s | Lv.%d | 积分:%d | 英雄数:%d",
                id, nickname, level, score, heroIds.size());
    }

    @Override
    public String toString() { return toDisplayString(); }
}
