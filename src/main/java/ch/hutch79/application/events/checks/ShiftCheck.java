package ch.hutch79.application.events.checks;

import ch.hutch79.application.messages.ConsoleMessanger;
import ch.hutch79.domain.configs.v1.Command;
import org.bukkit.entity.Player;

public class ShiftCheck implements ICheck {
    private final Command command;
    private final Player player;
    private ConsoleMessanger debug = new ConsoleMessanger(true);


    public ShiftCheck(Command command, Player player) {
        this.command = command;
        this.player = player;
    }

    @Override
    public boolean execute() {
        if (command.isRequireShift() != player.isSneaking()) {
            debug.message("command: " + command.getKey() + " - return wrong shift state");
            return false;
        }
        return true;
    }
}
