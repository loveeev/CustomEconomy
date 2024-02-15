package dev.loveeev.untitled10.command;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import dev.loveeev.untitled10.Main;
import dev.loveeev.untitled10.database.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class currency implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        MySQL mySQL = MySQL.getInstance();
        Player player = (Player) commandSender;
        if(args.length < 2){
            Main.getInstance().getChatUtility().sendSuccessNotification(player,"Используйте /currency add/del ....");
        }
        if(args.length < 4){
            Main.getInstance().getChatUtility().sendSuccessNotification(player,"Используйте /currency add/del 'название валюты' 'нация для валюты' 'modeldata'");
            return true;
        }
        if(args[0].equalsIgnoreCase("add")){
            String name = args[1];
            Nation nation = TownyAPI.getInstance().getNation(args[2]);
            if(nation==null){
                Main.getInstance().getChatUtility().sendSuccessNotification(player,"Вы неправильно указали нацию.");
                return true;
            }else {
                try (Connection connectionp = mySQL.getCon();
                     PreparedStatement pss = connectionp.prepareStatement("INSERT INTO `AstraEconomy` (`name`, `nation`, `modeldata`) VALUES (?, ?, ?)")) {
                    pss.setString(1, name);
                    pss.setString(2, nation.getName());
                    pss.setInt(3, Integer.parseInt(args[3]));
                    pss.executeUpdate();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Main.getInstance().getChatUtility().sendSuccessNotification(player,"Вы успешно добили новый тип валюты: " + name);

            }
        }
        if(args[0].equalsIgnoreCase("del")) {
            String name = args[1];
            {
                try (Connection connectionp = mySQL.getCon();
                     PreparedStatement pss = connectionp.prepareStatement("DELETE FROM `AstraEconomy` WHERE `name` = ?")) {
                    pss.setString(1, name);
                    pss.executeUpdate();
                    Main.getInstance().getChatUtility().sendSuccessNotification(player,"Вы добавили новый тип валюты");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length == 1){
            return getPartialMatches(args[0],List.of("add","del"));
        }
        if(args[0].equalsIgnoreCase("add")) {
            if (args.length == 2) {
                return getPartialMatches(args[2], TownyAPI.getInstance().getNations().stream().map(nation -> nation.getName()).collect(Collectors.toList()));
            }
        }else{
            if (args.length == 3) {
                return getPartialMatches(args[2], TownyAPI.getInstance().getNations().stream().map(nation -> nation.getName()).collect(Collectors.toList()));
            }
        }

        if(args[0].equalsIgnoreCase("add")) {
            if (args.length == 4) {
                try {
                    PreparedStatement statement = MySQL.getInstance().getCon().prepareStatement("SELECT name FROM AstraEconomy");
                    ResultSet resultSet = statement.executeQuery();
                    List<String> usernames = new ArrayList<>();
                    while (resultSet.next()) {
                        usernames.add(resultSet.getString("name"));
                    }
                    return getPartialMatches(args[2], usernames);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return new ArrayList<>();
    }
    private List<String> getPartialMatches(String arg, List<String> options) {
        return options.stream().filter(option -> option.startsWith(arg)).collect(Collectors.toList());
    }
}
