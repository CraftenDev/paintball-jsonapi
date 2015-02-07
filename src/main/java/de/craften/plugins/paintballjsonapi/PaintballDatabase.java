package de.craften.plugins.paintballjsonapi;

import de.craften.plugins.paintballjsonapi.entities.PaintballPlayer;
import de.craften.plugins.paintballjsonapi.entities.RankedPaintballPlayer;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaintballDatabase {
    private File dbFile;

    public PaintballDatabase(File dbFile) throws SQLException, ClassNotFoundException {
        this.dbFile = dbFile;

        Class.forName("org.sqlite.JDBC");
        getConnection();
    }

    private Connection getConnection() throws SQLException {
        Connection c;
        c = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getPath());
        c.setAutoCommit(false);
        return c;
    }

    /**
     * Gets the paintball stats for the player with the given login.
     *
     * @param login Login of a player
     * @return Paintball stats as HashMap or null if the player doesn't exist
     * @throws java.sql.SQLException
     */
    public PaintballPlayer getPlayer(String login) throws SQLException {
        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement("SELECT *, (SELECT COUNT(*)+1 FROM players p WHERE p.points > q.points)rank FROM players q WHERE name = ?")) {
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();

            PaintballPlayer result = null;
            if (rs.next()) {
                result = new RankedPaintballPlayer(
                        rs.getString("name"),
                        rs.getString("uuid"),
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
            return result;
        }
    }

    /**
     * Gets paintball stats for all players.
     *
     * @return List with the paintball stats for all players
     * @throws java.sql.SQLException
     */
    public List<PaintballPlayer> getPlayers() throws SQLException {
        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement("SELECT * FROM players")) {
            ResultSet rs = statement.executeQuery();

            ArrayList<PaintballPlayer> result = new ArrayList<PaintballPlayer>();
            while (rs.next()) {
                result.add(new PaintballPlayer(
                        rs.getString("name"),
                        rs.getString("uuid"),
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
            return result;
        }
    }
}
