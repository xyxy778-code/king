package com.king.service;

import com.king.dao.DataStore;
import com.king.entity.*;
import java.util.*;
import java.util.stream.Collectors;

public class RankingService {
    private final DataStore store = DataStore.getInstance();

    public List<Player> getPlayerRankingByScore() {
        return rankPlayers(Comparator.comparingInt(Player::getScore).reversed());
    }

    public List<Player> getPlayerRankingByLevel() {
        return rankPlayers(Comparator.comparingInt(Player::getLevel).reversed()
                .thenComparing(Comparator.comparingInt(Player::getScore).reversed()));
    }

    public List<Player> getPlayerRankingByWinRate() {
        return rankPlayers(Comparator.comparingInt(p -> {
            Hero hero = store.heroes.get(p.getHeroIds().isEmpty() ? "" : p.getHeroIds().get(0));
            return hero != null ? hero.getWinRate() : 0;
        }).reversed()
                .thenComparing(Comparator.comparingInt(Player::getScore).reversed()));
    }

    public List<Player> getPlayerRankingByMatchCount() {
        return rankPlayers(Comparator.comparingLong(p -> {
            return store.matches.getAll().stream()
                    .filter(m -> m.getHeroIds().stream()
                            .anyMatch(hid -> {
                                Hero h = store.heroes.get(hid);
                                return h != null && p.getHeroIds().contains(hid);
                            }))
                    .count();
        }).reversed()
                .thenComparing(Comparator.comparingInt(Player::getScore).reversed()));
    }

    public List<Player> getPlayerRankingByCustomScore() {
        return rankPlayers(Comparator.comparingDouble(p -> {
            int score = p.getScore();
            int level = p.getLevel();
            long matchCount = store.matches.getAll().stream()
                    .filter(m -> m.getHeroIds().stream()
                            .anyMatch(hid -> {
                                Hero h = store.heroes.get(hid);
                                return h != null && p.getHeroIds().contains(hid);
                            }))
                    .count();
            return -(score * 0.5 + level * 10 + matchCount * 5);
        }).thenComparing(Comparator.comparingInt(Player::getScore).reversed()));
    }

    public List<Hero> getHeroRankingByWinRate() {
        return rankHeroes(Comparator.comparingInt(Hero::getWinRate).reversed()
                .thenComparing(Comparator.comparingInt(Hero::getPrice).reversed()));
    }

    public List<Hero> getHeroRankingByPrice() {
        return rankHeroes(Comparator.comparingInt(Hero::getPrice).reversed()
                .thenComparing(Comparator.comparingInt(Hero::getWinRate).reversed()));
    }

    public List<Equipment> getEquipmentRankingByPrice() {
        return rankEquipments(Comparator.comparingInt(Equipment::getPrice).reversed()
                .thenComparing(Equipment::getName));
    }

    private List<Player> rankPlayers(Comparator<Player> comparator) {
        return store.players.getAll().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private List<Hero> rankHeroes(Comparator<Hero> comparator) {
        return store.heroes.getAll().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private List<Equipment> rankEquipments(Comparator<Equipment> comparator) {
        return store.equipments.getAll().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public static String getTieBreakerRule() {
        return "【同分处理规则】\n"
                + "1) 主排序值相同时，按次排序值降序排列\n"
                + "2) 次排序值仍相同时，按积分降序排列\n"
                + "3) 积分仍相同，按ID字典序排列\n"
                + "4) 所有排序值完全一致则视为并列排名（显示相同名次）";
    }
}
