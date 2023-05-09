package ch.hutch79;

import me.clip.placeholderapi.events.ExpansionRegisterEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import java.util.*;

public class EventListener implements Listener {

    private final FCommand mainInstance = FCommand.getInstance();
    private List<String> commandOptions;
    private PlayerSwapHandItemsEvent playerSwapHandItemsEvent;
    private PlayerDropItemEvent playerDropItemEvent;

    public void EventListenerInit() {
        mainInstance.reloadConfig();
        Set<String> commandOptions2 = Objects.requireNonNull(FCommand.getInstance().getConfig().getConfigurationSection("command")).getKeys(false);
        commandOptions = new ArrayList<>(commandOptions2.size());
        commandOptions.addAll(commandOptions2);
        Debugger.debug("commandOptions list: §e" + commandOptions);

        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §7Loaded Commands: " + commandOptions);
    }

    private String getInfo(int count, String value){

        String result = mainInstance.getConfig().getString("command." + commandOptions.get(count) + "." + value);

        if(result == null) {
            if (!value.equalsIgnoreCase("item")) {
                mainInstance.getLogger().warning("The Value " + value + " for the Command " + commandOptions.get(count) + " is not set!");
            }
            return "defaultValue";
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


            if (!getInfo(count, "key").equalsIgnoreCase(eventKey)) { // Which Key was pressed?
                Debugger.debug("return key - §e" + commandOptions.get(count));
                continue;
            }

            /*if (getInfo(count, "key").equalsIgnoreCase("f")) {

                if (!getInfo(count, "item").equalsIgnoreCase(String.valueOf(playerSwapHandItemsEvent.getMainHandItem().getType()))) {
                    if (!getInfo(count, "item").equalsIgnoreCase(String.valueOf(playerSwapHandItemsEvent.getOffHandItem().getType()))) {
                        Debugger.debug("return item in hand - §e" + commandOptions.get(count));
                        continue;
                    }
                }
            }

            if (getInfo(count, "key").equalsIgnoreCase("q")) {

                if (!getInfo(count, "item").equalsIgnoreCase("defaultValue")) {
                    if (!getInfo(count, "item").equalsIgnoreCase(String.valueOf(playerDropItemEvent.getItemDrop().getType()))) {
                        Debugger.debug("return dropped item - §e" + commandOptions.get(count));
                        continue;
                    }
                }


            }*/

            if (!getInfo(count, "permission").equalsIgnoreCase("None")) { // Correct Permission?
                if (!player.hasPermission(getInfo(count, "permission"))) {
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

    @EventHandler
    private void dropItemEvent(PlayerDropItemEvent e) {
        Debugger.debug("PlayerDropItemEvent detected");
        playerDropItemEvent = e;
        commandExecuter(e.getPlayer(), e.getEventName());
    }
}
