package ch.hutch79.application.events;

import ch.hutch79.domain.configs.v1.Command;
import ch.hutch79.domain.configs.v1.Config;
import ch.hutch79.application.FCommand;
import ch.hutch79.application.configManager.ConfigManager;
import ch.hutch79.application.messages.ConsoleMessanger;
import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.util.Map;
import java.util.Objects;

public class EventHandler {

    private final ConfigManager configManager;
    private String eventKey;
    private Player player;
    private ConsoleMessanger debug = new ConsoleMessanger(true);

    @Inject
    public EventHandler(ConfigManager configManager) {

        this.configManager = configManager;
    }

    public void handler(Event event) {
        PlayerSwapHandItemsEvent playerSwapHandItemsEvent = null;
        PlayerDropItemEvent playerDropItemEvent = null;
        if (Objects.equals(event.getEventName(), "PlayerSwapHandItemsEvent")) {
            eventKey = "f";
            playerSwapHandItemsEvent = (PlayerSwapHandItemsEvent) event;
            player = playerSwapHandItemsEvent.getPlayer();

        } else if (Objects.equals(event.getEventName(), "PlayerDropItemEvent")) {
            eventKey = "q";
            playerDropItemEvent = (PlayerDropItemEvent) event;
            player = playerDropItemEvent.getPlayer();
        }
        Config config = configManager.getConfig(Config.class);

        for (Map.Entry<String, Command> command : config.getCommand().entrySet()) {
            debug.message("EventHandler Command: " + command.getKey());

            if (!command.getValue().getKey().equalsIgnoreCase(eventKey)) {
                debug.message("command: " + command.getKey() + " - return wrong key");
                continue;
            }

            if (!command.getValue().getPermission().equalsIgnoreCase("") && !player.hasPermission(command.getValue().getPermission())) {
                debug.message("command: " + command.getKey() + " - return wrong permission");
                continue;
            }

            if (command.getValue().isRequireShift() != player.isSneaking()) {
                debug.message("command: " + command.getKey() + " - return wrong sneak state");
                continue;
            }

            for (String i : command.getValue().getCommandList()) {
                if (command.getValue().isExecuteAsServer()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), FCommand.getInstance().replacePlaceholders(player, i));
                    debug.message("command: " + command.getKey() + " - executed by console");
                } else {
                    player.performCommand(FCommand.getInstance().replacePlaceholders(player, i));
                    debug.message("command: " + command.getKey() + " - executed by player");
                }
            }

            if (command.getValue().isCancel()) {
                if (eventKey.equalsIgnoreCase("f")) {
                    playerSwapHandItemsEvent.setCancelled(true);
                } else {
                    playerDropItemEvent.setCancelled(true);
                }
            }
        }
    }
}
