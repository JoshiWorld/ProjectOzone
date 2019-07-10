package de.joshiworld.main.commands.home;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Players;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

/**
 *
 * @author JoshiWorld
 */
public class DelhomeCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            if(args.getOne(Text.of("home")).isPresent()) {
                String home = (String) args.getOne(Text.of("home")).get();

                if(Players.loadPlayerNode(p.getName()).getNode("homes").getChildrenMap().size() > 0) {
                    if(!Players.loadPlayerNode(p.getName()).getNode("homes", home.toLowerCase()).getChildrenMap().isEmpty()) {
                        Players.cfgNode.getNode("homes").removeChild(home.toLowerCase());
                        Players.save();

                        p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast den Home-Punkt: §e" + home.toLowerCase()+ " §agelöscht"));
                    } else {
                        p.sendMessage(Text.of(Ozone.getPrefix() + " §cDieser Home-Punkt exestiert nicht"));
                    }
                } else {
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu besitzt zurzeit keine Home-Punkte"));
                }
            } else {
                if(Players.loadPlayerNode(p.getName()).getNode("homes").getChildrenMap().size() > 0) {
                    if(!Players.loadPlayerNode(p.getName()).getNode("homes", "home").getChildrenMap().isEmpty()) {
                        Players.cfgNode.getNode("homes").removeChild("home");
                        Players.save();

                        p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast den Home-Punkt: §ehome §agelöscht"));
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
                .executor(new DelhomeCommand())
                .build();
    }
    
}
