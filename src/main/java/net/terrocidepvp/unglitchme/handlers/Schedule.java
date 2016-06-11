package net.terrocidepvp.unglitchme.handlers;

import net.terrocidepvp.unglitchme.UnglitchMe;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class Schedule {

    private List<String> scheduledPlayers = new ArrayList<>();
    private Location location;
    private Config config;

    private Schedule(UnglitchMe plugin) {
        config = new Config("players.yml", plugin);
        scheduledPlayers = config.getConfig().getStringList("queued-players");

        String worldName = plugin.getConfig().getString("teleport-to.world");
        if (worldName == null) {
            plugin.getLogger().severe("The world in the config is null! Disabling.");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }
        World world = plugin.getServer().getWorld(worldName);
        if (world == null) {
            plugin.getLogger().severe("The world in the config does not exist! Disabling.");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        if (plugin.getConfig().getBoolean("teleport-to.use-spawn-point")) {
            location = world.getSpawnLocation();
        } else {
            location = new Location(world,
                    plugin.getConfig().getDouble("teleport-to.if-not-use-this-instead.x"),
                    plugin.getConfig().getDouble("teleport-to.if-not-use-this-instead.y"),
                    plugin.getConfig().getDouble("teleport-to.if-not-use-this-instead.z"));
        }
    }

    public static Schedule createSchedule(UnglitchMe plugin) {
        return new Schedule(plugin);
    }

    public List<String> getScheduledPlayers() {
        return scheduledPlayers;
    }

    public Location getLocation() {
        return location;
    }

    public void writeToDisk() {
        config.set("queued-players", scheduledPlayers);
        config.save();
    }

}
