package com.king.controller;

import com.king.entity.*;
import com.king.service.*;
import java.util.*;

public class MenuController {
    private final AuthService authService = new AuthService();
    private final SearchService searchService = new SearchService();
    private final RankingService rankingService = new RankingService();
    private final DataManageService dataService = new DataManageService();
    private final Scanner scanner = new Scanner(System.in);
    private final Map<String, Runnable> loginMenus = new LinkedHashMap<>();
    private final Map<String, Runnable> mainMenus = new LinkedHashMap<>();
    private final Map<String, Runnable> rankingMenus = new LinkedHashMap<>();
    private final Map<String, Class<?>> entityMenus = new LinkedHashMap<>();

    public MenuController() {
        initMenus();
    }

    private void initMenus() {
        loginMenus.put("1", this::doLogin);
        loginMenus.put("2", this::doRegister);

        mainMenus.put("1", this::showDataManageMenu);
        mainMenus.put("2", this::showSearchMenu);
        mainMenus.put("3", this::showRankingMenu);
        mainMenus.put("4", () -> System.out.println(authService.getCurrentPlayer()));

        rankingMenus.put("1", () -> showRanking("玩家积分排行", rankingService.getPlayerRankingByScore()));
        rankingMenus.put("2", () -> showRanking("玩家等级排行", rankingService.getPlayerRankingByLevel()));
        rankingMenus.put("3", () -> showRanking("英雄胜率排行", rankingService.getHeroRankingByWinRate()));
        rankingMenus.put("4", () -> showRanking("英雄价格排行", rankingService.getHeroRankingByPrice()));
        rankingMenus.put("5", () -> showRanking("装备价格排行", rankingService.getEquipmentRankingByPrice()));

        entityMenus.put("1", Player.class);
        entityMenus.put("2", Hero.class);
        entityMenus.put("3", Equipment.class);
        entityMenus.put("4", Team.class);
        entityMenus.put("5", MatchRecord.class);
    }

    public void start() {
        while (true) {
            if (!authService.isLoggedIn()) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\n========== 王者荣耀管理系统 ==========");
        System.out.println("1. 登录");
        System.out.println("2. 注册");
        System.out.println("0. 退出");
        System.out.print("请选择: ");
        String choice = scanner.nextLine();

        if ("0".equals(choice)) { System.out.println("再见！"); System.exit(0); }
        Runnable action = loginMenus.get(choice);
        if (action != null) action.run();
        else System.out.println("无效选择！");
    }

    private void doLogin() {
        System.out.print("用户名: ");
        String username = scanner.nextLine();
        System.out.print("密码: ");
        String password = scanner.nextLine();
        var opt = authService.login(username, password);
        if (opt.isPresent()) {
            System.out.println("登录成功！欢迎 " + opt.get().getNickname());
        } else {
            System.out.println("用户名或密码错误！");
        }
    }

    private void doRegister() {
        System.out.print("ID (如 P004): ");
        String id = scanner.nextLine();
        System.out.print("用户名: ");
        String username = scanner.nextLine();
        System.out.print("密码: ");
        String password = scanner.nextLine();
        System.out.print("昵称: ");
        String nickname = scanner.nextLine();
        if (authService.register(id, username, password, nickname)) {
            System.out.println("注册成功！");
        } else {
            System.out.println("用户名已存在！");
        }
    }

    private void showMainMenu() {
        System.out.println("\n========== 主菜单 ==========");
        System.out.println("当前玩家: " + authService.getCurrentPlayer().getNickname());
        System.out.println("1. 数据管理");
        System.out.println("2. 搜索");
        System.out.println("3. 排行榜");
        System.out.println("4. 查看个人信息");
        System.out.println("0. 退出登录");
        System.out.print("请选择: ");
        String choice = scanner.nextLine();

        if ("0".equals(choice)) { authService.logout(); return; }
        Runnable action = mainMenus.get(choice);
        if (action != null) action.run();
        else System.out.println("无效选择！");
    }

    private void showDataManageMenu() {
        while (true) {
            System.out.println("\n--- 数据管理 ---");
            for (Map.Entry<String, Class<?>> e : entityMenus.entrySet()) {
                System.out.println(e.getKey() + ". " + dataService.getEntityLabel(e.getValue()) + "管理");
            }
            System.out.println("0. 返回");
            System.out.print("请选择: ");
            String choice = scanner.nextLine();

            if ("0".equals(choice)) return;
            Class<?> clazz = entityMenus.get(choice);
            if (clazz != null) manageEntity(clazz);
            else System.out.println("无效选择！");
        }
    }

    private void manageEntity(Class<?> clazz) {
        String label = dataService.getEntityLabel(clazz);
        System.out.println("\n--- " + label + "列表 ---");
        for (Object obj : dataService.getAll(clazz)) {
            System.out.println(obj);
        }
        System.out.println("按回车键返回...");
        scanner.nextLine();
    }

    private void showSearchMenu() {
        System.out.print("\n请输入搜索关键词: ");
        String keyword = scanner.nextLine();
        Map<String, List<? extends Searchable>> results = searchService.searchAll(keyword);

        for (Map.Entry<String, List<? extends Searchable>> e : results.entrySet()) {
            System.out.println("\n--- " + e.getKey() + "搜索结果 ---");
            if (e.getValue().isEmpty()) {
                System.out.println("无结果");
            } else {
                e.getValue().forEach(System.out::println);
            }
        }
        System.out.println("按回车键返回...");
        scanner.nextLine();
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
            String choice = scanner.nextLine();

            if ("0".equals(choice)) return;
            Runnable action = rankingMenus.get(choice);
            if (action != null) action.run();
            else System.out.println("无效选择！");
        }
    }

    private <T> void showRanking(String title, List<T> list) {
        System.out.println("\n--- " + title + " ---");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("第" + (i + 1) + "名: " + list.get(i));
        }
        System.out.println("按回车键返回...");
        scanner.nextLine();
    }
}
