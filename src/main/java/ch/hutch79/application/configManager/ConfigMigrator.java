package ch.hutch79.application.configManager;

import ch.hutch79.Domain.configs.v1.Config;
import ch.hutch79.application.FCommand;
import ch.hutch79.application.configManager.Migrations.MigrationV1;
import ch.hutch79.application.messages.ConsoleMessanger;
import ch.hutch79.application.messages.Debugger;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.io.IOException;


public class ConfigMigrator {

    @Inject
    public ConfigMigrator(Injector injector, ConfigManager configManager, FCommand fCommand) throws IOException {
        ConsoleMessanger messanger = new ConsoleMessanger();
        try {
            configManager.loadConfig(Config.class, "config.yml");
        } catch (Exception e1) {
            try {
                var hui = injector.getInstance(MigrationV1.class);
                hui.configMigration();
            } catch (Exception e2) {
                messanger.message("§cThe Config Migration failed. Therefore the Plugin will be disabled.");
                throw e2;
            }
        }

        Config config = configManager.getConfig(Config.class);

//        switch (config.getVersion()) {
//            case 1:
//                injector.getInstance(MigrationV2.class);
//        }
    }
}
