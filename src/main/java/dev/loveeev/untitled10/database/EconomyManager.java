package dev.loveeev.untitled10.database;

import lombok.Getter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EconomyManager {
    public static final String TABLE_NAME = "AstraEconomy";



    public static void createTable() {
        try {
            MySQL mySQL = MySQL.getInstance(); // Убедитесь, что у вас есть корректная реализация
            PreparedStatement st = mySQL.getCon().prepareStatement("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                    "name TEXT PRIMARY KEY NOT NULL, " +
                    "nation TEXT NOT NULL, " +
                    "modeldata INT NOT NULL)"
            );

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database Error,no create table");
        }
    }

}
