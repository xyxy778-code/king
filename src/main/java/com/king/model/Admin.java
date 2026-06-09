package com.king.entity;

public class Admin extends Person implements Searchable {
    private String role;

    public Admin() {}

    public Admin(String id, String username, String password, String nickname, String role) {
        super(id, username, password, nickname);
        this.role = role;
    }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toDisplayString() {
        return String.format("[%s] %s | 管理员 | 权限:%s", id, nickname, role);
    }

    @Override
    public String toString() { return toDisplayString(); }
}
