package ch.hutch79.utility;

import ch.hutch79.FCommand;
import org.bukkit.Bukkit;

public final class Debugger {
    public static void debug(String message) {
        if (FCommand.getDebug()) Bukkit.getConsoleSender().sendMessage("§cFcmd-debug §8> §7 " + message);
    }
}
