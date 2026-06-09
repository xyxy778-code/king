package com.king.service;

import com.king.dao.DataStore;
import com.king.entity.*;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class RankingService {
    private final DataStore store = DataStore.getInstance();

    public <T extends Rankable> List<T> rank(Collection<T> source) {
        return source.stream()
                .sorted((a, b) -> Integer.compare(b.getRankValue(), a.getRankValue()))
                .collect(Collectors.toList());
    }

    public <T> List<T> rankBy(Collection<T> source, ToIntFunction<? super T> valueExtractor) {
        return source.stream()
                .sorted((a, b) -> Integer.compare(valueExtractor.applyAsInt(b), valueExtractor.applyAsInt(a)))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayerRankingByScore() {
        return rank(store.players.getAll());
    }

    public List<Player> getPlayerRankingByLevel() {
        return rankBy(store.players.getAll(), Player::getLevel);
    }

    public List<Hero> getHeroRankingByWinRate() {
        return rank(store.heroes.getAll());
    }

    public List<Hero> getHeroRankingByPrice() {
        return rankBy(store.heroes.getAll(), Hero::getPriceRankValue);
    }

    public List<Equipment> getEquipmentRankingByPrice() {
        return rank(store.equipments.getAll());
    }
}
