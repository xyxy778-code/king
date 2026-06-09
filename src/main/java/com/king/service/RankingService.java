package com.king.service;

import com.king.dao.DataStore;
import com.king.entity.*;
import java.util.*;
import java.util.stream.Collectors;

public class RankingService {
    private final DataStore store = DataStore.getInstance();

    public List<Player> getPlayerRankingByScore() {
        return store.players.values().stream()
                .sorted((a, b) -> Integer.compare(b.getScore(), a.getScore()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayerRankingByLevel() {
        return store.players.values().stream()
                .sorted((a, b) -> Integer.compare(b.getLevel(), a.getLevel()))
                .collect(Collectors.toList());
    }

    public List<Hero> getHeroRankingByWinRate() {
        return store.heroes.values().stream()
                .sorted((a, b) -> Integer.compare(b.getWinRate(), a.getWinRate()))
                .collect(Collectors.toList());
    }

    public List<Hero> getHeroRankingByPrice() {
        return store.heroes.values().stream()
                .sorted((a, b) -> Integer.compare(b.getPrice(), a.getPrice()))
                .collect(Collectors.toList());
    }

    public List<Equipment> getEquipmentRankingByPrice() {
        return store.equipments.values().stream()
                .sorted((a, b) -> Integer.compare(b.getPrice(), a.getPrice()))
                .collect(Collectors.toList());
    }
}
