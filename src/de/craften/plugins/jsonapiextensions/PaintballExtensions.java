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
            }
        } else if (apiMethodName.matches("getAllPaintballPlayerStats")) {
            try {
                ArrayList<HashMap<String, Object>> map = new ArrayList<HashMap<String, Object>>();
                for (PaintballPlayer p : getPlayers())
                    map.add(p.toHashMap());
                return map;
            } catch (Exception e) {
                getLogger().warning("getAllPaintballPlayerStats failed");
            }
        } else if (apiMethodName.matches("getPaintballRanking")) {
            try {
                ArrayList<HashMap<String, Object>> map = new ArrayList<HashMap<String, Object>>();
                int i = 1;
                for (PaintballPlayer p : getPointsRanking(Integer.valueOf((String) objects[0]), Integer.valueOf((String) objects[1]))) {
                    HashMap<String, Object> player = p.toHashMap();
                    player.put("rank", i);
                    map.add(player);
                    i++;
                }
                return map;
            } catch (Exception e) {
                getLogger().warning("getAllPaintballPlayerStats failed");
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
        PreparedStatement statement = c.prepareStatement("SELECT * FROM players WHERE name = ?");
        statement.setString(1, login);
        ResultSet rs = statement.executeQuery();

        PaintballPlayer result = null;
        if (rs.next()) {
            result = new PaintballPlayer(
                    rs.getString("name"),
                    rs.getInt("hitquote"),
                    rs.getInt("roungs"),
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
                    rs.getInt("roungs"),
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
        if (limit > 0) {
            statement = c.prepareStatement("SELECT * FROM players ORDER BY points DESC LIMIT ?, ?");
            statement.setInt(1, offset);
            statement.setInt(2, limit);
        } else {
            statement = c.prepareStatement("SELECT * FROM players ORDER BY points DESC");
        }
        ResultSet rs = statement.executeQuery();

        ArrayList<PaintballPlayer> result = new ArrayList<PaintballPlayer>(limit);
        while (rs.next()) {
            result.add(new PaintballPlayer(
                    rs.getString("name"),
                    rs.getInt("hitquote"),
                    rs.getInt("roungs"),
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
}
