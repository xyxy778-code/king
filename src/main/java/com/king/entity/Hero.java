package com.king.entity;

public class Hero extends BaseEntity implements Searchable, Rankable {
    private String title;
    private String role;
    private int price;
    private int winRate;

    public Hero() { super(); }

    public Hero(String id, String name, String title, String role, int price) {
        super(id, name);
        this.title = title;
        this.role = role;
        this.price = price;
        this.winRate = 50;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public int getWinRate() { return winRate; }
    public void setWinRate(int winRate) { this.winRate = winRate; }

    @Override
    public boolean matches(String keyword) {
        String k = keyword.toLowerCase();
        return id.toLowerCase().contains(k)
                || name.toLowerCase().contains(k)
                || title.toLowerCase().contains(k)
                || role.toLowerCase().contains(k);
    }

    @Override
    public int getRankValue() { return winRate; }

    public int getPriceRankValue() { return price; }

    @Override
    public String toDisplayString() {
        return String.format("[%s] %s %s | 定位:%s | 价格:%d | 胜率:%d%%",
                id, name, title, role, price, winRate);
    }

    @Override
    public String toString() { return toDisplayString(); }
}
