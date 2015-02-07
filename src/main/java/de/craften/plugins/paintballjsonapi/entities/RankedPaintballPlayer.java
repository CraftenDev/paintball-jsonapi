package de.craften.plugins.paintballjsonapi.entities;

import java.util.Map;

public class RankedPaintballPlayer extends PaintballPlayer {
    private int rank;

    public RankedPaintballPlayer(String name, String uuid, int hitquote, int rounds, int teamattacks, int hits, int defeats, int airstrikes, int deaths, int kd, int money, int moneySpent, int shots, int kills, int draws, int points, int grenades, int wins, int rank) {
        super(name, uuid, hitquote, rounds, teamattacks, hits, defeats, airstrikes, deaths, kd, money, moneySpent, shots, kills, draws, points, grenades, wins);
        this.rank = rank;
    }

    public RankedPaintballPlayer(PaintballPlayer player, int rank) {
        super(player.getName(), player.getUuid(), player.getHitquote(), player.getRounds(), player.getTeamattacks(),
                player.getHits(), player.getDefeats(), player.getAirstrikes(), player.getDeaths(), player.getKd(),
                player.getMoney(), player.getMoneySpent(), player.getShots(), player.getKills(), player.getDraws(),
                player.getPoints(), player.getGrenades(), player.getWins());
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public Object jsonize() {
        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map) super.jsonize();
        result.put("rank", getRank());
        return result;
    }
}
