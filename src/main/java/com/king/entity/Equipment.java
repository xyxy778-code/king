package com.king.entity;

public class Equipment extends BaseEntity implements Searchable, Rankable {
    private String category;
    private int price;
    private String effect;

    public Equipment() { super(); }

    public Equipment(String id, String name, String category, int price, String effect) {
        super(id, name);
        this.category = category;
        this.price = price;
        this.effect = effect;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public String getEffect() { return effect; }
    public void setEffect(String effect) { this.effect = effect; }

    @Override
    public boolean matches(String keyword) {
        String k = keyword.toLowerCase();
        return id.toLowerCase().contains(k)
                || name.toLowerCase().contains(k)
                || category.toLowerCase().contains(k);
    }

    @Override
    public int getRankValue() { return price; }

    @Override
    public String toDisplayString() {
        return String.format("[%s] %s | 类型:%s | 价格:%d | 效果:%s",
                id, name, category, price, effect);
    }

    @Override
    public String toString() { return toDisplayString(); }
}
