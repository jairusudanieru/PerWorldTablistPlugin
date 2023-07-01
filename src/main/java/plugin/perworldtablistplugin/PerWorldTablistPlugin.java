package plugin.perworldtablistplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.perworldtablistplugin.Commands.Pwtp;
import plugin.perworldtablistplugin.Events.AuthMeEvent;
import plugin.perworldtablistplugin.Events.ChangeWorld;
import plugin.perworldtablistplugin.Events.PlayerJoin;
import plugin.perworldtablistplugin.Events.PlayerQuit;

public final class PerWorldTablistPlugin extends JavaPlugin {

    public void checkAuthMe() {
        Plugin authMe = Bukkit.getServer().getPluginManager().getPlugin("AuthMe");
        if (authMe != null) {
            Bukkit.getPluginManager().registerEvents(new AuthMeEvent(this),this);
        } else {
            Bukkit.getPluginManager().registerEvents(new PlayerJoin(this),this);
        }
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        checkAuthMe();
        Bukkit.getPluginCommand("pwtp").setExecutor(new Pwtp(this));
        Bukkit.getPluginManager().registerEvents(new ChangeWorld(this),this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(this),this);
        Bukkit.getLogger().info("[PerWorldTablistPlugin] Plugin Successfully Enabled!");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[PerWorldTablistPlugin] Plugin Successfully Disabled!");
    }
}

