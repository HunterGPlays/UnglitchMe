package net.terrocidepvp.unglitchme.listeners;

import net.terrocidepvp.unglitchme.UnglitchMe;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private UnglitchMe plugin;

    public JoinListener(UnglitchMe plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName().toLowerCase();

        for (String str : plugin.getSchedule().getScheduledPlayers() ) {
            if (str.toLowerCase().equals(playerName)) {
                // Teleport player.
                player.teleport(plugin.getSchedule().getLocation());
                plugin.getTeleportedMsg().forEach(player::sendMessage);
                // Clear from scheduled players.
                plugin.getSchedule().getScheduledPlayers().remove(str);
                plugin.getSchedule().writeToDisk();
                return;
            }
        }
    }

}
