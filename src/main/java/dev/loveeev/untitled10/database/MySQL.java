package dev.loveeev.untitled10.database;


import dev.loveeev.untitled10.Main;
import dev.loveeev.untitled10.config.config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MySQL {
    private static final Logger logger = LoggerFactory.getLogger(MySQL.class);

    public static String mySQLString;
    public static Double mySQLdouble;
    public static Integer mySQLinteger;
    private static Connection connection;
    private static MySQL instance;
    Main plugin;

    private MySQL() {
        // Приватный конструктор для синглтона
    }
    public static MySQL getInstance() {
        if (instance == null) {
            instance = new MySQL();
        }
        return instance;
    }

    public static boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            // Обработка исключения, если что-то пойдет не так при проверке соединения
            e.printStackTrace(); // или любая другая обработка ошибки
            return false; // Возвращаем false, так как состояние соединения неизвестно
        }
    }

    public static void initialize(JavaPlugin plugin) {
        message();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://" + config.DBHOST + "/" + config.DBNAME;
            connectionUrl += "?autoReconnect=true";
            connection = DriverManager.getConnection(connectionUrl, config.DBUSER, config.DBPASSWORD);
            plugin.getLogger().info("Успешное подключение к базе данных!");

        } catch (SQLException | ClassNotFoundException e) {
            plugin.getLogger().severe("Не удалось подключиться к базе данных: " + e.getMessage());
        }
    }

    public Connection getCon() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ВСЕ ЧТО НИЖЕ НЕ ТРОГАТЬ СУКА , УЕБУ НАХУЙ ЕСЛИ ТОЛЬКО ДЛЯ ЛУЧШЕЙ РАБОТЫ ХИКАРИ САМ МЕТОД НЕ МЕНЯТЬ
    public void update(String database, String table, String where, Object znah, String search) {
        String query = "UPDATE " + database + " SET " + table + " = ? WHERE " + where + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, znah);
            statement.setString(2, search);
            statement.executeUpdate();
        } catch (SQLException e) {
            Main.getInstance().getLogger().severe("Error updating database");
            throw new RuntimeException(e);
        }
    }

    public void updateint(String database, String table, String where, Integer znah, String search) {
        String query = "UPDATE " + database + " SET " + table + " = ? WHERE " + where + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, znah);
            statement.setString(2, search);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void selectstring(String database, String table, String search, String get, String method) {
        String query = "SELECT * FROM `" + database + "` WHERE " + table + " = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, search);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    Main.getInstance().getLogger().warning("Ошибка базы данных.");
                } else {
                    if ("string".equalsIgnoreCase(method)) {
                        MySQL.getInstance().mySQLString = rs.getString(get);
                    } else if ("double".equalsIgnoreCase(method)) {
                        MySQL.getInstance().mySQLdouble = rs.getDouble(get);
                    } else if ("integer".equalsIgnoreCase(method)) {
                        MySQL.getInstance().mySQLinteger = rs.getInt(get);
                    }
                }
            }
        } catch (SQLException e) {
            Main.getInstance().getLogger().severe("Error executing selectString query");
            throw new RuntimeException(e);
        }
    }

    public void selectint(String database, String table, Integer search, String get, String method) {
        String query = "SELECT * FROM `" + database + "` WHERE " + table + " = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, search);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    Main.getInstance().getLogger().warning("Ошибка базы данных.");
                } else {
                    if ("string".equalsIgnoreCase(method)) {
                        MySQL.getInstance().mySQLString = rs.getString(get);
                    } else if ("double".equalsIgnoreCase(method)) {
                        MySQL.getInstance().mySQLdouble = rs.getDouble(get);
                    } else if ("integer".equalsIgnoreCase(method)) {
                        MySQL.getInstance().mySQLinteger = rs.getInt(get);
                    }
                }
            }
        } catch (SQLException e) {
            Main.getInstance().getLogger().severe("Error executing selectInt query");
            throw new RuntimeException(e);
        }
    }

    public static void message(){
        Main.getInstance().getServer().getConsoleSender().sendMessage("§x§d§2§3§5§3§5#########################################################################");
        Main.getInstance().getServer().getConsoleSender().sendMessage("§x§d§2§3§5§3§5# |                                                                   | #");
        Main.getInstance().getServer().getConsoleSender().sendMessage("§x§d§2§3§5§3§5# |                           ASTRA GROUP                             | #");
        Main.getInstance().getServer().getConsoleSender().sendMessage("§x§d§2§3§5§3§5# |                 © 2020-2024 - Creativity Community                | #");
        Main.getInstance().getServer().getConsoleSender().sendMessage("§x§d§2§3§5§3§5# |                                                                   | #");
        Main.getInstance().getServer().getConsoleSender().sendMessage("§x§d§2§3§5§3§5# +-------------------------------------------------------------------+ #");
        Main.getInstance().getServer().getConsoleSender().sendMessage("§x§d§2§3§5§3§5#########################################################################");
        Main.getInstance().getServer().getConsoleSender().sendMessage("§x§d§2§3§5§3§5# +-------------------------------------------------------------------+ #");
        Main.getInstance().getServer().getConsoleSender().sendMessage("§x§d§2§3§5§3§5# |                  Web-Site: https://astraworld.su                  | #");
        Main.getInstance().getServer().getConsoleSender().sendMessage("§x§d§2§3§5§3§5# |                   VK: https://astraworld.su/vk                    | #");
        Main.getInstance().getServer().getConsoleSender().sendMessage("§x§d§2§3§5§3§5# |              Discord: https://astraworld.su/discord               | #");
        Main.getInstance().getServer().getConsoleSender().sendMessage("§x§d§2§3§5§3§5# +-------------------------------------------------------------------+ #");
        Main.getInstance().getServer().getConsoleSender().sendMessage("§x§d§2§3§5§3§5#########################################################################");
    }
}
