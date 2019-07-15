package de.joshiworld.main.commands.island;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Players;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

/**
 *
 * @author JoshiWorld
 */
public class IslandSetspawnChild implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            if(p.getWorld().getName().equalsIgnoreCase("skyblock")) {
                if(Players.loadPlayerNode(p.getName()).getNode("Island").getBoolean() == false || Players.loadPlayerNode(p.getName()).getNode("Island").getValue() == null) {
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu besitzt keine Insel. Mache /island create"));
                } else {
                    Players.cfgNode.getNode("Island").setValue(true);

                    Players.cfgNode.getNode("Island-Location", "world").setValue(p.getWorld().getName());
                    Players.cfgNode.getNode("Island-Location", "x").setValue(p.getLocation().getX());
                    Players.cfgNode.getNode("Island-Location", "y").setValue(p.getLocation().getY());
                    Players.cfgNode.getNode("Island-Location", "z").setValue(p.getLocation().getZ());

                    Players.save();
                    
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDein Insel-Spawn wurde neu gesetzt"));
                }
            } else {
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu musst in der Skywelt sein! /skyblock"));
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec base() {
        return CommandSpec.builder()
                .executor(new IslandSetspawnChild())
                .build();
    }
    
}
