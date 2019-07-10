package de.joshiworld.main.listeners;

import de.joshiworld.main.Ozone;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.text.Text;

/**
 *
 * @author JoshiWorld
 */
public class WarpCancelListener {
    
    @Listener
    public void onWarpDmg(DamageEntityEvent e) {
        if(e.getTargetEntity() instanceof Player) {
            Player p = (Player) e.getTargetEntity();
            
            if(Ozone.teleport.containsKey(p)) {
                Ozone.teleport.get(p).cancel();
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu hast Schaden bekommen. Deine Teleportation wurde abgebrochen!"));
                Ozone.teleport.remove(p);
            }
        }
    }
    
    @Listener
    public void onWarpMove(MoveEntityEvent e) {
        if(e.getSource() instanceof Player) {
            Player p = (Player) e.getSource();
            
            if(Ozone.teleport.containsKey(p)) {
                Ozone.teleport.get(p).cancel();
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu hast dich bewegt. Deine Teleportation wurde abgebrochen!"));
                Ozone.teleport.remove(p);
            }
        }
    }
    
}
