package com.king.service;

import com.king.dao.DataStore;
import com.king.entity.Admin;
import com.king.entity.Person;
import com.king.entity.Player;
import java.util.Optional;

public class AuthService {
    private final DataStore store = DataStore.getInstance();
    private Person currentUser;

    public Optional<Person> login(String username, String password) {
        for (Player p : store.players.getAll()) {
            if (p.getUsername().equals(username) && p.getPassword().equals(password)) {
                currentUser = p;
                return Optional.of(p);
            }
        }
        for (Admin a : store.admins.getAll()) {
            if (a.getUsername().equals(username) && a.getPassword().equals(password)) {
                currentUser = a;
                return Optional.of(a);
            }
        }
        return Optional.empty();
    }

    public void logout() {
        currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public Person getCurrentUser() {
        return currentUser;
    }

    public boolean isAdmin() {
        return currentUser instanceof Admin;
    }

    public boolean register(String id, String username, String password, String nickname) {
        for (Player p : store.players.getAll()) {
            if (p.getUsername().equals(username)) return false;
        }
        Player player = new Player(id, username, password, nickname);
        store.players.add(player);
        return true;
    }
}
