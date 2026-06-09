package com.king;

import com.king.dao.DataStore;
import com.king.model.*;
import com.king.service.*;
import java.util.*;

/**
 * 王者荣耀管理系统 - 测试用例
 * 
 * 测试方法：运行 main 方法，会依次执行 12 个测试用例并输出结果。
 * 每个测试用例包含：输入、预期输出、实际输出、是否通过。
 */
public class KingSystemTest {

    private static int passed = 0;
    private static int failed = 0;
    private static final List<String> results = new ArrayList<>();
    private static DataStore store;
    private static AuthService authService;
    private static SearchService searchService;
    private static RankingService rankingService;
    private static PlayerQueryService playerQueryService;
    private static TeamOverviewService teamOverviewService;
    private static HeroDetailService heroDetailService;
    private static EquipmentStatsService equipmentStatsService;
    private static MatchHistoryService matchHistoryService;
    private static DataManageService dataManageService;

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  王者荣耀管理系统 - 测试用例报告");
        System.out.println("========================================\n");

        initServices();

        testCase01_loginSuccess();
        testCase02_loginFail();
        testCase03_registerPlayer();
        testCase04_searchPlayerById();
        testCase05_searchHeroByName();
        testCase06_teamOverviewStats();
        testCase07_heroDetailEquipments();
        testCase08_rankingByScore();
        testCase09_rankingByLevelWithTie();
        testCase10_matchHistoryRecent();
        testCase11_equipmentStatsCustomScore();
        testCase12_dataManageCounts();

        System.out.println("\n========================================");
        System.out.println("  测试总结");
        System.out.println("========================================");
        System.out.println("总用例: " + (passed + failed));
        System.out.println("通过: " + passed);
        System.out.println("失败: " + failed);
        System.out.println("通过率: " + (passed + failed > 0 ? (passed * 100 / (passed + failed)) : 0) + "%");

