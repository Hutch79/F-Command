package ch.hutch79;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import java.util.*;

public class EventListener implements Listener {

    private final FCommand mainInstance = FCommand.getInstance();
    private boolean debug = false;
    public List<String> commandOptions;

    public void EventListenerInit() {
        mainInstance.reloadConfig();
        Set<String> commandOptions2 = Objects.requireNonNull(FCommand.getInstance().getConfig().getConfigurationSection("command")).getKeys(false);
        commandOptions = new ArrayList<>(commandOptions2.size());
        commandOptions.addAll(commandOptions2);
        debug = mainInstance.getConfig().getBoolean("debug");
        if (debug) {Bukkit.getConsoleSender().sendMessage("§cFcmd-debug §8> §7commandOptions list: §e" + commandOptions);}

        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §7Loaded Commands: " + commandOptions);
    }

    public String getInfo(int count, String value){

        String result = mainInstance.getConfig().getString("command." + commandOptions.get(count-1) + "." + value);

        if(result == null) {
            mainInstance.getLogger().warning("The Value " + value + " for the Command " + commandOptions.get(count) + " is not set!");
            return "defaultValue";
        }

        return result;
    }

    @EventHandler
    public void onSwapHandItemsEvent(PlayerSwapHandItemsEvent e) {

        if (debug) {Bukkit.getConsoleSender().sendMessage("§cFcmd-debug §8> §7PlayerSwapHandItemsEvent detected");}

        Player player = e.getPlayer();

        int count = 0;
        while (count < commandOptions.size()) {
            count++;

            int count1 = 0;
            while (count1 < 1) {
                count1++;

                if (debug) {Bukkit.getConsoleSender().sendMessage("§cFcmd-debug §8> §7Event while count: §e" + (count-1));}
                if (debug) {Bukkit.getConsoleSender().sendMessage("§cFcmd-debug §8> §7Event while current command: §e" + commandOptions.get(count-1));}


                if (!getInfo(count, "permission").equalsIgnoreCase("None")) { // Correct Permission?
                    if (!player.hasPermission(getInfo(count, "permission"))) {
                        if (debug) {Bukkit.getConsoleSender().sendMessage("§cFcmd-debug §8> §7return permission - §e" + commandOptions.get(count-1));}
                        break;
                    }
                }

                if (getInfo(count, "requireShift").equalsIgnoreCase("true")) {
                    if (!player.isSneaking()) {
                        if (debug) {Bukkit.getConsoleSender().sendMessage("§cFcmd-debug §8> §7return sneaking 1 - §e" + commandOptions.get(count-1));}
                        break;
                    }
                } else if (getInfo(count, "requireShift").equalsIgnoreCase("false")) {
                    if (player.isSneaking()) {
                        if (debug) {Bukkit.getConsoleSender().sendMessage("§cFcmd-debug §8> §7return sneaking 2 - §e" + commandOptions.get(count-1));}
                        break;
                    }
                }


                if (getInfo(count, "cancel").equalsIgnoreCase("true")) {
                    e.setCancelled(true);
                    if (debug) {Bukkit.getConsoleSender().sendMessage("§cFcmd-debug §8> §7event canceled - §e" + commandOptions.get(count-1));}
                }


                if (getInfo(count, "executeAsServer").equalsIgnoreCase("true")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), mainInstance.replacePlaceholders(player,getInfo(count, "command")));
                    if (debug) {Bukkit.getConsoleSender().sendMessage("§cFcmd-debug §8> §7Executed by Server - §e" + commandOptions.get(count-1));}
                } else {
                    player.performCommand(mainInstance.replacePlaceholders(player,getInfo(count, "command")));
                    if (debug) {Bukkit.getConsoleSender().sendMessage("§cFcmd-debug §8> §7Executed by Player - §e" + commandOptions.get(count-1));}
                }
            }
        }
    }
}
