package plugin.perworldtablistplugin.Events;

import fr.xephi.authme.events.LoginEvent;
import fr.xephi.authme.events.LogoutEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Set;

public class AuthMeEvent implements Listener {

    private final JavaPlugin plugin;
    public AuthMeEvent(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogin(LoginEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        String playerWorld = world.getName();

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

    @EventHandler
    public void onPlayerLogout(LogoutEvent event) {
        Player player = event.getPlayer();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.showPlayer(plugin, player);
            player.showPlayer(plugin, onlinePlayer);
        }
    }

}