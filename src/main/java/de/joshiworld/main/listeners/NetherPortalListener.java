package de.joshiworld.main.listeners;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;

/**
 *
 * @author JoshiWorld
 */
public class NetherPortalListener {
    
    @Listener
    public void onNetherPortal(ChangeBlockEvent.Place e) {
        if(e.getTransactions().get(0).getFinal().getState().getType().equals(BlockTypes.PORTAL) && 
                e.getTransactions().get(0).getFinal().getLocation().get().sub(0, 1, 0).getBlockType().equals(BlockTypes.OBSIDIAN)) {
            e.setCancelled(true);
        }
    }
    
}
