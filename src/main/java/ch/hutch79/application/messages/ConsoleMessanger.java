package ch.hutch79.application.messages;

import ch.hutch79.Domain.configs.v1.Config;
import ch.hutch79.application.configManager.ConfigManager;
import org.bukkit.Bukkit;

public class ConsoleMessanger {
    private boolean enabled = true;
    private String prefix = "§dF-Command §8> ";
    private static ConfigManager configManager;


    public ConsoleMessanger(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public ConsoleMessanger(boolean debuger) {
        this.prefix = "§cFcmd-debug §8> §7 ";


        // There is no Bukkit when executed with JUnit so nothing should get sended.
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                enabled = false;
                return;
            }
        }

        Config config = this.configManager.getConfig(Config.class);
        enabled = config.getDebug();
    }

    public ConsoleMessanger() {
        // There is no Bukkit when executed with JUnit so nothing should get sended.
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                enabled = false;
                break;
            }
        }
    }

    public void message(String message) {
        if (enabled)
            Bukkit.getConsoleSender().sendMessage(prefix + "§7" + message);
    }

    public void spacerFat() {
        if (enabled)
            message("§5======================================================");
    }

    public void spacer() {
        if (enabled)
            message("§5------------------------------------------------------");
    }
}
