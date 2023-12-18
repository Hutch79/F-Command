package ch.hutch79.commandExecutor;

import ch.hutch79.FCommand;
import ch.hutch79.utility.ConfigManager;
import ch.hutch79.utility.Debugger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventCommandExecutor {
    private final ConfigManager configManager;
    private final List<String> commandOptions;
    public EventCommandExecutor(List<String> commandOptions) {
        this.commandOptions = commandOptions;
        configManager = new ConfigManager(this.commandOptions);
    }

    public boolean commandExecutor(Player player, Event event) {
        String eventKey = "";

        if (Objects.equals(event.getEventName(), "PlayerSwapHandItemsEvent")) {
            eventKey = "f";
        } else if (Objects.equals(event.getEventName(), "PlayerDropItemEvent")) {
            eventKey = "q";
        }

        for (int count = 0; count < commandOptions.size(); count++) {


            Debugger.debug("Event while count: §e" + (count));
            Debugger.debug("Event while current command: §e" + commandOptions.get(count));


            if (!configManager.getInfo(count, "key").equalsIgnoreCase(eventKey)) { // Correct Key was pressed?
                Debugger.debug("return key - §e" + commandOptions.get(count));
                continue;
            }

            if (!configManager.getInfo(count, "permission").equalsIgnoreCase("none")) { // Permission is set?
                if (!player.hasPermission(configManager.getInfo(count, "permission"))) { // Correct Permission?
                    Debugger.debug("return permission - §e" + commandOptions.get(count));
                    continue;
                }
            }

            if (configManager.getInfo(count, "requireShift").equalsIgnoreCase("true")) {
                if (!player.isSneaking()) {
                    Debugger.debug("return sneaking 1 - §e" + commandOptions.get(count));
                    continue;
                }
            } else if (configManager.getInfo(count, "requireShift").equalsIgnoreCase("false")) {
                if (player.isSneaking()) {
                    Debugger.debug("return sneaking 2 - §e" + commandOptions.get(count));
                    continue;
                }
            }

            List<String> commandsList;
            String commandString = configManager.getInfo(count, "command");

            if (commandString.charAt(0) == '[' && commandString.charAt(commandString.length() - 1) == ']') { // Check if first and last character are `[` and `]`
                commandsList = configManager.getCfg().getStringList("command." + commandOptions.get(count) + "." + "command");
            } else {
                commandsList = new ArrayList<>(1);
                commandsList.add(commandString);
            }

            if (configManager.getInfo(count, "executeAsServer").equalsIgnoreCase("true")) {
                for (String i : commandsList) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), FCommand.getInstance().replacePlaceholders(player, i));
                }

                Debugger.debug("Executed by Server - §e" + commandOptions.get(count));
            } else {
                for (String i : commandsList) {
                    player.performCommand(FCommand.getInstance().replacePlaceholders(player, i));
                }
                Debugger.debug("Executed by Player - §e" + commandOptions.get(count));
            }


            if (configManager.getInfo(count, "cancel").equalsIgnoreCase("true")) {

                if (eventKey.equalsIgnoreCase("f") || eventKey.equalsIgnoreCase("q")) {
                    return true;
                }
                Debugger.debug("event canceled - §e" + commandOptions.get(count));
            }
        }
        return false;
    }
}