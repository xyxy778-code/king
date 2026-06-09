package com.king.service;

import com.king.dao.BaseDataAccess;
import com.king.dao.DataStore;
import com.king.entity.*;

public class DataManageService {
    private final DataStore store = DataStore.getInstance();
    private final java.util.Map<Class<?>, BaseDataAccess<?>> accessorMap;

    public DataManageService() {
        accessorMap = new java.util.LinkedHashMap<>();
        accessorMap.put(Player.class, store.players);
        accessorMap.put(Admin.class, store.admins);
        accessorMap.put(Hero.class, store.heroes);
        accessorMap.put(Equipment.class, store.equipments);
        accessorMap.put(Team.class, store.teams);
        accessorMap.put(MatchRecord.class, store.matches);
        accessorMap.put(DevLog.class, store.devLogs);
    }

    @SuppressWarnings("unchecked")
    public BaseDataAccess<?> getAccessor(Class<?> clazz) {
        return accessorMap.get(clazz);
    }

    public java.util.Collection<?> getAll(Class<?> clazz) {
        return getAccessor(clazz).getAll();
    }

    public java.util.Collection<BaseDataAccess<?>> getAllAccessors() {
        return accessorMap.values();
    }

    public String getEntityLabel(Class<?> clazz) {
        if (clazz == Player.class) return "玩家";
        if (clazz == Admin.class) return "管理员";
        if (clazz == Hero.class) return "英雄";
        if (clazz == Equipment.class) return "装备";
        if (clazz == Team.class) return "队伍";
        if (clazz == MatchRecord.class) return "比赛记录";
        if (clazz == DevLog.class) return "开发日志";
        return clazz.getSimpleName();
    }
}
