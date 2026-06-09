package com.king.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MatchRecord {
    private String id;
    private String teamAId;
    private String teamBId;
    private String winnerTeamId;
    private LocalDateTime matchTime;
    private List<String> heroIds;

    public MatchRecord() {
        this.heroIds = new ArrayList<>();
    }

    public MatchRecord(String id, String teamAId, String teamBId, String winnerTeamId) {
        this();
        this.id = id;
        this.teamAId = teamAId;
        this.teamBId = teamBId;
        this.winnerTeamId = winnerTeamId;
        this.matchTime = LocalDateTime.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTeamAId() { return teamAId; }
    public void setTeamAId(String teamAId) { this.teamAId = teamAId; }
    public String getTeamBId() { return teamBId; }
    public void setTeamBId(String teamBId) { this.teamBId = teamBId; }
    public String getWinnerTeamId() { return winnerTeamId; }
    public void setWinnerTeamId(String winnerTeamId) { this.winnerTeamId = winnerTeamId; }
    public LocalDateTime getMatchTime() { return matchTime; }
    public void setMatchTime(LocalDateTime matchTime) { this.matchTime = matchTime; }
    public List<String> getHeroIds() { return heroIds; }
    public void setHeroIds(List<String> heroIds) { this.heroIds = heroIds; }

    @Override
    public String toString() {
        return String.format("[%s] 队伍%s vs 队伍%s | 胜者:%s | %s",
                id, teamAId, teamBId, winnerTeamId, matchTime);
    }
}
