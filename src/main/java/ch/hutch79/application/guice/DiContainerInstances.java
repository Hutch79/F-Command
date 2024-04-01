package ch.hutch79.application.guice;

import ch.hutch79.application.FCommand;
import ch.hutch79.application.configManager.ConfigManager;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import jakarta.inject.Singleton;

public class DiContainerInstances extends AbstractModule {
    private static FCommand fCommand;
    private static ConfigManager configManager;
    public DiContainerInstances(FCommand _fCommand) {
        fCommand =  _fCommand;
        configManager = new ConfigManager(fCommand.getDataFolder());
    }

    @Provides
    @Singleton
     static FCommand provideFCommand() {
        return fCommand;
    }

    @Provides
    @Singleton
    static ConfigManager configManagerProvider() {
        return configManager;
    }
}
