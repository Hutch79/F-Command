package ch.hutch79.application.events.checks;

import ch.hutch79.application.messages.ConsoleMessanger;
import ch.hutch79.domain.configs.v1.Command;

public class KeyCheck implements ICheck {

    private final Command command;
    private final String eventKey;
    private ConsoleMessanger debug = new ConsoleMessanger(true);

    public KeyCheck(Command command, String eventKey) {

        this.command = command;
        this.eventKey = eventKey;
    }

    @Override
    public boolean execute() {
        if (!command.getKey().equalsIgnoreCase(eventKey)) {
            debug.message("command: " + command.getKey() + " - return wrong key");
            return false;
        }
        return true;
    }

}
