package ch.hutch79.application.events.checks;

import ch.hutch79.application.messages.ConsoleMessanger;
import ch.hutch79.domain.configs.v1.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class PermissionCheck implements ICheck{


    private final Command command;
    private final Player player;
    private ConsoleMessanger debug = new ConsoleMessanger(true);

    public PermissionCheck(Command command, Player player) {
        this.command = command;
        this.player = player;
    }

    @Override
    public boolean execute() {
        if (!command.getPermission().equalsIgnoreCase("") && !player.hasPermission(command.getPermission())) {
            debug.message("command: " + command.getKey() + " - return wrong permission");
            return false;
        }
        return true;
    }
}
