package com.king.service;

import com.king.dao.DataStore;
import com.king.entity.*;
import java.util.*;
import java.util.stream.Collectors;

public class PlayerQueryService {
    private final DataStore store = DataStore.getInstance();

    public List<Player> searchPlayer(String keyword) {
        String k = keyword.toLowerCase();
        return store.players.getAll().stream()
                .filter(p -> p.getId().toLowerCase().contains(k)
                        || p.getNickname().toLowerCase().contains(k)
                        || p.getUsername().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    public String getPlayerDetail(Player player) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== 玩家详情 ==========\n");
        sb.append("ID: ").append(player.getId()).append("\n");
        sb.append("昵称: ").append(player.getNickname()).append("\n");
        sb.append("等级: ").append(player.getLevel()).append("\n");
        sb.append("积分: ").append(player.getScore()).append("\n");
        sb.append("拥有英雄数: ").append(player.getHeroIds().size()).append("\n");

        sb.append("\n--- 所属队伍 ---\n");
        List<Team> teams = store.teams.getAll().stream()
                .filter(t -> t.getPlayerIds().contains(player.getId()))
                .collect(Collectors.toList());
        if (teams.isEmpty()) {
            sb.append("无\n");
        } else {
            teams.forEach(t -> sb.append(t.toDisplayString()).append("\n"));
        }

        sb.append("\n--- 英雄列表 ---\n");
        List<Hero> heroes = player.getHeroIds().stream()
                .map(hid -> store.heroes.get(hid))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (heroes.isEmpty()) {
            sb.append("无\n");
        } else {
            for (Hero h : heroes) {
                sb.append("  ").append(h.toDisplayString()).append("\n");
                if (!h.getEquipIds().isEmpty()) {
                    sb.append("    装备:\n");
                    for (String eid : h.getEquipIds()) {
                        Equipment e = store.equipments.get(eid);
                        if (e != null) {
                            sb.append("      - ").append(e.toDisplayString()).append("\n");
                        }
                    }
                } else {
                    sb.append("    装备: 无\n");
                }
            }
        }

        return sb.toString();
    }
}
