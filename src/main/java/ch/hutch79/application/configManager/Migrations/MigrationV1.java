package ch.hutch79.application.configManager.Migrations;

import ch.hutch79.domain.configs.v1.Command;
import ch.hutch79.domain.configs.v1.Config;
import ch.hutch79.application.messages.ConsoleMessanger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MigrationV1 {

    public Config configMigration(Path configPath) throws IOException {
        ConsoleMessanger debugger = new ConsoleMessanger();
        debugger.message("§2Config Migration V1 Started!");

        Config newConfig = new Config();
        Path config = Paths.get(configPath.toString() + File.separator + "config.yml");
        Path migratedConfigPath = Paths.get(configPath.toString() + File.separator + "Migrations");
        Path migratedConfig = Paths.get(migratedConfigPath.toString() + File.separator + "config-V0.yml");

        debugger.message("Old Path: " + config.toString());
        debugger.message("Copy Path: " + migratedConfig.toString());

        try {
            Files.createDirectories(migratedConfigPath);
            Files.copy(config, migratedConfig, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioException) {
            throw new IOException("Can't move old Config into Migration folder", ioException);
        }

        FileConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(new File(String.valueOf(migratedConfig)));
        Set<String> commandOptions2 = Objects.requireNonNull(yamlConfiguration.getConfigurationSection("command")).getKeys(false);
        List<String> commandOptions = new ArrayList<>(commandOptions2.size());
        commandOptions.addAll(commandOptions2);

        newConfig.setDebug(yamlConfiguration.getBoolean("debug"));

        for (int count = 0; count < commandOptions.size(); count++) {
            Command command = new Command();

            command.setKey(yamlConfiguration.getString("command." + commandOptions.get(count) + ".key"));
            command.setPermission(yamlConfiguration.getString("command." + commandOptions.get(count) + ".permission"));
            command.setRequireShift(yamlConfiguration.getBoolean("command." + commandOptions.get(count) + ".requireShift"));
            command.setCancel(yamlConfiguration.getBoolean("command." + commandOptions.get(count) + ".cancel"));
            command.setExecuteAsServer(yamlConfiguration.getBoolean("command." + commandOptions.get(count) + ".executeAsServer"));

            List<String> commandsList;
            String commandString = yamlConfiguration.getString("command." + commandOptions.get(count) + ".command");

            if (commandString.charAt(0) == '[' && commandString.charAt(commandString.length() - 1) == ']') { // Check if first and last character are `[` and `]`
                commandsList = yamlConfiguration.getStringList("command." + commandOptions.get(count) + "." + "command");
            } else {
                commandsList = new ArrayList<>(1);
                commandsList.add(commandString);
            }

            command.setCommandList(commandsList);

            var tempCommandList = newConfig.getCommand();
            tempCommandList.put(commandOptions.get(count), command);
            newConfig.setCommand(tempCommandList);
        }
        debugger.message("§2Config Migration V1 Successful!");
        return newConfig;
    }
}
