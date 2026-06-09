package com.king.service;

import com.king.dao.DataStore;
import com.king.entity.*;
import java.util.*;

public class DataManageService {
    private final DataStore store = DataStore.getInstance();

    // Player CRUD
    public void addPlayer(Player p) { store.players.put(p.getId(), p); }
    public Player getPlayer(String id) { return store.players.get(id); }
    public Player updatePlayer(Player p) { return store.players.put(p.getId(), p); }
    public Player deletePlayer(String id) { return store.players.remove(id); }
    public Collection<Player> getAllPlayers() { return store.players.values(); }

    // Hero CRUD
    public void addHero(Hero h) { store.heroes.put(h.getId(), h); }
    public Hero getHero(String id) { return store.heroes.get(id); }
    public Hero updateHero(Hero h) { return store.heroes.put(h.getId(), h); }
    public Hero deleteHero(String id) { return store.heroes.remove(id); }
    public Collection<Hero> getAllHeroes() { return store.heroes.values(); }

    // Equipment CRUD
    public void addEquipment(Equipment e) { store.equipments.put(e.getId(), e); }
    public Equipment getEquipment(String id) { return store.equipments.get(id); }
    public Equipment updateEquipment(Equipment e) { return store.equipments.put(e.getId(), e); }
    public Equipment deleteEquipment(String id) { return store.equipments.remove(id); }
    public Collection<Equipment> getAllEquipments() { return store.equipments.values(); }

    // Team CRUD
    public void addTeam(Team t) { store.teams.put(t.getId(), t); }
    public Team getTeam(String id) { return store.teams.get(id); }
    public Team updateTeam(Team t) { return store.teams.put(t.getId(), t); }
    public Team deleteTeam(String id) { return store.teams.remove(id); }
    public Collection<Team> getAllTeams() { return store.teams.values(); }

    // MatchRecord CRUD
    public void addMatch(MatchRecord m) { store.matches.put(m.getId(), m); }
    public MatchRecord getMatch(String id) { return store.matches.get(id); }
    public MatchRecord updateMatch(MatchRecord m) { return store.matches.put(m.getId(), m); }
    public MatchRecord deleteMatch(String id) { return store.matches.remove(id); }
    public Collection<MatchRecord> getAllMatches() { return store.matches.values(); }
}
