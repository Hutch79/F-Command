package ch.hutch79.application.configManager;

import ch.hutch79.application.configManager.Migrations.MigrationV1;
import com.google.inject.Inject;
import com.google.inject.Injector;


public class ConfigMigrator {
    private static Injector injector;

    @Inject
    public ConfigMigrator(Injector _injector) {
        injector = _injector;
        MigrationV1 mv0V1 = injector.getInstance(MigrationV1.class);
    }
}
