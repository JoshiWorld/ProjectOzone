package de.joshiworld.main.listeners;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Score;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;

/**
 *
 * @author JoshiWorld
 */
public class PlayerCommandListener {
    
    @Listener
    public void onPreCommand(SendCommandEvent e) {
        System.out.println(e.getCommand() + " " + e.getArguments());
        
        if(e.getCommand().equalsIgnoreCase("luckperms") || e.getCommand().equalsIgnoreCase("lp")) {
            if(e.getArguments().contains("user") && e.getArguments().contains("parent set")) {
                Sponge.getServer().getOnlinePlayers().stream().forEach(all -> {
                    Score.setScore(all);
                });
                
                Sponge.getCommandManager().process((CommandSource) e.getSource(), "update");
            } else if(e.getArguments().contains("user") && e.getArguments().contains("promote")) {
                Sponge.getServer().getOnlinePlayers().stream().forEach(all -> {
                    Score.setScore(all);
                });
                
                Sponge.getCommandManager().process((CommandSource) e.getSource(), "update");
            } else if(e.getArguments().contains("user") && e.getArguments().contains("demote")) {
                Sponge.getServer().getOnlinePlayers().stream().forEach(all -> {
                    Score.setScore(all);
                });
                
                Sponge.getCommandManager().process((CommandSource) e.getSource(), "update");
            }
        }
        
        
        if(e.getSource() instanceof Player) {
            Player p = (Player) e.getSource();
            
            if(!Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup().equalsIgnoreCase("admin")) {
                if(e.getCommand().equalsIgnoreCase("inventory") || e.getCommand().equalsIgnoreCase("inv")) {
                    if(e.getArguments().equalsIgnoreCase("get " + p.getName().toUpperCase() + "_" + p.getWorld().getName().toUpperCase())) {
                        e.setCancelled(false);
                    } else if(e.getArguments().equalsIgnoreCase("kit " + p.getName().toUpperCase() + "_" + p.getWorld().getName().toUpperCase())) {
                        e.setCancelled(false);
                    } else {
                        e.setCancelled(true);
                    }
                    
                    e.setResult(CommandResult.empty());
                }
            }
        } else {
            e.setCancelled(false);
        }
    }
    
}
