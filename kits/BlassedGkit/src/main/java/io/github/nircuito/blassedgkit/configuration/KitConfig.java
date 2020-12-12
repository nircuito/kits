package io.github.nircuito.blassedgkit.configuration;

import io.github.nircuito.blassedgkit.BlassedGkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;


import java.io.File;
import java.io.IOException;

    public class KitConfig {
        private static FileConfiguration player = null;
        private static File playerFile = null;
        private static Plugin plugin = BlassedGkit.getPlugin(BlassedGkit.class);

        public static void init() {
            reload();
            load();
            reload();
        }

        private static void load() {
            getConfig().options().copyDefaults(true);
            save();
        }

        public static void reload() {
            if (playerFile == null) {
                playerFile = new File(plugin.getDataFolder(), "kits.yml");
            }
            player = YamlConfiguration.loadConfiguration(playerFile);
        }

        public static FileConfiguration getConfig() {
            if (player == null) reload();
            return player;
        }

        public static void save() {
            if (player == null || playerFile == null) {
                return;
            }

            try {
                player.save(playerFile);
            } catch (IOException ex) {
                plugin.getLogger().severe("Could not save player.yml to " + playerFile.getAbsolutePath());
                ex.printStackTrace();
            }
        }
    }

