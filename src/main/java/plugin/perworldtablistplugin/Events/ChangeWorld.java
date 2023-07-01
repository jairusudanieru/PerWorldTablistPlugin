package plugin.perworldtablistplugin.Events;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Set;

public class ChangeWorld implements Listener {

    private final JavaPlugin plugin;
    public ChangeWorld(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        String oldWorld = event.getFrom().getName();
        String newWorld = world.getName();

        ConfigurationSection worldGroups = plugin.getConfig().getConfigurationSection("worldGroups");
        if (worldGroups == null) return;
        Set<String> groupNames = worldGroups.getKeys(false);
        for (String groupName : groupNames) {
            List<String> worldNames = worldGroups.getStringList(groupName + ".worlds");

            if (worldNames.contains(newWorld) && !worldNames.contains(oldWorld)) {
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