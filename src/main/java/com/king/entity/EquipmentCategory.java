package com.king.entity;

public enum EquipmentCategory {
    ATTACK("攻击"),
    MAGIC("法术"),
    DEFENSE("防御"),
    MOVE("移动"),
    JUNGLE("打野");

    private final String label;

    EquipmentCategory(String label) {
        this.label = label;
    }

    public String getLabel() { return label; }

    public static EquipmentCategory fromLabel(String label) {
        for (EquipmentCategory c : values()) {
            if (c.label.equals(label)) return c;
        }
        throw new IllegalArgumentException("未知装备类别: " + label);
    }
}
