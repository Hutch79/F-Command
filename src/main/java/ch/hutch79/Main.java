package ch.hutch79;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bstats.bukkit.Metrics;
import com.jeff_media.updatechecker.*;

public final class Main extends JavaPlugin {
    PluginDescriptionFile pdf = this.getDescription();
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        final int SPIGOT_RESOURCE_ID = 108009; // Update checker
        Metrics metrics = new Metrics(this, 17738); // bStats

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
            getLogger().warning("Mostly I added some new features or have changed some things and need feedback before releasing.");
            getLogger().warning("So if you want to provide Feedback for this Version, don't hesitate to do so on GitHub");
            getLogger().warning("If you find any Bugs, please report them on GitHub: https://github.com/Hutch79/F-Command");
        }

        Bukkit.getPluginManager().registerEvents(new EventListener(), this);

        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5======================================================");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5| §6F-Command " + pdf.getVersion() + " §bby Hutch79");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5| §7Has been §2Enabled");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5------------------------------------------------------");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5| §cIf you find any Bugs, please report them on GitHub");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5| §6https://github.com/Hutch79/F-Command");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5| §9Discord: §6https://dc.hutch79.ch");
        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §5======================================================");
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

    public static Main getInstance() {
        return instance;
    }

}
