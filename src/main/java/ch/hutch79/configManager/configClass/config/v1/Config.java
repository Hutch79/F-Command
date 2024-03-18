package ch.hutch79.configManager.configClass.config.v1;


import java.util.Map;

public class Config {
    private boolean debug;


    private int version;
    private Map<String, Command> command;



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
