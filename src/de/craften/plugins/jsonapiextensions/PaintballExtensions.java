package de.craften.plugins.jsonapiextensions;

import com.alecgorge.minecraft.jsonapi.api.APIMethodName;
import com.alecgorge.minecraft.jsonapi.api.JSONAPICallHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PaintballExtensions implements JSONAPICallHandler {
    @Override
    public boolean willHandle(APIMethodName apiMethodName) {
        return apiMethodName.matches("getPaintballPlayerStats") || apiMethodName.matches("getAllPaintballPlayerStats");
    }

    @Override
    public Object handle(APIMethodName apiMethodName, Object[] objects) {
        if (apiMethodName.matches("getPaintballPlayerStats")) {
            try {
                Connection c = getPaintballDatabaseConnection();
                PreparedStatement statement = c.prepareStatement("SELECT * FROM players WHERE name = ?");
                statement.setString(1, (String) objects[0]);
                ResultSet rs = statement.executeQuery();

                PaintballPlayer result = null;
                if (rs.next()) {
                    result = new PaintballPlayer(
                            rs.getString(9),
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getInt(3),
                            rs.getInt(4),
                            rs.getInt(5),
                            rs.getInt(6),
                            rs.getInt(7),
                            rs.getInt(8),
                            rs.getInt(10),
                            rs.getInt(11),
                            rs.getInt(12),
                            rs.getInt(13),
                            rs.getInt(14),
                            rs.getInt(15),
                            rs.getInt(16),
                            rs.getInt(17)
                    );
                }

                rs.close();
                c.close();

                if (result != null)
                    return result.toHashMap();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (apiMethodName.matches("getAllPaintballPlayerStats")) {
            try {
                Connection c = getPaintballDatabaseConnection();
                PreparedStatement statement = c.prepareStatement("SELECT * FROM players");
                ResultSet rs = statement.executeQuery();

                ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
                while (rs.next()) {
                    result.add(new PaintballPlayer(
                            rs.getString(9),
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getInt(3),
                            rs.getInt(4),
                            rs.getInt(5),
                            rs.getInt(6),
                            rs.getInt(7),
                            rs.getInt(8),
                            rs.getInt(10),
                            rs.getInt(11),
                            rs.getInt(12),
                            rs.getInt(13),
                            rs.getInt(14),
                            rs.getInt(15),
                            rs.getInt(16),
                            rs.getInt(17)
                    ).toHashMap());
                }

                rs.close();
                c.close();

                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private Connection getPaintballDatabaseConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:plugins/Paintball/pbdata_110.db");
        c.setAutoCommit(false);
        return c;
    }
}
