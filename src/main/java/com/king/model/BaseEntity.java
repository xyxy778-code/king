package com.king.model;

public abstract class BaseEntity implements Identifiable {
    protected String id;
    protected String name;

    public BaseEntity() {}

    public BaseEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public abstract String toDisplayString();
}
