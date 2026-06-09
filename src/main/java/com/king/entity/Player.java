package com.king.entity;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String id;
    private String username;
    private String password;
    private String nickname;
    private int level;
    private int score;
    private List<String> heroIds;

    public Player() {
        this.heroIds = new ArrayList<>();
    }

    public Player(String id, String username, String password, String nickname) {
        this();
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.level = 1;
        this.score = 0;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
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
    public String toString() {
        return String.format("[%s] %s | Lv.%d | 积分:%d | 英雄数:%d",
                id, nickname, level, score, heroIds.size());
    }
}
