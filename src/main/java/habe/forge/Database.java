package habe.forge;

import java.sql.*;

public class Database {

    private static Connection connection;

    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String DATABASE = "forge";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static void connect() {
        try {
            Connection tempConn = DriverManager.getConnection(
                    "jdbc:mysql://" + HOST + ":" + PORT + "?useSSL=false&autoReconnect=true",
                    USER, PASSWORD
            );

            tempConn.createStatement().execute("CREATE DATABASE IF NOT EXISTS " + DATABASE);
            tempConn.close();

            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useSSL=false&autoReconnect=true",
                    USER, PASSWORD
            );
            createTable();
            System.out.println("[FORGE] MySQL Verbunden c:");
        } catch (SQLException e) {
            System.err.println("[FORGE] MySQL Verbindung nicht geschafft :c -> " + e.getMessage());
        }
    }

    private static void createTable() throws SQLException {
        connection.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS player_points (" +
                        "uuid VARCHAR(36) PRIMARY KEY," +
                        "name VARCHAR(16)," +
                        "points INT DEFAULT 0" +
                        ")"
        );
    }

    public static int  getPoints(String uuid) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT points FROM player_points WHERE uuid = ?");
            stmt.setString(1, uuid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("points");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void setPoints(String uuid, String name, int points) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO player_points (uuid, name, points) VALUES (?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE points = ?, name = ?");
            stmt.setString(1, uuid);
            stmt.setString(2, name);
            stmt.setInt(3, points);
            stmt.setInt(4, points);
            stmt.setString(5, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addPoints(String uuid, String name, int amount) {
        int current = getPoints(uuid);
        setPoints(uuid, name, Math.max(0, current + amount));
    }

    public static void removePoints(String uuid, String name, int amount) {
        setPoints(uuid, name, Math.max(0, getPoints(uuid) - amount));
    }

    public static void disconnect() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
