package ch.hutch79.application.configManager;

import ch.hutch79.application.FCommand;
import ch.hutch79.application.configManager.Migrations.Mv0_v1;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bukkit.Bukkit;

import java.io.Console;


public class ConfigMigrator {
    private static Injector injector;

    @Inject
    public ConfigMigrator(Injector _injector) {
        injector = _injector;
        Mv0_v1 mv0V1 = injector.getInstance(Mv0_v1.class);
    }
}
