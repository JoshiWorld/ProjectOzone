package de.joshiworld.main.listeners;

import de.joshiworld.main.config.Score;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;

/**
 *
 * @author JoshiWorld
 */
public class PlayerCommandListener {
    
    @Listener
    public void onPreCommand(SendCommandEvent e) {
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
    }
    
}
