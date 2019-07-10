package de.joshiworld.main.listeners;

import de.joshiworld.main.Ozone;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventManager;

/**
 *
 * @author JoshiWorld
 */
public class ListenersInit {
    
    private static EventManager getEventManager() {
        return Sponge.getEventManager();
    }
    
    public static void init() {
        listeners();
    }
    
    public static void reloadInit() {
        getEventManager().unregisterPluginListeners(Ozone.getPlugin());
        getEventManager().unregisterPluginListeners(Ozone.getPlugin());
        listeners();
    }
    
    private static void listeners() {
        getEventManager().registerListeners(Ozone.getPlugin(), new PlayerJoinListener());
        getEventManager().registerListeners(Ozone.getPlugin(), new PlayerQuitListener());
        getEventManager().registerListeners(Ozone.getPlugin(), new PlayerChatListener());
        getEventManager().registerListeners(Ozone.getPlugin(), new ChangeSignListener());
        getEventManager().registerListeners(Ozone.getPlugin(), new ShopSignListener());
        getEventManager().registerListeners(Ozone.getPlugin(), new WarpCancelListener());
        getEventManager().registerListeners(Ozone.getPlugin(), new PlayerCommandListener());
    }
    
}
