package io.github.nircuito.blassedgkit;

import io.github.nircuito.blassedgkit.commands.GkitCommand;
import io.github.nircuito.blassedgkit.configuration.KitConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlassedGkit extends JavaPlugin {

    @Override
    public void onEnable() {
        BukkitConsole(ChatColor.YELLOW+"-----------------------");
        BukkitConsole(ChatColor.YELLOW+"  Blassed Gkit Custom  ");
        BukkitConsole(ChatColor.YELLOW+"   Plugin Enabled 1.0  ");
        BukkitConsole(ChatColor.YELLOW+"-----------------------");
        KitConfig.init();
        registerCommands();
        events();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        BukkitConsole(ChatColor.YELLOW+"-----------------------");
        BukkitConsole(ChatColor.YELLOW+"  Blassed Gkit Custom  ");
        BukkitConsole(ChatColor.YELLOW+"   Plugin Disable 1.0  ");
        BukkitConsole(ChatColor.YELLOW+"-----------------------");
    }
    public static void BukkitConsole(String input) {
        Bukkit.getConsoleSender().sendMessage(input);
    }
    public void registerCommands() {
        getCommand("Gkit").setExecutor(new GkitCommand());

    }
    public void events() {

    }
    public static String CC(String Input) {
       return ChatColor.translateAlternateColorCodes('&', Input);

    }
}
