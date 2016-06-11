package net.terrocidepvp.unglitchme.handlers;

import net.terrocidepvp.unglitchme.UnglitchMe;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Config {

    private Plugin PLUGIN;
    private String FILENAME;
    private File FOLDER;
    private FileConfiguration config;
    private File configFile;

    public Config(File folder, String filename, UnglitchMe instance) {
        if (!filename.endsWith(".yml")) {
            filename += ".yml";
        }
        FILENAME = filename;
        PLUGIN = instance;
        FOLDER = folder;
        config = null;
        configFile = null;
        reload();
    }

    public Config(String filename, UnglitchMe instance) {
        if (!filename.endsWith(".yml")) {
            filename += ".yml";
        }

        FILENAME = filename;
        PLUGIN = instance;
        FOLDER = PLUGIN.getDataFolder();
        config = null;
        configFile = null;
        reload();
    }

    public FileConfiguration getConfig() {
        if (config == null) {
            reload();
        }
        return config;
    }

    public void reload() {
        if (!FOLDER.exists()) {
            try {
                if (FOLDER.mkdir()) {
                    PLUGIN.getLogger().log(Level.INFO, "Folder " + FOLDER.getName() + " created.");
                } else {
                    PLUGIN.getLogger().log(Level.WARNING, "Unable to create folder " + FOLDER.getName() + ".");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        configFile = new File(FOLDER, FILENAME);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void save() {
        if (config == null || configFile == null)
            return;
        try {
            getConfig().save(configFile);
        } catch (IOException ex) {
            PLUGIN.getLogger().log(Level.WARNING, "Could not save config to " + configFile.getName(), ex);
        }
    }

    public void saveDefaultConfig() {
        if (configFile == null) {
            configFile = new File(PLUGIN.getDataFolder(), FILENAME);
        }

        if (!configFile.exists()) {
            PLUGIN.saveResource(FILENAME, false);
        }
    }

    public void set(String path, Object o) {
        getConfig().set(path, o);
    }

}
