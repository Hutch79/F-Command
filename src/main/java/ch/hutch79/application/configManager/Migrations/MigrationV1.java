package ch.hutch79.application.configManager.Migrations;

import ch.hutch79.Domain.configs.v1.Config;
import ch.hutch79.application.FCommand;
import ch.hutch79.application.configManager.ConfigManager;
import ch.hutch79.application.configManager.ConfigManagerV0;
import com.google.inject.Inject;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MigrationV1 {
    private static ConfigManagerV0 configManagerV0;
    private static ConfigManager configManager;

    @Inject
    public MigrationV1(ConfigManager _configManager, FCommand _fCommand) {
        Bukkit.getConsoleSender().sendMessage("§4 1=========================================================================");
        Bukkit.getConsoleSender().sendMessage(_fCommand.toString());
        Bukkit.getConsoleSender().sendMessage(_configManager.toString());
        Bukkit.getConsoleSender().sendMessage(_fCommand.getConfig().getConfigurationSection("command").getKeys(false).toArray().toString());


        Set<String> commandOptions2 = Objects.requireNonNull(_fCommand.getConfig().getConfigurationSection("command")).getKeys(false);
        Bukkit.getConsoleSender().sendMessage("§4-");
        List<String> commandOptions = new ArrayList<>(commandOptions2.size());
        Bukkit.getConsoleSender().sendMessage("§4-");
        commandOptions.addAll(commandOptions2);
        Bukkit.getConsoleSender().sendMessage("§4-");
        configManagerV0 = new ConfigManagerV0(commandOptions);
        Bukkit.getConsoleSender().sendMessage("§4-");
        configManager = _configManager;


        Bukkit.getConsoleSender().sendMessage("§4 2=========================================================================");
        configManager.loadConfig(Config.class ,"config.yml");
        Config hui = configManager.getConfig(Config.class);

        Bukkit.getConsoleSender().sendMessage("§d" + hui.getDebug());
        Bukkit.getConsoleSender().sendMessage("§d" + hui.getCommand().get(hui.getCommand().keySet().toArray()[1]).getCommandList());

        hui.setDebug(false);
        configManager.writeConfig(hui, "config.yml");

        Bukkit.getConsoleSender().sendMessage("§d" + hui.getDebug());
        Bukkit.getConsoleSender().sendMessage("§4 3=========================================================================");

    }
}
