package de.joshiworld.main.listeners;

import de.joshiworld.main.Ozone;
import java.util.concurrent.TimeUnit;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.scheduler.Task;

/**
 *
 * @author JoshiWorld
 */
public class ChangeWorldListener {
    
    @Listener
    public void onChangeWorld(MoveEntityEvent.Teleport e) {
        if(e.getTargetEntity() instanceof Player) {
            Player p = (Player) e.getTargetEntity();
            
            String world = p.getWorld().getName();
            
            Sponge.getCommandManager().process(p.getCommandSource().get(), "inventory kit " + p.getName().toUpperCase() + "_" + p.getWorld().getName().toUpperCase());
            //Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory delete " + p.getName().toUpperCase() + "_" + p.getWorld().getName().toUpperCase());
            //Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory delete --confirm ");
            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory create " + p.getName().toUpperCase() + "_" + p.getWorld().getName().toUpperCase());
            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory add " + p.getWorld().getName() + " " + p.getName().toUpperCase() + "_" + p.getWorld().getName().toUpperCase());
            
            Task.builder().execute(() -> {
                if(!world.equalsIgnoreCase(p.getWorld().getName())) {
                    if(p.getWorld().getName().equalsIgnoreCase("bodenwelt") || p.getWorld().getName().equalsIgnoreCase("skyblock")) {
                        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory clear " + p.getName());
                        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory clear --confirm");
                        Sponge.getCommandManager().process(p.getCommandSource().get(), "inventory get " + p.getName().toUpperCase() + "_" + p.getWorld().getName().toUpperCase());
                    }
                }
            }).delay(250, TimeUnit.MILLISECONDS).name(p.getName() + "_teleportWorld").submit(Ozone.getPlugin());
        }
    }
    
    @Listener
    public void onFallDown(MoveEntityEvent e) {
        if(e.getTargetEntity() instanceof Player) {
            Player p = (Player) e.getTargetEntity();
            
            if(p.getLocation().getY() < 30) {
                Sponge.getCommandManager().process(p.getCommandSource().get(), "spawn");
            }
        }
    }
    
}
