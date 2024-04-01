package ch.hutch79.application.events;

import ch.hutch79.application.messages.ConsoleMessanger;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class EventRecivers implements Listener {
    private final Injector injector;
    private Boolean ignoreEvent = false;
    ConsoleMessanger debug = new ConsoleMessanger(true);

    @Inject
    public EventRecivers(Injector injector) {

        this.injector = injector;
    }


    @org.bukkit.event.EventHandler
    private void onSwapHandItemsEvent(PlayerSwapHandItemsEvent e) {
        debug.message("PlayerSwapHandItemsEvent detected");
        EventHandler eventHandler = injector.getInstance(EventHandler.class);
        eventHandler.handler(e);
    }
    @org.bukkit.event.EventHandler
    private void inventoryClickEvent(InventoryClickEvent e) {
        if (e.getSlotType() == InventoryType.SlotType.valueOf("OUTSIDE")) {
            debug.message("PlayerDropItemEvent will be ignored. player: "  + e.getWhoClicked());
            ignoreEvent = true;
        }
    }

    @org.bukkit.event.EventHandler
    private void dropItemEvent(PlayerDropItemEvent e) {
        debug.message("PlayerDropItemEvent detected: " + e.getPlayer());
        if (!ignoreEvent) {
            debug.message("Executed");
            EventHandler eventHandler = injector.getInstance(EventHandler.class);
            eventHandler.handler(e);
        }
        else {
        debug.message("Ignored");
        }
        ignoreEvent = false;
    }
}
