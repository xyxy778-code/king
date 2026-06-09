package com.king.entity;

public class Hero extends BaseEntity implements Searchable, Rankable {
    private String title;
    private HeroType heroType;
    private int price;
    private int winRate;
    private String ownerPlayerId;

    public Hero() { super(); }

    public Hero(String id, String name, String title, HeroType heroType, int price) {
        super(id, name);
        this.title = title;
        this.heroType = heroType;
        this.price = price;
        this.winRate = 50;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public HeroType getHeroType() { return heroType; }
    public void setHeroType(HeroType heroType) { this.heroType = heroType; }
    public String getRole() { return heroType.getLabel(); }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public int getWinRate() { return winRate; }
    public void setWinRate(int winRate) { this.winRate = winRate; }
    public String getOwnerPlayerId() { return ownerPlayerId; }
    public void setOwnerPlayerId(String ownerPlayerId) { this.ownerPlayerId = ownerPlayerId; }
    public boolean isOwned() { return ownerPlayerId != null && !ownerPlayerId.isBlank(); }

    @Override
    public boolean matches(String keyword) {
        String k = keyword.toLowerCase();
        return id.toLowerCase().contains(k)
                || name.toLowerCase().contains(k)
                || title.toLowerCase().contains(k)
                || heroType.getLabel().toLowerCase().contains(k);
    }

    @Override
    public int getRankValue() { return winRate; }

    public int getPriceRankValue() { return price; }

    @Override
    public String toDisplayString() {
        return String.format("[%s] %s %s | 定位:%s | 价格:%d | 胜率:%d%%%s",
                id, name, title, heroType.getLabel(), price, winRate,
                isOwned() ? " | 拥有者:" + ownerPlayerId : "");
    }

    @Override
    public String toString() { return toDisplayString(); }
}
