package com.king.service;

import com.king.dao.DataStore;
import com.king.entity.*;
import java.util.*;
import java.util.stream.Collectors;

public class TeamOverviewService {
    private final DataStore store = DataStore.getInstance();

    public List<Team> searchTeam(String keyword) {
        String k = keyword.toLowerCase();
        return store.teams.getAll().stream()
                .filter(t -> t.getId().toLowerCase().contains(k)
                        || t.getName().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    public String getTeamDetail(Team team) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== 队伍概览 ==========\n");
        sb.append("ID: ").append(team.getId()).append("\n");
        sb.append("名称: ").append(team.getName()).append("\n");

        List<Player> members = team.getMembers();
        sb.append("成员数: ").append(members.size()).append("\n\n");

        sb.append("--- 队伍成员 ---\n");
        for (Player p : members) {
            sb.append("  ").append(p.toDisplayString()).append("\n");
        }

        double avgLevel = team.getAverageLevel();
        sb.append(String.format("\n平均等级: %.1f\n", avgLevel));

        List<MatchRecord> teamMatches = store.matches.getAll().stream()
                .filter(m -> m.getTeamAId().equals(team.getId()) || m.getTeamBId().equals(team.getId()))
                .collect(Collectors.toList());
        int total = teamMatches.size();
        long wins = teamMatches.stream().filter(m -> m.getWinnerTeamId().equals(team.getId())).count();
        double winRate = total > 0 ? (double) wins / total * 100 : 0;
        sb.append("比赛场次: ").append(total).append("\n");
        sb.append(String.format("胜率: %d/%d (%.1f%%)\n", wins, total, winRate));

        Player strongest = members.stream()
                .max(Comparator.comparingInt(Player::getScore))
                .orElse(null);
        sb.append("\n最强玩家: ");
        if (strongest != null) {
            sb.append(strongest.getNickname())
              .append(" (积分: ").append(strongest.getScore()).append(")");
        } else {
            sb.append("无");
        }
        sb.append("\n");

        return sb.toString();
    }
}
