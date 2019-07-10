package de.joshiworld.main.listeners;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Config;
import de.joshiworld.main.config.Score;
import java.util.concurrent.TimeUnit;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.serializer.TextSerializers;

/**
 *
 * @author JoshiWorld
 */
public class PlayerQuitListener {
    
    @Listener
    public void onQuit(ClientConnectionEvent.Disconnect e) {
        if(e.getSource() instanceof Player) {
            Player p = (Player) e.getSource();
            
            String prefixe = Config.cfgNode.getNode("Prefixe", Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup()).getString();
            String prefix = Config.cfgNode.getNode("Prefix", Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup()).getString();
            
            e.setMessage(TextSerializers.FORMATTING_CODE.deserialize("&c« " + prefixe + " " + prefix + p.getName() + " &c«"));
            
            if(Sponge.getServer().getOnlinePlayers().size() < 2) {
                Sponge.getScheduler().getScheduledTasks(Ozone.getPlugin()).stream().forEach(tasks -> {
                    tasks.cancel();
                });
            }
            
            Sponge.getScheduler().createTaskBuilder().execute(() -> {
                Sponge.getServer().getOnlinePlayers().stream().forEach(all -> {
                    Score.setScore(all);
                });
            }).delay(250, TimeUnit.MILLISECONDS).name(p.getName() + ":disconnect").submit(Ozone.getPlugin());
        }
    }
    
}
