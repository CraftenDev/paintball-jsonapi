package de.craften.plugins.jsonapiextensions;

import java.util.HashMap;

public class RankedPaintballPlayer extends PaintballPlayer {
    private int rank;

    public RankedPaintballPlayer(String name, int hitquote, int rounds, int teamattacks, int hits, int defeats, int airstrikes, int deaths, int kd, int money, int moneySpent, int shots, int kills, int draws, int points, int grenades, int wins, int rank) {
        super(name, hitquote, rounds, teamattacks, hits, defeats, airstrikes, deaths, kd, money, moneySpent, shots, kills, draws, points, grenades, wins);
        this.rank = rank;
    }

    @Override
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
        h.put("rank", getRank());
        return h;
    }

    public int getRank() {
        return rank;
    }
}
