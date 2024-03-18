package ch.hutch79.application.messages;

import ch.hutch79.application.FCommand;
import org.bukkit.Bukkit;

public final class Debugger {
    public static void debug(String message) {
        if (true) Bukkit.getConsoleSender().sendMessage("§cFcmd-debug §8> §7 " + message);
    }
}
