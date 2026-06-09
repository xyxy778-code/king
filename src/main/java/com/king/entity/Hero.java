package com.king.entity;

public class Hero {
    private String id;
    private String name;
    private String title;
    private String role;
    private int price;
    private int winRate;

    public Hero() {}

    public Hero(String id, String name, String title, String role, int price) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.role = role;
        this.price = price;
        this.winRate = 50;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public int getWinRate() { return winRate; }
    public void setWinRate(int winRate) { this.winRate = winRate; }

    @Override
    public String toString() {
        return String.format("[%s] %s %s | 定位:%s | 价格:%d | 胜率:%d%%",
                id, name, title, role, price, winRate);
    }
}
