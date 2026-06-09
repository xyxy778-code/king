package com.king.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DevLog implements Identifiable {
    private String id;
    private LocalDateTime time;
    private String aiTool;
    private String role;
    private String promptContent;
    private String aiAnswerSummary;
    private boolean adopted;
    private String gitCommit;

    public DevLog() {}

    public DevLog(String id, String aiTool, String role, String promptContent,
                  String aiAnswerSummary, boolean adopted, String gitCommit) {
        this.id = id;
        this.time = LocalDateTime.now();
        this.aiTool = aiTool;
        this.role = role;
        this.promptContent = promptContent;
        this.aiAnswerSummary = aiAnswerSummary;
        this.adopted = adopted;
        this.gitCommit = gitCommit;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDateTime getTime() { return time; }
    public void setTime(LocalDateTime time) { this.time = time; }
    public String getAiTool() { return aiTool; }
    public void setAiTool(String aiTool) { this.aiTool = aiTool; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getPromptContent() { return promptContent; }
    public void setPromptContent(String promptContent) { this.promptContent = promptContent; }
    public String getAiAnswerSummary() { return aiAnswerSummary; }
    public void setAiAnswerSummary(String aiAnswerSummary) { this.aiAnswerSummary = aiAnswerSummary; }
    public boolean isAdopted() { return adopted; }
    public void setAdopted(boolean adopted) { this.adopted = adopted; }
    public String getGitCommit() { return gitCommit; }
    public void setGitCommit(String gitCommit) { this.gitCommit = gitCommit; }

    public String toDisplayString() {
        return String.format("[%s] %s | AI:%s | 角色:%s | prompt:%s | 采纳:%s | commit:%s",
                id, time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                aiTool, role, truncate(promptContent, 20),
                adopted ? "是" : "否",
                gitCommit != null ? truncate(gitCommit, 12) : "无");
    }

    private String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max) + "...";
    }

    @Override
    public String toString() { return toDisplayString(); }
}
