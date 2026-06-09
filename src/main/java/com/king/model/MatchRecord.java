package com.king.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MatchRecord extends BaseEntity implements Searchable {
    private String teamAId;
    private String teamBId;
    private String winnerTeamId;
    private LocalDateTime matchTime;
    private List<String> heroIds;

    public MatchRecord() {
        super();
        this.heroIds = new ArrayList<>();
    }

    public MatchRecord(String id, String teamAId, String teamBId, String winnerTeamId) {
        super(id, "比赛-" + id);
        this.teamAId = teamAId;
        this.teamBId = teamBId;
        this.winnerTeamId = winnerTeamId;
        this.matchTime = LocalDateTime.now();
        this.heroIds = new ArrayList<>();
    }

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
    public boolean matches(String keyword) {
        String k = keyword.toLowerCase();
        return id.toLowerCase().contains(k)
                || teamAId.toLowerCase().contains(k)
                || teamBId.toLowerCase().contains(k)
                || winnerTeamId.toLowerCase().contains(k);
    }

    @Override
    public String toDisplayString() {
        return String.format("[%s] 队伍%s vs 队伍%s | 胜者:%s | %s",
                id, teamAId, teamBId, winnerTeamId, matchTime);
    }

    @Override
    public String toString() { return toDisplayString(); }
}
