package com.king.model;

public abstract class Person extends BaseEntity implements Searchable {
    protected String username;
    protected String password;
    protected String nickname;

    public Person() {}

    public Person(String id, String username, String password, String nickname) {
        super(id, username);
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    @Override
    public boolean matches(String keyword) {
        String k = keyword.toLowerCase();
        return id.toLowerCase().contains(k)
                || username.toLowerCase().contains(k)
                || nickname.toLowerCase().contains(k);
    }
}
