package com.king.controller;

import com.king.dao.FilePersistence;
import com.king.entity.*;
import com.king.service.*;
import com.king.util.DataException;
import com.king.util.InputException;
import java.util.*;

public class MenuController {
    private final AuthService authService = new AuthService();
    private final SearchService searchService = new SearchService();
    private final RankingService rankingService = new RankingService();
    private final DataManageService dataService = new DataManageService();
    private final PlayerQueryService playerQueryService = new PlayerQueryService();
    private final TeamOverviewService teamOverviewService = new TeamOverviewService();
    private final HeroDetailService heroDetailService = new HeroDetailService();
    private final EquipmentStatsService equipmentStatsService = new EquipmentStatsService();
    private final MatchHistoryService matchHistoryService = new MatchHistoryService();
    private final FilePersistence filePersistence = new FilePersistence();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            try {
                if (!authService.isLoggedIn()) {
                    showLoginMenu();
                } else {
                    showMainMenu();
                }
            } catch (InputException e) {
                System.out.println("输入错误: " + e.getMessage());
            } catch (DataException e) {
                System.out.println("数据错误: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("系统错误: " + e.getMessage());
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\n========== 王者荣耀管理系统 ==========");
        System.out.println("1. 登录");
        System.out.println("2. 注册");
        System.out.println("3. 从文件加载数据");
        System.out.println("0. 退出");
        System.out.print("请选择: ");
        String choice = readLine();

        switch (choice) {
            case "1" -> doLogin();
            case "2" -> doRegister();
            case "3" -> doLoadData();
            case "0" -> { System.out.println("再见！"); System.exit(0); }
            default -> throw new InputException("无效选项: " + choice);
        }
    }

    private void doLogin() {
        System.out.print("用户名: ");
        String username = readLine();
        System.out.print("密码: ");
        String password = readLine();
        var opt = authService.login(username, password);
        if (opt.isPresent()) {
            Person user = opt.get();
            String role = (user instanceof Admin) ? "管理员" : "玩家";
            System.out.println("登录成功！欢迎 " + user.getNickname() + " (" + role + ")");
        } else {
            throw new InputException("用户名或密码错误！");
        }
    }

    private void doRegister() {
        System.out.print("ID (如 P004): ");
        String id = readLine();
        System.out.print("用户名: ");
        String username = readLine();
        System.out.print("密码: ");
        String password = readLine();
        System.out.print("昵称: ");
        String nickname = readLine();
        if (!authService.register(id, username, password, nickname)) {
            throw new InputException("用户名已存在！");
        }
        System.out.println("注册成功！");
    }

    private void doLoadData() {
        try {
            filePersistence.loadAll();
            System.out.println("数据加载成功！");
        } catch (DataException e) {
            throw new InputException("读取文件失败: " + e.getMessage(), e);
        }
    }

    private void showMainMenu() {
        Person user = authService.getCurrentUser();
        System.out.println("\n========== 主菜单 ==========");
        System.out.println("当前用户: " + user.getNickname() + " (" + (user instanceof Admin ? "管理员" : "玩家") + ")");
        System.out.println("1. 数据管理");
        System.out.println("2. 搜索");
        System.out.println("3. 排行榜");
        System.out.println("4. 查看个人信息");
        System.out.println("5. 管理员专区");
        System.out.println("6. 保存数据到文件");
        System.out.println("7. 玩家查询（ID/昵称）");
        System.out.println("8. 队伍概览");
        System.out.println("9. 英雄详情");
        System.out.println("10. 装备统计");
        System.out.println("11. 比赛历史");
        System.out.println("0. 退出登录");
        System.out.print("请选择: ");
        String choice = readLine();

        switch (choice) {
            case "1" -> showDataManageMenu();
            case "2" -> showSearchMenu();
            case "3" -> showRankingMenu();
            case "4" -> System.out.println(user);
            case "5" -> { if (authService.isAdmin()) showAdminMenu(); else throw new InputException("无权限！"); }
            case "6" -> doSaveData();
            case "7" -> doPlayerQuery();
            case "8" -> doTeamOverview();
            case "9" -> doHeroDetail();
            case "10" -> doEquipmentStats();
            case "11" -> doMatchHistory();
            case "0" -> { authService.logout(); System.out.println("已退出登录。"); }
            default -> throw new InputException("无效选项: " + choice);
        }
    }

    private void doSaveData() {
        try {
            filePersistence.saveAll();
            System.out.println("数据保存成功！");
        } catch (DataException e) {
            throw new InputException("保存文件失败: " + e.getMessage(), e);
        }
    }

    private void doPlayerQuery() {
        System.out.print("请输入玩家ID或昵称: ");
        String keyword = readLine();
        List<Player> results = playerQueryService.searchPlayer(keyword);
        if (results.isEmpty()) {
            System.out.println("未找到匹配的玩家");
            return;
        }
        if (results.size() == 1) {
            System.out.println(playerQueryService.getPlayerDetail(results.get(0)));
        } else {
            System.out.println("找到多个匹配:");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i));
            }
            System.out.print("请选择序号查看详情（0跳过）: ");
            String sel = readLine();
            try {
                int idx = Integer.parseInt(sel) - 1;
                if (idx >= 0 && idx < results.size()) {
                    System.out.println(playerQueryService.getPlayerDetail(results.get(idx)));
                }
            } catch (NumberFormatException e) {
                System.out.println("无效序号");
            }
        }
        System.out.println("按回车键返回...");
        readLine();
    }

    private void doTeamOverview() {
        System.out.print("请输入队伍ID或名称: ");
        String keyword = readLine();
        List<Team> results = teamOverviewService.searchTeam(keyword);
        if (results.isEmpty()) { System.out.println("未找到匹配的队伍"); return; }
        if (results.size() == 1) {
            System.out.println(teamOverviewService.getTeamDetail(results.get(0)));
        } else {
            System.out.println("找到多个匹配:");
            for (int i = 0; i < results.size(); i++) System.out.println((i+1) + ". " + results.get(i));
            System.out.print("请选择序号（0跳过）: ");
            try {
                int idx = Integer.parseInt(readLine()) - 1;
                if (idx >= 0 && idx < results.size())
                    System.out.println(teamOverviewService.getTeamDetail(results.get(idx)));
            } catch (NumberFormatException e) { System.out.println("无效序号"); }
        }
        System.out.println("按回车键返回..."); readLine();
    }

    private void doHeroDetail() {
        System.out.print("请输入英雄ID或名称: ");
        String keyword = readLine();
        List<Hero> results = heroDetailService.searchHero(keyword);
        if (results.isEmpty()) { System.out.println("未找到匹配的英雄"); return; }
        if (results.size() == 1) {
            System.out.println(heroDetailService.getHeroDetail(results.get(0)));
        } else {
            System.out.println("找到多个匹配:");
            for (int i = 0; i < results.size(); i++) System.out.println((i+1) + ". " + results.get(i));
            System.out.print("请选择序号（0跳过）: ");
            try {
                int idx = Integer.parseInt(readLine()) - 1;
                if (idx >= 0 && idx < results.size())
                    System.out.println(heroDetailService.getHeroDetail(results.get(idx)));
            } catch (NumberFormatException e) { System.out.println("无效序号"); }
        }
        System.out.println("按回车键返回..."); readLine();
    }

    private void doEquipmentStats() {
        while (true) {
            System.out.println("\n--- 装备统计 ---");
            System.out.println("1. 按使用次数排序");
            System.out.println("2. 按胜率影响排序");
            System.out.println("3. 按综合评分排序（公式: 胜率×0.4 + 携带英雄数×2.0 + 使用次数×0.1）");
            System.out.println("0. 返回");
            System.out.print("请选择: ");
            String choice = readLine();
            List<EquipmentStatsService.EquipmentStat> list;
            switch (choice) {
                case "1" -> { list = equipmentStatsService.getStatsByUsage(); for (var s : list) s.useCount = s.equipCount; }
                case "2" -> list = equipmentStatsService.getStatsByWinRate();
                case "3" -> { list = equipmentStatsService.getStatsByCustomScore(); for (var s : list) s.computeCustomScore(); }
                case "0" -> { return; }
                default -> { System.out.println("无效选择！"); continue; }
            }
            boolean first = true;
            for (var s : list) {
                if (first && "2".equals(choice)) {
                    System.out.println("\n排序依据: 装备在比赛中的胜率");
                    first = false;
                } else if (first && "1".equals(choice)) {
                    System.out.println("\n排序依据: 装备被英雄携带的总次数");
                    first = false;
                } else if (first) {
                    System.out.println("\n排序依据: 综合评分 = 胜率×0.4 + 携带英雄数×2.0 + 使用次数×0.1");
                    first = false;
                }
                System.out.println(s);
            }
            System.out.println("按回车键返回..."); readLine();
        }
    }

    private void doMatchHistory() {
        System.out.print("请输入要显示的最新比赛场数N: ");
        try {
            int n = Integer.parseInt(readLine());
            if (n <= 0) { System.out.println("请输入正整数"); return; }
            List<MatchRecord> matches = matchHistoryService.getRecentMatches(n);
            if (matches.isEmpty()) { System.out.println("暂无比赛记录"); return; }
            for (MatchRecord m : matches) {
                System.out.println(matchHistoryService.getMatchDetail(m));
            }
        } catch (NumberFormatException e) {
            System.out.println("无效数字");
        }
        System.out.println("按回车键返回..."); readLine();
    }

    private void showAdminMenu() {
        while (true) {
            System.out.println("\n--- 管理员专区 ---");
            System.out.println("1. 保存数据到文件");
            System.out.println("2. 从文件加载数据");
            System.out.println("0. 返回");
            System.out.print("请选择: ");
            String choice = readLine();

            switch (choice) {
                case "1" -> doSaveData();
                case "2" -> doLoadData();
                case "0" -> { return; }
                default -> System.out.println("无效选择！");
            }
        }
    }

    private void showDataManageMenu() {
        Map<String, Class<?>> entityMenus = new LinkedHashMap<>();
        entityMenus.put("1", Player.class);
        entityMenus.put("2", Admin.class);
        entityMenus.put("3", Hero.class);
        entityMenus.put("4", Equipment.class);
        entityMenus.put("5", Team.class);
        entityMenus.put("6", MatchRecord.class);

        while (true) {
            System.out.println("\n--- 数据管理 ---");
            for (Map.Entry<String, Class<?>> e : entityMenus.entrySet()) {
                System.out.println(e.getKey() + ". " + dataService.getEntityLabel(e.getValue()) + "管理");
            }
            System.out.println("0. 返回");
            System.out.print("请选择: ");
            String choice = readLine();

            if ("0".equals(choice)) return;
            Class<?> clazz = entityMenus.get(choice);
            if (clazz != null) {
                String label = dataService.getEntityLabel(clazz);
                System.out.println("\n--- " + label + "列表 ---");
                Collection<?> all = dataService.getAll(clazz);
                if (all.isEmpty()) {
                    System.out.println("暂无数据");
                } else {
                    for (Object obj : all) {
                        System.out.println(obj);
                    }
                }
                System.out.println("按回车键返回...");
                readLine();
            } else {
                throw new InputException("无效选项: " + choice);
            }
        }
    }

    private void showSearchMenu() {
        System.out.print("\n请输入搜索关键词: ");
        String keyword = readLine();
        Map<String, List<? extends Searchable>> results = searchService.searchAll(keyword);

        boolean hasResult = false;
        for (Map.Entry<String, List<? extends Searchable>> e : results.entrySet()) {
            System.out.println("\n--- " + e.getKey() + "搜索结果 ---");
            if (e.getValue().isEmpty()) {
                System.out.println("无结果");
            } else {
                hasResult = true;
                e.getValue().forEach(System.out::println);
            }
        }
        if (!hasResult) {
            System.out.println("未找到任何匹配结果");
        }
        System.out.println("按回车键返回...");
        readLine();
    }

    private void showRankingMenu() {
        while (true) {
            System.out.println("\n--- 排行榜 ---");
            System.out.println("1. 玩家积分排行");
            System.out.println("2. 玩家等级排行");
            System.out.println("3. 英雄胜率排行");
            System.out.println("4. 英雄价格排行");
            System.out.println("5. 装备价格排行");
            System.out.println("0. 返回");
            System.out.print("请选择: ");
            String choice = readLine();

            switch (choice) {
                case "1" -> showRanking("玩家积分排行", rankingService.getPlayerRankingByScore());
                case "2" -> showRanking("玩家等级排行", rankingService.getPlayerRankingByLevel());
                case "3" -> showRanking("英雄胜率排行", rankingService.getHeroRankingByWinRate());
                case "4" -> showRanking("英雄价格排行", rankingService.getHeroRankingByPrice());
                case "5" -> showRanking("装备价格排行", rankingService.getEquipmentRankingByPrice());
                case "0" -> { return; }
                default -> throw new InputException("无效选项: " + choice);
            }
        }
    }

    private <T> void showRanking(String title, List<T> list) {
        System.out.println("\n--- " + title + " ---");
        if (list.isEmpty()) {
            System.out.println("暂无数据");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println("第" + (i + 1) + "名: " + list.get(i));
            }
        }
        System.out.println("按回车键返回...");
        readLine();
    }

    private String readLine() {
        try {
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            throw new InputException("输入读取失败", e);
        }
    }
}
