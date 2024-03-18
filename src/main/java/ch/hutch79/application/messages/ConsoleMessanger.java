package ch.hutch79.application.messages;

import org.bukkit.Bukkit;

public class ConsoleMessanger {
    private String prefix = "§dF-Command §8> ";
    public ConsoleMessanger(boolean debuger) {
        this.prefix = "§cFcmd-debug §8> §7 ";
    }

    public ConsoleMessanger() {

    }

    public void message(String message) {
        Bukkit.getConsoleSender().sendMessage(prefix + message);
    }

    public void spacerFat() {
        message("§5======================================================");
    }

    public void spacer() {
        message("§5------------------------------------------------------");
    }
}
