package ch.hutch79.domain.configs.v1;

import java.util.List;

public class Command {
    private String key;
    private boolean requireShift;
    private String permission;
    private List<String> commandList;
    private boolean cancel;
    private boolean executeAsServer;




    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public boolean isRequireShift() {
        return requireShift;
    }

    public void setRequireShift(boolean requireShift) {
        this.requireShift = requireShift;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getCommandList() {
        return commandList;
    }

    public void setCommandList(List<String> command) {
        this.commandList = command;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean isExecuteAsServer() {
        return executeAsServer;
    }

    public void setExecuteAsServer(boolean executeAsServer) {
        this.executeAsServer = executeAsServer;
    }
}
