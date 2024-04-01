package ch.hutch79.Domain.configs.v1;


import java.util.HashMap;
import java.util.Map;

public class Config {
    private boolean debug = false;
    private int version = 1;
    private Map<String, Command> command = new HashMap<>();



    public boolean getDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Map<String, Command> getCommand() {
        return command;
    }

    public void setCommand(Map<String, Command> command) {
        this.command = command;
    }
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
