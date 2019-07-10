package de.joshiworld.main.listeners;

import de.joshiworld.main.Ozone;
import java.util.Optional;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;

/**
 *
 * @author JoshiWorld
 */
public class ShopSignListener {
    
    @Listener
    public void onShopSignGet(InteractBlockEvent.Secondary.MainHand e) {
        if(e.getSource() instanceof Player) {
            Player p = (Player) e.getSource();
        
            if(Ozone.shopsign.containsKey(p)) {
                if(e.getTargetBlock().getLocation().get().hasTileEntity()) {
                    Optional<TileEntity> tile = e.getTargetBlock().getLocation().get().getTileEntity();

                    if(tile.get() instanceof Sign) {
                        Sign s = (Sign) tile.get();

                        if(s.lines().get(0).equals(Text.of("§4§nAdmin-Sell")) || s.lines().get(0).equals(Text.of("§4§nAdmin-Buy"))) {
                            Ozone.shopsign.put(p, s.getLocation());

                            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast ein Sign ausgewählt"));
                        } else {
                            p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu kannst nur ein Adminshop-Sign auswählen!"));
                        }
                    }
                }
            }
        }
    }
    
}
