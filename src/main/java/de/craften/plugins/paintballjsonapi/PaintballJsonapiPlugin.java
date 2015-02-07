package de.craften.plugins.paintballjsonapi;

import com.alecgorge.minecraft.jsonapi.JSONAPI;
import com.alecgorge.minecraft.jsonapi.api.APIMethodName;
import com.alecgorge.minecraft.jsonapi.api.JSONAPICallHandler;
import de.craften.plugins.paintballjsonapi.entities.PaintballPlayer;
import de.craften.plugins.paintballjsonapi.entities.RankedPaintballPlayer;
import de.craften.plugins.paintballjsonapi.jsonize.Jsonize;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.*;

public class PaintballJsonapiPlugin extends JavaPlugin implements JSONAPICallHandler {
    private PaintballDatabase database = null;
    private List<RankedPaintballPlayer> cachedRankings = null;
    private Map<String, RankedPaintballPlayer> cachedPlayers = null;

    @Override
    public void onEnable() {
        try {
            database = new PaintballDatabase(new File(new File(getDataFolder().getParentFile(), "Paintball"), "pbdata_130.db"));
        } catch (ClassNotFoundException | SQLException e) {
            getLogger().severe("Could not connect to database.");
        }

        if (database != null) {
            JSONAPI jsonapi = (JSONAPI) getServer().getPluginManager().getPlugin("JSONAPI");
            jsonapi.registerAPICallHandler(this);


            getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
                @Override
                public void run() {
                    try {
                        List<RankedPaintballPlayer> newRanking = getRanking();
                        Map<String, RankedPaintballPlayer> newPlayers = new HashMap<>();
                        for (RankedPaintballPlayer player : newRanking) {
                            newPlayers.put(player.getName(), player);
                        }
                        cachedRankings = newRanking;
                        cachedPlayers = newPlayers;
                        getLogger().info("Cached players updated. " + cachedPlayers.size() + " players");
                    } catch (SQLException e) {
                        getLogger().warning("Could not update players.");
                    }
                }
            }, 0, 20 * 60 * 60); //1h
        }
    }

    @Override
    public boolean willHandle(APIMethodName apiMethodName) {
        if (!apiMethodName.getNamespace().matches("paintball"))
            return false;

        if (apiMethodName.getMethodName().matches("player"))
            return true;
        if (apiMethodName.getMethodName().matches("ranking"))
            return true;

        return false;
    }

    @Override
    public Object handle(APIMethodName apiMethodName, Object[] objects) {
        if (apiMethodName.getMethodName().matches("player") && objects.length == 1) {
            try {
                PaintballPlayer player = database.getPlayer((String) objects[0]);
                if (player != null)
                    return player.jsonize();
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else if (apiMethodName.getMethodName().matches("ranking") && objects.length == 2) {
            if (cachedRankings == null) {
                getLogger().warning("Rankings requested but not yet available");
                return false;
            }
            int offset = Integer.parseInt(objects[0].toString());
            int count = Integer.parseInt(objects[1].toString());

            List<RankedPaintballPlayer> ranking = cachedRankings;

            if (offset >= cachedRankings.size()) {
                return new ArrayList<>(0);
            } else {
                return Jsonize.collection(ranking.subList(offset, offset + count > ranking.size() ? ranking.size() : offset + count));
            }
        }

        return false;
    }

    private List<RankedPaintballPlayer> getRanking() throws SQLException {
        List<PaintballPlayer> players = database.getPlayers();
        Collections.sort(players, new PaintballPlayerPointsComparator());

        List<RankedPaintballPlayer> ranking = new ArrayList<>(players.size());

        int last = Integer.MAX_VALUE;
        int place = 0;
        int cnt = 1;
        for (PaintballPlayer player : players) {
            if (player.getPoints() < last) {
                place += cnt;
                cnt = 1;
            } else {
                cnt++;
            }
            last = player.getPoints();
            ranking.add(new RankedPaintballPlayer(player, place));
        }
        return ranking;
    }

    private class PaintballPlayerPointsComparator implements java.util.Comparator<PaintballPlayer> {
        @Override
        public int compare(PaintballPlayer paintballPlayer, PaintballPlayer paintballPlayer2) {
            return paintballPlayer2.getPoints() - paintballPlayer.getPoints();
        }
    }
}
