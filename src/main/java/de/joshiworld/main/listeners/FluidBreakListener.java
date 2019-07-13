package de.joshiworld.main.listeners;

import java.util.Optional;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.property.block.MatterProperty;
import org.spongepowered.api.data.property.block.MatterProperty.Matter;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;

/**
 *
 * @author JoshiWorld
 */
public class FluidBreakListener {
    
    @Listener
    public void onFluidStop(ChangeBlockEvent.Place e) {
        BlockSnapshot snap = e.getTransactions().get(0).getFinal();
        Optional<MatterProperty> matter = snap.getState().getProperty(MatterProperty.class);
        if(matter.isPresent() && matter.get().getValue().equals(Matter.LIQUID)) {
            if(Sponge.getServer().getWorld(snap.getWorldUniqueId()).get().getName().equalsIgnoreCase("skyblock") || 
                    Sponge.getServer().getWorld(snap.getWorldUniqueId()).get().getName().equalsIgnoreCase("world")) {
                if(snap.getLocation().get().getY() < 50) {
                    e.setCancelled(true);

                    /*if(!snap.getLocation().get().add(0, 1, 0).getBlockType().equals(BlockTypes.WATER) || 
                        !snap.getLocation().get().add(0, 1, 0).getBlockType().equals(BlockTypes.LAVA)) {
                        snap.getLocation().get().removeBlock();
                    }*/
                }
            }
        }
    }
    
}
