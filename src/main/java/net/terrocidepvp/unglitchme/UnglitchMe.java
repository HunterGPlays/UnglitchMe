package net.terrocidepvp.unglitchme;

import net.terrocidepvp.unglitchme.commands.CommandManager;
import net.terrocidepvp.unglitchme.handlers.Schedule;
import net.terrocidepvp.unglitchme.listeners.JoinListener;
import net.terrocidepvp.unglitchme.utils.ColorCodeUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class UnglitchMe extends JavaPlugin {

    private static UnglitchMe instance;
    public static UnglitchMe getInstance() {
        return instance;
    }

    private Schedule schedule;
    private List<String> teleportedMsg;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        reloadConfig();

        if (!getConfig().isSet("config-version")) {
            getLogger().severe("The config.yml file is broken!");
            getLogger().severe("The plugin failed to detect a 'config-version'.");
            getLogger().severe("The plugin will not load until you generate a new, working config OR if you fix the config.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        int configVersion = 1;
        /*
         Updated for: N/A
         Release: v1.0.0
        */
        if (getConfig().getInt("config-version") != configVersion) {
            getLogger().severe("Your config is outdated!");
            getLogger().severe("The plugin will not load unless you change the config version to " + configVersion + ".");
            getLogger().severe("This means that you will need to reset your config, as there may have been major changes to the plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("Loading values from config...");
        teleportedMsg = ColorCodeUtil.translate(getConfig().getStringList("plugin-messages.teleported-player"));
        schedule = Schedule.createSchedule(this);
        getServer().getScheduler().runTaskTimer(this, () -> schedule.writeToDisk(), 20L, 1200L);

        getLogger().info("Loading listeners...");
        CommandManager commandManager = new CommandManager(this);
        getCommand("unglitch").setExecutor(commandManager);
        getCommand("unglitchme").setExecutor(commandManager);
        new JoinListener(this);
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public List<String> getTeleportedMsg() {
        return teleportedMsg;
    }
}
