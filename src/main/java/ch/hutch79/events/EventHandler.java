package ch.hutch79.events;

import ch.hutch79.FCommand;
import ch.hutch79.commandExecutor.EventCommandExecutor;
import ch.hutch79.utility.Debugger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.io.File;
import java.util.*;

public class EventHandler implements Listener {

    private final FCommand mainInstance = FCommand.getInstance();
    private EventCommandExecutor eventCommandExecutor;

    FileConfiguration cfg;

    public void eventListenerInit() {
        mainInstance.reloadConfig();
        Set<String> commandOptions2 = Objects.requireNonNull(FCommand.getInstance().getConfig().getConfigurationSection("command")).getKeys(false);
        List<String> commandOptions = new ArrayList<>(commandOptions2.size());
        commandOptions.addAll(commandOptions2);
        FCommand.setDebug(mainInstance.getConfig().getBoolean("debug"));
        Debugger.debug("commandOptions list: §e" + commandOptions);

        cfg = YamlConfiguration.loadConfiguration(new File("plugins" + File.separator + "F-Command", "config.yml"));

        Bukkit.getConsoleSender().sendMessage("§dF-Command §8> §7Loaded Commands: " + commandOptions);

        eventCommandExecutor = new EventCommandExecutor(commandOptions);
    }

    @org.bukkit.event.EventHandler
    private void onSwapHandItemsEvent(PlayerSwapHandItemsEvent e) {
        Debugger.debug("PlayerSwapHandItemsEvent detected");
        if (eventCommandExecutor.commandExecutor(e.getPlayer(), e))
            e.setCancelled(true);
    }
    private Boolean ignoreEvent = false;
    @org.bukkit.event.EventHandler
    private void inventoryClickEvent(InventoryClickEvent e) {
        if (e.getSlotType() == InventoryType.SlotType.valueOf("OUTSIDE")) {
            Debugger.debug("Event ignored, not OUTSIDE");
            ignoreEvent = true;
        }
    }

    @org.bukkit.event.EventHandler
    private void dropItemEvent(PlayerDropItemEvent e) {
        Debugger.debug("PlayerDropItemEvent detected: " + e.getPlayer());
        if (!ignoreEvent) {
            Debugger.debug("It was Q");
            if (eventCommandExecutor.commandExecutor(e.getPlayer(), e))
                e.setCancelled(true);
        }
        ignoreEvent = false;
    }
}
