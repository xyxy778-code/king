package com.king.service;

import com.king.dao.DataStore;
import com.king.entity.*;
import java.util.*;
import java.util.stream.Collectors;

public class SearchService {
    private final DataStore store = DataStore.getInstance();

    public List<Player> searchPlayers(String keyword) {
        String k = keyword.toLowerCase();
        return store.players.values().stream()
                .filter(p -> p.getId().toLowerCase().contains(k)
                        || p.getUsername().toLowerCase().contains(k)
                        || p.getNickname().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    public List<Hero> searchHeroes(String keyword) {
        String k = keyword.toLowerCase();
        return store.heroes.values().stream()
                .filter(h -> h.getId().toLowerCase().contains(k)
                        || h.getName().toLowerCase().contains(k)
                        || h.getTitle().toLowerCase().contains(k)
                        || h.getRole().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    public List<Equipment> searchEquipments(String keyword) {
        String k = keyword.toLowerCase();
        return store.equipments.values().stream()
                .filter(e -> e.getId().toLowerCase().contains(k)
                        || e.getName().toLowerCase().contains(k)
                        || e.getCategory().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    public List<Team> searchTeams(String keyword) {
        String k = keyword.toLowerCase();
        return store.teams.values().stream()
                .filter(t -> t.getId().toLowerCase().contains(k)
                        || t.getName().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    public List<MatchRecord> searchMatches(String keyword) {
        String k = keyword.toLowerCase();
        return store.matches.values().stream()
                .filter(m -> m.getId().toLowerCase().contains(k)
                        || m.getTeamAId().toLowerCase().contains(k)
                        || m.getTeamBId().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }
}
