package ch.hutch79.migrationTest;

import ch.hutch79.application.configManager.Migrations.MigrationV1;
import ch.hutch79.domain.configs.v1.Config;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class MigrationV1Test {
    @Test
    public void MigrationV1() {
        MigrationV1 migrationV1 = new MigrationV1();
        var configPath = Paths.get(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "java" + File.separator + "ch" + File.separator + "hutch79" + File.separator + "migrationTest" + File.separator + "configs");
        Config migratedConfig;
        try {
            migratedConfig = migrationV1.configMigration(configPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Assert.assertTrue(migratedConfig.getDebug());
        Assert.assertEquals(4, migratedConfig.getCommand().size());
        Assert.assertEquals("f", migratedConfig.getCommand().get("command1").getKey());
        Assert.assertTrue(migratedConfig.getCommand().get("command2").isRequireShift());
        Assert.assertEquals("say q shift", migratedConfig.getCommand().get("command3").getCommandList().get(0));
        Assert.assertTrue(migratedConfig.getCommand().get("command4").isCancel());
        Assert.assertFalse(migratedConfig.getCommand().get("command3").isExecuteAsServer());
    }
}
