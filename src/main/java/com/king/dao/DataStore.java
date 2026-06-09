package com.king.dao;

import com.king.entity.*;
import java.util.*;

public class DataStore {
    public final BaseDataAccess<Player> players = new BaseDataAccess<>();
    public final BaseDataAccess<Admin> admins = new BaseDataAccess<>();
    public final BaseDataAccess<Hero> heroes = new BaseDataAccess<>();
    public final BaseDataAccess<Equipment> equipments = new BaseDataAccess<>();
    public final BaseDataAccess<Team> teams = new BaseDataAccess<>();
    public final BaseDataAccess<MatchRecord> matches = new BaseDataAccess<>();
    public final BaseDataAccess<DevLog> devLogs = new BaseDataAccess<>();

    private static final DataStore INSTANCE = new DataStore();

    private DataStore() {
        initSampleData();
    }

    public static DataStore getInstance() {
        return INSTANCE;
    }

    private void initSampleData() {
        admins.add(new Admin("A001", "root", "root123", "超级管理员", "超级管理员"));
        admins.add(new Admin("A002", "op", "op123", "运营管理员", "运营管理"));

        // ==================== 20 装备 ====================
        equipments.add(new Equipment("E001", "无尽战刃", EquipmentCategory.ATTACK, 2140, "+130物理攻击, +20%暴击率"));
        equipments.add(new Equipment("E002", "破军", EquipmentCategory.ATTACK, 2950, "+200物理攻击, 破军效果"));
        equipments.add(new Equipment("E003", "宗师之力", EquipmentCategory.ATTACK, 2100, "+80物理攻击, +20%暴击率, 强击被动"));
        equipments.add(new Equipment("E004", "影刃", EquipmentCategory.ATTACK, 2070, "+40%攻击速度, +20%暴击率"));
        equipments.add(new Equipment("E005", "破晓", EquipmentCategory.ATTACK, 3400, "+100物理攻击, +35%攻速, +40%暴击"));
        equipments.add(new Equipment("E006", "回响之杖", EquipmentCategory.MAGIC, 2100, "+240法术攻击, +7%移速"));
        equipments.add(new Equipment("E007", "博学者之怒", EquipmentCategory.MAGIC, 2300, "+400法术攻击, 毁灭被动"));
        equipments.add(new Equipment("E008", "虚无法杖", EquipmentCategory.MAGIC, 2110, "+240法术攻击, +45%法穿"));
        equipments.add(new Equipment("E009", "噬神之书", EquipmentCategory.MAGIC, 2090, "+180法术攻击, +25%法术吸血"));
        equipments.add(new Equipment("E010", "辉月", EquipmentCategory.MAGIC, 1990, "+160法术攻击, 主动无敌"));
        equipments.add(new Equipment("E011", "极寒风暴", EquipmentCategory.DEFENSE, 2100, "+360物理防御, +20%冷却"));
        equipments.add(new Equipment("E012", "魔女斗篷", EquipmentCategory.DEFENSE, 2120, "+360法术防御, +1000最大生命"));
        equipments.add(new Equipment("E013", "不祥征兆", EquipmentCategory.DEFENSE, 2180, "+270物理防御, +1200最大生命"));
        equipments.add(new Equipment("E014", "红莲斗篷", EquipmentCategory.DEFENSE, 1830, "+240物理防御, +1000生命, 灼烧"));
        equipments.add(new Equipment("E015", "霸者重装", EquipmentCategory.DEFENSE, 2070, "+2000最大生命, 脱战回血"));
        equipments.add(new Equipment("E016", "急速战靴", EquipmentCategory.MOVE, 710, "+30%攻击速度"));
        equipments.add(new Equipment("E017", "抵抗之靴", EquipmentCategory.MOVE, 710, "+110法术防御, +35%韧性"));
        equipments.add(new Equipment("E018", "冷静之靴", EquipmentCategory.MOVE, 710, "+15%冷却缩减"));
        equipments.add(new Equipment("E019", "贪婪之噬", EquipmentCategory.JUNGLE, 2160, "+60物理攻击, +8%移速, 打野刀"));
        equipments.add(new Equipment("E020", "符文大剑", EquipmentCategory.JUNGLE, 2160, "+100法术攻击, +400法力, 打野刀"));

        // ==================== 15 英雄 ====================
        Hero h01 = new Hero("H01", "赵云", "苍天翔龙", HeroType.WARRIOR, 18888); h01.setWinRate(52);
        Hero h02 = new Hero("H02", "吕布", "无双之魔", HeroType.WARRIOR, 18888); h02.setWinRate(50);
        Hero h03 = new Hero("H03", "花木兰", "传说之刃", HeroType.WARRIOR, 18888); h03.setWinRate(51);
        Hero h04 = new Hero("H04", "诸葛亮", "绝代智谋", HeroType.MAGE, 18888); h04.setWinRate(53);
        Hero h05 = new Hero("H05", "貂蝉", "绝世舞姬", HeroType.MAGE, 18888); h05.setWinRate(49);
        Hero h06 = new Hero("H06", "安琪拉", "暗夜萝莉", HeroType.MAGE, 13888); h06.setWinRate(48);
        Hero h07 = new Hero("H07", "后羿", "半神之弓", HeroType.ARCHER, 13888); h07.setWinRate(50);
        Hero h08 = new Hero("H08", "孙尚香", "千金重弩", HeroType.ARCHER, 18888); h08.setWinRate(54);
        Hero h09 = new Hero("H09", "马可波罗", "远游之枪", HeroType.ARCHER, 13888); h09.setWinRate(47);
        Hero h10 = new Hero("H10", "李白", "青莲剑仙", HeroType.ASSASSIN, 18888); h10.setWinRate(46);
        Hero h11 = new Hero("H11", "兰陵王", "暗影刀锋", HeroType.ASSASSIN, 13888); h11.setWinRate(52);
        Hero h12 = new Hero("H12", "庄周", "逍遥幻梦", HeroType.SUPPORT, 13888); h12.setWinRate(55);
        Hero h13 = new Hero("H13", "蔡文姬", "天籁弦音", HeroType.SUPPORT, 13888); h13.setWinRate(53);
        Hero h14 = new Hero("H14", "廉颇", "正义爆裂", HeroType.TANK, 13888); h14.setWinRate(51);
        Hero h15 = new Hero("H15", "白起", "最终兵器", HeroType.TANK, 13888); h15.setWinRate(50);
        heroes.add(h01); heroes.add(h02); heroes.add(h03); heroes.add(h04); heroes.add(h05);
        heroes.add(h06); heroes.add(h07); heroes.add(h08); heroes.add(h09); heroes.add(h10);
        heroes.add(h11); heroes.add(h12); heroes.add(h13); heroes.add(h14); heroes.add(h15);

        // ==================== 10 玩家（每个≥3英雄）====================
        Player p01 = new Player("P01", "admin", "123456", "王者管理员");
        p01.setLevel(30); p01.setScore(9999);
        p01.getHeroIds().addAll(Arrays.asList("H01","H04","H12","H08"));
        Player p02 = new Player("P02", "xiaoming", "123456", "明月几时有");
        p02.setLevel(28); p02.setScore(8520);
        p02.getHeroIds().addAll(Arrays.asList("H02","H05","H07","H13"));
        Player p03 = new Player("P03", "zhangfei", "123456", "燕人张飞");
        p03.setLevel(25); p03.setScore(7210);
        p03.getHeroIds().addAll(Arrays.asList("H03","H10","H14","H01"));
        Player p04 = new Player("P04", "liubei", "123456", "仁德之君");
        p04.setLevel(22); p04.setScore(6340);
        p04.getHeroIds().addAll(Arrays.asList("H06","H09","H11"));
        Player p05 = new Player("P05", "guanyu", "123456", "武圣关羽");
        p05.setLevel(27); p05.setScore(9130);
        p05.getHeroIds().addAll(Arrays.asList("H08","H02","H15","H05"));
        Player p06 = new Player("P06", "diaochan", "123456", "绝世舞姬本人");
        p06.setLevel(20); p06.setScore(5120);
        p06.getHeroIds().addAll(Arrays.asList("H05","H12","H03"));
        Player p07 = new Player("P07", "caocao", "123456", "乱世奸雄");
        p07.setLevel(26); p07.setScore(7890);
        p07.getHeroIds().addAll(Arrays.asList("H14","H04","H09","H11"));
        Player p08 = new Player("P08", "zhouyu", "123456", "大都督");
        p08.setLevel(19); p08.setScore(4250);
        p08.getHeroIds().addAll(Arrays.asList("H06","H13","H10"));
        Player p09 = new Player("P09", "sunquan", "123456", "碧眼儿");
        p09.setLevel(24); p09.setScore(6780);
        p09.getHeroIds().addAll(Arrays.asList("H07","H01","H15","H02"));
        Player p10 = new Player("P10", "zhenji", "123456", "洛神");
        p10.setLevel(21); p10.setScore(5430);
        p10.getHeroIds().addAll(Arrays.asList("H04","H08","H05","H12"));
        players.add(p01); players.add(p02); players.add(p03); players.add(p04); players.add(p05);
        players.add(p06); players.add(p07); players.add(p08); players.add(p09); players.add(p10);

        // 英雄关联玩家
        h01.setOwnerPlayerId("P01"); h04.setOwnerPlayerId("P01"); h12.setOwnerPlayerId("P01"); h08.setOwnerPlayerId("P01");
        h02.setOwnerPlayerId("P02"); h05.setOwnerPlayerId("P02"); h07.setOwnerPlayerId("P02"); h13.setOwnerPlayerId("P02");
        h03.setOwnerPlayerId("P03"); h10.setOwnerPlayerId("P03"); h14.setOwnerPlayerId("P03"); h01.setOwnerPlayerId("P03");
        h06.setOwnerPlayerId("P04"); h09.setOwnerPlayerId("P04"); h11.setOwnerPlayerId("P04");
        h08.setOwnerPlayerId("P05"); h02.setOwnerPlayerId("P05"); h15.setOwnerPlayerId("P05"); h05.setOwnerPlayerId("P05");
        h05.setOwnerPlayerId("P06"); h12.setOwnerPlayerId("P06"); h03.setOwnerPlayerId("P06");
        h14.setOwnerPlayerId("P07"); h04.setOwnerPlayerId("P07"); h09.setOwnerPlayerId("P07"); h11.setOwnerPlayerId("P07");
        h06.setOwnerPlayerId("P08"); h13.setOwnerPlayerId("P08"); h10.setOwnerPlayerId("P08");
        h07.setOwnerPlayerId("P09"); h01.setOwnerPlayerId("P09"); h15.setOwnerPlayerId("P09"); h02.setOwnerPlayerId("P09");
        h04.setOwnerPlayerId("P10"); h08.setOwnerPlayerId("P10"); h05.setOwnerPlayerId("P10"); h12.setOwnerPlayerId("P10");

        // 英雄分配装备（每个英雄≥2件）
        h01.getEquipIds().addAll(Arrays.asList("E001","E002","E003"));
        h02.getEquipIds().addAll(Arrays.asList("E001","E011","E012"));
        h03.getEquipIds().addAll(Arrays.asList("E003","E004","E005"));
        h04.getEquipIds().addAll(Arrays.asList("E006","E007","E010"));
        h05.getEquipIds().addAll(Arrays.asList("E006","E008","E009"));
        h06.getEquipIds().addAll(Arrays.asList("E007","E010","E008"));
        h07.getEquipIds().addAll(Arrays.asList("E001","E004","E016"));
        h08.getEquipIds().addAll(Arrays.asList("E003","E005","E016"));
        h09.getEquipIds().addAll(Arrays.asList("E001","E003","E017"));
        h10.getEquipIds().addAll(Arrays.asList("E001","E005","E017"));
        h11.getEquipIds().addAll(Arrays.asList("E002","E019","E017"));
        h12.getEquipIds().addAll(Arrays.asList("E012","E015","E018"));
        h13.getEquipIds().addAll(Arrays.asList("E012","E011","E018"));
        h14.getEquipIds().addAll(Arrays.asList("E011","E013","E014"));
        h15.getEquipIds().addAll(Arrays.asList("E014","E015","E013"));

        // ==================== 3 队伍 ====================
        Team t1 = new Team("T01", "王者之师");
        t1.getPlayerIds().addAll(Arrays.asList("P01","P03","P05","P07"));
        Team t2 = new Team("T02", "荣耀战队");
        t2.getPlayerIds().addAll(Arrays.asList("P02","P04","P06","P08"));
        Team t3 = new Team("T03", "巅峰联盟");
        t3.getPlayerIds().addAll(Arrays.asList("P09","P10"));
        teams.add(t1); teams.add(t2); teams.add(t3);

        // ==================== 10 比赛记录 ====================
        MatchRecord m01 = new MatchRecord("M01", "T01", "T02", "T01");
        m01.getHeroIds().addAll(Arrays.asList("H01","H03","H08","H14","H10","H02","H06","H07","H13","H11"));
        m01.setMatchTime(java.time.LocalDateTime.of(2026,5,1,14,0));
        matches.add(m01);
        MatchRecord m02 = new MatchRecord("M02", "T02", "T03", "T02");
        m02.getHeroIds().addAll(Arrays.asList("H02","H06","H09","H13","H10","H04","H07","H12","H15","H11"));
        m02.setMatchTime(java.time.LocalDateTime.of(2026,5,3,15,30));
        matches.add(m02);
        MatchRecord m03 = new MatchRecord("M03", "T01", "T03", "T01");
        m03.getHeroIds().addAll(Arrays.asList("H01","H08","H12","H14","H10","H04","H07","H15","H09","H11"));
        m03.setMatchTime(java.time.LocalDateTime.of(2026,5,5,19,0));
        matches.add(m03);
        MatchRecord m04 = new MatchRecord("M04", "T02", "T01", "T01");
        m04.getHeroIds().addAll(Arrays.asList("H05","H09","H11","H13","H06","H03","H01","H15","H14","H08"));
        m04.setMatchTime(java.time.LocalDateTime.of(2026,5,8,20,0));
        matches.add(m04);
        MatchRecord m05 = new MatchRecord("M05", "T03", "T02", "T03");
        m05.getHeroIds().addAll(Arrays.asList("H04","H07","H12","H09","H15","H02","H06","H11","H13","H05"));
        m05.setMatchTime(java.time.LocalDateTime.of(2026,5,10,18,30));
        matches.add(m05);
        MatchRecord m06 = new MatchRecord("M06", "T01", "T02", "T01");
        m06.getHeroIds().addAll(Arrays.asList("H01","H03","H08","H12","H14","H02","H05","H09","H13","H10"));
        m06.setMatchTime(java.time.LocalDateTime.of(2026,5,12,21,0));
        matches.add(m06);
        MatchRecord m07 = new MatchRecord("M07", "T03", "T01", "T03");
        m07.getHeroIds().addAll(Arrays.asList("H04","H07","H11","H15","H12","H01","H03","H08","H14","H10"));
        m07.setMatchTime(java.time.LocalDateTime.of(2026,5,15,14,0));
        matches.add(m07);
        MatchRecord m08 = new MatchRecord("M08", "T02", "T03", "T02");
        m08.getHeroIds().addAll(Arrays.asList("H02","H05","H06","H10","H13","H04","H09","H11","H12","H15"));
        m08.setMatchTime(java.time.LocalDateTime.of(2026,5,18,16,0));
        matches.add(m08);
        MatchRecord m09 = new MatchRecord("M09", "T01", "T03", "T01");
        m09.getHeroIds().addAll(Arrays.asList("H01","H08","H14","H03","H12","H04","H07","H09","H11","H15"));
        m09.setMatchTime(java.time.LocalDateTime.of(2026,5,20,19,30));
        matches.add(m09);
        MatchRecord m10 = new MatchRecord("M10", "T02", "T01", "T02");
        m10.getHeroIds().addAll(Arrays.asList("H02","H05","H06","H13","H10","H01","H03","H08","H14","H12"));
        m10.setMatchTime(java.time.LocalDateTime.of(2026,5,22,20,0));
        matches.add(m10);
    }
}
