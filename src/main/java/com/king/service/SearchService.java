package com.king.service;

import com.king.dao.DataStore;
import com.king.entity.*;
import java.util.*;
import java.util.stream.Collectors;

public class SearchService {
    private final DataStore store = DataStore.getInstance();

    public <T extends Searchable> List<T> search(Collection<T> source, String keyword) {
        return source.stream()
                .filter(s -> s.matches(keyword))
                .collect(Collectors.toList());
    }

    public Map<String, List<? extends Searchable>> searchAll(String keyword) {
        Map<String, List<? extends Searchable>> results = new LinkedHashMap<>();
        results.put("玩家", search(store.players.getAll(), keyword));
        results.put("管理员", search(store.admins.getAll(), keyword));
        results.put("英雄", search(store.heroes.getAll(), keyword));
        results.put("装备", search(store.equipments.getAll(), keyword));
        results.put("队伍", search(store.teams.getAll(), keyword));
        results.put("比赛", search(store.matches.getAll(), keyword));
        return results;
    }
}
