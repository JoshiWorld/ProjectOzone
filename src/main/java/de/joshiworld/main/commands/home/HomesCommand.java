package de.joshiworld.main.commands.home;

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
public class HomesCommand implements CommandExecutor {
    
    String msg = "";

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            if(Players.loadPlayerNode(p.getName()).getNode("homes").getChildrenMap().size() > 0) {
                Players.loadPlayerNode(p.getName()).getNode("homes").getChildrenMap().keySet().stream().forEach(homes -> {
                    msg = msg + "§e" + homes.toString().toLowerCase() + "§7, ";
                });
                
                msg = msg.substring(0, msg.length() - 2);
                
                p.sendMessage(Text.of(Ozone.getPrefix() + " §aDeine Home-Punkte: " + msg));
                msg = "";
            } else {
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu besitzt zurzeit keine Home-Punkte"));
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .executor(new HomesCommand())
                .build();
    }
    
}