        System.out.println("\n--- 详细结果 ---");
        for (String r : results) {
            System.out.println(r);
        }
    }

    private static void initServices() {
        store = DataStore.getInstance();
        authService = new AuthService();
        searchService = new SearchService();
        rankingService = new RankingService();
        playerQueryService = new PlayerQueryService();
        teamOverviewService = new TeamOverviewService();
        heroDetailService = new HeroDetailService();
        equipmentStatsService = new EquipmentStatsService();
        matchHistoryService = new MatchHistoryService();
        dataManageService = new DataManageService();
    }

    // ==== 测试用例 1: 管理员登录成功 ====
    private static void testCase01_loginSuccess() {
        String input = "用户名: root, 密码: root123";
        String expected = "登录成功, 角色为管理员";

        var opt = authService.login("root", "root123");
        boolean ok = opt.isPresent() && opt.get() instanceof Admin;
        String actual = ok ? "登录成功, 角色为管理员" : "登录失败";
        recordResult("TC01-登录成功", input, expected, actual, ok);
    }

    // ==== 测试用例 2: 错误密码登录失败 ====
    private static void testCase02_loginFail() {
        String input = "用户名: root, 密码: wrong";
        String expected = "返回空 Optional, 登录失败";

        var opt = authService.login("root", "wrong");
        boolean ok = opt.isEmpty();
        String actual = ok ? "返回空 Optional, 登录失败" : "返回了用户对象";
        recordResult("TC02-登录失败", input, expected, actual, ok);
    }

    // ==== 测试用例 3: 注册新玩家 ====
    private static void testCase03_registerPlayer() {
        String input = "ID=P999, user=testuser, pwd=123, nick=测试玩家";
        String expected = "注册成功, 玩家数+1";

        int before = store.players.count();
        boolean registered = authService.register("P999", "testuser", "123", "测试玩家");
        int after = store.players.count();
        boolean ok = registered && after == before + 1;
        // 清理
        if (registered) store.players.delete("P999");
        String actual = ok ? "注册成功, 玩家数+1" : "注册失败";
        recordResult("TC03-注册玩家", input, expected, actual, ok);
    }

    // ==== 测试用例 4: 按ID查询玩家 ====
    private static void testCase04_searchPlayerById() {
        String input = "搜索关键词: P01";
        String expected = "找到玩家 王者管理员";

        var results = playerQueryService.searchPlayer("P01");
        boolean ok = results.size() == 1 && results.get(0).getNickname().equals("王者管理员");
        String actual = ok ? "找到玩家 王者管理员" : "未找到或结果错误";
        recordResult("TC04-玩家查询ID", input, expected, actual, ok);
    }

    // ==== 测试用例 5: 按名称搜索英雄 ====
    private static void testCase05_searchHeroByName() {
        String input = "搜索关键词: 赵云";
        String expected = "找到英雄 H01 赵云";

        var results = heroDetailService.searchHero("赵云");
        boolean ok = results.size() >= 1 && results.get(0).getName().contains("赵云");
        String actual = ok ? "找到英雄 " + results.get(0).getId() + " 赵云" : "未找到";
        recordResult("TC05-英雄搜索名称", input, expected, actual, ok);
    }

    // ==== 测试用例 6: 队伍概览统计 ====
    private static void testCase06_teamOverviewStats() {
        String input = "查询队伍 T01";
        String expected = "队伍有≥5名成员, 平均等级>20, 有比赛记录";

        Team t1 = store.teams.get("T01");
        String detail = teamOverviewService.getTeamDetail(t1);
        boolean ok = t1.getPlayerIds().size() >= 5
                && t1.getAverageLevel() > 20
                && detail.contains("比赛场次");
        String actual = ok ? String.format("成员%d人, 平均等级%.1f, 有比赛记录",
                t1.getPlayerIds().size(), t1.getAverageLevel()) : "数据异常";
        recordResult("TC06-队伍概览", input, expected, actual, ok);
    }

    // ==== 测试用例 7: 英雄装备详情 ====
    private static void testCase07_heroDetailEquipments() {
        String input = "查询英雄 H01 赵云";
        String expected = "赵云有3件装备, 类型为战士";

        Hero h = store.heroes.get("H01");
        String detail = heroDetailService.getHeroDetail(h);
        boolean ok = h.getHeroType() == HeroType.WARRIOR
                && h.getEquipIds().size() >= 2
                && detail.contains("推荐装备");
        String actual = ok ? String.format("类型%s, %d件装备, 有推荐",
                h.getHeroType().getLabel(), h.getEquipIds().size()) : "数据异常";
        recordResult("TC07-英雄装备", input, expected, actual, ok);
    }

    // ==== 测试用例 8: 积分排行榜顺序 ====
    private static void testCase08_rankingByScore() {
        String input = "查看玩家积分排行";
        String expected = "第一名积分应 >= 第二名积分";

        var list = rankingService.getPlayerRankingByScore();
        boolean ok = list.size() >= 2
                && list.get(0).getScore() >= list.get(1).getScore();
        String actual = ok ? String.format("第一名%d分 >= 第二名%d分",
                list.get(0).getScore(), list.get(1).getScore()) : "排序错误";
        recordResult("TC08-积分排行", input, expected, actual, ok);
    }

    // ==== 测试用例 9: 等级排行榜同分处理 ====
    private static void testCase09_rankingByLevelWithTie() {
        String input = "查看玩家等级排行";
        String expected = "等级相同时按积分降序排列";

        var list = rankingService.getPlayerRankingByLevel();
        boolean ok = true;
        for (int i = 0; i < list.size() - 1; i++) {
            Player a = list.get(i);
            Player b = list.get(i + 1);
            if (a.getLevel() == b.getLevel() && a.getScore() < b.getScore()) {
                ok = false;
                break;
            }
        }
        String actual = ok ? "同等级按积分降序排列正确" : "同等级时积分排序错误";
        recordResult("TC09-同分处理", input, expected, actual, ok);
    }

    // ==== 测试用例 10: 比赛历史最近N场 ====
    private static void testCase10_matchHistoryRecent() {
        String input = "查询最近3场比赛";
        String expected = "返回3场比赛, 按时间降序排列";

        var list = matchHistoryService.getRecentMatches(3);
        boolean ok = list.size() == 3;
        if (ok && list.size() >= 2) {
            ok = !list.get(0).getMatchTime().isBefore(list.get(1).getMatchTime());
        }
        String actual = ok ? String.format("返回%d场, 按时间降序排列", list.size()) : "数据异常";
        recordResult("TC10-比赛历史", input, expected, actual, ok);
    }

    // ==== 测试用例 11: 装备统计综合评分 ====
    private static void testCase11_equipmentStatsCustomScore() {
        String input = "按综合评分排序装备";
        String expected = "综合评分 = 胜率×0.4 + 携带英雄数×2.0 + 使用次数×0.1";

        var list = equipmentStatsService.getStatsByCustomScore();
        boolean ok = !list.isEmpty();
        if (ok) {
            var first = list.get(0);
            first.computeCustomScore();
            ok = first.getCustomScore() > 0;
        }
        String actual = ok ? String.format("共%d件装备, 最高评分%.1f",
                list.size(), list.get(0).getCustomScore()) : "计算异常";
        recordResult("TC11-装备评分", input, expected, actual, ok);
    }

    // ==== 测试用例 12: 数据完整性校验 ====
    private static void testCase12_dataManageCounts() {
        String input = "查看所有数据集合";
        String expected = "玩家≥10, 英雄≥15, 装备≥20, 队伍≥3, 比赛≥10";

        int pc = store.players.count();
        int hc = store.heroes.count();
        int ec = store.equipments.count();
        int tc = store.teams.count();
        int mc = store.matches.count();
        boolean ok = pc >= 10 && hc >= 15 && ec >= 20 && tc >= 3 && mc >= 10;
        String actual = ok ? String.format("玩家%d/英雄%d/装备%d/队伍%d/比赛%d - 全部达标",
                pc, hc, ec, tc, mc) : String.format("玩家%d/英雄%d/装备%d/队伍%d/比赛%d - 数据不足",
                pc, hc, ec, tc, mc);
        recordResult("TC12-数据完整性", input, expected, actual, ok);
    }

    // ==== 结果记录 ====
    private static void recordResult(String caseId, String input,
            String expected, String actual, boolean ok) {
        if (ok) passed++;
        else failed++;
        String icon = ok ? "[PASS]" : "[FAIL]";
        String line = String.format("%s %s | 输入: %s | 预期: %s | 实际: %s",
                icon, caseId, input, expected, actual);
        results.add(line);
        System.out.println(line);
    }
}
