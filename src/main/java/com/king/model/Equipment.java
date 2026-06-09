package com.king.entity;

public class Equipment extends BaseEntity implements Searchable, Rankable {
    private EquipmentCategory category;
    private int price;
    private String effect;

    public Equipment() { super(); }

    public Equipment(String id, String name, EquipmentCategory category, int price, String effect) {
        super(id, name);
        this.category = category;
        this.price = price;
        this.effect = effect;
    }

    public EquipmentCategory getCategoryEnum() { return category; }
    public void setCategoryEnum(EquipmentCategory category) { this.category = category; }
    public String getCategory() { return category.getLabel(); }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public String getEffect() { return effect; }
    public void setEffect(String effect) { this.effect = effect; }

    @Override
    public boolean matches(String keyword) {
        String k = keyword.toLowerCase();
        return id.toLowerCase().contains(k)
                || name.toLowerCase().contains(k)
                || category.getLabel().toLowerCase().contains(k);
    }

    @Override
    public int getRankValue() { return price; }

    @Override
    public String toDisplayString() {
        return String.format("[%s] %s | 类型:%s | 价格:%d | 效果:%s",
                id, name, category.getLabel(), price, effect);
    }

    @Override
    public String toString() { return toDisplayString(); }
}
