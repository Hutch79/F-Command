package ch.hutch79.migrationTest;

import ch.hutch79.Domain.configs.v1.Config;
import ch.hutch79.application.configManager.Migrations.MigrationV1;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class MigrationV1Test {
    @Test
    public void MigrationV1() {
        System.out.println(System.getProperty("user.dir"));
        MigrationV1 migrationV1 = new MigrationV1();
        var configPath = Paths.get(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "java" + File.separator + "ch" + File.separator + "hutch79" + File.separator + "migrationTest" + File.separator + "configs");
        Config hui;
        try {
            hui = migrationV1.configMigration(configPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assert.assertTrue(hui.getDebug());
        Assert.assertEquals(4, hui.getCommand().size());
        Assert.assertEquals("f", hui.getCommand().get("command1").getKey());
        Assert.assertTrue(hui.getCommand().get("command2").isRequireShift());
        Assert.assertEquals("say q shift", hui.getCommand().get("command3").getCommandList().get(0));
        Assert.assertTrue(hui.getCommand().get("command4").isCancel());
        Assert.assertFalse(hui.getCommand().get("command3").isExecuteAsServer());
    }
}
