package com.king.service;

import com.king.dao.DataStore;
import com.king.entity.Player;
import java.util.Optional;

public class AuthService {
    private final DataStore store = DataStore.getInstance();
    private Player currentPlayer;

    public Optional<Player> login(String username, String password) {
        for (Player p : store.players.getAll()) {
            if (p.getUsername().equals(username) && p.getPassword().equals(password)) {
                currentPlayer = p;
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public void logout() {
        currentPlayer = null;
    }

    public boolean isLoggedIn() {
        return currentPlayer != null;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
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
