package plugin.perworldtablistplugin.Events;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Set;

public class PlayerJoin implements Listener {

    JavaPlugin plugin;
    public PlayerJoin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        String playerWorld = world.getName();

        Plugin authMe = Bukkit.getServer().getPluginManager().getPlugin("AuthMe");
        if (authMe != null) return;

        ConfigurationSection worldGroups = plugin.getConfig().getConfigurationSection("worldGroups");
        if (worldGroups == null) return;
        Set<String> groupNames = worldGroups.getKeys(false);
        for (String groupName : groupNames) {
            List<String> worldNames = worldGroups.getStringList(groupName + ".worlds");

            if (worldNames.contains(playerWorld)) {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (worldNames.contains(onlinePlayer.getWorld().getName())) {
                        onlinePlayer.showPlayer(plugin, player);
                        player.showPlayer(plugin, onlinePlayer);
                    } else {
                        onlinePlayer.hidePlayer(plugin, player);
                        player.hidePlayer(plugin, onlinePlayer);
                    }
                }
            }

        }

    }

}
