package ch.hutch79.application.configManager.Migrations;

import ch.hutch79.Domain.configs.v1.Command;
import ch.hutch79.Domain.configs.v1.Config;
import ch.hutch79.application.FCommand;
import ch.hutch79.application.configManager.ConfigManager;
import ch.hutch79.application.messages.ConsoleMessanger;
import com.google.inject.Inject;
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
    private ConfigManager configManager;

    @Inject
    public MigrationV1(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void configMigration() throws IOException {
        ConsoleMessanger debugger = new ConsoleMessanger(true);
        debugger.message("&2Config Migration Started!");

        Config newConfig = new Config();

        Path config = Paths.get("plugins" + File.separator + "F-Command" + File.separator + "config.yml");
        Path migratedConfigPath = Paths.get("plugins" + File.separator + "F-Command" + File.separator + "Migrations");
        Path migratedConfig = Paths.get("plugins" + File.separator + "F-Command" + File.separator + "Migrations" + File.separator + "config-V0.yml");

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

        for (int count = 0; count < commandOptions.size(); count++) {
            Command command = new Command();

            command.setKey(yamlConfiguration.getString("command." + commandOptions.get(count) + ".key"));
            command.setPermission(yamlConfiguration.getString("command." + commandOptions.get(count) + ".permission"));

            command.setRequireShift(false);
            if (yamlConfiguration.getString("command." + commandOptions.get(count) + ".requireShift").equals("true")) {
                command.setRequireShift(true);
            }

            command.setCancel(true);
            if (yamlConfiguration.getString("command." + commandOptions.get(count) + ".cancel").equals("false")) {
                command.setCancel(false);
            }

            command.setCancel(false);
            if (yamlConfiguration.getString("command." + commandOptions.get(count) + ".executeAsServer").equals("true")) {
                command.setExecuteAsServer(true);
            }

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
        configManager.writeConfig(newConfig, "config.yml");
        debugger.message("&2Migration Successful!");
    }
}
