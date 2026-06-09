package com.king.service;

import com.king.dao.DataStore;
import com.king.entity.DevLog;
import java.util.*;
import java.util.stream.Collectors;

public class DevLogService {
    private final DataStore store = DataStore.getInstance();

    public void addLog(DevLog log) {
        store.devLogs.add(log);
    }

    public List<DevLog> getAllLogs() {
        return store.devLogs.getAll().stream()
                .sorted((a, b) -> b.getTime().compareTo(a.getTime()))
                .collect(Collectors.toList());
    }

    public DevLog getLog(String id) {
        return store.devLogs.get(id);
    }

    public boolean deleteLog(String id) {
        return store.devLogs.delete(id) != null;
    }

    public String generateNextId() {
        int max = 0;
        for (DevLog log : store.devLogs.getAll()) {
            String lid = log.getId();
            if (lid.startsWith("DL")) {
                try {
                    int num = Integer.parseInt(lid.substring(2));
                    if (num > max) max = num;
                } catch (NumberFormatException e) { }
            }
        }
        return String.format("DL%03d", max + 1);
    }
}
