package ch.hutch79.configManager.configClass;

import ch.hutch79.configManager.Configuration;
import ch.hutch79.configManager.configClass.config.Command;

import java.util.List;

public class Config extends Configuration {
    private boolean debug;
    private int version;
    private List<Command> command;

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<Command> getCommand() {
        return command;
    }

    public void setCommand(List<Command> command) {
        this.command = command;
    }
}
