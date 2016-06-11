package net.terrocidepvp.unglitchme.commands;

import net.terrocidepvp.unglitchme.UnglitchMe;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {

    private UnglitchMe plugin;

    public CommandManager(UnglitchMe plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("unglitchme.use")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
            return true;
        }

        if (args.length >= 1) {
            // Check if name is valid.
            String playerName = args[0];
            if (playerName.length() > 16) {
                sender.sendMessage(ChatColor.RED + "Invalid player name! Too long.");
                return true;
            }
            // Teleport straight if they're online.
            Player player = plugin.getServer().getPlayer(playerName);
            if (player != null) {
                player.teleport(plugin.getSchedule().getLocation());
                plugin.getTeleportedMsg().forEach(player::sendMessage);
                return true;
            }
            // Add name to scheduled players.
            plugin.getSchedule().getScheduledPlayers().add(playerName.toLowerCase());
            plugin.getSchedule().writeToDisk();
            sender.sendMessage(ChatColor.GREEN + playerName + ChatColor.GRAY + " has been scheduled for a teleport once they log in!");
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid usage! Try '/unglitchme {playerName}' to schedule a teleport to spawn once they log in!");
            return true;
        }
    }
}
