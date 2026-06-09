package com.king.entity;

public class Equipment {
    private String id;
    private String name;
    private String category;
    private int price;
    private String effect;

    public Equipment() {}

    public Equipment(String id, String name, String category, int price, String effect) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.effect = effect;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public String getEffect() { return effect; }
    public void setEffect(String effect) { this.effect = effect; }

    @Override
    public String toString() {
        return String.format("[%s] %s | 类型:%s | 价格:%d | 效果:%s",
                id, name, category, price, effect);
    }
}
