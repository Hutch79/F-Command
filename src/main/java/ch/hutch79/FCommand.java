package ch.hutch79;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bstats.bukkit.Metrics;
import com.jeff_media.updatechecker.*;
import java.util.Objects;

public final class FCommand extends JavaPlugin {
    PluginDescriptionFile pdf = this.getDescription();
    private static FCommand instance;
    private static EventListener eventListener;
    private boolean isPlaceholderApiInstalled = false;

    @Override
    public void onEnable() {
        instance = this;

        eventListener = new EventListener();

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        eventListener.EventListenerInit();
        Bukkit.getPluginManager().registerEvents(eventListener, this);

        Objects.requireNonNull(getCommand("fcommand")).setExecutor(new Command());
        Objects.requireNonNull(getCommand("fcommand")).setTabCompleter(new CommandTab());

        Metrics metrics = new Metrics(this, 17738); // bStats
        final int SPIGOT_RESOURCE_ID = 108009; // Update checker

        new UpdateChecker(this, UpdateCheckSource.SPIGET, "" + SPIGOT_RESOURCE_ID + "")
                .setDownloadLink("https://www.spigotmc.org/resources/108009/")
                .setChangelogLink("https://www.spigotmc.org/resources/108009/updates")
                .setColoredConsoleOutput(true)
                .setNotifyOpsOnJoin(true)
                .setNotifyByPermissionOnJoin("f-command.admin")
                .setUserAgent(new UserAgentBuilder().addPluginNameAndVersion().addServerVersion())
                .checkEveryXHours(12) //Warn every 12 hours
                .checkNow();




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
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5| §9Discord: §6https://dc.hutch79.ch");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5======================================================");

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5| §aPlaceholderAPI §7has been found, hooking into it now.");
            isPlaceholderApiInstalled = true;
        }
        else {
            Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5| §aPlaceholderAPI §7has not been found.");
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
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5| §9Discord: §6https://dc.hutch79.ch");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5======================================================");
    }

    public static FCommand getInstance() {
        return instance;
    }

    public static EventListener getListener() {
        return eventListener;
    }

    public String replacePlaceholders(Player player, String input) {
        if(isPlaceholderApiInstalled) {
            return me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, input);
        }
        return input;
    }

}
