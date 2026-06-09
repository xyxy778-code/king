package com.king.dao;

import com.king.entity.*;
import java.util.*;

public class DataStore {
    public final Map<String, Player> players = new LinkedHashMap<>();
    public final Map<String, Hero> heroes = new LinkedHashMap<>();
    public final Map<String, Equipment> equipments = new LinkedHashMap<>();
    public final Map<String, Team> teams = new LinkedHashMap<>();
    public final Map<String, MatchRecord> matches = new LinkedHashMap<>();

    private static final DataStore INSTANCE = new DataStore();

    private DataStore() {
        initSampleData();
    }

    public static DataStore getInstance() {
        return INSTANCE;
    }

    private void initSampleData() {
        // heroes
        Hero h1 = new Hero("H001", "赵云", "苍天翔龙", "战士", 18888);
        h1.setWinRate(52);
        Hero h2 = new Hero("H002", "诸葛亮", "绝代智谋", "法师", 18888);
        h2.setWinRate(51);
        Hero h3 = new Hero("H003", "后羿", "半神之弓", "射手", 13888);
        h3.setWinRate(50);
        Hero h4 = new Hero("H004", "李白", "青莲剑仙", "刺客", 18888);
        h4.setWinRate(49);
        Hero h5 = new Hero("H005", "庄周", "逍遥幻梦", "辅助", 13888);
        h5.setWinRate(53);
        heroes.put(h1.getId(), h1);
        heroes.put(h2.getId(), h2);
        heroes.put(h3.getId(), h3);
        heroes.put(h4.getId(), h4);
        heroes.put(h5.getId(), h5);

        // equipments
        equipments.put("E001", new Equipment("E001", "无尽战刃", "攻击", 2140, "+130物理攻击, +20%暴击率"));
        equipments.put("E002", new Equipment("E002", "回响之杖", "法术", 2100, "+240法术攻击, +7%移速"));
        equipments.put("E003", new Equipment("E003", "急速战靴", "移动", 710, "+30%攻击速度"));
        equipments.put("E004", new Equipment("E004", "魔女斗篷", "防御", 2120, "+360法术防御, +1000最大生命"));
        equipments.put("E005", new Equipment("E005", "破晓", "攻击", 3400, "+100物理攻击, +35%攻速"));

        // players
        Player p1 = new Player("P001", "admin", "123456", "王者管理员");
        p1.setLevel(30);
        p1.setScore(9999);
        p1.getHeroIds().addAll(Arrays.asList("H001", "H002"));
        Player p2 = new Player("P002", "player1", "123456", "李白本白");
        p2.setLevel(25);
        p2.setScore(4520);
        p2.getHeroIds().addAll(Arrays.asList("H004", "H003"));
        Player p3 = new Player("P003", "player2", "123456", "最强法师");
        p3.setLevel(18);
        p3.setScore(2100);
        p3.getHeroIds().addAll(Arrays.asList("H002", "H005"));
        players.put(p1.getId(), p1);
        players.put(p2.getId(), p2);
        players.put(p3.getId(), p3);

        // teams
        Team t1 = new Team("T001", "王者之师");
        t1.getPlayerIds().addAll(Arrays.asList("P001", "P002"));
        Team t2 = new Team("T002", "荣耀战队");
        t2.getPlayerIds().addAll(Arrays.asList("P003"));
        teams.put(t1.getId(), t1);
        teams.put(t2.getId(), t2);

        // matches
        matches.put("M001", new MatchRecord("M001", "T001", "T002", "T001"));
    }
}
