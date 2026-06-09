package com.king.entity;

import java.util.ArrayList;
import java.util.List;

public class Player extends BaseEntity implements Searchable, Rankable, HasOwner {
    private String username;
    private String password;
    private String nickname;
    private int level;
    private int score;
    private List<String> heroIds;

    public Player() {
        super();
        this.heroIds = new ArrayList<>();
    }

    public Player(String id, String username, String password, String nickname) {
        super(id, username);
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.level = 1;
        this.score = 0;
        this.heroIds = new ArrayList<>();
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public List<String> getHeroIds() { return heroIds; }
    public void setHeroIds(List<String> heroIds) { this.heroIds = heroIds; }

    @Override
    public boolean matches(String keyword) {
        String k = keyword.toLowerCase();
        return id.toLowerCase().contains(k)
                || username.toLowerCase().contains(k)
                || nickname.toLowerCase().contains(k);
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
