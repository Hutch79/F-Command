package ch.hutch79.application.configManager;

import ch.hutch79.application.configManager.Migrations.MigrationV1;
import ch.hutch79.domain.configs.v1.Config;
import ch.hutch79.application.messages.ConsoleMessanger;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


public class ConfigMigrator {

    public ConfigMigrator(ConfigManager configManager) throws IOException {
        ConsoleMessanger messanger = new ConsoleMessanger();
        try {
            configManager.loadConfig(Config.class, "config.yml");
        } catch (Exception e1) {
            try {
                var migrationV1 = new MigrationV1();
                Config configNew = migrationV1.configMigration(Paths.get("plugins" + File.separator + "F-Command"));
                configManager.writeConfig(configNew, "config.yml");
            } catch (Exception e2) {
                messanger.message("§cThe Config Migration failed. Therefore the Plugin will be disabled.");
                throw e2;
            }
        }
        configManager.loadConfig(Config.class, "config.yml");
    }
}
