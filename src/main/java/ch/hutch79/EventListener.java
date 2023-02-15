package ch.hutch79;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import java.util.*;

public class EventListener implements Listener {

    private final Main mainInstance = Main.getInstance();
    public List<String> commandOptions;

    public void EventListenerInit() {
        mainInstance.reloadConfig();
        Set<String> commandOptions2 = Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection("command")).getKeys(false);
        commandOptions = new ArrayList<>(commandOptions2.size());
        commandOptions.addAll(commandOptions2);
        Bukkit.getConsoleSender().sendMessage("hui: " + commandOptions);
    }

    public String getInfo(int count, String value){

        String result = mainInstance.getConfig().getString("command." + commandOptions.get(count-1) + "." + value);

        if(result == null) {
            mainInstance.getLogger().warning("The Value " + value + " for the Command " + commandOptions.get(count-1) + " is not set!");
            return "defaultValue";
        }

        return result;
    }

    @EventHandler
    public void onSwapHandItemsEvent(PlayerSwapHandItemsEvent e) {

        Player player = e.getPlayer();
        Bukkit.getConsoleSender().sendMessage("hui: " + commandOptions);
        int count = 0;
        while (count < commandOptions.size()) {
            count++;

            if (!getInfo(count, "permission").equalsIgnoreCase("None")) { // Correct Permission?
                if (!player.hasPermission(getInfo(count, "permission"))) {
                    return;
                }
            }

            if (getInfo(count, "requireShift").equalsIgnoreCase("true")) {
                if (!player.isSneaking()) {
                    return;
                }
            } else if (getInfo(count, "requireShift").equalsIgnoreCase("false")) {
                if (player.isSneaking()) {
                    return;
                }
            }


            if (getInfo(count, "cancel").equalsIgnoreCase("true")) {
                e.setCancelled(true);
            }


            if (getInfo(count, "executeAsServer").equalsIgnoreCase("true")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), mainInstance.replacePlaceholders(player,getInfo(count, "command")));
            } else {
                player.performCommand(mainInstance.replacePlaceholders(player,getInfo(count, "command")));
            }


        }

    }

}
