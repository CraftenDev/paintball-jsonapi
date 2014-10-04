package de.craften.plugins.jsonapiextensions;

import com.alecgorge.minecraft.jsonapi.api.APIMethodName;
import com.alecgorge.minecraft.jsonapi.api.JSONAPICallHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class PaintballExtensions implements JSONAPICallHandler {
    @Override
    public boolean willHandle(APIMethodName apiMethodName) {
        return apiMethodName.matches("getPaintballPlayerStats")
                || apiMethodName.matches("getAllPaintballPlayerStats")
                || apiMethodName.matches("getPaintballRanking");
    }

    @Override
    public Object handle(APIMethodName apiMethodName, Object[] objects) {
        if (apiMethodName.matches("getPaintballPlayerStats")) {
            try {
                PaintballPlayer player = getPlayer((String) objects[0]);
                if (player != null)
                    return player.toHashMap();
                return null;
            } catch (Exception e) {
                getLogger().warning("getPaintballPlayerStats failed");
                e.printStackTrace();
            }
        } else if (apiMethodName.matches("getAllPaintballPlayerStats")) {
            try {
                ArrayList<HashMap<String, Object>> map = new ArrayList<HashMap<String, Object>>();
                for (PaintballPlayer p : getPlayers())
                    map.add(p.toHashMap());
                return map;
            } catch (Exception e) {
                getLogger().warning("getAllPaintballPlayerStats failed");
                e.printStackTrace();
            }
        } else if (apiMethodName.matches("getPaintballRanking")) {
            try {
                ArrayList<HashMap<String, Object>> map = new ArrayList<HashMap<String, Object>>();
                int i = 1;
                for (PaintballPlayer p : getPointsRanking(((Number) objects[0]).intValue(), ((Number) objects[1]).intValue())) {
                    map.add(p.toHashMap());
                    i++;
                }
                return map;
            } catch (Exception e) {
                getLogger().warning("getAllPaintballPlayerStats failed");
                e.printStackTrace();
            }
        }

        return false;
    }

    private static Logger getLogger() {
        return CraftenJsonApiExtender.instance.getServer().getLogger();
    }

    private Connection getDatabase() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            getLogger().warning("SQLite not found!");
        }

        Connection c = DriverManager.getConnection("jdbc:sqlite:plugins/Paintball/pbdata_110.db");
        c.setAutoCommit(false);
        return c;
    }

    /**
     * Gets the paintball stats for the player with the given login.
     *
     * @param login Login of a player
     * @return Paintball stats as HashMap or null if the player doesn't exist
     * @throws SQLException
     */
    private PaintballPlayer getPlayer(String login) throws SQLException {
        Connection c = getDatabase();
        PreparedStatement statement = c.prepareStatement("SELECT *, (SELECT COUNT(*)+1 FROM players p WHERE p.points > q.points)rank FROM players q WHERE name = ?");
        statement.setString(1, login);
        ResultSet rs = statement.executeQuery();

        PaintballPlayer result = null;
        if (rs.next()) {
            result = new RankedPaintballPlayer(
                    rs.getString("name"),
                    rs.getInt("hitquote"),
                    rs.getInt("rounds"),
                    rs.getInt("teamattacks"),
                    rs.getInt("hits"),
                    rs.getInt("defeats"),
                    rs.getInt("airstrikes"),
                    rs.getInt("deaths"),
                    rs.getInt("kd"),
                    rs.getInt("money"),
                    rs.getInt("money_spent"),
                    rs.getInt("shots"),
                    rs.getInt("kills"),
                    rs.getInt("draws"),
                    rs.getInt("points"),
                    rs.getInt("grenades"),
                    rs.getInt("wins"),
                    rs.getInt("rank")
            );
        }

        rs.close();
        c.close();

        return result;
    }

    /**
     * Gets paintball stats for all players.
     *
     * @return List with the paintball stats for all players
     * @throws SQLException
     */
    private Iterable<PaintballPlayer> getPlayers() throws SQLException {
        Connection c = getDatabase();
        PreparedStatement statement = c.prepareStatement("SELECT * FROM players");
        ResultSet rs = statement.executeQuery();

        ArrayList<PaintballPlayer> result = new ArrayList<PaintballPlayer>();
        while (rs.next()) {
            result.add(new PaintballPlayer(
                    rs.getString("name"),
                    rs.getInt("hitquote"),
                    rs.getInt("rounds"),
                    rs.getInt("teamattacks"),
                    rs.getInt("hits"),
                    rs.getInt("defeats"),
                    rs.getInt("airstrikes"),
                    rs.getInt("deaths"),
                    rs.getInt("kd"),
                    rs.getInt("money"),
                    rs.getInt("money_spent"),
                    rs.getInt("shots"),
                    rs.getInt("kills"),
                    rs.getInt("draws"),
                    rs.getInt("points"),
                    rs.getInt("grenades"),
                    rs.getInt("wins")
            ));
        }

        rs.close();
        c.close();

        return result;
    }

    /**
     * Gets the ´limit´ first players in the ranking, sorted by points.
     *
     * @param limit Number of ranks to get
     * @return List with max. ´limit´ players, sorted by rank (0 = 1th, 1 = 2nd, ...)
     */
    private List<PaintballPlayer> getPointsRanking(int offset, int limit) throws SQLException {
        Connection c = getDatabase();
        PreparedStatement statement;
        statement = c.prepareStatement("SELECT * FROM players ORDER BY points DESC");
        ResultSet rs = statement.executeQuery();
        int rank = 0;
        int num = 0;
        int currentPoints = -1;

        ArrayList<PaintballPlayer> result = new ArrayList<PaintballPlayer>(limit);
        while (rs.next()) {
            num++;
            if (rs.getInt("points") < currentPoints || rank == 0) {
                rank = num;
                currentPoints = rs.getInt("points");
            }
            if (num > offset) {
                result.add(new RankedPaintballPlayer(
                        rs.getString("name"),
                        rs.getInt("hitquote"),
                        rs.getInt("rounds"),
                        rs.getInt("teamattacks"),
                        rs.getInt("hits"),
                        rs.getInt("defeats"),
                        rs.getInt("airstrikes"),
                        rs.getInt("deaths"),
                        rs.getInt("kd"),
                        rs.getInt("money"),
                        rs.getInt("money_spent"),
                        rs.getInt("shots"),
                        rs.getInt("kills"),
                        rs.getInt("draws"),
                        rs.getInt("points"),
                        rs.getInt("grenades"),
                        rs.getInt("wins"),
                        rank
                ));
            }
            if (result.size() == limit)
                break;
        }

        rs.close();
        c.close();

        return result;
    }
}