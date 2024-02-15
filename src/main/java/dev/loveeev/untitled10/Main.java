package dev.loveeev.untitled10;

import dev.loveeev.untitled10.command.currency;
import dev.loveeev.untitled10.database.EconomyManager;
import dev.loveeev.untitled10.database.MySQL;
import dev.loveeev.untitled10.layer.SpigotServiceLayer;
import dev.loveeev.untitled10.layer.UtilityLayer;
import dev.loveeev.untitled10.util.ChatUtility;
import lombok.Getter;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.SimpleSettings;

public final class Main extends SimplePlugin {

    UtilityLayer utilityLayer;
    SpigotServiceLayer serviceLayer;

    @Getter
    private static Main instance;

    @Override
    public void onPluginStart() {
        SimpleSettings.HIDE_NASHORN_WARNINGS = true;
        instance = this;
        getCommand("currency").setExecutor(new currency());
        getCommand("currency").setTabCompleter(new currency());
        utilityLayer = new UtilityLayer(this);
        utilityLayer.enable();
        serviceLayer = new SpigotServiceLayer(this);
        serviceLayer.enable();
        saveDefaultConfig();
        reloadConfig();
        MySQL.initialize(this);
        if(MySQL.isConnected()) {
            EconomyManager.createTable();
            this.getServer().getConsoleSender().sendMessage("§x§4§0§f§7§8§1[ASTRACORE] База данных подключена.");
        }else{
            this.getServer().getConsoleSender().sendMessage("§x§f§f§4§8§4§8[ASTRACORE] База данных не подключена. Плагин выключиться.");
        }
    }




    @Override
    public void onPluginStop() {
        serviceLayer.disable();
        utilityLayer.disable();
    }

    public ChatUtility getChatUtility() {
        return utilityLayer.getChatUtility();
    }
}
