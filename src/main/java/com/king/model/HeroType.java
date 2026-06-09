package com.king.model;

public enum HeroType {
    WARRIOR("战士"),
    MAGE("法师"),
    ARCHER("射手"),
    ASSASSIN("刺客"),
    SUPPORT("辅助"),
    TANK("坦克");

    private final String label;

    HeroType(String label) {
        this.label = label;
    }

    public String getLabel() { return label; }

    public static HeroType fromLabel(String label) {
        for (HeroType t : values()) {
            if (t.label.equals(label)) return t;
        }
        throw new IllegalArgumentException("未知英雄类型: " + label);
    }
}
