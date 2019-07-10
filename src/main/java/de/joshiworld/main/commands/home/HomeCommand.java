package de.joshiworld.main.commands.home;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Players;
import java.util.concurrent.TimeUnit;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

/**
 *
 * @author JoshiWorld
 */
public class HomeCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            if(args.getOne(Text.of("home")).isPresent()) {
                String home = (String) args.getOne(Text.of("home")).get();

                if(Players.loadPlayerNode(p.getName()).getNode("homes").getChildrenMap().size() > 0) {
                    if(!Players.loadPlayerNode(p.getName()).getNode("homes", home.toLowerCase()).getChildrenMap().isEmpty()) {
                        Location loc = new Location(
                                Sponge.getServer().getWorld(Players.loadPlayerNode(p.getName()).getNode("homes", home.toLowerCase(), "world").getString()).get(), 
                                Players.loadPlayerNode(p.getName()).getNode("homes", home.toLowerCase(), "x").getDouble(), 
                                Players.loadPlayerNode(p.getName()).getNode("homes", home.toLowerCase(), "y").getDouble(), 
                                Players.loadPlayerNode(p.getName()).getNode("homes", home.toLowerCase(), "z").getDouble());

                        if(!Ozone.teleport.containsKey(p)) {
                            p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu wirst in 5 Sekunden teleportiert.."));

                            Ozone.teleport.put(p, Task.builder().execute(() -> {
                                Ozone.teleport.remove(p);

                                if(!p.getWorld().getName().equalsIgnoreCase(Players.loadPlayerNode(p.getName()).getNode("homes", home.toLowerCase(), "world").getString())) {
                                    p.transferToWorld(Sponge.getServer().getWorld(Players.loadPlayerNode(p.getName()).getNode("homes", home.toLowerCase(), "world").getString()).get());
                                }

                                p.setLocation(loc);
                            }).delay(5, TimeUnit.SECONDS).name(p.getName() + "_home_" + home.toLowerCase()).submit(Ozone.getPlugin()));
                        } else {
                            p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu kannst dich nicht 2x zur gleichen Zeit teleportieren"));
                        }
                    } else {
                        p.sendMessage(Text.of(Ozone.getPrefix() + " §cDieser Home-Punkt exestiert nicht"));
                    }
                } else {
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu besitzt zurzeit keine Home-Punkte"));
                }
            } else {
                if(Players.loadPlayerNode(p.getName()).getNode("homes").getChildrenMap().size() > 0) {
                    if(!Players.loadPlayerNode(p.getName()).getNode("homes", "home").getChildrenMap().isEmpty()) {
                        Location loc = new Location(
                                Sponge.getServer().getWorld(Players.loadPlayerNode(p.getName()).getNode("homes", "home", "world").getString()).get(), 
                                Players.loadPlayerNode(p.getName()).getNode("homes", "home", "x").getDouble(), 
                                Players.loadPlayerNode(p.getName()).getNode("homes", "home", "y").getDouble(), 
                                Players.loadPlayerNode(p.getName()).getNode("homes", "home", "z").getDouble());

                        if(!Ozone.teleport.containsKey(p)) {
                            p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu wirst in 5 Sekunden teleportiert.."));

                            Ozone.teleport.put(p, Task.builder().execute(() -> {
                                Ozone.teleport.remove(p);

                                if(!p.getWorld().getName().equalsIgnoreCase(Players.loadPlayerNode(p.getName()).getNode("homes", "home", "world").getString())) {
                                    p.transferToWorld(Sponge.getServer().getWorld(Players.loadPlayerNode(p.getName()).getNode("homes", "home", "world").getString()).get());
                                }

                                p.setLocation(loc);
                            }).delay(5, TimeUnit.SECONDS).name(p.getName() + "_home_" + "home").submit(Ozone.getPlugin()));
                        } else {
                            p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu kannst dich nicht 2x zur gleichen Zeit teleportieren"));
                        }
                    } else {
                        p.sendMessage(Text.of(Ozone.getPrefix() + " §cDieser Home-Punkt exestiert nicht"));
                    }
                } else {
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu besitzt zurzeit keine Home-Punkte"));
                }
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.optional(GenericArguments.string(Text.of("home"))))))
                .executor(new HomeCommand())
                .build();
    }
    
}
