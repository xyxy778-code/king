package com.king.service;

import com.king.dao.DataStore;
import com.king.entity.*;
import java.util.*;
import java.util.stream.Collectors;

public class EquipmentStatsService {
    private final DataStore store = DataStore.getInstance();

    public List<EquipmentStat> getStatsByUsage() {
        return computeStats().stream()
                .sorted((a, b) -> Integer.compare(b.useCount, a.useCount))
                .collect(Collectors.toList());
    }

    public List<EquipmentStat> getStatsByWinRate() {
        return computeStats().stream()
                .filter(s -> s.totalMatches > 0)
                .sorted((a, b) -> Double.compare(b.winRate, a.winRate))
                .collect(Collectors.toList());
    }

    public List<EquipmentStat> getStatsByCustomScore() {
        return computeStats().stream()
                .sorted((a, b) -> Double.compare(b.customScore, a.customScore))
                .collect(Collectors.toList());
    }

    private List<EquipmentStat> computeStats() {
        Map<String, EquipmentStat> statMap = new LinkedHashMap<>();
        for (Equipment e : store.equipments.getAll()) {
            statMap.put(e.getId(), new EquipmentStat(e));
        }

        for (Hero hero : store.heroes.getAll()) {
            for (String eid : hero.getEquipIds()) {
                EquipmentStat stat = statMap.get(eid);
                if (stat != null) {
                    stat.equipCount++;
                }
            }
        }

        for (MatchRecord m : store.matches.getAll()) {
            for (String hid : m.getHeroIds()) {
                Hero hero = store.heroes.get(hid);
                if (hero != null) {
                    for (String eid : hero.getEquipIds()) {
                        EquipmentStat stat = statMap.get(eid);
                        if (stat != null) {
                            stat.totalMatches++;
                            if (m.getWinnerTeamId() != null) {
                                String winnerHeroOwner = hero.getOwnerPlayerId();
                                if (winnerHeroOwner != null) {
                                    Player p = store.players.get(winnerHeroOwner);
                                    if (p != null) {
                                        String teamId = findPlayerTeam(p.getId());
                                        if (teamId != null && teamId.equals(m.getWinnerTeamId())) {
                                            stat.winMatches++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for (EquipmentStat s : statMap.values()) {
            if (s.totalMatches > 0) {
                s.winRate = (double) s.winMatches / s.totalMatches * 100;
            }
        }

        return new ArrayList<>(statMap.values());
    }

    private String findPlayerTeam(String playerId) {
        for (Team t : store.teams.getAll()) {
            if (t.getPlayerIds().contains(playerId)) return t.getId();
        }
        return null;
    }

    public static class EquipmentStat {
        public final Equipment equipment;
        public int useCount;
        public int equipCount;
        public int totalMatches;
        public int winMatches;
        public double winRate;

        public EquipmentStat(Equipment equipment) {
            this.equipment = equipment;
        }

        public double getCustomScore() {
            return winRate * 0.4 + equipCount * 2.0 + useCount * 0.1;
        }

        public double customScore;

        public void computeCustomScore() {
            this.customScore = getCustomScore();
        }

        @Override
        public String toString() {
            return String.format("[%s] %s | 使用次数:%d | 携带英雄数:%d | 比赛场次:%d | 胜率:%.1f%% | 综合评分:%.1f",
                    equipment.getId(), equipment.getName(), useCount, equipCount, totalMatches, winRate, getCustomScore());
        }
    }
}
