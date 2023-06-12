package ch.hutch79.utility;

import ch.hutch79.FCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.io.File;
import java.util.*;

public class EventListener implements Listener {

    private final FCommand mainInstance = FCommand.getInstance();
    private List<String> commandOptions;
    private PlayerSwapHandItemsEvent playerSwapHandItemsEvent;
    private PlayerDropItemEvent playerDropItemEvent;
    FileConfiguration cfg;

    public void EventListenerInit() {
        mainInstance.reloadConfig();
        Set<String> commandOptions2 = Objects.requireNonNull(FCommand.getInstance().getConfig().getConfigurationSection("command")).getKeys(false);
        commandOptions = new ArrayList<>(commandOptions2.size());
        commandOptions.addAll(commandOptions2);
        FCommand.setDebug(mainInstance.getConfig().getBoolean("debug"));
        Debugger.debug("commandOptions list: §e" + commandOptions);

        cfg = YamlConfiguration.loadConfiguration(new File("plugins" + File.separator + "F-Command", "config.yml"));

        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §7Loaded Commands: " + commandOptions);
    }

    private String getInfo(int count, String value){

        String result = cfg.getString("command." + commandOptions.get(count) + "." + value);
        if(result == null) {
            Debugger.debug("The Value " + value + " for the Command " + commandOptions.get(count) + " is not set!");

            if (value.equals("key")) {
                return "f";
            }

            if (value.equals("permission")) {
                Debugger.debug("§6Haha");
                return "none";
            }

            if (value.equals("requireShift")) {
                return "true";
            }

            if (value.equals("cancel")) {
                return "false";
            }

            if (value.equals("executeAsServer")) {
                return "false";
            }

            if (value.equals("command")) {
                return "say hi, im a default command. Please edit the config.yml to set your own command.";
            }
        }

        return result;
    }

    private void commandExecuter (Player player, String eventKey) {

        if (Objects.equals(eventKey, "PlayerSwapHandItemsEvent")) {
            eventKey = "f";
        } else if (Objects.equals(eventKey, "PlayerDropItemEvent")) {
            eventKey = "q";
        }

        for (int count = 0; count < commandOptions.size(); count++) {


            Debugger.debug("Event while count: §e" + (count));
            Debugger.debug("Event while current command: §e" + commandOptions.get(count));


            if (!getInfo(count, "key").equalsIgnoreCase(eventKey)) { // Correct Key was pressed?
                Debugger.debug("return key - §e" + commandOptions.get(count));
                continue;
            }

            if (!getInfo(count, "permission").equalsIgnoreCase("none")) { // Permission in Config?
                if (!player.hasPermission(getInfo(count, "permission"))) { // Correct Permission?
                    Debugger.debug("return permission - §e" + commandOptions.get(count));
                    continue;
                }
            }

            if (getInfo(count, "requireShift").equalsIgnoreCase("true")) {
                if (!player.isSneaking()) {
                    Debugger.debug("return sneaking 1 - §e" + commandOptions.get(count));
                    continue;
                }
            } else if (getInfo(count, "requireShift").equalsIgnoreCase("false")) {
                if (player.isSneaking()) {
                    Debugger.debug("return sneaking 2 - §e" + commandOptions.get(count));
                    continue;
                }
            }


            if (getInfo(count, "cancel").equalsIgnoreCase("true")) {

                if (eventKey.equalsIgnoreCase("f")) {
                    playerSwapHandItemsEvent.setCancelled(true);
                } else if (eventKey.equalsIgnoreCase("q")) {
                    playerDropItemEvent.setCancelled(true);
                }
                Debugger.debug("event canceled - §e" + commandOptions.get(count));
            }


            if (getInfo(count, "executeAsServer").equalsIgnoreCase("true")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), mainInstance.replacePlaceholders(player,getInfo(count, "command")));
                Debugger.debug("Executed by Server - §e" + commandOptions.get(count));
            } else {
                player.performCommand(mainInstance.replacePlaceholders(player,getInfo(count, "command")));
                Debugger.debug("Executed by Player - §e" + commandOptions.get(count));
            }
        }
    }

    @EventHandler
    private void onSwapHandItemsEvent(PlayerSwapHandItemsEvent e) {
        Debugger.debug("PlayerSwapHandItemsEvent detected");
        playerSwapHandItemsEvent = e;
        commandExecuter(e.getPlayer(), e.getEventName());
    }
    private Boolean ignoreEvent = false;
    @EventHandler
    private void inventoryClickEvent(InventoryClickEvent e) {
        if (e.getSlotType() != InventoryType.SlotType.valueOf("OUTSIDE")) {
            Debugger.debug("Event ignored, not OUTSIDE");
            ignoreEvent = true;
        }
    }

    @EventHandler
    private void dropItemEvent(PlayerDropItemEvent e) {
        Debugger.debug("PlayerDropItemEvent detected: " + e.getPlayer());
        if (!ignoreEvent) {
            Debugger.debug("It was Q");
            playerDropItemEvent = e;
            commandExecuter(e.getPlayer(), e.getEventName());
        }
        ignoreEvent = false;
    }
}
