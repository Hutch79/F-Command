package ch.hutch79.application.guice;

import ch.hutch79.application.FCommand;
import ch.hutch79.application.configManager.ConfigManager;
import ch.hutch79.application.configManager.ConfigMigrator;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class DiContainerInstances extends AbstractModule {
    private static FCommand fCommand;
    private static ConfigManager configManager;
    public DiContainerInstances(FCommand _fCommand) {
        fCommand =  _fCommand;
        configManager = new ConfigManager(fCommand.getDataFolder());
    }

    @Provides
     static FCommand provideFCommand() {
        return fCommand;
    }

    @Provides
    static ConfigManager ConfigManagerProvider() {
        return configManager;
    }
}
