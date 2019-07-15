package de.joshiworld.main.commands.island;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Players;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

/**
 *
 * @author JoshiWorld
 */
public class IslandCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            if(p.getWorld().getName().equalsIgnoreCase("skyblock") || p.getWorld().getName().equalsIgnoreCase("nether")) {
                if(Players.loadPlayerNode(p.getName()).getNode("Island").getBoolean() == true) {
                    Location loc = new Location(
                            Sponge.getServer().getWorld(Players.loadPlayerNode(p.getName()).getNode("Island-Location", "world").getString()).get(), 
                            Players.loadPlayerNode(p.getName()).getNode("Island-Location", "x").getDouble(), 
                            Players.loadPlayerNode(p.getName()).getNode("Island-Location", "y").getDouble(), 
                            Players.loadPlayerNode(p.getName()).getNode("Island-Location", "z").getDouble());

                    p.transferToWorld(Sponge.getServer().getWorld(Players.loadPlayerNode(p.getName()).getNode("Island-Location", "world").getString()).get());
                    p.setLocationSafely(loc);
                } else {
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu hast noch keine Insel. Mache §e/island create §cum eine Insel zu erstellen"));
                }
            } else {
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu musst in der Skywelt sein! /skyblock"));
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .child(IslandCreateChild.base(), "create")
                .child(IslandTeleportChild.base(), "teleport")
                .executor(new IslandCommand())
                .build();
    }
    
}
