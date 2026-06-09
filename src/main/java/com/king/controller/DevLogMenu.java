package com.king.controller;

import com.king.entity.DevLog;
import com.king.service.DevLogService;
import com.king.util.InputException;
import java.util.*;

public class DevLogMenu {
    private final DevLogService devLogService = new DevLogService();
    private final Scanner scanner = new Scanner(System.in);

    public void show() {
        while (true) {
            System.out.println("\n--- 开发日志管理 ---");
            System.out.println("1. 记录新的开发日志");
            System.out.println("2. 查看所有开发日志");
            System.out.println("3. 查看日志详情");
            System.out.println("4. 删除日志");
            System.out.println("0. 返回");
            System.out.print("请选择: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> doAddLog();
                case "2" -> doListLogs();
                case "3" -> doViewLog();
                case "4" -> doDeleteLog();
                case "0" -> { return; }
                default -> System.out.println("无效选择！");
            }
        }
    }

    private void doAddLog() {
        String id = devLogService.generateNextId();
        System.out.println("新日志ID: " + id);

        System.out.print("AI工具 (如: opencode/deepseek-chat): ");
        String aiTool = scanner.nextLine();

        System.out.print("角色 (user/assistant): ");
        String role = scanner.nextLine();

        System.out.print("Prompt内容: ");
        String prompt = scanner.nextLine();

        System.out.print("AI回答总结: ");
        String summary = scanner.nextLine();

        System.out.print("是否采纳 (y/n): ");
        String yn = scanner.nextLine();
        boolean adopted = "y".equalsIgnoreCase(yn) || "yes".equalsIgnoreCase(yn);

        System.out.print("Git commit (按回车跳过): ");
        String gitCommit = scanner.nextLine();
        if (gitCommit.isBlank()) gitCommit = null;

        DevLog log = new DevLog(id, aiTool, role, prompt, summary, adopted, gitCommit);
        devLogService.addLog(log);
        System.out.println("开发日志已记录！");
    }

    private void doListLogs() {
        List<DevLog> logs = devLogService.getAllLogs();
        if (logs.isEmpty()) {
            System.out.println("暂无开发日志");
            return;
        }
        System.out.println("\n--- 所有开发日志 ---");
        for (DevLog log : logs) {
            System.out.println(log);
        }
        System.out.println("按回车键返回...");
        scanner.nextLine();
    }

    private void doViewLog() {
        System.out.print("输入日志ID: ");
        String id = scanner.nextLine();
        DevLog log = devLogService.getLog(id);
        if (log == null) {
            System.out.println("未找到日志: " + id);
            return;
        }
        System.out.println("\n========== 开发日志详情 ==========");
        System.out.println("ID: " + log.getId());
        System.out.println("时间: " + log.getTime());
        System.out.println("AI工具: " + log.getAiTool());
        System.out.println("角色: " + log.getRole());
        System.out.println("Prompt内容: " + log.getPromptContent());
        System.out.println("AI回答总结: " + log.getAiAnswerSummary());
        System.out.println("是否采纳: " + (log.isAdopted() ? "是" : "否"));
        System.out.println("Git commit: " + (log.getGitCommit() != null ? log.getGitCommit() : "无"));
        System.out.println("按回车键返回...");
        scanner.nextLine();
    }

    private void doDeleteLog() {
        System.out.print("输入要删除的日志ID: ");
        String id = scanner.nextLine();
        if (devLogService.deleteLog(id)) {
            System.out.println("已删除: " + id);
        } else {
            System.out.println("未找到日志: " + id);
        }
    }
}
