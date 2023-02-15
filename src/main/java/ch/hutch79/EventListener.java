package ch.hutch79;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import java.util.*;

public class EventListener implements Listener {

    private final Main mainClass = Main.getInstance();
    List<String> commandOptions;

    public EventListener() {

        Set<String> commandOptions2 = Objects.requireNonNull(mainClass.getConfig().getConfigurationSection("command")).getKeys(false);

        commandOptions = new ArrayList<>(commandOptions2.size());
        commandOptions.addAll(commandOptions2);

    }

    public String getInfo(int count, String value){

        try {
            return Objects.requireNonNull(mainClass.getConfig().getString("command." + commandOptions.get(count-1) + "." + value));
        }
        catch (NullPointerException e) {
            mainClass.getLogger().warning(value + " could not be fount in configuration of command " + commandOptions.get(count-1));
            return "ijfuwiqehfzqgw9r8mhe0w87gh";
        }
    }

    @EventHandler
    public void onSwapHandItemsEvent(PlayerSwapHandItemsEvent e) {

        Player player = e.getPlayer();

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
            }





            if (getInfo(count, "cancel").equalsIgnoreCase("true")) {
                e.setCancelled(true);
            }

             player.performCommand(mainClass.replacePlaceholders(player,getInfo(count, "command")));


        }

    }

}
