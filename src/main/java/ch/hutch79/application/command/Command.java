package ch.hutch79.application.command;

import ch.hutch79.application.configManager.ConfigManager;
import ch.hutch79.domain.configs.v1.Config;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;

public class Command implements CommandExecutor {

    private final ConfigManager configManager;

    public Command(ConfigManager _configManager) {
        configManager = _configManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length != 1) return false; // Check if exactly one argument is provided

        if (!sender.hasPermission("fcommand.admin")) {
            sender.sendMessage("§dF-Command §8> §cYou don't have Permission to execute this command");
            return false;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            try {
                configManager.loadConfig(Config.class, "config.yml");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            sender.sendMessage("§dF-Command §8> §7Config has been reloaded");
            return true;
        }

        return false;
    }
}
