package de.craften.plugins.jsonapiextensions;

import java.util.HashMap;

public class PaintballPlayer {
    private String name;
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

    public PaintballPlayer(String name, int hitquote, int rounds, int teamattacks, int hits, int defeats, int airstrikes, int deaths, int kd, int money, int moneySpent, int shots, int kills, int draws, int points, int grenades, int wins) {
        this.name = name;
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

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> h = new HashMap<String, Object>();
        h.put("name", getName());
        h.put("hitquote", getHitquote());
        h.put("rounds", getRounds());
        h.put("teamattacks", getTeamattacks());
        h.put("hits", getHits());
        h.put("defeats", getDefeats());
        h.put("airstrikes", getAirstrikes());
        h.put("deaths", getDeaths());
        h.put("kd", getKd());
        h.put("money", getMoney());
        h.put("moneySpent", getMoneySpent());
        h.put("shots", getShots());
        h.put("kills", getKills());
        h.put("draws", getDraws());
        h.put("points", getPoints());
        h.put("grenades", getGrenades());
        h.put("wins", getWins());
        return h;
    }

    public String getName() {
        return name;
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
}
