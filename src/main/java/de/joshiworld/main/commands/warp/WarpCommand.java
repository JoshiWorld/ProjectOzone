package de.joshiworld.main.commands.warp;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Warps;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.effect.sound.SoundCategories;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

/**
 *
 * @author JoshiWorld
 */
public class WarpCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            String warp = (String) args.getOne(Text.of("warp")).get();
            
            if(Warps.getWarpExists(warp.toLowerCase())) {
                Location loc = new Location(
                        Sponge.getGame().getServer().getWorld(Warps.getNode(warp.toLowerCase()).getNode("world").getString()).get(), 
                        Warps.getNode(warp.toLowerCase()).getNode("x").getDouble(), 
                        Warps.getNode(warp.toLowerCase()).getNode("y").getDouble(), 
                        Warps.getNode(warp.toLowerCase()).getNode("z").getDouble());
                
                if(!Ozone.teleport.containsKey(p)) {
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu wirst in 5 Sekunden teleportiert.."));
                    
                    Ozone.teleport.put(p, Task.builder().execute(() -> {
                        Ozone.teleport.remove(p);
                        
                        if(!p.getWorld().getName().equalsIgnoreCase(Warps.getNode(warp.toLowerCase()).getNode("world").getString())) {
                            p.transferToWorld(Sponge.getGame().getServer().getWorld(Warps.getNode(warp.toLowerCase()).getNode("world").getString()).get());
                        }

                        p.setLocation(loc);
                        
                        p.playSound(SoundTypes.ENTITY_PLAYER_LEVELUP, SoundCategories.HOSTILE, p.getLocation().getPosition(), 1);
                    }).delay(5, TimeUnit.SECONDS).name(p.getName() + "_warp_" + warp.toLowerCase()).submit(Ozone.getPlugin()));
                } else {
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu kannst dich nicht 2x zur gleichen Zeit teleportieren"));
                }
            } else {
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDer Warp: §e§l" + warp.toLowerCase() + " §cexestiert nicht"));
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        Map<String, Object> choices = new HashMap<>();
        
        for(File warp : Warps.getWarps()) {
            choices.put(warp.getName().replace(".conf", ""), warp.getName().replace(".conf", ""));
        }
        
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.choices(Text.of("warp"), choices))))
                .executor(new WarpCommand())
                .build();
    }
    
}
