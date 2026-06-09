package com.king.dao;

import com.king.entity.*;
import java.util.*;

public class DataStore {
    public final BaseDataAccess<Player> players = new BaseDataAccess<>();
    public final BaseDataAccess<Hero> heroes = new BaseDataAccess<>();
    public final BaseDataAccess<Equipment> equipments = new BaseDataAccess<>();
    public final BaseDataAccess<Team> teams = new BaseDataAccess<>();
    public final BaseDataAccess<MatchRecord> matches = new BaseDataAccess<>();

    private static final DataStore INSTANCE = new DataStore();

    private DataStore() {
        initSampleData();
    }

    public static DataStore getInstance() {
        return INSTANCE;
    }

    private void initSampleData() {
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
        heroes.add(h1); heroes.add(h2); heroes.add(h3); heroes.add(h4); heroes.add(h5);

        equipments.add(new Equipment("E001", "无尽战刃", "攻击", 2140, "+130物理攻击, +20%暴击率"));
        equipments.add(new Equipment("E002", "回响之杖", "法术", 2100, "+240法术攻击, +7%移速"));
        equipments.add(new Equipment("E003", "急速战靴", "移动", 710, "+30%攻击速度"));
        equipments.add(new Equipment("E004", "魔女斗篷", "防御", 2120, "+360法术防御, +1000最大生命"));
        equipments.add(new Equipment("E005", "破晓", "攻击", 3400, "+100物理攻击, +35%攻速"));

        Player p1 = new Player("P001", "admin", "123456", "王者管理员");
        p1.setLevel(30); p1.setScore(9999);
        p1.getHeroIds().addAll(Arrays.asList("H001", "H002"));
        Player p2 = new Player("P002", "player1", "123456", "李白本白");
        p2.setLevel(25); p2.setScore(4520);
        p2.getHeroIds().addAll(Arrays.asList("H004", "H003"));
        Player p3 = new Player("P003", "player2", "123456", "最强法师");
        p3.setLevel(18); p3.setScore(2100);
        p3.getHeroIds().addAll(Arrays.asList("H002", "H005"));
        players.add(p1); players.add(p2); players.add(p3);

        Team t1 = new Team("T001", "王者之师");
        t1.getPlayerIds().addAll(Arrays.asList("P001", "P002"));
        Team t2 = new Team("T002", "荣耀战队");
        t2.getPlayerIds().addAll(Arrays.asList("P003"));
        teams.add(t1); teams.add(t2);

        matches.add(new MatchRecord("M001", "T001", "T002", "T001"));
    }
}
