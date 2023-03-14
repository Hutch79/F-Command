package ch.hutch79;

import org.bukkit.Bukkit;

public final class Debugger {
    public static void debug(String message) {
        if (FCommand.isDebug()) Bukkit.getConsoleSender().sendMessage("§cFcmd-debug §8> §7 " + message);
    }
}
