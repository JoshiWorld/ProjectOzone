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
public class IslandLeaveChild implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            if(Players.loadPlayerNode(p.getName()).getNode("MemberOf") == null) {
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu bist bei keinem anderen auf der Insel"));
            } else {
                String oldLeader = Players.loadPlayerNode(p.getName()).getNode("MemberOf").getString();
                
                Players.loadPlayerNode(p.getName()).getNode("MemberOf").setValue(null);
                Players.save();
                
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu hast die Insel von §e" + oldLeader + " §cverlassen"));
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec base() {
        return CommandSpec.builder()
                .executor(new IslandLeaveChild())
                .build();
    }
}