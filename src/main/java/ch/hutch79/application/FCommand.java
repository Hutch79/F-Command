package ch.hutch79.application;

import ch.hutch79.application.command.Command;
import ch.hutch79.application.command.CommandTab;
import ch.hutch79.application.configManager.ConfigManager;
import ch.hutch79.application.configManager.ConfigMigrator;
import ch.hutch79.application.events.EventHandler;
import ch.hutch79.application.events.EventRecivers;
import ch.hutch79.application.messages.ConsoleMessanger;
import ch.hutch79.domain.configs.v1.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bstats.bukkit.Metrics;
import com.jeff_media.updatechecker.UpdateChecker;
import com.jeff_media.updatechecker.UpdateCheckSource;
import com.jeff_media.updatechecker.UserAgentBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public final class FCommand extends JavaPlugin {
    PluginDescriptionFile pdf = this.getDescription();
    private static FCommand instance;
    private static ConfigManager configManager;
    private static EventHandler eventHandler;
    private boolean isPlaceholderApiInstalled = false;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        instance = this;
        configManager = new ConfigManager(instance.getDataFolder());
        try {
            configManager.loadConfig(Config.class, "config.yml");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        new ConsoleMessanger(configManager);

        Objects.requireNonNull(getCommand("fcommand")).setExecutor(new Command(configManager));
        Objects.requireNonNull(getCommand("fcommand")).setTabCompleter(new CommandTab());

        eventHandler = new EventHandler(configManager);
        Bukkit.getPluginManager().registerEvents(new EventRecivers(eventHandler), this);

        new Metrics(this, 17738); // bStats

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

    public String replacePlaceholders(Player player, String input) {
        if(isPlaceholderApiInstalled) {
            return me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, input);
        }
        return input;
    }

    public PluginDescriptionFile getPdf() {
        return pdf;
    }
}
