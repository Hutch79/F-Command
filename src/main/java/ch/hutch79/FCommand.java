package ch.hutch79;

import ch.hutch79.command.Command;
import ch.hutch79.command.CommandTab;
import ch.hutch79.configManager.ConfigManager;
import ch.hutch79.configManager.configClass.config.v0.Config;
import ch.hutch79.events.EventHandler;
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
    private static EventHandler eventHandler;
    private boolean isPlaceholderApiInstalled = false;
    private static boolean debug;

    @Override
    public void onEnable() {
        instance = this;

        eventHandler = new EventHandler();
//        getConfig().options().copyDefaults();
//        saveDefaultConfig();
//        reloadConfig();

//        eventHandler.eventListenerInit();
        Bukkit.getPluginManager().registerEvents(eventHandler, this);

        Objects.requireNonNull(getCommand("fcommand")).setExecutor(new Command());
        Objects.requireNonNull(getCommand("fcommand")).setTabCompleter(new CommandTab());

        new Metrics(this, 17738); // bStats

//        debug = getConfig().getBoolean("debug");

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
            getLogger().warning("If you find any Bugs, please report them on GitHub: https://github.com/Hutch79/F-Command");
        }

        ConfigManager configManager = new ConfigManager(getDataFolder());
//        configManager.configStuff();
        Config hui = configManager.getConfig(Config.class ,"config.yml");

        Bukkit.getConsoleSender().sendMessage("§d" + hui.getDebug());
        Bukkit.getConsoleSender().sendMessage("§d" + hui.getCommand().get(1).getCommandList().get(2));

        Bukkit.getConsoleSender().sendMessage("§d" + pdf.getName() + " §8> §5======================================================");
        Bukkit.getConsoleSender().sendMessage("§d" + pdf.getName() + " §8> §5| §6" + pdf.getName() + " " + pdf.getVersion() + " §bby Hutch79");
        Bukkit.getConsoleSender().sendMessage("§d" + pdf.getName() + " §8> §5| §7Has been §2Enabled");
        Bukkit.getConsoleSender().sendMessage("§d" + pdf.getName() + " §8> §5------------------------------------------------------");
        Bukkit.getConsoleSender().sendMessage("§d" + pdf.getName() + " §8> §5| §cIf you find any Bugs, please report them on GitHub");
        Bukkit.getConsoleSender().sendMessage("§d" + pdf.getName() + " §8> §5| §6https://github.com/Hutch79/F-Command");
        Bukkit.getConsoleSender().sendMessage("§d" + pdf.getName() + " §8> §5| §9Discord: §6https://dc.hutch79.ch");
        Bukkit.getConsoleSender().sendMessage("§d" + pdf.getName() + " §8> §5======================================================");

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getConsoleSender().sendMessage("§d" + pdf.getName() + " §8> §5| §aPlaceholderAPI §7has been found, hooking into it now.");
            isPlaceholderApiInstalled = true;
        }
        else {
            Bukkit.getConsoleSender().sendMessage("§d" + pdf.getName() + " §8> §5| §aPlaceholderAPI §7has not been found.");
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§d" + pdf.getName() + " §8> §5======================================================");
        Bukkit.getConsoleSender().sendMessage("§d" + pdf.getName() + " §8> §5| §6F-Command " + pdf.getVersion() + " §bby Hutch79");
        Bukkit.getConsoleSender().sendMessage("§d" + pdf.getName() + " §8> §5| §7Has been §cDisabled");
        Bukkit.getConsoleSender().sendMessage("§d" + pdf.getName() + " §8> §5------------------------------------------------------");
        Bukkit.getConsoleSender().sendMessage("§d" + pdf.getName() + " §8> §5| §cIf you find any Bugs, please report them on GitHub");
        Bukkit.getConsoleSender().sendMessage("§d" + pdf.getName() + " §8> §5| §6https://github.com/Hutch79/F-Command");
        Bukkit.getConsoleSender().sendMessage("§d" + pdf.getName() + " §8> §5| §9Discord: §6https://dc.hutch79.ch");
        Bukkit.getConsoleSender().sendMessage("§d" + pdf.getName() + " §8> §5======================================================");
    }

    public static FCommand getInstance() {
        return instance;
    }

    public static EventHandler getListener() {
        return eventHandler;
    }

    public static boolean getDebug() {
        return debug;
    }

    public static void setDebug(Boolean value) {
        debug = value;
    }

    public String replacePlaceholders(Player player, String input) {
        if(isPlaceholderApiInstalled) {
            return me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, input);
        }
        return input;
    }

}
