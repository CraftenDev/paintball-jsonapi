package de.craften.plugins.paintballjsonapi.entities;

import de.craften.plugins.paintballjsonapi.jsonize.Jsonizable;

import java.util.HashMap;

public class PaintballPlayer implements Jsonizable {
    private String name;
    private String uuid;
    private int hitquote;
    private int rounds;
    private int teamattacks;
    private int hits;
    private int defeats;
    private int airstrikes;
    private int deaths;
    private int kd;
    private int money;
    private int moneySpent;
    private int shots;
    private int kills;
    private int draws;
    private int points;
    private int grenades;
    private int wins;

    public PaintballPlayer(String name, String uuid, int hitquote, int rounds, int teamattacks, int hits, int defeats, int airstrikes, int deaths, int kd, int money, int moneySpent, int shots, int kills, int draws, int points, int grenades, int wins) {
        this.name = name;
        this.uuid = uuid;
        this.hitquote = hitquote;
        this.rounds = rounds;
        this.teamattacks = teamattacks;
        this.hits = hits;
        this.defeats = defeats;
        this.airstrikes = airstrikes;
        this.deaths = deaths;
        this.kd = kd;
        this.money = money;
        this.moneySpent = moneySpent;
        this.shots = shots;
        this.kills = kills;
        this.draws = draws;
        this.points = points;
        this.grenades = grenades;
        this.wins = wins;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public int getHitquote() {
        return hitquote;
    }

    public int getRounds() {
        return rounds;
    }

    public int getTeamattacks() {
        return teamattacks;
    }

    public int getHits() {
        return hits;
    }

    public int getDefeats() {
        return defeats;
    }

    public int getAirstrikes() {
        return airstrikes;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getKd() {
        return kd;
    }

    public int getMoney() {
        return money;
    }

    public int getMoneySpent() {
        return moneySpent;
    }

    public int getShots() {
        return shots;
    }

    public int getKills() {
        return kills;
    }

    public int getDraws() {
        return draws;
    }

    public int getPoints() {
        return points;
    }

    public int getGrenades() {
        return grenades;
    }

    public int getWins() {
        return wins;
    }

    @Override
    public Object jsonize() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", getName());
        result.put("uuid", getUuid());
        result.put("hitquote", getHitquote());
        result.put("rounds", getRounds());
        result.put("teamattacks", getTeamattacks());
        result.put("hits", getHits());
        result.put("defeats", getDefeats());
        result.put("airstrikes", getAirstrikes());
        result.put("deaths", getDeaths());
        result.put("kd", getKd());
        result.put("money", getMoney());
        result.put("moneySpent", getMoneySpent());
        result.put("shots", getShots());
        result.put("kills", getKills());
        result.put("draws", getDraws());
        result.put("points", getPoints());
        result.put("grenades", getGrenades());
        result.put("wins", getWins());
        return result;
    }
}
