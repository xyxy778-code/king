package com.king.service;

import com.king.dao.BaseDataAccess;
import com.king.dao.DataStore;
import com.king.entity.*;
import java.util.*;

public class DataManageService {
    private final DataStore store = DataStore.getInstance();
    private final Map<Class<?>, BaseDataAccess<?>> accessorMap;

    public DataManageService() {
        accessorMap = new LinkedHashMap<>();
        accessorMap.put(Player.class, store.players);
        accessorMap.put(Hero.class, store.heroes);
        accessorMap.put(Equipment.class, store.equipments);
        accessorMap.put(Team.class, store.teams);
        accessorMap.put(MatchRecord.class, store.matches);
    }

    @SuppressWarnings("unchecked")
    public BaseDataAccess<Identifiable> getAccessor(Class<?> clazz) {
        return (BaseDataAccess<Identifiable>) accessorMap.get(clazz);
    }

    public Collection<?> getAll(Class<?> clazz) {
        return getAccessor(clazz).getAll();
    }

    public String getEntityLabel(Class<?> clazz) {
        if (clazz == Player.class) return "玩家";
        if (clazz == Hero.class) return "英雄";
        if (clazz == Equipment.class) return "装备";
        if (clazz == Team.class) return "队伍";
        if (clazz == MatchRecord.class) return "比赛记录";
        return clazz.getSimpleName();
    }
}
