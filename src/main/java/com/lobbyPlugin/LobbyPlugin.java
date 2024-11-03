package com.lobbyPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class LobbyPlugin extends JavaPlugin {

    private FileConfiguration config;

    @Override
    public void onEnable() {
        // Load or create the config file
        config = this.getConfig();
        config.options().copyDefaults(true);
        saveConfig();

        // Register commands
        this.getCommand("setlobby").setExecutor(this);
        this.getCommand("lobby").setExecutor(this);
        this.getCommand("l").setExecutor(this); // Register the /l command as an alias
        this.getCommand("sethub").setExecutor(this);
        this.getCommand("hub").setExecutor(this);

        getLogger().info(ChatColor.GREEN + "LobbyPlugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + "LobbyPlugin has been disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;
        String commandName = command.getName().toLowerCase();

        switch (commandName) {
            case "setlobby":
                if (player.hasPermission("lobbyplugin.setlobby")) {
                    setLobby(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission to set the lobby.");
                }
                break;
            case "lobby":
            case "l": // Handle the /l command as well
                if (player.hasPermission("lobbyplugin.lobby")) {
                    teleportToLobby(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission to teleport to the lobby.");
                }
                break;
            case "sethub":
                if (player.hasPermission("lobbyplugin.sethub")) {
                    setHub(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission to set the hub.");
                }
                break;
            case "hub":
                if (player.hasPermission("lobbyplugin.hub")) {
                    teleportToHub(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission to teleport to the hub.");
                }
                break;
            default:
                return false;
        }
        return true;
    }

    private void setLobby(Player player) {
        Location loc = player.getLocation();
        config.set("lobby.world", loc.getWorld().getName());
        config.set("lobby.x", loc.getX());
        config.set("lobby.y", loc.getY());
        config.set("lobby.z", loc.getZ());
        config.set("lobby.yaw", loc.getYaw());
        config.set("lobby.pitch", loc.getPitch());
        saveConfig();
        player.sendMessage(ChatColor.GREEN + "Lobby location set!");
    }

    private void teleportToLobby(Player player) {
        if (config.contains("lobby.world")) {
            Location loc = new Location(
                    Bukkit.getWorld(config.getString("lobby.world")),
                    config.getDouble("lobby.x"),
                    config.getDouble("lobby.y"),
                    config.getDouble("lobby.z"),
                    (float) config.getDouble("lobby.yaw"),
                    (float) config.getDouble("lobby.pitch")
            );
            player.teleport(loc);
            player.sendMessage(ChatColor.GREEN + "Teleported to the lobby!");
        } else {
            player.sendMessage(ChatColor.RED + "Lobby location not set.");
        }
    }

    private void setHub(Player player) {
        Location loc = player.getLocation();
        config.set("hub.world", loc.getWorld().getName());
        config.set("hub.x", loc.getX());
        config.set("hub.y", loc.getY());
        config.set("hub.z", loc.getZ());
        config.set("hub.yaw", loc.getYaw());
        config.set("hub.pitch", loc.getPitch());
        saveConfig();
        player.sendMessage(ChatColor.GREEN + "Hub location set!");
    }

    private void teleportToHub(Player player) {
        if (config.contains("hub.world")) {
            Location loc = new Location(
                    Bukkit.getWorld(config.getString("hub.world")),
                    config.getDouble("hub.x"),
                    config.getDouble("hub.y"),
                    config.getDouble("hub.z"),
                    (float) config.getDouble("hub.yaw"),
                    (float) config.getDouble("hub.pitch")
            );
            player.teleport(loc);
            player.sendMessage(ChatColor.GREEN + "Teleported to the hub!");
        } else {
            player.sendMessage(ChatColor.RED + "Hub location not set.");
        }
    }
}
