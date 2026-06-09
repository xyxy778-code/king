package com.king.service;

import com.king.dao.DataStore;
import com.king.entity.*;
import java.util.*;
import java.util.stream.Collectors;

public class HeroDetailService {
    private final DataStore store = DataStore.getInstance();

    public List<Hero> searchHero(String keyword) {
        String k = keyword.toLowerCase();
        return store.heroes.getAll().stream()
                .filter(h -> h.getId().toLowerCase().contains(k)
                        || h.getName().toLowerCase().contains(k)
                        || h.getTitle().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    public String getHeroDetail(Hero hero) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== 英雄详情 ==========\n");
        sb.append("ID: ").append(hero.getId()).append("\n");
        sb.append("名称: ").append(hero.getName()).append("\n");
        sb.append("称号: ").append(hero.getTitle()).append("\n");

        sb.append("\n--- 基础属性 ---\n");
        sb.append("类型: ").append(hero.getHeroType().getLabel()).append("\n");
        sb.append("价格: ").append(hero.getPrice()).append(" 金币\n");
        sb.append("胜率: ").append(hero.getWinRate()).append("%\n");

        sb.append("\n--- 可用装备 ---\n");
        if (hero.getEquipIds().isEmpty()) {
            sb.append("  无\n");
        } else {
            for (String eid : hero.getEquipIds()) {
                Equipment e = store.equipments.get(eid);
                if (e != null) sb.append("  ").append(e.toDisplayString()).append("\n");
            }
        }

        sb.append("\n--- 使用该英雄的玩家 ---\n");
        String ownerId = hero.getOwnerPlayerId();
        if (ownerId != null && !ownerId.isBlank()) {
            Player owner = store.players.get(ownerId);
            if (owner != null) {
                sb.append("  ").append(owner.toDisplayString()).append("\n");
            }
        } else {
            sb.append("  暂无玩家使用\n");
        }

        sb.append("\n--- 推荐装备（按价格排序） ---\n");
        List<Equipment> sorted = hero.getEquipIds().stream()
                .map(eid -> store.equipments.get(eid))
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(Equipment::getPrice))
                .collect(Collectors.toList());
        if (sorted.isEmpty()) {
            sb.append("  无推荐\n");
        } else {
            for (int i = 0; i < sorted.size(); i++) {
                sb.append("  ").append(i + 1).append(". ")
                  .append(sorted.get(i).getName())
                  .append(" (").append(sorted.get(i).getPrice()).append("金币)")
                  .append(" - ").append(sorted.get(i).getEffect()).append("\n");
            }
        }

        return sb.toString();
    }
}
