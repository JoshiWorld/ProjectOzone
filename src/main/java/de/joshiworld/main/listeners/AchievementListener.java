package de.joshiworld.main.listeners;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.advancement.AdvancementEvent;
import org.spongepowered.api.text.Text;

/**
 *
 * @author JoshiWorld
 */
public class AchievementListener {
    
    @Listener
    public void onAchievGrant(AdvancementEvent.Grant e) {
        Player p = e.getTargetEntity();
        
        p.sendMessage(Text.of("§d" + e.getAdvancement().getName()));
    }
    
}
