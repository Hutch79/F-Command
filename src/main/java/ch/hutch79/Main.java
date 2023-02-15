package ch.hutch79;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    PluginDescriptionFile pdf = this.getDescription();
    private static Main instance;
    private boolean isPlaceholderApiInstalled = false;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        if (pdf.getVersion().contains("Beta")) {
            getLogger().warning("It seems you're using a dev Build");
            getLogger().warning("You can use this Build on Production Servers but for some reasons i would not recommend that.");
            getLogger().warning("Mostly i added some new features or have changed some things and need feedback before releasing.");
            getLogger().warning("So if you want to provide Feedback for this Version, don't hesitate to do so on GitHub");
            getLogger().warning("If you find any Bugs, please report them on GitHub: https://github.com/Hutch79/CookieClicker");
        }

        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5======================================================");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5| §6F-Command " + pdf.getVersion() + " §bby Hutch79");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5| §7Has been §2Enabled");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5------------------------------------------------------");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5| §cIf you find any Bugs, please report them on GitHub");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5| §6https://github.com/Hutch79/CookieClicker");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5======================================================");

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5| §aPlaceholderAPI §7has been found, hooking into it now.");
            isPlaceholderApiInstalled = true;
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5======================================================");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5| §6F-Command " + pdf.getVersion() + " §bby Hutch79");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5| §7Has been §cDisabled");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5------------------------------------------------------");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5| §cIf you find any Bugs, please report them on GitHub");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5| §6https://github.com/Hutch79/F-Command");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5======================================================");
    }

    public static Main getInstance() {
        return instance;
    }

    public String replacePlaceholders(Player player, String input) {
        if(isPlaceholderApiInstalled) {
            return me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, input);
        }
        return input;
    }

}
