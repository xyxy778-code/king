package com.king.dao;

import com.king.model.*;
import com.king.util.DataException;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FilePersistence {
    private static final String DATA_DIR = "data";
    private static final String PLAYERS_FILE = DATA_DIR + "/players.txt";
    private static final String HEROES_FILE = DATA_DIR + "/heroes.txt";
    private static final String EQUIPMENTS_FILE = DATA_DIR + "/equipments.txt";
    private static final String TEAMS_FILE = DATA_DIR + "/teams.txt";
    private static final String MATCHES_FILE = DATA_DIR + "/matches.txt";
    private static final String ADMINS_FILE = DATA_DIR + "/admins.txt";

    private final DataStore store = DataStore.getInstance();

    public void saveAll() throws DataException {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
            saveEntities(ADMINS_FILE, store.admins.getAll(), this::adminToLine);
            saveEntities(PLAYERS_FILE, store.players.getAll(), this::playerToLine);
            saveEntities(HEROES_FILE, store.heroes.getAll(), this::heroToLine);
            saveEntities(EQUIPMENTS_FILE, store.equipments.getAll(), this::equipmentToLine);
            saveEntities(TEAMS_FILE, store.teams.getAll(), this::teamToLine);
            saveEntities(MATCHES_FILE, store.matches.getAll(), this::matchToLine);
        } catch (IOException e) {
            throw new DataException("保存数据失败", e);
        }
    }

    public void loadAll() throws DataException {
        try {
            if (!Files.exists(Paths.get(DATA_DIR))) return;
            store.admins.storage.clear();
            store.players.storage.clear();
            store.heroes.storage.clear();
            store.equipments.storage.clear();
            store.teams.storage.clear();
            store.matches.storage.clear();

            loadEntities(ADMINS_FILE, this::lineToAdmin, store.admins);
            loadEntities(PLAYERS_FILE, this::lineToPlayer, store.players);
            loadEntities(HEROES_FILE, this::lineToHero, store.heroes);
            loadEntities(EQUIPMENTS_FILE, this::lineToEquipment, store.equipments);
            loadEntities(TEAMS_FILE, this::lineToTeam, store.teams);
            loadEntities(MATCHES_FILE, this::lineToMatch, store.matches);
        } catch (IOException e) {
            throw new DataException("读取数据失败", e);
        }
    }

    // === save helpers ===
    private <T extends Identifiable> void saveEntities(String file, Collection<T> entities,
                                                       java.util.function.Function<T, String> converter) throws IOException {
        List<String> lines = entities.stream().map(converter).toList();
        Files.write(Paths.get(file), lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private <T extends Identifiable> void loadEntities(String file,
                                                       java.util.function.Function<String, T> parser,
                                                       BaseDataAccess<T> accessor) throws IOException {
        Path path = Paths.get(file);
        if (!Files.exists(path)) return;
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            if (line.isBlank()) continue;
            T entity = parser.apply(line);
            if (entity != null) accessor.add(entity);
        }
    }

    // === serialization ===
    private String adminToLine(Admin a) {
        return String.join("|", a.getId(), a.getUsername(), a.getPassword(), a.getNickname(), a.getRole());
    }

    private Admin lineToAdmin(String line) {
        String[] parts = line.split("\\|", 5);
        if (parts.length < 5) return null;
        Admin a = new Admin(parts[0], parts[1], parts[2], parts[3], parts[4]);
        return a;
    }

    private String playerToLine(Player p) {
        return String.join("|", p.getId(), p.getUsername(), p.getPassword(), p.getNickname(),
                String.valueOf(p.getLevel()), String.valueOf(p.getScore()),
                String.join(",", p.getHeroIds()));
    }

    private Player lineToPlayer(String line) {
        String[] parts = line.split("\\|", 7);
        if (parts.length < 7) return null;
        Player p = new Player(parts[0], parts[1], parts[2], parts[3]);
        p.setLevel(Integer.parseInt(parts[4]));
        p.setScore(Integer.parseInt(parts[5]));
        if (!parts[6].isBlank()) {
            p.getHeroIds().addAll(Arrays.asList(parts[6].split(",")));
        }
        return p;
    }

    private String heroToLine(Hero h) {
        return String.join("|", h.getId(), h.getName(), h.getTitle(), h.getHeroType().name(),
                String.valueOf(h.getPrice()), String.valueOf(h.getWinRate()),
                h.getOwnerPlayerId() == null ? "" : h.getOwnerPlayerId());
    }

    private Hero lineToHero(String line) {
        String[] parts = line.split("\\|", 7);
        if (parts.length < 7) return null;
        Hero h = new Hero(parts[0], parts[1], parts[2],
                HeroType.valueOf(parts[3]), Integer.parseInt(parts[4]));
        h.setWinRate(Integer.parseInt(parts[5]));
        if (!parts[6].isBlank()) h.setOwnerPlayerId(parts[6]);
        return h;
    }

    private String equipmentToLine(Equipment e) {
        return String.join("|", e.getId(), e.getName(), e.getCategoryEnum().name(),
                String.valueOf(e.getPrice()), e.getEffect());
    }

    private Equipment lineToEquipment(String line) {
        String[] parts = line.split("\\|", 5);
        if (parts.length < 5) return null;
        return new Equipment(parts[0], parts[1],
                EquipmentCategory.valueOf(parts[2]), Integer.parseInt(parts[3]), parts[4]);
    }

    private String teamToLine(Team t) {
        return String.join("|", t.getId(), t.getName(), String.join(",", t.getPlayerIds()));
    }

    private Team lineToTeam(String line) {
        String[] parts = line.split("\\|", 3);
        if (parts.length < 3) return null;
        Team t = new Team(parts[0], parts[1]);
        if (!parts[2].isBlank()) {
            t.getPlayerIds().addAll(Arrays.asList(parts[2].split(",")));
        }
        return t;
    }

    private String matchToLine(MatchRecord m) {
        return String.join("|", m.getId(), m.getTeamAId(), m.getTeamBId(), m.getWinnerTeamId(),
                m.getMatchTime().toString(), String.join(",", m.getHeroIds()));
    }

    private MatchRecord lineToMatch(String line) {
        String[] parts = line.split("\\|", 6);
        if (parts.length < 6) return null;
        MatchRecord m = new MatchRecord(parts[0], parts[1], parts[2], parts[3]);
        m.setMatchTime(java.time.LocalDateTime.parse(parts[4]));
        if (!parts[5].isBlank()) {
            m.getHeroIds().addAll(Arrays.asList(parts[5].split(",")));
        }
        return m;
    }
}
