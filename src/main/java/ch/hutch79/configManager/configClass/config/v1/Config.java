package ch.hutch79.configManager.configClass.config.v1;


import java.util.List;

public class Config {
    private boolean debug;
    private List<Command> command;



    public boolean getDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public List<Command> getCommand() {
        return command;
    }

    public void setCommand(List<Command> command) {
        this.command = command;
    }
}
