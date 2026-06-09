package com.king.service;

import com.king.dao.DataStore;
import com.king.entity.*;
import java.util.*;
import java.util.stream.Collectors;

public class MatchHistoryService {
    private final DataStore store = DataStore.getInstance();

    public List<MatchRecord> getRecentMatches(int n) {
        return store.matches.getAll().stream()
                .sorted((a, b) -> b.getMatchTime().compareTo(a.getMatchTime()))
                .limit(n)
                .collect(Collectors.toList());
    }

    public String getMatchDetail(MatchRecord match) {
        StringBuilder sb = new StringBuilder();
        Team teamA = store.teams.get(match.getTeamAId());
        Team teamB = store.teams.get(match.getTeamBId());
        boolean isDraw = match.getWinnerTeamId() == null || match.getWinnerTeamId().isBlank();

        sb.append("\n========== 比赛详情 ==========\n");
        sb.append("ID: ").append(match.getId()).append("\n");
        sb.append("时间: ").append(match.getMatchTime()).append("\n");

        String nameA = teamA != null ? teamA.getName() : match.getTeamAId();
        String nameB = teamB != null ? teamB.getName() : match.getTeamBId();
        sb.append("对阵: ").append(nameA).append(" vs ").append(nameB).append("\n");

        if (isDraw) {
            sb.append("结果: 平局\n");
        } else {
            String winnerName = match.getWinnerTeamId().equals(match.getTeamAId()) ? nameA : nameB;
            sb.append("结果: ").append(winnerName).append(" 获胜\n");
        }

        sb.append("\n--- 英雄选择 ---\n");
        if (match.getHeroIds().isEmpty()) {
            sb.append("  无记录\n");
        } else {
            sb.append("  【").append(nameA).append("】\n");
            HashSet<String> teamAPlayers = teamA != null ? new HashSet<>(teamA.getPlayerIds()) : new HashSet<>();
            List<String> teamAHeroes = new ArrayList<>();
            List<String> teamBHeroes = new ArrayList<>();
            for (String hid : match.getHeroIds()) {
                Hero hero = store.heroes.get(hid);
                if (hero != null) {
                    String ownerId = hero.getOwnerPlayerId();
                    if (teamAPlayers.contains(ownerId)) {
                        teamAHeroes.add(hero.getName());
                    } else {
                        teamBHeroes.add(hero.getName());
                    }
                }
            }
            for (String h : teamAHeroes) {
                sb.append("    - ").append(h).append("\n");
            }
            sb.append("  【").append(nameB).append("】\n");
            for (String h : teamBHeroes) {
                sb.append("    - ").append(h).append("\n");
            }
        }

        return sb.toString();
    }
}
